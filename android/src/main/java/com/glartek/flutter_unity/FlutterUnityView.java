package com.glartek.flutter_unity;

import android.view.View;

import androidx.annotation.NonNull;

import org.json.JSONObject;

import io.flutter.Log;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.platform.PlatformView;

public class FlutterUnityView implements PlatformView, MethodChannel.MethodCallHandler {
    private static String tag = "FlutterUnityView";

    private final FlutterUnityPlugin plugin;
    private final int id;
    private final MethodChannel channel;

    FlutterUnityView(FlutterUnityPlugin plugin, int viewId) {
        this.plugin = plugin;
        this.id = viewId;
        this.channel = new MethodChannel(plugin.getFlutterPluginBinding().getBinaryMessenger(), "unity_view_" + viewId);
        FlutterUnityPlugin.views.add(this);
        plugin.getPlayer().windowFocusChanged(plugin.getPlayer().getView().requestFocus());
        plugin.getPlayer().resume();
        this.channel.setMethodCallHandler(this);
    }

    @Override
    public View getView() {
        Log.d(FlutterUnityView.tag, "getView");
        return plugin.getPlayer().getView();
    }

    @Override
    public void dispose() {
        Log.d(FlutterUnityView.tag, "dispose");
        FlutterUnityPlugin.views.remove(this);
        plugin.getPlayer().pause();
        channel.setMethodCallHandler(null);
    }

    @Override
    public void onMethodCall(@NonNull MethodCall call, @NonNull MethodChannel.Result result) {
        Log.d(FlutterUnityView.tag, "onMethodCall -> " + call.method);

        switch (call.method) {
            case "pause":
                plugin.getPlayer().pause();
                result.success(null);
                break;
            case "resume":
                plugin.getPlayer().resume();
                result.success(null);
                break;
            case "send":
                try {
                    String gameObjectName = call.argument("gameObjectName");
                    String methodName = call.argument("methodName");
                    String message = call.argument("message");
                    JSONObject jsonObj = new JSONObject();
                    jsonObj.put("id", id);
                    jsonObj.put("data", message);
                    FlutterUnityPlayer.UnitySendMessage(gameObjectName, methodName, jsonObj.toString());
                    result.success(null);
                } catch (Exception e) {
                    e.printStackTrace();
                    result.error(null, e.getMessage(), null);
                }

                break;
            default:
                result.notImplemented();
        }
    }

    int getId() {
        return id;
    }

    void onMessage(final String message) {
        Log.d(FlutterUnityView.tag, "onMessage -> " + message);

        plugin.getPlayer().post(new Runnable() {
            @Override
            public void run() {
                channel.invokeMethod("onUnityViewMessage", message);
            }
        });
    }
}
