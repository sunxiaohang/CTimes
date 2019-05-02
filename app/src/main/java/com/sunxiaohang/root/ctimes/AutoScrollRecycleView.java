package com.sunxiaohang.root.ctimes;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import java.lang.ref.WeakReference;

public class AutoScrollRecycleView extends RecyclerView{
    private static final long TIME_AUTO_POLL = 30;
    private AutoPollTask autoPollTask;
    private boolean running;
    public static boolean appStop =false;
    public AutoScrollRecycleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        autoPollTask = new AutoPollTask(this);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return false;
    }

    static class AutoPollTask implements Runnable {
        private final WeakReference<AutoScrollRecycleView> mReference;
        private AutoPollTask(AutoScrollRecycleView reference) {
            this.mReference = new WeakReference<>(reference);
        }
        @Override
        public void run() {
            AutoScrollRecycleView recyclerView = mReference.get();
            if (recyclerView != null && recyclerView.running && !recyclerView.appStop) {
                recyclerView.smoothScrollBy(20, 0);//每次滚动距离
                recyclerView.postDelayed(recyclerView.autoPollTask,TIME_AUTO_POLL);
            }
        }
    }
    //开启
    public void start() {
        if (running){
            return;
        }
        running = true;
        postDelayed(autoPollTask,TIME_AUTO_POLL);
    }
}
