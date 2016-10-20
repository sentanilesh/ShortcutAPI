# ShortcutAPI

Android 7.1 allows you to define shortcuts to specific actions in your app. These shortcuts can be displayed in a supported launcher, such as the one provided with Nexus and Pixel devices. Shortcuts let your users quickly start common or recommended tasks within your app.

Code Gradle Info :

<pre>android {
    compileSdkVersion 25
    buildToolsVersion "25.0.0"
    defaultConfig {
        applicationId "com.android.shortcutapi"
        minSdkVersion 25
        targetSdkVersion 25
        versionCode 1
        versionName "1.0"
        testInstrumentationRunner "android.support.test.runner.AndroidJUnitRunner"
    }
}</pre>

<pre>dependencies {
    compile fileTree(dir: 'libs', include: ['*.jar'])
    compile 'com.android.support:appcompat-v7:25.0.0'
 }</pre>
 
 Run Code in Android Emulator API 25 (Android 7.1.1)
 
