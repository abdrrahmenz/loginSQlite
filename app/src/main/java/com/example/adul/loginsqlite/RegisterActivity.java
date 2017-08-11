package com.example.adul.loginsqlite;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {

    DatabaseHelper helper ;
    EditText username, password, confirm_pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        helper = new DatabaseHelper(this);
        username = (EditText) findViewById(R.id.edt_user_signup);
        password = (EditText) findViewById(R.id.edt_pass_signup);
        confirm_pass = (EditText) findViewById(R.id.edt_passconfirm_signup);
        Button sigup = (Button) findViewById(R.id.btn_signup);

        sigup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSignupClick();
            }
        });
    }

    public void onSignupClick() {
//        if (v.getId() == R.id.btn_signup){



            String usern = username.getText().toString();
            String passn = password.getText().toString();
            String passconfn = confirm_pass.getText().toString();

            if (!passn.equals(passconfn)){
                Toast.makeText(this, "Password don't match! ", Toast.LENGTH_SHORT).show();
            }else {

                NameAdmin n = new NameAdmin();
                n.setUsername(usern);
                n.setPassword(passn);

                helper.insertUser(n);
            }
            startActivity(new Intent(RegisterActivity.this, MainActivity.class));
//        }
    }

    // on back key press exit the application.

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent intent = new Intent(RegisterActivity.this,
                    SplashActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtra("EXIT", true);
            startActivity(intent);
        }
        return super.onKeyDown(keyCode, event);
    }
}
