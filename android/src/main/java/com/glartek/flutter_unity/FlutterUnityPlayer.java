package com.glartek.flutter_unity;

import android.content.Context;
import android.util.Log;
import android.view.InputDevice;
import android.view.MotionEvent;

import com.unity3d.player.UnityPlayer;

public class FlutterUnityPlayer extends UnityPlayer {
    private static String tag = "FlutterUnityPlayer";

    public FlutterUnityPlayer(Context context) {
        super(context);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d(FlutterUnityPlayer.tag, "onTouchEvent");
        performClick();
        event.setSource(InputDevice.SOURCE_TOUCHSCREEN);
        return super.onTouchEvent(event);
    }

    @Override
    public boolean performClick() {
        return super.performClick();
    }
}
