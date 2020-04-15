package com.glartek.flutter_unity;

import android.content.Context;

import io.flutter.Log;
import io.flutter.plugin.platform.PlatformView;
import io.flutter.plugin.platform.PlatformViewFactory;

public class FlutterUnityViewFactory extends PlatformViewFactory {
    private final FlutterUnityPlugin plugin;

    FlutterUnityViewFactory(FlutterUnityPlugin plugin) {
        super(null);
        this.plugin = plugin;
    }

    @Override
    public PlatformView create(Context context, int viewId, Object args) {
        Log.d(String.valueOf(this), "create: " + viewId);
        return new FlutterUnityView(plugin, context, viewId);
    }
}
