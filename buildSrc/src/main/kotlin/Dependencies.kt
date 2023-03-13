import BuildPlugins.Versions.depGraphVersion

// Kotlin
private const val kotlinVersion = "1.8.0"
private const val coroutinesVersion = "1.6.4"
private const val ktlintVersion = "0.43.2"
private const val kotlinxSerializationVersion = "1.4.1"
private const val collectionsImmutableVersion = "0.3.5"

// DI
private const val koinVersion = "3.2.2"

// API - Square
private const val retrofitVersion = "2.9.0"
private const val loggingInterceptorVersion = "4.10.0"
private const val serializationConverterVersion = "0.8.0"
private const val leakCanaryVersion = "2.9.1"

// AndroidX
private const val splashScreenVersion = "1.0.0"
private const val lifecycleVersion = "2.5.1"
private const val pagingRuntimeVersion = "3.1.1"


// Compose
private const val composeBomVersion = "2023.01.00"
const val composeCompilerVersion = "1.4.0"
private const val activityComposeVersion = "1.6.1"
private const val composeNavigationVersion = "2.5.3"
private const val accompanistVersion = "0.28.0"
private const val customViewVersion = "1.2.0-alpha01"
private const val customViewPoolingContainerVersion = "1.0.0"
private const val pagingComposeVersion = "1.0.0-alpha18"


// Other
private const val timberVersion = "5.0.1"
private const val coilVersion = "2.2.2"
private const val twitterComposeRulesVersion = "0.0.26"

// Test
private const val jUnitVersion = "4.13.2"
private const val jUnitAndroidVersion = "1.1.5"
private const val mockkVersion = "1.13.4"
private const val espressoVersion = "3.5.1"

object BuildPlugins {
    object Versions {
        const val agpMajor = 7
        const val agpMinor = ".2.2"
        const val androidGradle = "$agpMajor$agpMinor"
        const val androidMaven = "2.1"
        const val depGraphVersion = "0.10"
    }
    const val androidGradle = "com.android.tools.build:gradle:${Versions.androidGradle}"
    const val androidMaven = "com.github.dcendents:android-maven-gradle-plugin:${Versions.androidMaven}"
    const val kotlinGradlePlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinVersion"
    const val kotlinSerializationPlugin = "org.jetbrains.kotlin:kotlin-serialization:$kotlinVersion"
    const val depGraph = "com.savvasdalkitsis:module-dependency-graph:$depGraphVersion"
    const val depGraphPlugin = "com.savvasdalkitsis.module-dependency-graph"
}

object Dependencies {

    object Kotlin {
        const val kotlin = "org.jetbrains.kotlin:kotlin-stdlib:$kotlinVersion"
        const val kotlinStdLib = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:$kotlinVersion"
        const val kotlinReflect = "org.jetbrains.kotlin:kotlin-reflect:$kotlinVersion"
        const val coroutines = "org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion"
        const val coroutinesAndroid = "org.jetbrains.kotlinx:kotlinx-coroutines-android:$coroutinesVersion"
        const val coroutinesKtx = "org.jetbrains.kotlinx:kotlinx-coroutines-play-services:$coroutinesVersion"
        const val coroutinesDebug = "org.jetbrains.kotlinx:kotlinx-coroutines-debug:$coroutinesVersion"
        const val kotlinxSerialization = "org.jetbrains.kotlinx:kotlinx-serialization-json:$kotlinxSerializationVersion"
        const val collectionsImmutable = "org.jetbrains.kotlinx:kotlinx-collections-immutable:$collectionsImmutableVersion"
        const val ktlint = "com.github.shyiko:ktlint:$ktlintVersion"
    }

    object Di {
        const val koin = "io.insert-koin:koin-android:$koinVersion"
        const val koinCompose = "io.insert-koin:koin-androidx-compose:$koinVersion"
    }

    object Database {
        private object Versions {
            const val room = "2.5.0"
            const val datastore = "1.0.0"
        }
        const val roomRuntime = "androidx.room:room-runtime:${Versions.room}"
        const val roomCompiler = "androidx.room:room-compiler:${Versions.room}"
        const val roomKtx = "androidx.room:room-ktx:${Versions.room}"
        const val roomTest = "androidx.room:room-testing:${Versions.room}"
        const val roomPaging = "androidx.room:room-paging:${Versions.room}"
    }

    object SupportLibs {
        const val splashScreen = "androidx.core:core-splashscreen:$splashScreenVersion"
        const val lifecycleRuntime = "androidx.lifecycle:lifecycle-runtime-ktx:$lifecycleVersion"
        const val pagingRuntime = "androidx.paging:paging-runtime-ktx:${pagingRuntimeVersion}"
    }

    object Libraries {
        const val retrofit = "com.squareup.retrofit2:retrofit:$retrofitVersion"
        const val retrofitSerialization = "com.jakewharton.retrofit:retrofit2-kotlinx-serialization-converter:$serializationConverterVersion"
        const val loggingInterceptor = "com.squareup.okhttp3:logging-interceptor:$loggingInterceptorVersion"
        const val timber = "com.jakewharton.timber:timber:$timberVersion"
        const val coil = "io.coil-kt:coil:$coilVersion"
        const val coilSvg = "io.coil-kt:coil-svg:$coilVersion"
        const val coilCompose = "io.coil-kt:coil-compose:$coilVersion"
        const val leakCanary = "com.squareup.leakcanary:leakcanary-android:$leakCanaryVersion"
    }

    object Compose {
        const val bom = "androidx.compose:compose-bom:$composeBomVersion"
        const val ui = "androidx.compose.ui:ui"
        const val material3 = "androidx.compose.material3:material3"
        const val materialIconsExtended = "androidx.compose.material:material-icons-extended"
        const val uiTooling = "androidx.compose.ui:ui-tooling"
        const val uiToolingPreview = "androidx.compose.ui:ui-tooling-preview"
        const val customView = "androidx.customview:customview:$customViewVersion"
        const val customViewPoolingContainer = "androidx.customview:customview-poolingcontainer:$customViewPoolingContainerVersion"
        const val animation = "androidx.compose.animation:animation"
        const val activityCompose = "androidx.activity:activity-compose:$activityComposeVersion"
        const val navigation = "androidx.navigation:navigation-compose:$composeNavigationVersion"
        const val accompanistNavigationAnimation = "com.google.accompanist:accompanist-navigation-animation:$accompanistVersion"
        const val googleFonts = "androidx.compose.ui:ui-text-google-fonts"
        const val accompanistPager = "com.google.accompanist:accompanist-pager:$accompanistVersion"
        const val accompanistPagerIndicators = "com.google.accompanist:accompanist-pager-indicators:$accompanistVersion"
        const val accompanistSystemUiController = "com.google.accompanist:accompanist-systemuicontroller:$accompanistVersion"
        const val pagingCompose = "androidx.paging:paging-compose:$pagingComposeVersion"
    }

    object Testing {
        const val jUnit = "junit:junit:$jUnitVersion"
        const val jUnitAndroid = "androidx.test.ext:junit:$jUnitAndroidVersion"
        const val mockk = "io.mockk:mockk:$mockkVersion"
        const val coroutinesTest = "org.jetbrains.kotlinx:kotlinx-coroutines-test:$coroutinesVersion"
        const val espressoCore = "androidx.test.espresso:espresso-core:$espressoVersion"
        const val composeAndroidTest = "androidx.compose.ui:ui-test-junit4"
        const val pagingTest = "androidx.paging:paging-common:$pagingRuntimeVersion"

    }
}

object DependencyGroups {
    val commonKotlin = listOf(
        Dependencies.Kotlin.kotlin,
        Dependencies.Kotlin.kotlinStdLib,
        Dependencies.Kotlin.coroutines,
        Dependencies.Kotlin.coroutinesKtx,
        Dependencies.Kotlin.collectionsImmutable,
    )

    val commonAndroid = listOf(
        Dependencies.Kotlin.coroutinesAndroid,
        Dependencies.Di.koin,
        Dependencies.Libraries.timber
    ) + commonKotlin

    val commonTest = listOf(
        Dependencies.Testing.jUnit,
        Dependencies.Testing.coroutinesTest
    )
    val commonAndroidTest = listOf(
        Dependencies.Testing.jUnitAndroid,
        Dependencies.Testing.espressoCore
    )

    val compose = listOf(
        Dependencies.Compose.ui,
        Dependencies.Compose.material3,
        Dependencies.Compose.materialIconsExtended,
        Dependencies.Compose.uiToolingPreview,
        Dependencies.Compose.animation,
        Dependencies.Compose.activityCompose,
        Dependencies.Compose.navigation,
        Dependencies.Compose.accompanistNavigationAnimation,
        Dependencies.Compose.pagingCompose
    )

    val network = listOf(
        Dependencies.Libraries.retrofit,
        Dependencies.Libraries.retrofitSerialization,
        Dependencies.Libraries.loggingInterceptor,
        Dependencies.Kotlin.kotlinxSerialization
    )

    val database = listOf(
        Dependencies.Database.roomPaging,
        Dependencies.Database.roomKtx,
        Dependencies.Database.roomRuntime,
    )

    val diUi = listOf(
        Dependencies.Di.koin,
        Dependencies.Di.koinCompose,
    )
}

object Modules {
    const val App = ":app"
    const val Core = ":core"

    object Libs {
        const val CommonsUi = ":libs:commonsui"
        const val TestFixtures = ":libs:testfixtures"
    }

    val libsModules = listOf(
        Libs.CommonsUi,
    )
}
