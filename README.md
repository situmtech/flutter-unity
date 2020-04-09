# flutter_unity

A Flutter plugin for embedding Unity projects in Flutter projects.

iOS is currently not supported.

## Usage
To use this plugin, add `flutter_unity` as a [dependency in your pubspec.yaml file](https://flutter.dev/platform-plugins/).

## Configuring your Unity project (Android)
1. Go to **File** > **Build Settings...** to open the [Build Settings](https://docs.unity3d.com/Manual/BuildSettings.html) window.
2. Select **Android** and click **Switch Platform**.
3. Click **Add Open Scenes**.
4. Check **Export Project**.
5. Click **Player Settings...** to open the [Player Settings](https://docs.unity3d.com/Manual/class-PlayerSettings.html) window.
6. In the [Player Settings](https://docs.unity3d.com/Manual/class-PlayerSettings.html) window, configure the following:

| Setting | Value |
|---|---|
| Other Settings > Rendering > Graphics APIs | OpenGLES3 |
| Other Settings > Configuration > Scripting Backend | IL2CPP |
| Other Settings > Configuration > Target Architectures | ARMv7, ARM64 |

7. Close the [Player Settings](https://docs.unity3d.com/Manual/class-PlayerSettings.html) window.
8. Click **Export** and save as `unityExport`.

## Configuring your Flutter project (Android)
1. Copy the `unityExport` folder to `<your_flutter_project>/android/unityExport`.
2. Run `flutter pub run flutter_unity:unity_export_transmogrify`.
3. Open `<your_flutter_project>/android/build.gradle` and, under `allprojects` > `repositories`, add the following:
```
flatDir {
    dirs "${project(':unityExport').projectDir}/libs"
}
```
4. Open `<your_flutter_project>/android/settings.gradle` and add the following:
```
include ':unityExport'
```
