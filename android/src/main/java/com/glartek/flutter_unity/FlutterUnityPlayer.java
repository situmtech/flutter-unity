package com.glartek.flutter_unity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.InputDevice;
import android.view.MotionEvent;

import com.unity3d.player.UnityPlayer;

public class FlutterUnityPlayer extends UnityPlayer {
    public FlutterUnityPlayer(Context context) {
        super(context);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        Log.d(String.valueOf(this), "onTouchEvent");
        motionEvent.setSource(InputDevice.SOURCE_TOUCHSCREEN);
        return super.onTouchEvent(motionEvent);
    }
}
