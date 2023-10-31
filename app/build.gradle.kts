import com.winwang.buildsrc.Libs

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("org.jetbrains.kotlin.kapt")
    id("dagger.hilt.android.plugin")
    id("kotlin-parcelize")
}

android {
    namespace = "com.winwang.openeye"
    compileSdk = 33

    defaultConfig {
        applicationId = "com.winwang.openeye"
        minSdk = 24
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    kotlin {
        jvmToolchain(8)
    }

    buildFeatures {
        compose = true
        viewBinding = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.3"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }

}

dependencies {

//    implementation("androidx.core:core-ktx:1.9.0")
//    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.1")
//    implementation("androidx.activity:activity-compose:1.7.0")
//    implementation(platform("androidx.compose:compose-bom:2023.03.00"))
//    implementation("androidx.compose.ui:ui")
//    implementation("androidx.compose.ui:ui-graphics")
//    implementation("androidx.compose.ui:ui-tooling-preview")
//    implementation("androidx.compose.material3:material3")

    implementation(Libs.KTX.coreKtx)
    implementation(Libs.KTX.lifecycleRuntimeKtx)
    implementation(Libs.Compose.composeUI)
    implementation(Libs.Compose.composeGraphics)
    implementation(Libs.Compose.composeMaterial)
    implementation(Libs.Compose.composeUIPreview)
    implementation(Libs.Compose.composeActivity)
    implementation(Libs.Compose.composeViewBinding)
    implementation(Libs.Compose.composeConstraintlayout)
    implementation(Libs.Compose.pagingCompose)
    implementation(Libs.Compose.pagingRuntime)
    implementation(Libs.Accompanist.composeAccompanistPage)
    implementation(Libs.Accompanist.composeAccompanistCoil)
    implementation(Libs.Accompanist.composeAccompanistSystemUI)
    implementation(Libs.Hilt.HiltAndroid)
//    implementation(Libs.Hilt.HiltViewmodel)
    implementation(Libs.Androidx.livedata)
    kapt(Libs.Hilt.HiltCompiler)
    implementation(Libs.Navigation.HiltNavigationCompose)
    implementation(Libs.Navigation.NavigationCompose)
    implementation(Libs.CommonLib.coil)
    implementation(Libs.CommonLib.coilGif)
    implementation(Libs.Compose.composeLivedata)
    implementation(Libs.Kotlin.stdlib)
    implementation(Libs.Kotlin.reflect)
    implementation(Libs.CommonLib.retrofit)
    implementation(Libs.CommonLib.okhttp)
    implementation(Libs.CommonLib.okhttpInterceptor)
    implementation(Libs.CommonLib.gson)
    implementation(Libs.CommonLib.gsonFactory)
    implementation(Libs.Androidx.appcompat)
    implementation(Libs.Androidx.material)
    implementation(Libs.CommonLib.androidUtils)


    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.03.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation(Libs.Compose.uiTool)
    debugImplementation(Libs.Compose.uiToolTestManifest)
    implementation(platform("org.jetbrains.kotlin:kotlin-bom:1.8.0"))
}