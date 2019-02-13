package demo.orbitsys.com.kmgassindemo.util;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;

public class ApplicationController extends Application implements Application.ActivityLifecycleCallbacks{

    int mPreviousHeight;
    private static ApplicationController applicationControler;
    public static synchronized ApplicationController getInstance() {
        return applicationControler;
    }


    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        setFullScreen(activity);

    }

    @Override
    public void onActivityStarted(final Activity activity) {
        setFullScreen(activity);

    }

    @Override
    public void onActivityResumed(final Activity activity) {
        final View contentView = activity.findViewById(android.R.id.content);
        contentView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                int newHeight = contentView.getHeight();
                if (mPreviousHeight != 0) {
                    if (mPreviousHeight > newHeight) {
                        // Height decreased: keyboard was shown
                    } /*else if (mPreviousHeight <= newHeight) {
                        // Height increased: keyboard was hidden
                        setFullScreen(activity);
                    }*/ else {
                        setFullScreen(activity);
                    }
                }
                mPreviousHeight = newHeight;
            }
        });
    }

    @Override
    public void onActivityPaused(Activity activity) {

    }

    @Override
    public void onActivityStopped(Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {

    }
    public static void setFullScreen(Activity c) {
        View decorView = c.getWindow().getDecorView();
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);

    }
}
