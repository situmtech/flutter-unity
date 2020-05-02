package com.glartek.flutter_unity;

import android.view.WindowManager;

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

    private FlutterPluginBinding flutterPluginBinding;
    private FlutterUnityPlayer player;

    public static void onMessage(String data) {
        Log.d(String.valueOf(FlutterUnityPlugin.class), "onMessage: " + data);
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
        Log.d(String.valueOf(this), "onAttachedToEngine");
        flutterPluginBinding = binding;
        binding.getPlatformViewRegistry().registerViewFactory("unity_view", new FlutterUnityViewFactory(this));
    }

    @Override
    public void onDetachedFromEngine(@NonNull FlutterPluginBinding binding) {
        Log.d(String.valueOf(this), "onDetachedFromEngine");
        flutterPluginBinding = null;
    }

    @Override
    public void onAttachedToActivity(@NonNull ActivityPluginBinding binding) {
        Log.d(String.valueOf(this), "onAttachedToActivity");
        player = new FlutterUnityPlayer(binding.getActivity());
        binding.getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    @Override
    public void onDetachedFromActivityForConfigChanges() {
        Log.d(String.valueOf(this), "onDetachedFromActivityForConfigChanges");
        player.destroy();
        player = null;
    }

    @Override
    public void onReattachedToActivityForConfigChanges(@NonNull ActivityPluginBinding binding) {
        Log.d(String.valueOf(this), "onReattachedToActivityForConfigChanges");
        player = new FlutterUnityPlayer(binding.getActivity());
        binding.getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    @Override
    public void onDetachedFromActivity() {
        Log.d(String.valueOf(this), "onDetachedFromActivity");
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
