# ANIRBAN 360 Android Widget — Cloud Build

This branch contains the GitHub Actions build workflow.

## Add the Android source
Upload/extract the contents of `ANIRBAN360_Android_Widget_Source.zip` into this repository so the project root folder is named exactly:

`ANIRBAN360_Android_Widget/`

The folder must contain `settings.gradle.kts`, `build.gradle.kts`, and `app/`.

## Build
Open the Actions tab and run **Build ANIRBAN 360 Android Widget APK** (or push source files to this branch). On success, download the artifact named **ANIRBAN360-Widget-debug-apk**. It contains `app-debug.apk`.

The workflow builds a debug APK with JDK 17 and Gradle 8.9. It does not publish or install anything on a phone.
