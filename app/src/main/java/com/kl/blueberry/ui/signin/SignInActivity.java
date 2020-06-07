package com.kl.blueberry.ui.signin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;

import com.kl.blueberry.R;
import com.kl.blueberry.databinding.SignInActivityBinding;

public class SignInActivity extends AppCompatActivity {

    SignInActivityBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.sign_in_activity);
        binding.setViewModel(new SignInViewModel());
    }
}
