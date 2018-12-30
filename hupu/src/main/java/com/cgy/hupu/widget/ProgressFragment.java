package com.cgy.hupu.widget;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.cgy.hupu.R;
import com.cgy.hupu.widget.state.ContentState;
import com.cgy.hupu.widget.state.EmptyState;
import com.cgy.hupu.widget.state.ErrorState;
import com.cgy.hupu.widget.state.NonState;
import com.cgy.hupu.widget.state.ProgressState;
import com.cgy.hupu.widget.state.ShowState;

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

    private View contentView;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup main = (ViewGroup) inflater.inflate(R.layout.epf_layout, container, false);

        View content = onCreateContentView(inflater);
        View error = onCreateContentErrorView(inflater);
        View empty = onCreateContentEmptyView(inflater);
        View progress = onCreateProgressView(inflater);


        replaceViewById(main, R.id.epf_content, content);
        replaceViewById(main, R.id.epf_error, error);
        replaceViewById(main, R.id.epf_empty, empty);
        replaceViewById(main, R.id.epf_progress, progress);

        contentView = main;
        animIn = onCreateAnimationIn();
        animOut = onCreateAnimationOut();
        initStatus();
        isPrepare = true;
        return main;
    }

    public Animation onCreateAnimationIn() {
        return AnimationUtils.loadAnimation(getActivity(), android.R.anim.fade_in);
    }

    public Animation onCreateAnimationOut() {
        return AnimationUtils.loadAnimation(getActivity(), android.R.anim.fade_out);
    }

    private void replaceViewById(ViewGroup container, int viewId, View newView) {
        if (newView == null) {
            return;
        }
        newView.setId(viewId);
        View oldView = container.findViewById(viewId);
        int index = container.indexOfChild(oldView);
        container.addView(newView, index);

        newView.setVisibility(View.GONE);
    }

    private ShowState emptyState, progressState, errorState, contentState, loginState, collectState;

    private Animation animIn, animOut;

    private void initStatus() {
        emptyState = new EmptyState();
        progressState = new ProgressState();
        errorState = new ErrorState();
        contentState = new ContentState();

        initState(emptyState);
        initState(progressState);
        initState(errorState);
        initState(contentState);

    }

    private void initState(ShowState state) {
        state.setAnimIn(animIn);
        state.setAnimOut(animOut);
        state.setFragmentView(contentView);
    }

    private ShowState lastState = new NonState();

    private void showContent(boolean animate) {
        if (lastState == contentState) {
            return;
        }

        contentState.show(animate);
        lastState.dismiss(animate);
        lastState = contentState;
    }

    public void showEmpty(boolean animate) {
        if (lastState == emptyState) {
            return;
        }
        emptyState.show(animate);
        lastState.dismiss(animate);
        lastState = emptyState;
    }

    public void showError(boolean animate) {
        if (lastState == errorState) {
            return;
        }
        errorState.show(animate);
        lastState.dismiss(animate);
        lastState = errorState;
    }

    public void showProgress(boolean animate) {
        if (lastState == errorState) {
            return;
        }
        progressState.show(animate);
        lastState.dismiss(animate);
        lastState = progressState;
    }
}
