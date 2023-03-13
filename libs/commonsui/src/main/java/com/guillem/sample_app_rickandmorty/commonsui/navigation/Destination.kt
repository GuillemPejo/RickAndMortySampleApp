package com.guillem.sample_app_rickandmorty.commonsui.navigation

import android.os.Bundle
import androidx.compose.runtime.Composable
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.guillem.sample_app_rickandmorty.commonsui.navigation.transition.NavTransition
import java.net.URLDecoder
import java.net.URLEncoder
import java.nio.charset.Charset
import java.nio.charset.StandardCharsets
import kotlin.reflect.KClass
import kotlin.reflect.KParameter
import kotlin.reflect.KProperty1
import kotlin.reflect.KType
import kotlin.reflect.full.isSubclassOf
import kotlin.reflect.full.memberProperties
import kotlin.reflect.full.primaryConstructor
import kotlin.reflect.typeOf
import org.koin.core.scope.Scope

@Suppress("TooGenericExceptionThrown")
sealed class NavEntry {
    private val pathRoot: String = this::class.simpleName.let { className ->
        requireNotNull(className) { "Destinations should not be anonymous classes" }
        className
    }

    protected open val serializers: Map<KType, (Any) -> String?> = emptyMap()

    fun build(transition: NavTransition = NavTransition.Default): String =
        (getArguments() + Argument.NonNullable(transition)).buildRoute()

    fun buildIdentifier(): String = (this::class as KClass<Any>).let { thisClass ->
        (thisClass.primaryConstructor?.parameters ?: emptyList()).mapNotNull { parameter ->
            parameter.name?.let { name ->
                thisClass.memberProperties.firstOrNull { it.name == name }?.get(this)
                    ?.let { value ->
                        name to value
                    }
            }
        }
    }.fold(pathRoot) { acc, (name, value) -> "$acc, $name:$value" }
        .let { "{$it}" }

    private fun List<Argument>.buildRoute(): String = fold(pathRoot) { accPath, argument ->
        "$accPath${argument.toRoute()}"
    }

    private fun getArguments(): List<Argument> =
        (this::class as KClass<Any>).let { thisClass ->
            (thisClass.primaryConstructor?.parameters ?: emptyList()).mapNotNull { parameter ->
                parameter.name?.let { name ->
                    thisClass.memberProperties.firstOrNull { it.name == name }
                }?.let { property ->
                    Argument.from(property, property.get(this), serializers)
                }
            }
        }

    private sealed class Argument {
        protected abstract val value: Any?
        protected val encodedValue: String
            get() = (value?.let { serializer?.invoke(it) } ?: value.toContentString()).encode()

        protected abstract val serializer: ((Any) -> String?)?

        abstract fun toRoute(): String

        data class NonNullable(
            override val value: Any?,
            override val serializer: ((Any) -> String?)? = null,
        ) : Argument() {
            override fun toRoute(): String = "/$encodedValue"
        }
        data class Nullable(
            val name: String,
            override val value: Any?,
            override val serializer: ((Any) -> String?)? = null,
        ) : Argument() {
            override fun toRoute(): String = "?$name=$encodedValue"
        }

        companion object {
            fun from(
                property: KProperty1<Any, *>,
                value: Any?,
                serializers: Map<KType, (Any) -> String?> = emptyMap(),
            ): Argument =
                if (property.returnType.isMarkedNullable) {
                    Nullable(property.name, value, serializers[property.returnType])
                } else {
                    NonNullable(value, serializers[property.returnType])
                }
        }
    }
}

@Suppress("UnnecessaryAbstractClass")
abstract class Subgraph : NavEntry()

abstract class Destination<OuterArgs> : NavEntry() {
    @Composable
    abstract fun Content(navEntry: NavBackStackEntry, diScope: Scope, outerArgs: OuterArgs)
}

@Suppress("SpreadOperator")
open class DestinationDeclaration<Dest : NavEntry>(private val destinationClass: KClass<Dest>) {
    private val pathRoot: String get() = destinationClass.simpleName.let { className ->
        requireNotNull(className) { "Destinations should not be anonymous classes" }
        className
    }

    val route: String = destinationClass.primaryConstructor?.parameters
        ?.fold(pathRoot) { acc, property ->
            "$acc${property.paramAsRouteDeclaration()}"
        } + NavTransitionRouteArg

    val arguments: List<NamedNavArgument> =
        (destinationClass.primaryConstructor?.parameters ?: emptyList()).mapNotNull { property ->
            property.buildNavArgument()
        } + (NavTransition.ARG_KEY to NavType.StringType).buildNavArgument()

    open val deserializers: Map<KType, (String) -> Any?> = emptyMap()

    fun from(bundle: Bundle?): Dest {
        val args = (destinationClass.primaryConstructor?.parameters ?: emptyList())
            .map { property ->
                val stringValue = bundle?.getString(property.name).decode()
                deserializers[property.type]?.let { deserializer ->
                    deserializer(stringValue)
                } ?: property.buildFromString(stringValue)
            }
        return destinationClass.constructors.first().call(*args.toTypedArray())
    }

    @Suppress("CyclomaticComplexMethod")
    private fun KParameter.buildFromString(
        stringValue: String
    ): Any? = when {
        type == typeOf<String>() -> stringValue
        type == typeOf<Boolean>() -> stringValue.toBoolean()
        type == typeOf<Int>() -> stringValue.toInt()
        type == typeOf<Long>() -> stringValue.toLong()
        type == typeOf<Float>() -> stringValue.toFloat()
        isEnum() -> buildEnum(stringValue)
        type == typeOf<String?>() -> stringValue.toNullable()
        type == typeOf<Boolean?>() -> stringValue.toNullable()?.toBoolean()
        type == typeOf<Int?>() -> stringValue.toNullable()?.toInt()
        type == typeOf<Long?>() -> stringValue.toNullable()?.toLong()
        type == typeOf<Float?>() -> stringValue.toNullable()?.toFloat()
        type == typeOf<Array<String>>() -> stringValue.toArray(String::toString)
        type == typeOf<Array<Boolean>>() -> stringValue.toArray(String::toBoolean)
        type == typeOf<Array<Int>>() -> stringValue.toArray(String::toInt)
        type == typeOf<Array<Long>>() -> stringValue.toArray(String::toLong)
        type == typeOf<Array<Float>>() -> stringValue.toArray(String::toFloat)
        else -> throw IllegalStateException("Unexpected type in nav destination. Property: $this")
    }

    private fun KParameter.paramAsRouteDeclaration() =
        if (type.isMarkedNullable) "?$name={$name}" else "/{$name}"

    private fun KParameter.isEnum(): Boolean =
        (type.classifier as? KClass<*>)?.isSubclassOf(Enum::class) == true

    private fun KParameter.buildEnum(stringValue: String): Enum<*>? {
        val enumClassName = (type.classifier as? KClass<*>)?.qualifiedName
            ?: throw IllegalStateException("Could not get enum class name for property: $this")
        return Class.forName(enumClassName)
            .enumConstants
            .filterIsInstance(Enum::class.java)
            .firstOrNull { it.name == stringValue.toNullable() }
    }

    private fun KParameter.buildNavArgument(): NamedNavArgument? = name?.let { name ->
        (name to NavType.StringType).buildNavArgument(isNullable = type.isMarkedNullable)
    }

    private fun Pair<String, NavType<*>>.buildNavArgument(
        isNullable: Boolean = false,
    ) = navArgument(first) {
        type = second
        nullable = isNullable
    }
}
open class SubgraphDeclaration<Dest : NavEntry>(
    destinationClass: KClass<Dest>,
    val startDestination: DestinationDeclaration<*>,
) : DestinationDeclaration<Dest>(destinationClass)

private fun String.toNullable(): String? = if (this == "null") null else this

private fun String?.encode(charset: Charset = StandardCharsets.UTF_8): String =
    URLEncoder.encode(this, charset.toString())

private fun String?.decode(charset: Charset = StandardCharsets.UTF_8): String =
    URLDecoder.decode(this, charset.toString())

private fun Any?.toContentString(): String =
    if (this is Array<*>) contentDeepToString() else toString()

private inline fun <reified T> String.toArray(onParse: String.() -> T): Array<T> =
    drop(1).dropLast(1).split(", ").map { it.onParse() }.toTypedArray()

private const val NavTransitionRouteArg = "/{${NavTransition.ARG_KEY}}"
