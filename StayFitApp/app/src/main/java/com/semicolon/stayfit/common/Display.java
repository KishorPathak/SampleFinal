package com.semicolon.stayfit.common;

import android.util.Log;

/**
 * Created By Shubhankar Roy
 */
public abstract class Display {
    private String tag;

    protected Display(String tag) {
        this.tag = tag;
    }

    public abstract void show(String msg);

    public void log(String msg) {
        Log.d(tag, msg);
    }
}
