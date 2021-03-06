plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-kapt")
    id("kotlin-parcelize")
    kotlin("plugin.serialization") version "1.5.31"
    id("dagger.hilt.android.plugin")
}

android {
    compileSdk = 31

    defaultConfig {
        applicationId = "com.marvel.characters"
        minSdk = 21
        targetSdk = 31
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        buildConfigField("String", "BASE_URL", "\"https://gateway.marvel.com/v1/public/\"")
        buildConfigField("String", "PUBLIC_KEY", "\"XXXXXXXXXX\"")
        buildConfigField("String", "PRIVATE_KEY", "\"XXXXXXXXXX\"")
    }

    buildTypes {
        getByName("debug") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile ("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
        getByName("release") {
            isMinifyEnabled = true
            proguardFiles(getDefaultProguardFile ("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_1_8.toString()
        allWarningsAsErrors = true
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    implementation("androidx.core:core-ktx:1.6.0")
    implementation("androidx.appcompat:appcompat:1.3.1")
    implementation("com.google.android.material:material:1.4.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.1")
    implementation("androidx.swiperefreshlayout:swiperefreshlayout:1.1.0")
    implementation("androidx.palette:palette-ktx:1.0.0")
    implementation("androidx.activity:activity-ktx:1.3.1")

    //Lifecycle
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.3.1")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.3.1")
    implementation("androidx.lifecycle:lifecycle-extensions:2.2.0")

    //Dagger Hilt
    implementation("com.google.dagger:hilt-android:2.38.1")
    implementation("androidx.lifecycle:lifecycle-common-java8:2.3.1")
    kapt("com.google.dagger:hilt-compiler:2.38.1")
    kapt("androidx.hilt:hilt-compiler:1.0.0")

    //Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.1.0")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.5.1")

    //Retrofit and OkHttp
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.jakewharton.retrofit:retrofit2-kotlinx-serialization-converter:0.8.0")
    implementation("com.squareup.okhttp3:okhttp:4.9.1")
    implementation("com.squareup.okhttp3:logging-interceptor:4.9.1")

    //Glide
    implementation("com.github.bumptech.glide:glide:4.12.0")
    implementation("com.github.bumptech.glide:recyclerview-integration:4.11.0")
    kapt("com.github.bumptech.glide:compiler:4.12.0")

    //Paging
    implementation("androidx.paging:paging-runtime-ktx:3.0.1")

    //Lottie
    implementation("com.airbnb.android:lottie:4.2.0")

    //Shimmer
    implementation("com.facebook.shimmer:shimmer:0.5.0")

    //ZoomHelper
    implementation("io.github.aghajari:ZoomHelper:1.1.0")

    testImplementation("junit:junit:4.13.2")
    testImplementation("org.mockito:mockito-core:3.7.7")
    androidTestImplementation("org.mockito:mockito-android:3.7.7")
    androidTestImplementation("androidx.test.ext:junit:1.1.3")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")
    androidTestImplementation("androidx.test.espresso:espresso-contrib:3.4.0")
    androidTestImplementation("com.android.support.test.espresso:espresso-intents:3.0.2")
    androidTestImplementation("com.google.dagger:hilt-android-testing:2.38.1")
}