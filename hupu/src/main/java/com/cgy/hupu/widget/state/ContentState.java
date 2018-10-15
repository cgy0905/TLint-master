package com.cgy.hupu.widget.state;


import com.cgy.hupu.R;

/**
 * Created by cgy on 2018/10/15.
 */
public class ContentState extends AbstractShowState implements ShowState {

    @Override
    public void show(boolean animate) {
        showViewById(R.id.epf_content, animate);
    }

    @Override
    public void dismiss(boolean animate) {
        dismissViewById(R.id.epf_content, animate);
    }
}
