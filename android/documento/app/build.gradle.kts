plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "br.com.hive.app"
    compileSdk = 35

    defaultConfig {
        applicationId = "br.com.hive.app"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        val hiveApiKey: String = project.findProperty("HIVE_SDK_API_KEY") as? String ?: ""
        buildConfigField("String", "HIVE_SDK_API_KEY", "\"$hiveApiKey\"")
    }

    buildTypes {
        // você precisa dizer qual variante da lib o app deve usar quando rodar no emulador em Debug.
        debug {
        // applicationIdSuffix = ".debug"
            matchingFallbacks.clear()
            matchingFallbacks.addAll(listOf("preprod", "homol", "release"))
        }
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        create("homol") {
            initWith(getByName("debug"))
            // applicationIdSuffix = ".homol"
            // Força o Gradle a engolir a diferença de atributos e puxar a homol da lib
            matchingFallbacks.clear()
            matchingFallbacks.addAll(listOf("homol", "release"))
        }
        create("preprod") {
            initWith(getByName("debug"))
            // applicationIdSuffix = ".preprod"
            // Força o Gradle a engolir a diferença de atributos e puxar a preprod da lib
            matchingFallbacks.clear()
            matchingFallbacks.addAll(listOf("preprod", "release"))
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    buildFeatures {
        compose = true
        viewBinding = true
        buildConfig = true
    }

    packaging {
        resources {
            excludes += setOf(
                "META-INF/DEPENDENCIES",
                "META-INF/LICENSE",
                "META-INF/LICENSE.txt",
                "META-INF/license.txt",
                "META-INF/NOTICE",
                "META-INF/NOTICE.txt",
                "META-INF/notice.txt",
                "META-INF/ASL2.0",
                "META-INF/AL2.0",
                "META-INF/LGPL2.1",
                "META-INF/*.kotlin_module"
            )
        }
        jniLibs {
            // Se houver conflito de bibliotecas nativas (.so) entre a sua lib e outras:
            pickFirsts += setOf(
                "**/libc++_shared.so",
                "**/libmediapipe_tasks_vision_jni.so",
                "**/libimage_processing_util_jni.so"
            )
            // Se o erro de strip continuar enchendo o log, você pode pedir pro Gradle ignorar:
            // keepDebugSymbols += setOf("**/*.so")
        }
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)

    //
    implementation(libs.hive.captura.documento)

    implementation(libs.okhttp)
    implementation(libs.google.material)
    implementation(libs.gson)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.viewbinding)
    implementation(libs.androidx.camera.view)
    implementation(libs.androidx.camera.core)
    implementation(libs.androidx.camera.camera2)
    implementation(libs.androidx.camera.lifecycle)
    implementation(libs.glide)
    implementation(libs.androidx.activity.ktx)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
}