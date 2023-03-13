import com.android.build.api.dsl.CommonExtension
import com.android.build.api.dsl.LibraryExtension
import com.android.build.gradle.BaseExtension
import com.android.build.gradle.internal.dsl.BaseAppModuleExtension
import com.android.build.gradle.internal.dsl.ProductFlavor
import org.gradle.api.JavaVersion
import org.gradle.api.NamedDomainObjectContainer
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import java.io.File

object AndroidSdk {
    const val min = 23
    const val target = 33
    const val compile = 33
    const val buildToolsVersion = "30.0.3"
    const val applicationId = "com.guillem.sample_app_rickandmorty"
    const val appAuthRedirectScheme = "com.guillem.sample_app_rickandmorty"
}

class Version {
    private val major = 1
    private val minor = 0
    private val patch = 0
    private val build = 0
    private val buildText = ""

    val versionCode = major * 1000000 + minor * 10000 + patch * 100 + build
    val versionName = "$major.$minor.${patch}$buildText"
}

object ConfigField {
    object Dimension {
        const val Default = "default"
    }

    object BuildTypes {
        const val Debug = "debug"
        const val Release = "release"
    }

    object Type {
        const val STRING = "String"
        const val BOOLEAN = "boolean"
    }

    object Field {
        const val BASE_URL = "BASE_URL"
    }

    enum class Flavor(
        val flavorName : String,
        val BASE_URL : String,
    ) {
        Dev(
            flavorName = "dev",
            BASE_URL = MainServerConstants.Staging.BASE_URL,
        ),
        Prod(
            flavorName = "prod",
            BASE_URL = MainServerConstants.Prod.BASE_URL,
        )
    }

    private enum class MainServerConstants(
        val BASE_URL: String,
    ) {
        Staging(
            BASE_URL = "\"https://rickandmortyapi.com/api/\"",
        ),
        Prod(
            BASE_URL = "\"https://rickandmortyapi.com/api/\"",
        ),
    }
}

fun Project.commonAndroidConfig() {
    this.extensions.getByType<BaseExtension>().androidBlock()
    project.tasks.withType(KotlinCompile::class.java).configureEach { kotlinSetup() }

    this.dependencies {
        implementations(DependencyGroups.commonAndroid)
        testImplementations(DependencyGroups.commonTest)
        androidTestImplementations(DependencyGroups.commonAndroidTest)
    }
}

fun BaseAppModuleExtension.setupCompose() {
    commonSetupCompose()
}

fun LibraryExtension.setupCompose() {
    commonSetupCompose()
}

private fun CommonExtension<*, *, *, *>.commonSetupCompose() {
    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = composeCompilerVersion
    }
}

private fun KotlinCompile.kotlinSetup() {
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_1_8.toString()
        freeCompilerArgs = listOf(
            "-Xallow-result-return-type",
            "-progressive",
            "-opt-in=kotlinx.coroutines.ExperimentalCoroutinesApi",
            "-opt-in=kotlinx.coroutines.FlowPreview",
            "-opt-in=kotlinx.serialization.ExperimentalSerializationApi",
        )
    }
}

private fun BaseExtension.androidBlock() {
    val versionConfig = Version()
    val keystoreProperties = Keystore()
    val properties = Properties()

    compileSdkVersion(AndroidSdk.compile)
    buildToolsVersion(AndroidSdk.buildToolsVersion)

    defaultConfig {
        minSdk = AndroidSdk.min
        targetSdk = AndroidSdk.target
        versionCode = versionConfig.versionCode
        versionName = versionConfig.versionName
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        manifestPlaceholders(mapOf("appAuthRedirectScheme" to AndroidSdk.appAuthRedirectScheme))
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }


    signingConfigs {
        create(ConfigField.BuildTypes.Release) {
            storeFile = File(keystoreProperties.storeFile.trim())
            storePassword = keystoreProperties.storePassword
            keyAlias = keystoreProperties.keyAlias
            keyPassword = keystoreProperties.keyPassword
        }
    }

    buildTypes {
        getByName(ConfigField.BuildTypes.Debug) {
            isDebuggable = true
            isMinifyEnabled = false
        }
        getByName(ConfigField.BuildTypes.Release) {
            isDebuggable = false
            isMinifyEnabled = true
            if (keystoreProperties.isAutoSigning) {
                signingConfig = signingConfigs.getByName(ConfigField.BuildTypes.Release)
            }
            proguardFiles(
                getDefaultProguardFile(
                    "proguard-android-optimize.txt"
                ),
                "proguard-rules.pro"
            )
        }
    }

    flavorDimensions(ConfigField.Dimension.Default)

    productFlavors {
        addFlavor(ConfigField.Flavor.Dev, properties)
        addFlavor(ConfigField.Flavor.Prod, properties)
    }

    packagingOptions {
        resources.excludes.add("META-INF/*")
    }
}

private fun NamedDomainObjectContainer<ProductFlavor>.addFlavor(
    flavor: ConfigField.Flavor,
    properties: Properties,
) {
    create(flavor.flavorName) {
        dimension(ConfigField.Dimension.Default)
        buildConfigField(
            ConfigField.Type.STRING,
            ConfigField.Field.BASE_URL,
            flavor.BASE_URL
        )
        properties.load(this, flavor)
    }
}