package com.cgy.hupu.module.report;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.cgy.hupu.R;
import com.cgy.hupu.module.BaseSwipeBackActivity;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ReportActivity extends BaseSwipeBackActivity implements ReportContract.View {


    public static void startActivity(Context context, String tid, String pid) {
        Intent intent = new Intent(context, ReportActivity.class);
        intent.putExtra("tid", tid);
        intent.putExtra("pid", pid);
        context.startActivity(intent);
    }

    @Inject
    ReportPresenter mPresenter;

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.list_types)
    ListView listTypes;
    @BindView(R.id.etContent)
    EditText etContent;

    private List<String> list = new ArrayList<>();
    private ReportAdapter adapter;
    private int type = 1;
    private MaterialDialog mDialog;
    private String pid;
    private String tid;


    @Override
    public int initContentView() {
        return R.layout.activity_report;
    }

    @Override
    public void initInjector() {

    }

    @Override
    public void initUiAndListener() {
        ButterKnife.bind(this);
        mPresenter.attachView(this);
        initToolBar(toolbar);
        setTitle("举报");
        pid = getIntent().getStringExtra("pid");
        tid = getIntent().getStringExtra("tid");

    }

    @Override
    protected boolean isApplyStatusBarTranslucency() {
        return false;
    }

    @Override
    protected boolean isApplyStatusBarColor() {
        return false;
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void reportSuccess() {

    }

    public class ReportAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            return null;


        }
        class ViewHolder {

        }
    }

}
