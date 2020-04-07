package com.glartek.flutter_unity;

import androidx.annotation.NonNull;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import io.flutter.Log;
import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.embedding.engine.plugins.activity.ActivityAware;
import io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding;

public class FlutterUnityPlugin implements FlutterPlugin, ActivityAware {
    static List<FlutterUnityView> views = new ArrayList<>();

    private static String tag = "FlutterUnityPlugin";

    private FlutterPluginBinding flutterPluginBinding;
    private FlutterUnityPlayer player;

    public static void onMessage(String data) {
        Log.d(FlutterUnityPlugin.tag, "onMessage -> " + data);

        try {
            JSONObject jsonObj = new JSONObject(data);
            int messageId = jsonObj.getInt("id");
            String messageData = jsonObj.getString("data");

            for (FlutterUnityView view : FlutterUnityPlugin.views) {
                if (messageId < 0 || messageId == view.getId()) {
                    view.onMessage(messageData);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onAttachedToEngine(@NonNull FlutterPluginBinding binding) {
        Log.d(FlutterUnityPlugin.tag, "onAttachedToEngine");
        flutterPluginBinding = binding;
        binding.getPlatformViewRegistry().registerViewFactory("unity_view", new FlutterUnityViewFactory(this));
    }

    @Override
    public void onDetachedFromEngine(@NonNull FlutterPluginBinding binding) {
        Log.d(FlutterUnityPlugin.tag, "onDetachedFromEngine");
        flutterPluginBinding = null;
    }

    @Override
    public void onAttachedToActivity(@NonNull ActivityPluginBinding binding) {
        Log.d(FlutterUnityPlugin.tag, "onAttachedToActivity");
        player = new FlutterUnityPlayer(binding.getActivity());
    }

    @Override
    public void onDetachedFromActivityForConfigChanges() {
        Log.d(FlutterUnityPlugin.tag, "onDetachedFromActivityForConfigChanges");
        player.destroy();
        player = null;
    }

    @Override
    public void onReattachedToActivityForConfigChanges(@NonNull ActivityPluginBinding binding) {
        Log.d(FlutterUnityPlugin.tag, "onReattachedToActivityForConfigChanges");
        player = new FlutterUnityPlayer(binding.getActivity());
    }

    @Override
    public void onDetachedFromActivity() {
        Log.d(FlutterUnityPlugin.tag, "onDetachedFromActivity");
        player.destroy();
        player = null;
    }

    FlutterPluginBinding getFlutterPluginBinding() {
        return flutterPluginBinding;
    }

    FlutterUnityPlayer getPlayer() {
        return player;
    }
}
