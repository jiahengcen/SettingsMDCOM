package com.sty.setting.md.app;

import android.app.Application;
import android.os.Handler;
import android.support.annotation.NonNull;

import com.sty.setting.md.SysParam;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadFactory;

/**
 * Created by Steven.S on 2018/1/18/0018.
 */

public class MyApplication extends Application {
    private static MyApplication mApp;
    private static SysParam sysParam;

    private Handler handler;
    private ExecutorService backgroundExecutor;

    @Override
    public void onCreate() {
        super.onCreate();
        mApp = this;

        sysParam = SysParam.getInstance();
        handler = new Handler();
        backgroundExecutor = Executors.newFixedThreadPool(10, new ThreadFactory() {
            @Override
            public Thread newThread(@NonNull Runnable r) {
                Thread thread = new Thread(r, "Background executor service");
                thread.setPriority(Thread.MIN_PRIORITY);
                thread.setDaemon(true);
                return thread;
            }
        });
    }

    public static MyApplication getApp(){
        return mApp;
    }

    public static SysParam getSysParam(){
        return sysParam;
    }

    public Future<?> runInBackground(Runnable runnable){return backgroundExecutor.submit(runnable);}

    public void runOnUiThread(Runnable runnable){
        handler.post(runnable);
    }

    public void runOnUiThreadDelay(Runnable runnable, long delayMillis){
        handler.postDelayed(runnable, delayMillis);
    }
}
