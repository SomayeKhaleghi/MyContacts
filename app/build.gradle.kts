import com.android.build.gradle.internal.api.ApkVariantOutputImpl

plugins {
    id("com.android.application")
}

android {
    namespace = "app.demo.mycontacts"
    compileSdk = 34

    defaultConfig {
        applicationId = "app.demo.mycontacts"
        minSdk = 18
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    splits {
        abi {
            isEnable  =   true
            reset()
            include ("x86", "x86_64", "armeabi-v7a", "arm64-v8a" )
            isUniversalApk = true
        }
    }

    val versionCodes = mapOf(
        "armeabi-v7a" to 1,
        "arm64-v8a" to 2,
        "x86" to 3,
        "x86_64" to 4
    )

    applicationVariants.all {
        val variant = this
        variant.outputs
            .map { it as com.android.build.gradle.internal.api.BaseVariantOutputImpl }
            .forEach { output ->
                output.outputFileName = "v2rayNG_" + variant.versionName + "_" + output.getFilter(com.android.build.OutputFile.ABI) + ".apk"
                val newVersionCode = (versionCodes.get(output.getFilter(com.android.build.OutputFile.ABI))
                    ?.times(1000000) ?: 100) + android.defaultConfig.versionCode!!
                (output as ApkVariantOutputImpl).versionCodeOverride = newVersionCode
            }
    }

}

dependencies {

    /* implementation("androidx.appcompat:appcompat:1.6.1")
     implementation("com.google.android.material:material:1.11.0")
     implementation("androidx.constraintlayout:constraintlayout:2.1.4")*/

    implementation("com.google.android.material:material:1.9.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    implementation ("androidx.appcompat:appcompat:1.3.1")
    implementation ("androidx.lifecycle:lifecycle-viewmodel:2.3.1")
    implementation ("androidx.lifecycle:lifecycle-livedata:2.3.1")
    implementation ("androidx.lifecycle:lifecycle-extensions:2.2.0")
    implementation ("androidx.room:room-runtime:2.3.0")
    annotationProcessor ("androidx.room:room-compiler:2.3.0")

    implementation ("com.intuit.ssp:ssp-android:1.1.0")
    implementation ("com.intuit.sdp:sdp-android:1.1.0")
}