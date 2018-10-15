package com.cgy.hupu.widget.state;

import android.view.View;
import android.view.animation.Animation;

/**
 * Created by cgy on 2018/10/15  13:11
 */
public interface ShowState {

    /**
     * 显示该状态
     * @param animate 是否动画
     */
    void show(boolean animate);

    /**
     * 隐藏该状态
     * @param animate
     */
    void dismiss(boolean animate);

    /**
     * 设置FragmentView
     * @param fragmentView
     */
    void setFragmentView(View fragmentView);

    /**
     * 进入动画
     * @param in
     */
    void setAnimIn(Animation in);

    /**
     * 退出动画
     * @param out
     */
    void setAnimOut(Animation out);


}
