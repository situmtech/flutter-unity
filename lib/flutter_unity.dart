import 'dart:async';

import 'package:flutter/foundation.dart';
import 'package:flutter/material.dart';
import 'package:flutter/services.dart';

class UnityViewController {
  UnityViewController._(
    UnityView widget,
    int id,
  )   : _widget = widget,
        _channel = MethodChannel('unity_view_$id') {
    _channel.setMethodCallHandler(_methodCallHandler);
  }

  UnityView _widget;
  final MethodChannel _channel;

  Future<dynamic> _methodCallHandler(MethodCall call) async {
    switch (call.method) {
      case 'onUnityViewMessage':
        if (_widget.onUnityViewMessage != null) {
          _widget.onUnityViewMessage(this, call.arguments);
        }

        return null;
      default:
        throw UnimplementedError('Unimplemented method: ${call.method}');
    }
  }

  void pause() {
    _channel.invokeMethod('pause');
  }

  void resume() {
    _channel.invokeMethod('resume');
  }

  void send(
    String gameObjectName,
    String methodName,
    String message,
  ) {
    _channel.invokeMethod('send', {
      'gameObjectName': gameObjectName,
      'methodName': methodName,
      'message': message,
    });
  }
}

typedef void UnityViewCreatedCallback(
  UnityViewController controller,
);
typedef void UnityViewMessageCallback(
  UnityViewController controller,
  String message,
);

class UnityView extends StatefulWidget {
  const UnityView({
    Key key,
    this.onUnityViewCreated,
    this.onUnityViewMessage,
  }) : super(key: key);

  final UnityViewCreatedCallback onUnityViewCreated;
  final UnityViewMessageCallback onUnityViewMessage;

  @override
  _UnityViewState createState() => _UnityViewState();
}

class _UnityViewState extends State<UnityView> {
  final Completer<UnityViewController> completer =
      Completer<UnityViewController>();

  @override
  void initState() {
    super.initState();
  }

  @override
  void dispose() {
    super.dispose();

    completer.future.then((UnityViewController controller) {
      controller._channel.setMethodCallHandler(null);
    });
  }

  @override
  Widget build(BuildContext context) {
    switch (defaultTargetPlatform) {
      case TargetPlatform.android:
        return AndroidView(
          viewType: 'unity_view',
          onPlatformViewCreated: onPlatformViewCreated,
        );
        break;
      default:
        throw UnsupportedError('Unsupported platform: $defaultTargetPlatform');
    }
  }

  @override
  void didUpdateWidget(UnityView oldWidget) {
    super.didUpdateWidget(oldWidget);

    completer.future.then((UnityViewController controller) {
      controller._widget = widget;
    });
  }

  void onPlatformViewCreated(int id) {
    UnityViewController controller = UnityViewController._(widget, id);

    completer.complete(controller);

    if (widget.onUnityViewCreated != null) {
      widget.onUnityViewCreated(controller);
    }
  }
}
