package com.youliao.news.java;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.MotionEventCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.FrameLayout;

import com.youliao.sdk.news.ui.NewsFragment;

public class JavaFrameLayout extends FrameLayout {
    private boolean isInPosition = false;
    private int mLastMotionY;
    private int mLastMotionX;
    private NewsFragment mFragment;

    public JavaFrameLayout(@NonNull Context context) {
        super(context);
    }

    public JavaFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public JavaFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        int y = (int) ev.getY();
        int x = (int) ev.getX();
        final int action = MotionEventCompat.getActionMasked(ev);
        int deltaY = 0;
        int deltaX = 0;
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                mLastMotionY = y;
                mLastMotionX = x;
                break;
            case MotionEvent.ACTION_MOVE:
                deltaY = mLastMotionY - y;
                deltaX = mLastMotionX - x;
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                break;
        }
        if (getParent() != null) {
            if (Math.abs(deltaX) > Math.abs(deltaY)) {
                getParent().requestDisallowInterceptTouchEvent(true);
            } else {
                if (isInPosition) {
                    if (isScrollTop() && deltaY < 0) {
                        getParent().requestDisallowInterceptTouchEvent(false);
                    } else {
                        getParent().requestDisallowInterceptTouchEvent(true);
                    }
                } else {
                    if (isScrollTop()) {
                        getParent().requestDisallowInterceptTouchEvent(false);
                    }
                }
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    public void setFragment(NewsFragment fragment) {
        mFragment = fragment;
    }

    public void setInPosition(boolean isInPosition) {
        this.isInPosition = isInPosition;
    }

    boolean isScrollTop() {
        return mFragment.isScrollTop();
    }
}
