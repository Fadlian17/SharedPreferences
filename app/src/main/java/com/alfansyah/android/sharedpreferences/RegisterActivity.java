package com.alfansyah.android.sharedpreferences;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {

    private EditText mViewUser,mViewPassword,mViewRepassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //inisialisasi variabel dengan form user,form password dan form repassword
        mViewUser = findViewById(R.id.et_emailSignup);
        mViewPassword = findViewById(R.id.et_passwordSignup);
        mViewRepassword = findViewById(R.id.et_passwordSignup2);

        //menjalankan method razia() jika button register disentuh
        mViewRepassword.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_NULL){
                    razia();
                    return true;
                }
                return false;
            }
        });
        //menjalankan method razia() apabila button disentuh
        findViewById(R.id.button_signupSignup).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                razia();
            }
        });
    }

    //cek input yang dihasilkan dari form user, form password, dan form repassword
    private void razia(){
        //reset semua error dan fokus menjadi default
        mViewUser.setError(null);
        mViewPassword.setError(null);
        mViewRepassword.setError(null);
        View fokus = null;
        boolean cancel = false;

        //Mengambil text dari Form User, Password, Repassword dengan variable baru bertipe String
        String repassword = mViewRepassword.getText().toString();
        String user = mViewUser.getText().toString();
        String password = mViewPassword.getText().toString();

        //Jika form user kosong atau MEMENUHI kriteria di Method cekUser() maka, Set error di Form-
        //         * User dengan menset variable fokus dan error di Viewnya juga cancel menjadi true

        if (TextUtils.isEmpty(user)) {
            mViewUser.setError("This field is required");
            fokus = mViewUser;
            cancel = true;
        }else if(cekUser(user)){
            mViewUser.setError("This Username is already exist");
            fokus = mViewUser;
            cancel = true;
        }

        //Jika form password kosong atau MEMENUHI kriteria di Method cekPassword() maka, Set error di Form-
        //         * User dengan menset variable fokus dan error di Viewnya juga cancel menjadi true

        if (TextUtils.isEmpty(password)) {
            mViewPassword.setError("This field is required");
            fokus = mViewPassword;
            cancel = true;
        }else if(!cekPassword(password,repassword)){
            mViewRepassword.setError("This Password is incorrect");
            fokus = mViewRepassword;
            cancel = true;
        }

        if (cancel){
            fokus.requestFocus();
        }else {
            Preferences.setRegisteredUser(getBaseContext(),user);
            Preferences.setRegisteredPass(getBaseContext(),password);
            finish();
        }
    }

    //true jika parameter sama dengan data user yang terdaftar di preference
    private boolean cekUser(String user) {
        return user.equals(Preferences.getRegisteredUser(getBaseContext()));
    }

    //true jika parameter sama dengan data password yang terdaftar di preference
    private boolean cekPassword(String password , String repassword) {
        return password.equals(repassword);
    }
}
