plugins {
    id("com.android.library")
    id("kotlin-android")
    id("kotlin-parcelize")
    id("kotlinx-serialization")
}

commonAndroidConfig()

android {
    setupCompose()
}

dependencies {
    modules(listOf(Modules.Core))

    implementation(platform(Dependencies.Compose.bom))
    implementations(DependencyGroups.compose)
    debugImplementation(Dependencies.Compose.uiTooling)

    implementation(Dependencies.Compose.accompanistPager)
    implementation(Dependencies.Compose.accompanistPagerIndicators)
    implementation(Dependencies.Compose.accompanistSystemUiController)

    debugApi(Dependencies.Compose.customView)
    debugApi(Dependencies.Compose.customViewPoolingContainer)
    implementation(Dependencies.Compose.googleFonts)

    implementation(Dependencies.Di.koin)
    implementation(Dependencies.Di.koinCompose)
    implementation(Dependencies.Kotlin.kotlinReflect)
    implementation(Dependencies.SupportLibs.lifecycleRuntime)
    implementation(Dependencies.Kotlin.kotlinxSerialization)
    implementation(Dependencies.Libraries.coil)
}
