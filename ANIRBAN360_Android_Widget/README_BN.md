# ANIRBAN 360 Android Home Screen Widget — Source Project

এই প্রজেক্টটি একটি আলাদা Android companion app। এটি Android-এর আসল AppWidgetProvider ব্যবহার করে Home Screen-এ progress, completed count, ছয়টি workstream-এর সংক্ষিপ্ত status এবং last sync দেখায়।

## বর্তমান সীমাবদ্ধতা
- এটি Floot project-এর ভেতরের native widget নয়; আলাদা Android companion APK।
- Tracker endpoint অবশ্যই প্রকাশিত HTTPS URL হতে হবে। বর্তমান Floot tracker প্রকাশিত: https://anrban360.floot.app
- এই ZIP-এ source project আছে, signed APK নেই। Android SDK/Gradle দিয়ে build করতে হবে।
- Widget-এর refresh Android WorkManager-এর মাধ্যমে আনুমানিক ৩০ মিনিট অন্তর; Android battery policy অনুযায়ী সময় পিছোতে পারে। ↻ চাপলে manual refresh হয়।
- Widget শুধু GET endpoint পড়ে; কোনো status পরিবর্তন করে না।

## Build
1. Android Studio-তে এই folder Open করো।
2. Gradle sync শেষ হলে Build → Build Bundle(s) / APK(s) → Build APK(s)।
3. `app/build/outputs/apk/debug/app-debug.apk` ইনস্টল করো।

## Setup
1. App খুলে URL দাও: `https://anrban360.floot.app/_api/tracker`
2. Save URL & Sync চাপো।
3. Android Home Screen-এ ফাঁকা জায়গা long-press → Widgets → ANIRBAN 360 Widget।

## নিরাপত্তা
Widget-এ কোনো API key/token রাখা হয় না। এটি কেবল tracker-এর read-only GET endpoint ব্যবহার করে। Tracker endpoint-এ ব্যক্তিগত তথ্য যোগ কোরো না।
