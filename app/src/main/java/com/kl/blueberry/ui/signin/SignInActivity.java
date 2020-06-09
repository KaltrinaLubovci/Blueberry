package com.kl.blueberry.ui.signin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.InputType;
import android.text.method.PasswordTransformationMethod;
import android.text.method.TransformationMethod;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.kl.blueberry.R;
import com.kl.blueberry.databinding.SignInActivityBinding;
import com.kl.blueberry.events.OpenActivityEvent;
import com.kl.blueberry.utils.ParentActivity;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import static com.kl.blueberry.utils.Usage.showToast;

public class SignInActivity extends ParentActivity {

    SignInActivityBinding binding;
    String username;
    String password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.sign_in_activity);
        binding.setViewModel(new SignInViewModel());
        customizeTextInput();
        onClicks();
    }

    private void onClicks(){

        binding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateData()){
                    showToast(SignInActivity.this, "Data is ok!");
                }
            }
        });
    }
    private void customizeTextInput() {

        EditText etEmail = binding.layoutUsername.findViewById(R.id.et_text_input);
        TextView tvEmailHint = binding.layoutUsername.findViewById(R.id.tv_label);
        EditText etPassword = binding.layoutPassword.findViewById(R.id.et_text_input);
        TextView tvPasswordHint = binding.layoutPassword.findViewById(R.id.tv_label);

        etPassword.setHint(R.string.password);
        tvPasswordHint.setText(R.string.password);

//        etEmail.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
        etEmail.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    etEmail.setHint("");
                    tvEmailHint.setVisibility(View.VISIBLE);
                } else {
                    if (etEmail.getText().toString().isEmpty()) {
                        etEmail.setHint(R.string.username);
                        tvEmailHint.setVisibility(View.GONE);
                    } else {
                        tvEmailHint.setVisibility(View.VISIBLE);
                    }
                }
            }
        });

        etPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
        etPassword.setImeOptions(EditorInfo.IME_ACTION_DONE);
        etPassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                etPassword.setImeOptions(EditorInfo.IME_ACTION_DONE);
                if (hasFocus) {
                    etPassword.setHint("");
                    tvPasswordHint.setVisibility(View.VISIBLE);
                } else {
                    if (etPassword.getText().toString().isEmpty()) {
                        etPassword.setHint("Password");
                        tvPasswordHint.setVisibility(View.GONE);
                    } else {
                        tvPasswordHint.setVisibility(View.VISIBLE);
                    }
                }
            }
        });

        etPassword.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE){
                    v.clearFocus();
                }
                return false;
            }
        });

    }

    private Boolean validateData(){
        EditText etUsername = binding.layoutUsername.findViewById(R.id.et_text_input);
        username = etUsername.getEditableText().toString();
        EditText etPassword = binding.layoutPassword.findViewById(R.id.et_text_input);
        password = etPassword.getEditableText().toString();

        if (username.isEmpty() && !password.isEmpty()) {
            showToast(this, "Username field cannot be empty!");
            return false;
        } else if (!username.isEmpty() && password.isEmpty()) {
            showToast(this, "Password field cannot be empty!");
            return false;
        } else if (username.isEmpty() && password.isEmpty()) {
            showToast(this,"Please fill all fields!");
            return false;
        } else {
           return true;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(OpenActivityEvent openActivityEvent){
        Intent intent = new Intent(this, openActivityEvent.getActivity().getClass());
        startActivity(intent);
    }
}
