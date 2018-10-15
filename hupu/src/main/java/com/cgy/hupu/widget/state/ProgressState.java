package com.cgy.hupu.widget.state;


import com.cgy.hupu.R;

/**
 * Created by cgy on 2018/10/15  13:18
 */
public class ProgressState extends AbstractShowState {

    @Override
    public void show(boolean animate) {
        showViewById(R.id.epf_progress, animate);
    }

    @Override
    public void dismiss(boolean animate) {
        dismissViewById(R.id.epf_progress, animate);
    }
}
