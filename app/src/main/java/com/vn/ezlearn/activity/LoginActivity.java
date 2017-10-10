package com.vn.ezlearn.activity;

import android.app.ProgressDialog;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.vn.ezlearn.R;
import com.vn.ezlearn.config.AppConfig;
import com.vn.ezlearn.config.EzlearnService;
import com.vn.ezlearn.databinding.ActivityLoginBinding;
import com.vn.ezlearn.modelresult.LoginResult;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class LoginActivity extends AppCompatActivity {
    public static final int LOGIN_REQUEST = 11;
    private ActivityLoginBinding loginBinding;
    private EzlearnService apiService;
    private Subscription mSubscription;
    private LoginResult mLoginResult;
    private ProgressDialog progressDialog;
    private boolean isAttach = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loginBinding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        loginBinding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = loginBinding.edUserName.getText().toString();
                String password = loginBinding.edPassword.getText().toString();
                if (username == null || username.isEmpty()) {
                    Toast.makeText(LoginActivity.this, getString(R.string.please_input_username),
                            Toast.LENGTH_SHORT).show();
                } else if (password == null || password.isEmpty()) {
                    Toast.makeText(LoginActivity.this, getString(R.string.please_input_password),
                            Toast.LENGTH_SHORT).show();
                } else {
                    login(username, password);
                }
            }
        });
    }

    private void login(String username, String password) {
        progressDialog = ProgressDialog.show(this, "", getString(R.string.loading), true, false);
        apiService = MyApplication.with(this).getEzlearnService();
        if (mSubscription != null && !mSubscription.isUnsubscribed())
            mSubscription.unsubscribe();
        mSubscription = apiService.getLogin(username, password)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<LoginResult>() {
                    @Override
                    public void onCompleted() {
                        if (mLoginResult.success && mLoginResult.data != null) {
                            if (mLoginResult.data.message != null
                                    && !mLoginResult.data.message.isEmpty()) {
                                Toast.makeText(LoginActivity.this, mLoginResult.data.message,
                                        Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(LoginActivity.this, getString(R.string.login_success),
                                        Toast.LENGTH_SHORT).show();
                            }
                            AppConfig.getInstance(LoginActivity.this).setToken(
                                    mLoginResult.data.access_token);
                            AppConfig.getInstance(LoginActivity.this).setName(
                                    mLoginResult.data.display_name);
                            setResult(RESULT_OK);
                            finish();
                        } else {
                            Toast.makeText(LoginActivity.this, getString(R.string.error_connect),
                                    Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (isAttach && progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }
                    }

                    @Override
                    public void onNext(LoginResult loginResult) {
                        if (isAttach && progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }
                        if (loginResult != null) {
                            mLoginResult = loginResult;
                        }
                    }
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        isAttach = false;
        if (mSubscription != null && !mSubscription.isUnsubscribed()) mSubscription.unsubscribe();
        mSubscription = null;
    }
}
