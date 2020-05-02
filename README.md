# flutter_unity

A Flutter plugin for embedding Unity projects in Flutter projects.

iOS is currently not supported.

## Usage
To use this plugin, add `flutter_unity` as a [dependency in your pubspec.yaml file](https://flutter.dev/platform-plugins/).

## Example
Refer to the [example project](https://github.com/Glartek/flutter-unity/tree/master/example) and the [included Unity project](https://github.com/Glartek/flutter-unity/tree/master/example/unity/FlutterUnityExample). Both projects are fully configured.

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
3. Open `<your_flutter_project>/android/build.gradle` and, under `allprojects { repositories {} }`, add the following:
```
flatDir {
    dirs "${project(':unityExport').projectDir}/libs"
}
```
Refer to the [example project's build.gradle](https://github.com/Glartek/flutter-unity/blob/master/example/android/build.gradle#L16-L18).

4. Open `<your_flutter_project>/android/settings.gradle` and add the following:
```
include ':unityExport'
```
Refer to the [example project's settings.gradle](https://github.com/Glartek/flutter-unity/blob/master/example/android/settings.gradle#L17).

## Exchanging messages between Flutter and Unity
#### Flutter
To send a message, define the `onCreated` callback in your `UnityView` widget, and use the `send` method from the received `controller`.

To receive a message, define the `onMessage` callback in your `UnityView` widget.
#### Unity
To send and receive messages, include [FlutterUnityPlugin.cs](https://github.com/Glartek/flutter-unity/blob/master/example/unity/FlutterUnityExample/Assets/FlutterUnityPlugin.cs) in your project, and use the `Messages.Send` and `Messages.Receive` methods.

A `Message` object has the following members:

* **id** (`int`)

A non-negative number representing the source view when receiving a message, and the destination view when sending a message. When sending a message, it can also be set to a negative number, indicating that the message is intended for any existing view.

* **data** (`string`)

The actual message.

Refer to the [included Unity project's Rotate.cs](https://github.com/Glartek/flutter-unity/blob/master/example/unity/FlutterUnityExample/Assets/Rotate.cs#L21-L32).
