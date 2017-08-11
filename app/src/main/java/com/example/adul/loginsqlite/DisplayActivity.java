package com.example.adul.loginsqlite;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.List;

public class DisplayActivity extends AppCompatActivity {

    DBHelper dbHelper;
    private List<String> listAccount;
    private List<NameAdmin> listModelAdmin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);

        dbHelper = new DBHelper(this);
//        TextView tv = (TextView) findViewById(R.id.txt_user_display);
//        tv.setText(username);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId())
        {
            case R.id.change:

                AlertDialog.Builder builder = new AlertDialog.Builder(DisplayActivity.this);
                LayoutInflater inflater = this.getLayoutInflater();
                View dialogView = inflater.inflate(R.layout.item_row, null);
                final EditText input = (EditText) dialogView.findViewById(R.id.edt_user_form);
                final EditText input_pass = (EditText) dialogView.findViewById(R.id.edt_pass_form);
                final String username = getIntent().getStringExtra("username");
                String password = getIntent().getStringExtra("password");
                input.setText(username);
                input_pass.setText(password);
                builder.setTitle("Change Data Account");

                builder.setView(dialogView);

                // Set up the buttons
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String namUpdate = input.getText().toString();
                        String passUpdate = input_pass.getText().toString();

                        //update the nama
                        Toast.makeText(getApplicationContext(), namUpdate+" "+passUpdate, Toast.LENGTH_SHORT).show();
                        dbHelper.updateEntry(username,namUpdate,passUpdate);


                    }
                });
                builder.setNegativeButton("Kembali", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                builder.show();
                break;
            case R.id.friends:
                startActivity(new Intent(DisplayActivity.this, ListActivity.class));
                break;
            case R.id.about:
                break;
        }
        return true;
    }

    // on back key press exit the application.

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent intent = new Intent(DisplayActivity.this,
                    SplashActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtra("EXIT", true);
            startActivity(intent);
        }
        return super.onKeyDown(keyCode, event);
    }
}
