package com.glartek.flutter_unity;

import android.content.Context;

import io.flutter.Log;
import io.flutter.plugin.common.StandardMessageCodec;
import io.flutter.plugin.platform.PlatformView;
import io.flutter.plugin.platform.PlatformViewFactory;

public class FlutterUnityViewFactory extends PlatformViewFactory {
    private static String tag = "FlutterUnityViewFactory";

    private final FlutterUnityPlugin plugin;

    FlutterUnityViewFactory(FlutterUnityPlugin plugin) {
        super(new StandardMessageCodec());
        this.plugin = plugin;
    }

    @Override
    public PlatformView create(Context context, int viewId, Object args) {
        Log.d(FlutterUnityViewFactory.tag, "create -> " + viewId);
        return new FlutterUnityView(plugin, viewId);
    }
}
