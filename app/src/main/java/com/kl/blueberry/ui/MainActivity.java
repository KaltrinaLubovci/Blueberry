package com.kl.blueberry.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.kl.blueberry.R;
import com.kl.blueberry.databinding.ActivityMainBinding;

import java.io.IOException;

import dagger.android.AndroidInjection;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AndroidInjection.inject(this);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        binding.btnTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Hello World!", Toast.LENGTH_SHORT).show();
            }
        });
    }


}
