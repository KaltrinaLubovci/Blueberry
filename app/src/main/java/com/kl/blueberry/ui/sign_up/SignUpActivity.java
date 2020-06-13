package com.kl.blueberry.ui.sign_up;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.kl.blueberry.R;
import com.kl.blueberry.data.Database;
import com.kl.blueberry.databinding.SignUpActivityBinding;
import com.kl.blueberry.events.OpenActivityEvent;
import com.kl.blueberry.model.register_user.User;
import com.kl.blueberry.utils.ParentActivity;
import static com.kl.blueberry.utils.Usage.showToast;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class SignUpActivity extends ParentActivity {

    EditText etFullName, etUserName, etEmail, etPassword, etConfirmPassword;
    TextView tvFullNameLabel, tvUsernameLabel, tvEmailLabel, tvPasswordLabel, tvConfirmPasswordLabel;
    String fullName, username, email, password, confirmPassword;


    SignUpActivityBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.sign_up_activity);
        binding.setViewModel(new SignUpViewModel());
        init();
        onClicks();
    }

    void init() {
        etFullName = binding.layoutFullName.findViewById(R.id.et_text_input);
        etUserName = binding.layoutUsername.findViewById(R.id.et_text_input);
        etEmail = binding.layoutEmail.findViewById(R.id.et_text_input);
        etPassword = binding.layoutPassword.findViewById(R.id.et_text_input);
        etConfirmPassword = binding.layoutConfirmPassword.findViewById(R.id.et_text_input);

        tvFullNameLabel = binding.layoutFullName.findViewById(R.id.tv_label);
        tvUsernameLabel = binding.layoutUsername.findViewById(R.id.tv_label);
        tvEmailLabel = binding.layoutEmail.findViewById(R.id.tv_label);
        tvPasswordLabel = binding.layoutPassword.findViewById(R.id.tv_label);
        tvConfirmPasswordLabel = binding.layoutConfirmPassword.findViewById(R.id.tv_label);

        etFullName.setTextColor(getResources().getColor(R.color.colorWhite));
        etUserName.setTextColor(getResources().getColor(R.color.colorWhite));
        etEmail.setTextColor(getResources().getColor(R.color.colorWhite));
        etPassword.setTextColor(getResources().getColor(R.color.colorWhite));
        etConfirmPassword.setTextColor(getResources().getColor(R.color.colorWhite));

        etFullName.setHintTextColor(getResources().getColor(R.color.colorLightBlue));
        etUserName.setHintTextColor(getResources().getColor(R.color.colorLightBlue));
        etEmail.setHintTextColor(getResources().getColor(R.color.colorLightBlue));
        etPassword.setHintTextColor(getResources().getColor(R.color.colorLightBlue));
        etConfirmPassword.setHintTextColor(getResources().getColor(R.color.colorLightBlue));

        tvFullNameLabel.setTextColor(getResources().getColor(R.color.colorWhite));
        tvUsernameLabel.setTextColor(getResources().getColor(R.color.colorWhite));
        tvEmailLabel.setTextColor(getResources().getColor(R.color.colorWhite));
        tvPasswordLabel.setTextColor(getResources().getColor(R.color.colorWhite));
        tvConfirmPasswordLabel.setTextColor(getResources().getColor(R.color.colorWhite));

        tvFullNameLabel.setBackgroundColor(getResources().getColor(R.color.colorDarkBlue));
        tvUsernameLabel.setBackgroundColor(getResources().getColor(R.color.colorDarkBlue));
        tvEmailLabel.setBackgroundColor(getResources().getColor(R.color.colorDarkBlue));
        tvPasswordLabel.setBackgroundColor(getResources().getColor(R.color.colorDarkBlue));
        tvConfirmPasswordLabel.setBackgroundColor(getResources().getColor(R.color.colorDarkBlue));

        customizeViews();
    }


    void customizeViews() {
        etFullName.setHint(R.string.fullname);
        tvFullNameLabel.setText(R.string.fullname);

        etUserName.setHint(R.string.username);
        tvUsernameLabel.setText(R.string.username);

        etEmail.setHint(R.string.email);
        tvEmailLabel.setText(R.string.email);

        etPassword.setHint(R.string.password);
        tvPasswordLabel.setText(R.string.password);

        etConfirmPassword.setHint(R.string.confirm_password);
        tvConfirmPasswordLabel.setText(R.string.confirm_password);

        etFullName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    etFullName.setHint("");
                    tvFullNameLabel.setVisibility(View.VISIBLE);
                } else {
                    if (etFullName.getText().toString().isEmpty()) {
                        etFullName.setHint(R.string.fullname);
                        tvFullNameLabel.setVisibility(View.GONE);
                    } else {
                        tvFullNameLabel.setVisibility(View.VISIBLE);
                    }
                }
            }
        });

        etUserName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    etUserName.setHint("");
                    tvUsernameLabel.setVisibility(View.VISIBLE);
                } else {
                    if (etUserName.getText().toString().isEmpty()) {
                        etUserName.setHint(R.string.username);
                        tvUsernameLabel.setVisibility(View.GONE);
                    } else {
                        tvUsernameLabel.setVisibility(View.VISIBLE);
                    }
                }
            }
        });

        etEmail.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    etEmail.setHint("");
                    tvEmailLabel.setVisibility(View.VISIBLE);
                } else {
                    if (etEmail.getText().toString().isEmpty()) {
                        etEmail.setHint(R.string.email);
                        tvEmailLabel.setVisibility(View.GONE);
                    } else {
                        tvEmailLabel.setVisibility(View.VISIBLE);
                    }
                }
            }
        });

        etPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
        etPassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    etPassword.setHint("");
                    tvPasswordLabel.setVisibility(View.VISIBLE);
                } else {
                    if (etPassword.getText().toString().isEmpty()) {
                        etPassword.setHint(R.string.password);
                        tvPasswordLabel.setVisibility(View.GONE);
                    } else {
                        tvPasswordLabel.setVisibility(View.VISIBLE);
                    }
                }
            }
        });

        etConfirmPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
        etConfirmPassword.setImeOptions(EditorInfo.IME_ACTION_DONE);
        etConfirmPassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                etConfirmPassword.setImeOptions(EditorInfo.IME_ACTION_DONE);
                if (hasFocus) {
                    etConfirmPassword.setHint("");
                    tvConfirmPasswordLabel.setVisibility(View.VISIBLE);
                } else {
                    if (etConfirmPassword.getText().toString().isEmpty()) {
                        etConfirmPassword.setHint(R.string.confirm_password);
                        tvConfirmPasswordLabel.setVisibility(View.GONE);
                    } else {
                        tvConfirmPasswordLabel.setVisibility(View.VISIBLE);
                    }
                }
            }
        });

        etConfirmPassword.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    v.clearFocus();
                }
                return false;
            }
        });

    }

    private Boolean validateFields() {
        fullName = etFullName.getEditableText().toString();
        username = etUserName.getEditableText().toString();
        email = etEmail.getEditableText().toString();
        password = etPassword.getEditableText().toString();
        confirmPassword = etConfirmPassword.getEditableText().toString();

        if (fullName.isEmpty() && !username.isEmpty() && !email.isEmpty() && !password.isEmpty() && !confirmPassword.isEmpty()) {
            showToast(this, "Fullname field cannot be empty!");
            return false;
        } else if (!fullName.isEmpty() && username.isEmpty() && !email.isEmpty() && !password.isEmpty() && !confirmPassword.isEmpty()) {
            showToast(this, "Username field cannot be empty!");
            return false;
        } else if (!fullName.isEmpty() && !username.isEmpty() && email.isEmpty() && !password.isEmpty() && !confirmPassword.isEmpty()) {
            showToast(this, "Email field cannot be empty!");
            return false;
        } else if (!fullName.isEmpty() && !username.isEmpty() && !email.isEmpty() && password.isEmpty() && !confirmPassword.isEmpty()) {
            showToast(this, "Password field cannot be empty!");
            return false;
        } else if (!fullName.isEmpty() && !username.isEmpty() && !email.isEmpty() && !password.isEmpty() && confirmPassword.isEmpty()) {
            showToast(this, "Confirm password field cannot be empty!");
            return false;
        } else if (fullName.isEmpty() && username.isEmpty() && email.isEmpty() && password.isEmpty() && confirmPassword.isEmpty()) {
            showToast(this, "Please fill all fields!");
            return false;
        } else if (fullName.isEmpty() || username.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            showToast(this, "Please fill all fields");
            return false;
        } else {
            if (password.length() < 8) {
                showToast(this, "Password to short (minimum 8 chars)!");
                return false;
            } else {
                if (!password.equals(confirmPassword)) {
                    showToast(this, "Password and Confirm password doesn\'t match!");
                    return false;
                } else {
                    return true;
                }
            }
        }
    }

    private void onClicks(){
        binding.btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateFields()){
                    showToast(SignUpActivity.this, "Success!");
                    binding.getViewModel().registerUser(SignUpActivity.this, fullName, username, email, password);
                }
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(OpenActivityEvent openActivityEvent){
        Intent intent = new Intent(this, openActivityEvent.getActivity().getClass());
        startActivity(intent);
    }
}
