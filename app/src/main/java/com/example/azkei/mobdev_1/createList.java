package com.example.azkei.mobdev_1;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by azkei on 23/11/2016.
 */

public class createList extends AppCompatActivity{

    DBManager db = new DBManager(this);
    int id;
    EditText itemName;
    EditText itemCost;
    EditText itemAmount;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.createlist);

        final Button setButton = (Button)findViewById(R.id.submit_db);
        final Button goBack = (Button)findViewById(R.id.goback);
        final int id = getIntent().getExtras().getInt("ID");

        setButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                try{
                    db.open();
                    itemName = (EditText)findViewById(R.id.editText_itemname);
                    itemCost = (EditText)findViewById(R.id.editText_itemcost);
                    itemAmount = (EditText)findViewById(R.id.editText_itemamount);
                    db.insertList(id,itemName.getText().toString(),itemCost.getText().toString(),itemAmount.getText().toString());
                    db.close();
                    Toast.makeText(createList.this, "Data Inserted", Toast.LENGTH_LONG).show();
                }catch(Exception ex){
                    Toast.makeText(createList.this, "Error Opening Database", Toast.LENGTH_LONG).show();
                }
            }
        });

        goBack.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                finish();
            }
        });
    }
}
