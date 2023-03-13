plugins {
    id("com.android.library")
    id("kotlin-android")
    id("kotlin-parcelize")
}

commonAndroidConfig()

dependencies {
    implementation(Dependencies.Testing.coroutinesTest)
    testImplementation(project(Modules.Libs.TestFixtures))
}
