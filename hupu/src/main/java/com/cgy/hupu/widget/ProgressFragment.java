package com.cgy.hupu.widget;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cgy.hupu.R;

/**
 * Created by cgy on 2018/10/11  16:21
 */
public class ProgressFragment extends Fragment {

    public boolean isPrepare = false;


    public View onCreateContentView(LayoutInflater inflater) {
        return null;
    }

    //Override this method to change error view
    public View onCreateContentErrorView(LayoutInflater inflater) {
        return null;
    }

    public View onCreateContentEmptyView(LayoutInflater inflater) {
        return null;
    }

    public View onCreateProgressView(LayoutInflater inflater) {
        return null;
    }

    private View mContentView;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup main = (ViewGroup) inflater.inflate(R.layout.epf_layout, container, false);

        View content = onCreateContentView(inflater);
        View error = onCreateContentErrorView(inflater);
        View empty = onCreateContentEmptyView(inflater);
        View progress = onCreateProgressView(inflater);


//        replaceViewById(main, R.id.epf_content, content);
//        replaceViewById(main, R.id.epf_error, error);
//        replaceViewById(main, R.id.epf_empty, empty);
//        replaceViewById(main, R.id.epf_progress, progress);
        return super.onCreateView(inflater, container, savedInstanceState);
    }
}
