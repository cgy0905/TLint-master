package com.cgy.hupu.module.post;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

import com.afollestad.materialdialogs.MaterialDialog;
import com.cgy.hupu.Constants;
import com.cgy.hupu.R;
import com.cgy.hupu.bean.BaseError;
import com.cgy.hupu.bean.Exam;
import com.cgy.hupu.module.BaseSwipeBackActivity;
import com.cgy.hupu.module.browser.BrowserActivity;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;
import rx.functions.Action1;

public class PostActivity extends BaseSwipeBackActivity implements PostContract.View {

    public static void startActivity(Context context, int type, String fid, String tid, String pid,
                                     String title) {
        Intent intent = new Intent(context, PostActivity.class);
        intent.putExtra("type", type);
        intent.putExtra("fid", fid);
        intent.putExtra("tid", tid);
        intent.putExtra("title", title);
        context.startActivity(intent);
    }

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.et_subject)
    EditText etSubject;
    @BindView(R.id.et_content)
    EditText etContent;
    @BindView(R.id.ll_pics)
    LinearLayout llPics;
    @BindView(R.id.scrollView)
    HorizontalScrollView scrollView;

    private ArrayList<String> selectImages = new ArrayList<>();
    private MaterialDialog mDialog;

    private String title;
    private int type;
    private String fid;
    private String tid;
    private String pid;

    @Inject
    PostPresenter mPresenter;

    @Override
    public int initContentView() {
        return R.layout.activity_post;
    }

    @Override
    public void initInjector() {
        DaggerPostComponent.builder()
                .applicationComponent(getApplicationComponent())
                .activityModule(getActivityModule())
                .build()
                .inject(this);
    }

    @Override
    public void initUiAndListener() {
        ButterKnife.bind(this);
        mPresenter.attachView(this);
        initToolBar(toolbar);
        initBundle();
        initPostType();
        initDialog();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.checkPermission(type, fid, tid);
    }

    private void initDialog() {
        mDialog = new MaterialDialog.Builder(this)
                .title("标题")
                .content("正在发送")
                .progress(true, 0)
                .build();
    }

    private void initPostType() {
        switch (type) {
            case Constants.TYPE_COMMENT:
                setTitle("评论");
                etSubject.setFocusable(false);
                etSubject.setFocusableInTouchMode(false);
                etSubject.setText("Reply:" + title);
                etContent.setHint("请输入评论内容");
                break;
            case Constants.TYPE_REPLY:
                setTitle("评论");
                etSubject.setFocusable(false);
                etSubject.setFocusableInTouchMode(false);
                etSubject.setText("Reply:" + title);
                etSubject.setHint("请输入评论内容");
                break;
            case Constants.TYPE_AT:
                setTitle("评论");
                etSubject.setFocusable(false);
                etSubject.setFocusableInTouchMode(false);
                etSubject.setText("Reply:" + title);
                etContent.setHint("请输入评论内容");
                break;
            case Constants.TYPE_FEEDBACK:
                setTitle("反馈");
                etSubject.setFocusable(false);
                etSubject.setFocusableInTouchMode(false);
                etSubject.setText("Feedback: TLint For Android");
                etContent.setHint("请输入反馈内容");
                break;
            case Constants.TYPE_QUOTE:
                setTitle("引用");
                etSubject.setFocusable(false);
                etSubject.setFocusableInTouchMode(false);
                etSubject.setText("Quote:" + title);
                etContent.setHint("请输入评论内容");
                break;
            default:
                setTitle("发新帖");
                break;

        }
    }

    private void initBundle() {
        type = getIntent().getIntExtra("type", Constants.TYPE_COMMENT);
        title = getIntent().getStringExtra("title");
        fid = getIntent().getStringExtra("fid");
        tid = getIntent().getStringExtra("tid");
        pid = getIntent().getStringExtra("pid");
    }

    @Override
    protected boolean isApplyStatusBarTranslucency() {
        return true;
    }

    @Override
    protected boolean isApplyStatusBarColor() {
        return true;
    }

    @Override
    public void renderError(BaseError error) {
        if (error != null) {
            System.out.println(error.text);
            new MaterialDialog.Builder(this)
                    .title("温馨提示")
                    .content(error.text)
                    .cancelable(false)
                    .positiveText("确定")
                    .callback(new MaterialDialog.ButtonCallback() {
                        @Override
                        public void onPositive(MaterialDialog dialog) {
                            finish();
                        }
                    })
                    .show();
        }
    }

    @Override
    public void renderExam(Exam exam) {
        if (exam != null) {
            new MaterialDialog.Builder(this)
                    .title("温馨提示")
                    .content(exam.title)
                    .cancelable(false)
                    .positiveText("开始答题")
                    .negativeText("放弃答题")
                    .callback(new MaterialDialog.ButtonCallback() {
                        @Override
                        public void onPositive(MaterialDialog dialog) {
                            BrowserActivity.startActivity(PostActivity.this, exam.url, false);
                        }

                        @Override
                        public void onNegative(MaterialDialog dialog) {
                            super.onNegative(dialog);
                        }
                    }).show();
        }
    }

    @Override
    public void showLoading() {
        if (!mDialog.isShowing() && !isFinishing()) {
            mDialog.show();
        }
    }

    @Override
    public void hideLoading() {
        if (mDialog.isShowing() && !isFinishing()) {
            mDialog.dismiss();
        }
    }

    @Override
    public void postSuccess() {
        Observable.timer(2, TimeUnit.SECONDS).subscribe(aLong -> finish());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_post, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_send) {
            send();
        } else if (id == R.id.action_camera) {
            GalleryActivity.startActivity(this, selectImages);
        } else if (id == android.R.id.home) {
            finish();
        }
        return true;
    }

    private void send() {

    }
}
