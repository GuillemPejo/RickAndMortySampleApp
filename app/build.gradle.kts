plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-parcelize")
    id("kotlin-kapt")
    id("kotlinx-serialization")
    id("org.jetbrains.kotlin.plugin.serialization")
}

commonAndroidConfig()

android {
    defaultConfig {
        applicationId = AndroidSdk.applicationId

        javaCompileOptions {
            annotationProcessorOptions {
                arguments["room.schemaLocation"] = "$projectDir/schemas"
            }
        }
    }
    setupCompose()

}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))

    modules(listOf(Modules.Core, Modules.Libs.CommonsUi))

    implementations(DependencyGroups.diUi)
    implementations(DependencyGroups.network)
    implementation(Dependencies.Libraries.coil)
    implementation(Dependencies.Libraries.coilCompose)
    implementation(Dependencies.SupportLibs.splashScreen)
    implementation(Dependencies.SupportLibs.pagingRuntime)

    implementation(platform(Dependencies.Compose.bom))
    implementations(DependencyGroups.compose)
    debugImplementation(Dependencies.Compose.uiTooling)

    testImplementation(Dependencies.Testing.mockk)
    testImplementation(Dependencies.Testing.pagingTest)
    testImplementation(project(Modules.Libs.TestFixtures))

    androidTestImplementation(project(Modules.Libs.TestFixtures))
    androidTestImplementation(Dependencies.Testing.composeAndroidTest)

    debugImplementation(Dependencies.Libraries.leakCanary)
    implementation(Dependencies.Kotlin.kotlinxSerialization)

    kapt(Dependencies.Database.roomCompiler)
    implementations(DependencyGroups.database)
    testImplementation(Dependencies.Database.roomTest)
    androidTestImplementation(Dependencies.Testing.coroutinesTest)
}
