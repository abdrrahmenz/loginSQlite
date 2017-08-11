package com.example.adul.loginsqlite;

import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ListActivity extends AppCompatActivity {

    DatabaseHelper db;
    DBHelper dbHelper;
    private List<String> listAccount;
    private List<NameAdmin> listModelAdmin;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        listView = (ListView) findViewById(R.id.listview);

        listAccount = new ArrayList<>();
        listModelAdmin = new ArrayList<>();

//        db = new DatabaseHelper(this);
        dbHelper = new DBHelper(this);
        dbHelper.opendatabase();
        //get the all data
        Cursor cursor = dbHelper.readAllDataSantri();
        // is any data?
        if (cursor.moveToFirst()){
            do {
//                NameAdmin admin = new NameAdmin();

                //get the data
                String id = cursor.getString(cursor.getColumnIndex(dbHelper.COL_ID));
                String namaSantri = cursor.getString(cursor.getColumnIndex(dbHelper.COL_USERNAME));
                String asalKota = cursor.getString(cursor.getColumnIndex(dbHelper.COL_PASSWORD));

                //add data to list
                listAccount.add("user : "+namaSantri+" pass : "+asalKota);
                Toast.makeText(this, asalKota+" "+namaSantri, Toast.LENGTH_SHORT).show();
                //set to the model
//                admin.setId(id);
//                admin.setUsername(namaSantri);
//                admin.setPassword(asalKota);
//
//                //add model to data
//                listModelAdmin.add(admin);

            }  while (cursor.moveToNext());
        }

        //prepare adapter
        ArrayAdapter<String> itemsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listAccount);

        //set the adapter
        listView.setAdapter(itemsAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {

                Context context = ListActivity.this;
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                String nama_santri = listModelAdmin.get(position).getUsername();
                String pass = listModelAdmin.get(position).getPassword();
                builder.setTitle("Update user dan pass : "+ nama_santri);

                LinearLayout layout = new LinearLayout(context);
                layout.setOrientation(LinearLayout.VERTICAL);

                // Set up the input
                final EditText input = new EditText(context);

                // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
                input.setInputType(InputType.TYPE_CLASS_TEXT); // | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                input.setText(nama_santri);
                layout.addView(input);

                final EditText input_pass = new EditText(context);
                input_pass.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
                input_pass.setText(pass);
                layout.addView(input_pass);

                builder.setView(layout);

                // Set up the buttons
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String namUpdate = input.getText().toString();
                        String passUpdate = input_pass.getText().toString();

                        //update the nama
                        dbHelper.updateNamaSantri(listModelAdmin.get(position).getId(), namUpdate, passUpdate);

                        //clear the list
                        listModelAdmin.clear();
                        listAccount.clear();

                        //show data
                        showData();

                    }
                });
                builder.setNegativeButton("Kembali", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                builder.show();
            }
        });
    }

    public void showData() {

    }

}
