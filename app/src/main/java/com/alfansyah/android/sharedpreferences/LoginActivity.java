package com.alfansyah.android.sharedpreferences;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    private EditText mViewUser,mViewPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //inisialisasi variable dengan form user dan form password di layout activity
        mViewUser = findViewById(R.id.et_emailSignin);
        mViewPassword = findViewById(R.id.et_passwordSignin);

        //menjalankan method()razia apabila data null
        mViewPassword.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                if (actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_NULL){
                    razia();
                    return true;
                }
                return false;
            }
        });

        //menjalankan method()razia apabila button login disentuh
        findViewById(R.id.button_signinSignin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                razia();
            }
        });

        //menjalankan intent apabila button register disentuh
        findViewById(R.id.button_signupSignin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getBaseContext(),RegisterActivity.class));
            }
        });
    }

    @Override
    protected void onStart(){
        super.onStart();
        if (Preferences.getLoggedInStatus(getBaseContext())){
            startActivity(new Intent(getBaseContext(),MainActivity.class));
            finish();
        }
    }

    //cek input dari form user dan password dan memberikan akses ke MainActivity
    private void razia(){
        //reset error jadi default
        mViewUser.setError(null);
        mViewPassword.setError(null);
        View fokus = null;
        boolean cancel = false;

        //ambil teks dari form user dan form password dengan tipe variabel berupa string
        String user = mViewUser.getText().toString();
        String password = mViewPassword.getText().toString();

        //Jika form user kosong atau TIDAK memenuhi kriteria di Method cekUser() maka, Set error
        if (TextUtils.isEmpty(user)){
            mViewUser.setError("This field is required");
            fokus = mViewUser;
            cancel = true;
        }else if (!cekUser(user)){
            mViewUser.setError("This Username not found");
            fokus = mViewUser;
            cancel = true;
        }

        //Jika form Password kosong atau TIDAK memenuhi kriteria di Method cekPassword() maka, Set error
        if (TextUtils.isEmpty(password)){
            mViewPassword.setError("This field is required");
            fokus = mViewPassword;
            cancel = true;
        }else if (!cekPassword(password)){
            mViewPassword.setError("This password incorrect");
            fokus = mViewPassword;
            cancel = true;
        }

        //jika cancel true , variabel fokus mendapatkan fokus
        if (cancel) fokus.requestFocus();
        else masuk();
    }


    private void masuk() {
        Preferences.setLoggedInUser(getBaseContext(),Preferences.getRegisteredUser(getBaseContext()));
        Preferences.setLoggedInStatus(getBaseContext(),true);
        startActivity(new Intent(getBaseContext(),MainActivity.class));
        finish();
    }

    //true jika parameter sama dengan data user yang terdaftar di preference
    private boolean cekUser(String user) {
        return user.equals(Preferences.getRegisteredUser(getBaseContext()));
    }

    //true jika parameter sama dengan data user yang terdaftar di preference
    private boolean cekPassword(String password) {
        return password.equals(Preferences.getRegisteredPass(getBaseContext()));
    }
}
