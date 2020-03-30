package com.alfansyah.android.sharedpreferences;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //dekralasi variabel
        TextView nama = findViewById(R.id.tv_namaMain);

        //set label ketika data user sedang login
        nama.setText(Preferences.getLoggedInUser(getBaseContext()));

        //set status dan user sedang login menjadi default atau kosong
        //data Preferences lokasi menuju Login Activity
        findViewById(R.id.button_logoutMain).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //hapus status login
                Preferences.clearLoggedInUser(getBaseContext());
                startActivity(new Intent(getBaseContext(), LoginActivity.class));
                finish();
            }
        });
    }
}
