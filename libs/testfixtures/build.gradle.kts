plugins {
    id("com.android.library")
    id("kotlin-android")
    id("kotlinx-serialization")
}

commonAndroidConfig()

dependencies {
    modules(
        listOf(
            Modules.Core,
            Modules.Libs.CommonsUi,
        )
    )

    implementation(Dependencies.Di.koin)
    implementation(Dependencies.Kotlin.kotlinxSerialization)
    implementation(Dependencies.Testing.coroutinesTest)
    implementation(Dependencies.Testing.jUnit)
}
