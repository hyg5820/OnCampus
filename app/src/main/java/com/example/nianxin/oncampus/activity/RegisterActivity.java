package com.example.nianxin.oncampus.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.nianxin.oncampus.ActivityCollector;
import com.example.nianxin.oncampus.R;
import com.example.nianxin.oncampus.model.User;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobSMS;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
import rx.Subscription;

public class RegisterActivity extends BaseActivity {

    //UI 依赖
    private ProgressBar mRegisterProgressBar;
    private LinearLayout mRegisterForm;
    private AutoCompleteTextView mEditPhone;
    private EditText mEditCode;
    private TextView mBtnGetCode;
    private EditText mEditPwd;
    private Button mBtnRegister;

    private UserRegistTask mAuthTask = null;
    private static boolean mVerifyCode = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        //设置注册表单
        mEditPhone = findViewById(R.id.edit_register_phone);
        mEditCode = findViewById(R.id.edit_register_code);
        mEditPwd = findViewById(R.id.edit_register_pwd);

        mBtnGetCode = findViewById(R.id.btn_register_code);
        mBtnGetCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requestSMSCode();
            }
        });
        mBtnRegister = findViewById(R.id.btn_register);
        mBtnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String code = mEditCode.getText().toString();
                String phone = mEditPhone.getText().toString();
                //if(verifySMSCode(phone,code)){
                    attemptRegist();
              //  }
            }
        });
        mRegisterForm = findViewById(R.id.layout_register_form);
        mRegisterProgressBar = findViewById(R.id.register_progress);
    }

    private Boolean verifySMSCode(String phone,String code){
        mVerifyCode = false;
        BmobSMS.verifySmsCode(phone, code, new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e==null){
                    //短信验证成功
                    mVerifyCode = true;
                }else {
                    //短信验证失败
                    mVerifyCode = false;
                }
            }
        });
        return mVerifyCode;
    }

    /**
     * 获取验证码
     */
    private void requestSMSCode(){
        String phone = mEditPhone.getText().toString();
        final MyTimer myTimer = new MyTimer(60000,1000);
        myTimer.start();
        BmobSMS.requestSMSCode(phone, "风云校园", new QueryListener<Integer>() {
            @Override
            public void done(Integer integer, BmobException e) {
                if (e ==null){//验证码发送成功

                }else {
                    myTimer.cancel();
                }
            }
        });
    }


    /**
     * 尝试以表单信息进行注册
     * 如果存在表单错误（手机号无效，密码不合格等）
     * 则会显示错误，并且不进行注册尝试
     */
    private void attemptRegist(){
        if (mAuthTask!=null){
            return;
        }

        //重置错误
        mEditPhone.setError(null);
        mEditPwd.setError(null);

        //暂存注册信息
        String phone = mEditPhone.getText().toString();
        String password = mEditPwd.getText().toString();

        boolean cancel = false;
        View focusView =null;

        //如果用户输入有效的密码，请检查它
        if(!TextUtils.isEmpty(password) && !isPasswordValid(password)){
            mEditPwd.setError("密码太短");
            focusView = mEditPwd;
            cancel = true;
        }
        //检查用户输入的手机号

        if (cancel){
            //存在错误，不尝试注册，并且将表单焦点放在错误中
            focusView.requestFocus();
        }else {
            //显示进度条框，并启动后台任务执行注册尝试
            showProgress(true);
            mAuthTask = new UserRegistTask(phone,password);
            mAuthTask.execute((Void) null);
        }
    }

    private Boolean isPasswordValid(String password){
        return true;
    }
    public class UserRegistTask extends AsyncTask<Void,Void,Boolean>{

        private final String mPhone;

        private final String mPassword;

        public UserRegistTask(String phone,String password) {
            mPhone = phone;
            mPassword = password;
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            try {
                // 模拟网络访问。
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
                return false;
            }

            //注册验证是否手机号已经注册
            if (mPhone.equals("15882079478")){
                return false;
            }else {

                User user = new User();
                user.setUsername(mPhone);
                user.setPassword(mPassword);
                user.signUp(new SaveListener<User>() {

                    @Override
                    public void done(User user, BmobException e) {
                        if (e == null) {
                            //注册成功
                            Log.d("Register", "Register Success");
                            ActivityCollector.removeActivity(RegisterActivity.this);
                            LoginActivity.actionStart(RegisterActivity.this);
                        } else {
                            //注册失败
                            Log.d("RegisterFail", "Register Fail");
                        }
                    }

                });

            }
            return true;
        }


        @Override
        protected void onCancelled() {
            mAuthTask = null;
            showProgress(false);
        }
    }

    /**
     * 显示进度UI并隐藏窗体
     * @param show
     */
    private void showProgress(final Boolean show){
        // 在Honeycomb MR2, 我们有 ViewPropertyAnimator api,
        // 这允许非常容易的动画。如果可用, 请在进度微调框中使用这些 api 淡出。
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mRegisterForm.setVisibility(show ? View.GONE : View.VISIBLE);
            mRegisterForm.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mRegisterForm.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mRegisterProgressBar.setVisibility(show ? View.VISIBLE : View.GONE);
            mRegisterProgressBar.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mRegisterProgressBar.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // ViewPropertyAnimator api 不可用, 因此只需显示和隐藏相关的 UI 组件即可。
            mRegisterProgressBar.setVisibility(show ? View.VISIBLE : View.GONE);
            mRegisterForm.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }
    /**
     * 倒计时内部类
     * @author nianxin
     * @time 2018/4/7 11:51
     */
    class MyTimer extends CountDownTimer{

        public MyTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long l) {
            mBtnGetCode.setText("重新发送"+"("+l/1000+")");
            mBtnGetCode.setEnabled(false);
        }

        @Override
        public void onFinish() {
            mBtnGetCode.setText("重新发送");
            mBtnGetCode.setEnabled(false);
        }
    }
}
