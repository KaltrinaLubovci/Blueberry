package com.kl.blueberry.ui.signin;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.InputType;
import android.text.method.PasswordTransformationMethod;
import android.text.method.TransformationMethod;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.kl.blueberry.R;
import com.kl.blueberry.data.Database;
import com.kl.blueberry.databinding.SignInActivityBinding;
import com.kl.blueberry.databinding.SignUpActivityBinding;
import com.kl.blueberry.events.OpenActivityEvent;
import com.kl.blueberry.model.register_user.User;
import com.kl.blueberry.ui.MainActivity;
import com.kl.blueberry.ui.sign_up.SignUpActivity;
import com.kl.blueberry.utils.ParentActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import static com.kl.blueberry.utils.Usage.showToast;

public class SignInActivity extends ParentActivity {
    EditText  etUserName, etPassword;
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
        registerSignin();


    }

    private void onClicks(){

        binding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateData()){
                    showToast(SignInActivity.this, "Data is ok!");
                    EventBus.getDefault().post(new OpenActivityEvent(new MainActivity()));
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

    public  void registerSignin(){
      binding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckUsername(etUserName.getText().toString(),etPassword.getText().toString());
                /*if(etUsername.getText().toString().equals("admin") &&
                etPassword.getText().toString().equals("admin"))
                {
                    Toast.makeText(Main2Activity.this,getString(R.string.loguar_sukses),
                            Toast.LENGTH_LONG).show();


                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(Main2Activity.this);

                    LayoutInflater layoutInflater = getLayoutInflater();
                    alertDialog.setView(layoutInflater.inflate(R.layout.custom_dialog_layout,null));
                    alertDialog.setPositiveButton(R.string.positive_button, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent main3Activity = new Intent(Main2Activity.this,Main3Activity.class);
                            main3Activity.putExtra("username",etUsername.getText().toString());
                            startActivity(main3Activity);
                        }
                    });
                    alertDialog.setNegativeButton(R.string.negative_button, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    alertDialog.show();


                }
                else
                {
                    etPassword.setError(getString(R.string.kredencialet_gabim));
                    etPassword.requestFocus();
                }*/
            }
        });


        binding.btnLogin.setOnClickListener(new View.OnClickListener() {//btnSignIn jo shume e sigurt a osht btnLogin apo btnSignUp
            @Override
            public void onClick(View v) {
                Intent intentSignup= new Intent(SignInActivity.this, SignUpActivity.class);
                startActivity(intentSignup);

            }
        });
    }

    public void CheckUsername(String username, String password)
    {
        SQLiteDatabase objDb = new Database(SignInActivity.this).getReadableDatabase();

        Cursor c = objDb.query(Database.UserTable,
                new String[]{User.ID,User.Fullname, User.Username, User.Email,User.Password},
                User.Username+"=?",new String[]{username},"","","");
        /*Cursor c = objDb.rawQuery("select * from "+ Databaza.PerdoruesitTable+" where "+
                Perdoruesi.Username+" =?",new String[]{username});*/
        if(c.getCount()==1)
        {
            c.moveToFirst();
            String dbUsername = c.getString(3);
            String dbPassword = c.getString(4);

            if(username.equals(dbUsername) &&
                    password.equals(dbPassword))
            {
                Toast.makeText(SignInActivity.this,getString(R.string.loguar_sukses),
                        Toast.LENGTH_LONG).show();


                AlertDialog.Builder alertDialog = new AlertDialog.Builder(SignInActivity.this);

                LayoutInflater layoutInflater = getLayoutInflater();
                alertDialog.setView(layoutInflater.inflate(R.layout.customize_text_input,null));////// customize_text_input???
                alertDialog.setPositiveButton(R.string.positive_button, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent signinviewmodel = new Intent(SignInActivity.this,SignInViewModel.class);
                        signinviewmodel.putExtra("username",etUserName.getText().toString());
                        startActivity(signinviewmodel);
                    }
                });
                alertDialog.setNegativeButton(R.string.negative_button, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                alertDialog.show();


            }
            else
            {
                etPassword.setError(getString(R.string.kredencialet_gabim));
                etPassword.requestFocus();
            }
        }
        else
        {
            etPassword.setError(getString(R.string.kredencialet_gabim));
            etPassword.requestFocus();
        }
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(OpenActivityEvent openActivityEvent){
        Intent intent = new Intent(this, openActivityEvent.getActivity().getClass());
        startActivity(intent);
    }
}
