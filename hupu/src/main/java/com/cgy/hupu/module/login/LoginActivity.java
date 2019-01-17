package com.cgy.hupu.module.login;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.widget.EditText;

import com.afollestad.materialdialogs.MaterialDialog;
import com.cgy.hupu.R;
import com.cgy.hupu.module.BaseSwipeBackActivity;

import javax.inject.Inject;

import butterknife.BindView;

public class LoginActivity extends BaseSwipeBackActivity implements LoginContract.View{

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
    }

    @Inject
    LoginPresenter mPresenter;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.et_userName)
    EditText mEtUserName;
    @BindView(R.id.text_input_userName)
    TextInputLayout mTextInputUserName;
    @BindView(R.id.et_password)
    EditText mEtPassword;
    @BindView(R.id.text_input_password)
    TextInputLayout mTextInputPassword;

    private MaterialDialog mDialog;

    @Override
    public int initContentView() {
        return R.layout.activity_login;
    }

    @Override
    public void initInjector() {
        DaggerLoginComponent.builder()
                .applicationComponent(getApplicationComponent())
                .activityModule(getActivityModule())
                .build()
                .inject(this);
    }

    @Override
    public void initUiAndListener() {
        mPresenter.attachView(this);
        initToolBar(mToolbar);
        setTitle("登录");
        mDialog = new MaterialDialog.Builder(this).title("提示").content("登录中").progress(true, 0).build();
        mEtUserName.addTextChangedListener(new MTextWatcher(mTextInputUserName));
        mEtPassword.addTextChangedListener(new MTextWatcher(mTextInputPassword));

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
    public void showLoading() {
        if (!isFinishing() && mDialog.isShowing()) {
            mDialog.show();
        }
    }

    @Override
    public void hideLoading() {
        if (!isFinishing() && mDialog.isShowing()) {
            mDialog.dismiss();
        }
    }

    @Override
    public void showUserNameError(String error) {
        mTextInputUserName.setError(error);
        mTextInputUserName.setErrorEnabled(true);
    }

    @Override
    public void showPasswordError(String error) {
        mTextInputPassword.setError(error);
        mTextInputPassword.setErrorEnabled(true);
    }

    @Override
    public void loginSuccess() {
        new Handler().postDelayed(() -> finish(), 1500);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    class MTextWatcher implements TextWatcher {
        TextInputLayout textInputLayout;
        public MTextWatcher(TextInputLayout textInputLayout) {
            this.textInputLayout = textInputLayout;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            textInputLayout.setErrorEnabled(false);
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    }
}
