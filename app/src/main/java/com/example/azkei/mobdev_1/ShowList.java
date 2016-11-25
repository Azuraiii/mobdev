package com.example.azkei.mobdev_1;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class ShowList extends AppCompatActivity {
    //pass our context
    DBManager db = new DBManager(this);
    ListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.showlist);

        Button createButton = (Button)findViewById(R.id.create_button);
        list = (ListView) findViewById(R.id.mylistview);
        //stores ID of the logged in account
        final int id = getIntent().getExtras().getInt("ID");

        //pulls out database items when activity starts
        try {
            db.open();
            Toast.makeText(ShowList.this, "DatabaseOpened", Toast.LENGTH_LONG).show();
            Cursor result = db.getAll("SELECT Shopping_List.item, Shopping_List.cost, Shopping_List.amount FROM Shopping_List WHERE customer_id = "+"'"+id+"';");

            if(result.getCount() == 0) {
                Toast.makeText(ShowList.this, "There is no list for this account", Toast.LENGTH_LONG).show();
            }else{
                ListAdapter cursorAdapter = new ListAdapter(getApplicationContext(), result);
                list.setAdapter(cursorAdapter);
                db.close();
                Toast.makeText(ShowList.this, "List has been pulled", Toast.LENGTH_LONG).show();
            }


        }catch(Exception ex){
            Toast.makeText(ShowList.this, "Error Opening Database.", Toast.LENGTH_LONG).show();
        }

        //move to create account page
        createButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(ShowList.this,createList.class);
                //pass id to the create list
                intent.putExtra("ID",id);
                startActivity(intent);
            }
        });

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> av, View view, int position, long arg) {
                Cursor mycursor = (Cursor) av.getItemAtPosition(position);
                String selection = mycursor.getString(3);


            }
        });
    }
}
