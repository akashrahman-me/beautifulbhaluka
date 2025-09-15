Nice — you can absolutely use **VS Code** as your editor and keep Android Studio only for SDK/AVD management. Below is a concise, copy-pasteable step-by-step guide for **Ubuntu** that reuses your existing Android Studio SDK & emulators.

# Quick plan (what you’ll do)

1. Install VS Code. ([Visual Studio Code][1])
2. Install the correct JDK (Android Gradle Plugin needs Java 17+). ([Android Developers][2])
3. Wire up Android SDK env vars and PATH to use `adb`, `emulator`, `sdkmanager`. ([Android Developers][3])
4. Build & install via Gradle CLI (`./gradlew installDebug`) or `adb`. Start emulators from CLI. ([Android Developers][4])
5. Add helpful VSCode extensions (Kotlin, Gradle, ADB/Android debug). ([Visual Studio Marketplace][5])

---

# Step-by-step (concise)

1. **Install VS Code**

    ```bash
    # download .deb from https://code.visualstudio.com or:
    sudo apt update
    sudo apt install ./<path-to-vscode.deb>
    ```

    (Or use the Microsoft repo per VS Code docs). ([Visual Studio Code][1])

2. **Install/OpenJDK 17 (recommended)**

    ```bash
    sudo apt update
    sudo apt install openjdk-17-jdk
    ```

    Set `JAVA_HOME` to the JDK path (example below). Modern Android Gradle plugin requires Java 17+. ([Android Developers][2])

3. **Export Android SDK + Java env vars (add to `~/.bashrc` or `~/.profile`)**
   Replace the SDK path if yours is different (Android Studio default: `~/Android/Sdk`):

    ```bash
    echo 'export JAVA_HOME=/usr/lib/jvm/java-17-openjdk-amd64' >> ~/.bashrc
    echo 'export ANDROID_HOME=$HOME/Android/Sdk' >> ~/.bashrc
    echo 'export PATH=$PATH:$ANDROID_HOME/emulator:$ANDROID_HOME/tools/bin:$ANDROID_HOME/platform-tools' >> ~/.bashrc
    source ~/.bashrc
    ```

    Note: Android tooling reads `ANDROID_HOME` / `ANDROID_SDK_ROOT` and adding `platform-tools`, `tools/bin`, and `emulator` to PATH lets you run `adb`, `sdkmanager`, `avdmanager`, and `emulator` from any terminal. ([Android Developers][3])

4. **Verify CLI tools are available**

    ```bash
    adb --version
    sdkmanager --list
    emulator -list-avds
    chmod +x gradlew
    ./gradlew --version   # from your project root (use gradlew wrapper)
    ```

    If any command fails, check your PATH and SDK location. ([Android Developers][4])

5. **Start an emulator (reuse AVDs you created in Android Studio)**

    ```bash
    emulator -avd <AVD_NAME> -gpu host      # e.g. emulator -avd Pixel_6_API_33
    ```

    If you need AVDs, create them once with Android Studio’s AVD Manager (keeps UI easy). ([Android Developers][4])

6. **Build & install from project root (Gradle wrapper)**

    ```bash
    ./gradlew assembleDebug        # build APK(s)
    ./gradlew installDebug         # build + install to connected device/emulator
    # OR manually:
    adb install -r app/build/outputs/apk/debug/app-debug.apk
    ```

    Use the Gradle wrapper (`./gradlew`) so you match the project Gradle version. ([Android Developers][4])

7. **Install recommended VSCode extensions** (quick list)

    - **Kotlin** (Kotlin LSP / fwcd.kotlin) — language features. ([Visual Studio Marketplace][5])
    - **Gradle for Java** (vscjava.vscode-gradle) — run Gradle tasks inside VS Code. ([Visual Studio Marketplace][6])
    - **ADB Interface** or **Android for VS Code** / **Android Debug** — run `adb` commands / attach debugger from VS Code. ([Visual Studio Marketplace][7])

8. **Add VS Code tasks to build/install (optional but handy)**
   Create `.vscode/tasks.json`:

    ```json
    {
        "version": "2.0.0",
        "tasks": [
            {
                "label": "Gradle: assembleDebug",
                "type": "shell",
                "command": "./gradlew assembleDebug",
                "problemMatcher": []
            },
            {
                "label": "Gradle: installDebug",
                "type": "shell",
                "command": "./gradlew installDebug",
                "problemMatcher": []
            }
        ]
    }
    ```

    Run these from the Command Palette → Run Task.

9. **Debugging from VS Code**

    - For quick attach/install: use the **Android Debug** / **Android for VS Code** extensions (they provide launch/attach UI). ([Visual Studio Marketplace][8])
    - For deeper Kotlin/Java debugging you may need extra Java debug configuration or to attach using `Attach to Android process` features provided by those extensions.

10. **Tips / gotchas**

-   If Gradle/AGP complains about Java version, use `JAVA_HOME` or set `org.gradle.java.home` in `gradle.properties`. (AGP frequently requires JDK 17+). ([Android Developers][2])
-   VS Code Kotlin support is improving but **won’t fully match Android Studio/IntelliJ** (layout editor, deep refactorings, advanced Kotlin inspections). Keep Android Studio installed for SDK/AVD and heavy UI/layout work. ([turtlestoffel.com][9])

---

# Minimal quick-check checklist (copy/paste)

```bash
# 1. Install JDK 17
sudo apt install openjdk-17-jdk

# 2. Set env vars (adjust SDK path if different)
echo 'export ANDROID_HOME=$HOME/Android/Sdk' >> ~/.bashrc
echo 'export PATH=$PATH:$ANDROID_HOME/emulator:$ANDROID_HOME/tools/bin:$ANDROID_HOME/platform-tools' >> ~/.bashrc
source ~/.bashrc

# 3. From your project folder:
./gradlew assembleDebug
./gradlew installDebug
# or adb install -r app/build/outputs/apk/debug/app-debug.apk

# 4. To start emulator:
emulator -list-avds
emulator -avd <name>
```

---

after changes run this:

If you want, I can:

```
./gradlew installDebug
adb shell monkey -p com.akash.beautifulbhaluka -c android.intent.category.LAUNCHER 1
```

---

-   produce a ready `.vscode/tasks.json` + `.vscode/launch.json` tuned to your project (I’ll assume a standard `app` module), or
-   show the exact commands to set env vars for your exact SDK path (tell me your SDK path) — otherwise I used the common `~/Android/Sdk`.

Which of those would you like me to generate now?

[1]: https://code.visualstudio.com/docs/setup/linux?utm_source=chatgpt.com "Visual Studio Code on Linux"
[2]: https://developer.android.com/build/jdks?utm_source=chatgpt.com "Java versions in Android builds | Android Studio"
[3]: https://developer.android.com/tools/variables?utm_source=chatgpt.com "Environment variables | Android Studio"
[4]: https://developer.android.com/build/building-cmdline?utm_source=chatgpt.com "Build your app from the command line | Android Studio"
[5]: https://marketplace.visualstudio.com/items?itemName=fwcd.kotlin&utm_source=chatgpt.com "Kotlin"
[6]: https://marketplace.visualstudio.com/items?itemName=vscjava.vscode-gradle&utm_source=chatgpt.com "Gradle for Java"
[7]: https://marketplace.visualstudio.com/items?itemName=vinicioslc.adb-interface-vscode&utm_source=chatgpt.com "ADB Interface for VSCode"
[8]: https://marketplace.visualstudio.com/items?itemName=nisargjhaveri.android-debug&utm_source=chatgpt.com "Android Debug"
[9]: https://www.turtlestoffel.com/Kotlin-Support-in-VSCode?utm_source=chatgpt.com "Kotlin Support in VSCode - Turtlestoffel Homepage"
