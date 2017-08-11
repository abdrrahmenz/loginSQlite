package com.example.adul.loginsqlite;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    DBHelper db;
    EditText username, password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



//        db = new DBHelper(this);
        username = (EditText) findViewById(R.id.edt_user_signin);
        password = (EditText) findViewById(R.id.edt_pass_signin);
        Button sigup = (Button) findViewById(R.id.btn_signin);
        sigup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSigninClick();
            }
        });
        db = new DBHelper(this);
//        db.openDataBase();
//
//        Log.i("Adul", transList.toString());

//        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, transList);
//        transactionList.setAdapter(adapter);

        TextView txt_signup = (TextView) findViewById(R.id.txt_signup);

        txt_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(i);
            }
        });
        TextView txt_list = (TextView) findViewById(R.id.txt_list);
        txt_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, ListActivity.class);
                startActivity(i);
            }
        });


    }

    public void onSigninClick() {

            String str_user = username.getText().toString();
            String str_pass =  password.getText().toString();

            String pass = db.searchPass(str_user);
            if (str_pass.equals(pass)){
                Intent i = new Intent(MainActivity.this, DisplayActivity.class);
                i.putExtra("username", str_user);
                i.putExtra("password", str_pass);
                startActivity(i);
            }else {
                Toast.makeText(this, "Username & Password don't match! ", Toast.LENGTH_SHORT).show();
            }

    }

    @Override
    protected void onResume() {
        super.onResume();
//        DBHelper dbHelper = new DBHelper(this);
//        transList = (ArrayList<NameAdmin>) dbHelper.getTransactions();
//        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, transList);
//        transactionList.setAdapter(adapter);
    }

    // on back key press exit the application.

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent intent = new Intent(MainActivity.this,
                    SplashActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtra("EXIT", true);
            startActivity(intent);
        }
        return super.onKeyDown(keyCode, event);
    }
}
