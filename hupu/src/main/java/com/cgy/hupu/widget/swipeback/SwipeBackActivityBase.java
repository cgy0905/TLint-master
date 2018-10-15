package com.cgy.hupu.widget.swipeback;

/**
 * Created by cgy on 2018/10/15  15:34
 */
public interface SwipeBackActivityBase {

    /**
     * @return the SwipeBackLayout associated with this activity.
     */
    public abstract SwipeBackLayout getSwipeBackLayout();

    public abstract void setSwipeBackEnable(boolean enable);

    /**
     * Scroll out contentView and finish the activity
     */
    public abstract void scrollToFinishActivity();
}
