<img src="https://github.com/situmtech/flutter-unity/raw/master/flutter-unity.png">

# situm_flutter_unity

A Flutter plugin for embedding Unity projects in Flutter projects.

Both Android and iOS are supported.

> [!NOTE]  
> This is a fork of https://github.com/Glartek/flutter-unity.

## Usage
To use this plugin, add `situm_flutter_unity` as a [dependency in your pubspec.yaml file](https://flutter.dev/platform-plugins/).

## Example
Refer to the [example project](https://github.com/situmtech/flutter-unity/tree/master/example) and the [included Unity project](https://github.com/situmtech/flutter-unity/tree/master/example/unity/FlutterUnityExample).

## Testing
To test this plugin, do the following:
1. Run `git clone https://github.com/situmtech/flutter-unity.git` to create a local copy of flutter-unity.
2. Open flutter-unity in **Android Studio**.
#### Android
3. Connect your Android device and run the project.

#### iOS
3. Configure the [example project](https://github.com/situmtech/flutter-unity/tree/master/example) and the [included Unity project](https://github.com/situmtech/flutter-unity/tree/master/example/unity/FlutterUnityExample).
4. Connect your iOS device and run the project.

## Configuring your Unity project
#### Android
1. Go to **File** > **Build Settings...** to open the [Build Settings](https://docs.unity3d.com/Manual/BuildSettings.html) window.
2. Select **Android** and click **Switch Platform**.
3. Click **Add Open Scenes**.
4. Check **Export Project**.
5. Click **Player Settings...** to open the [Player Settings](https://docs.unity3d.com/Manual/class-PlayerSettings.html) window.
6. In the [Player Settings](https://docs.unity3d.com/Manual/class-PlayerSettings.html) window, configure the following:
<table>
  <thead>
    <tr>
      <th>Setting
      </th>
      <th>Value
      </th>
    </tr>
  </thead>
  <tbody>
    <tr>
      <td>Resolution and Presentation > Start in fullscreen mode
      </td>
      <td>No
      </td>
    </tr>
    <tr>
      <td>Other Settings > Rendering > Graphics APIs
      </td>
      <td>OpenGLES3
      </td>
    </tr>
    <tr>
      <td>Other Settings > Configuration > Scripting Backend
      </td>
      <td>IL2CPP
      </td>
    </tr>
    <tr>
      <td>Other Settings > Configuration > Target Architectures
      </td>
      <td>ARMv7, ARM64
      </td>
    </tr>
  </tbody>
</table>

7. Close the [Player Settings](https://docs.unity3d.com/Manual/class-PlayerSettings.html) window.
8. Click **Export** and save as `unityExport`.
#### iOS
1. Go to **File** > **Build Settings...** to open the [Build Settings](https://docs.unity3d.com/Manual/BuildSettings.html) window.
2. Select **iOS** and click **Switch Platform**.
3. Click **Add Open Scenes**.
4. Click **Build** and save as `UnityProject`.

## Configuring your Flutter project
#### Android
1. Copy the `unityExport` folder to `<your_flutter_project>/android/unityExport`.
2. Run `flutter pub run situm_flutter_unity:unity_export_transmogrify`.
3. Open `<your_flutter_project>/android/unityExport/build.gradle` and check if `buildTypes { profile {} }` is present. If not, add the following:
```
buildTypes {
    profile {}
}
```
Refer to the [example project's unityExport/build.gradle](https://github.com/situmtech/flutter-unity/blob/master/example/android/unityExport/build.gradle#L43-L45).

4. Open `<your_flutter_project>/android/build.gradle` and, under `allprojects { repositories {} }`, add the following:
```
flatDir {
    dirs "${project(':unityExport').projectDir}/libs"
}
```
Refer to the [example project's build.gradle](https://github.com/situmtech/flutter-unity/blob/master/example/android/build.gradle#L16-L18).

5. Open `<your_flutter_project>/android/settings.gradle` and add the following:
```
include ':unityExport'
```
Refer to the [example project's settings.gradle](https://github.com/situmtech/flutter-unity/blob/master/example/android/settings.gradle#L17).

6. Open `<your_flutter_project>/android/app/src/main/AndroidManifest.xml` and add the following:
```
<uses-permission android:name="android.permission.WAKE_LOCK"/>
```
Refer to the [example project's AndroidManifest.xml](https://github.com/situmtech/flutter-unity/blob/master/example/android/app/src/main/AndroidManifest.xml#L8).

Steps 1, 2 and 3 must be repeated for every new build of the Unity project.

#### iOS
1. Copy the `UnityProject` folder to `<your_flutter_project>/ios/UnityProject` and open `<your_flutter_project>/ios/Runner.xcworkspace` in **Xcode**.
2. Go to **File** > **Add Files to "Runner"...**, and add `<your_flutter_project>/ios/UnityProject/Unity-iPhone.xcodeproj`.
3. Select `Unity-iPhone/Data`, and, in the **Inspectors** pane, set the **Target Membership** to **UnityFramework**.
4. Select `Unity-iPhone`, select **PROJECT** : **Unity-iPhone**, and, in the **Build Settings** tab, configure the following:
<table>
  <thead>
    <tr>
      <th>Setting
      </th>
      <th>Value
      </th>
    </tr>
  </thead>
  <tbody>
    <tr>
      <td>Build Options > Enable Bitcode
      </td>
      <td>No
      </td>
    </tr>
    <tr>
      <td>Linking > Other Linker Flags
      </td>
      <td>-Wl,-U,_FlutterUnityPluginOnMessage
      </td>
    </tr>
  </tbody>
</table>

5. Select `Runner`, select **TARGETS** : **Runner**, and, in the **General** tab, configure the following:
<table>
  <thead>
    <tr>
      <th>Setting
      </th>
      <th>Value
      </th>
    </tr>
  </thead>
  <tbody>
    <tr>
      <td>Frameworks, Libraries, and Embedded Content
      </td>
      <td>
        <table>
          <thead>
            <tr>
              <th>Name
              </th>
              <th>Embed
              </th>
            </tr>
          </thead>
          <tbody>
            <tr>
              <td>UnityFramework.framework
              </td>
              <td>Embed & Sign
              </td>
            </tr>
          </tbody>
        </table>
      </td>
    </tr>
  </tbody>
</table>

6. Select `Runner/Runner/Info.plist`, and configure the following:
<table>
  <thead>
    <tr>
      <th>Key
      </th>
      <th>Type
      </th>
      <th>Value
      </th>
    </tr>
  </thead>
  <tbody>
    <tr>
      <td>io.flutter.embedded_views_preview
      </td>
      <td>Boolean
      </td>
      <td>YES
      </td>
    </tr>
  </tbody>
</table>

Steps 1, 3 and 4 must be repeated for every new build of the Unity project.

## Exchanging messages between Flutter and Unity
#### Flutter
To send a message, define the `onCreated` callback in your `UnityView` widget, and use the `send` method from the received `controller`.

To receive a message, define the `onMessage` callback in your `UnityView` widget.
#### Unity
To send and receive messages, include [FlutterUnityPlugin.cs](https://github.com/situmtech/flutter-unity/blob/master/example/unity/FlutterUnityExample/Assets/FlutterUnityPlugin.cs) in your project, and use the `Messages.Send` and `Messages.Receive` methods.

A `Message` object has the following members:

* **id** (`int`)

A non-negative number representing the source view when receiving a message, and the destination view when sending a message. When sending a message, it can also be set to a negative number, indicating that the message is intended for any existing view.

* **data** (`string`)

The actual message.

Refer to the [included Unity project's Rotate.cs](https://github.com/situmtech/flutter-unity/blob/master/example/unity/FlutterUnityExample/Assets/Rotate.cs#L21-L32).
