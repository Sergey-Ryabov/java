package com.ocodetesttask;

import android.os.Handler;
import android.os.HandlerThread;

/**
 * Created by Сергей on 13.07.2015.
 */
public class SaveThread extends HandlerThread {

    private Handler handler;

    public SaveThread(String name) {
        super(name);
    }

    public void postTask(Runnable task){
        handler.post(task);
    }

    public void prepareHandler(){
        handler = new Handler(getLooper());
    }

}
