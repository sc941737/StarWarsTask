To run, make sure your Gradle is set to use Java 11. You can set that in Android Studio in:
File > Settings > Build, Execution, Deployment > Build Tools > Gradle > Gradle JDK > 11

To build a signed bundle or apk, make sure to edit the keystore file path in app/build.gradle:
```
android {
    ...
    signingConfigs {
        val demo by creating {
            keyAlias = "key0"
            keyPassword = "123456"
>>>>>>>>>   storeFile = file("path/to/star_wars_keystore.jks") <<<<<<<<<<
            storePassword = "123456"
        }
    }
}
```
