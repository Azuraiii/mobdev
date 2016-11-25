package com.example.azkei.mobdev_1;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import static java.lang.Boolean.TRUE;

/**
 * Created by azkei on 23/11/2016.
 */

public class logIn extends AppCompatActivity {

    DBManager db = new DBManager(this);

    Button loginButton;
    Button registerButton;

    EditText loginText;
    EditText passwordText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.login);
        //buttons
        loginButton = (Button) findViewById(R.id.loginID);
        registerButton = (Button) findViewById(R.id.registerID);
        //edit texts
        loginText = (EditText)findViewById(R.id.username);
        passwordText = (EditText)findViewById(R.id.password);

        registerButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                try{
                    //compare variables
                    String var1;
                    String var2;
                    //user input turned into Strings
                    String registerUser = loginText.getText().toString();
                    String registerPassword = passwordText.getText().toString();

                    //searches data base for name and password
                    Cursor a = db.searchAccount("SELECT Accounts.name FROM Accounts WHERE name LIKE "+"'"+registerUser+"';");
                    Cursor b = db.searchPassword("SELECT Accounts.password FROM Accounts WHERE password LIKE "+registerPassword+"';");

                    var1 = a.getString(a.getColumnIndex("name"));
                    var2 = b.getString(b.getColumnIndex("password"));
                    //compares database value and input value
                    if(var1.equals(registerUser) == TRUE && var2.equals(registerPassword)== TRUE){
                        Toast.makeText(logIn.this, "Account has already been created", Toast.LENGTH_LONG).show();
                    }else {
                        db.open();
                        db.insertAccount(loginText.getText().toString(), passwordText.getText().toString());

                        db.close();
                        Toast.makeText(logIn.this, "Account Created !", Toast.LENGTH_LONG).show();
                    }
                }catch(Exception ex){
                    Toast.makeText(logIn.this, "Error Opening Database", Toast.LENGTH_LONG).show();
                }
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                try{
                    String var1;
                    String var2;
                    int id;

                    String loginUser = loginText.getText().toString();
                    String loginPassword = passwordText.getText().toString();
                    //search database name and password in account
                    //put that into a cursor
                    Cursor c = db.searchAccount("SELECT Accounts.name FROM Accounts WHERE name LIKE "+"'"+loginUser+"';");
                    Cursor d = db.searchPassword("SELECT Accounts.password FROM Accounts WHERE password LIKE "+"'"+loginPassword+"';");

                    var1 = c.getString(c.getColumnIndex("name"));
                    var2 = d.getString(d.getColumnIndex("password"));

                    //if query results match with user input
                    //user has logged in
                    if(var1.equals(loginUser) == TRUE && var2.equals(loginPassword) == TRUE){
                        //get id of the user's account

                        Cursor a = db.searchID("SELECT _id from Accounts WHERE name LIKE "+"'"+loginUser+ "'" +
                                "AND password LIKE "+"'"+loginPassword+"';");
                        id = a.getInt(a.getColumnIndex("_id"));

                        //move to showlist activity
                        //pass the id value to the next activity.
                        Intent intent = new Intent(logIn.this, ShowList.class);
                        intent.putExtra("ID",id);
                        startActivity(intent);

                        Toast.makeText(logIn.this, "You have successfully signed in", Toast.LENGTH_LONG).show();
                    }else{
                        Toast.makeText(logIn.this, "Account Does Not Exist", Toast.LENGTH_LONG).show();
                    }
                }catch(Exception e){
                    Log.e("DB ERROR",String.valueOf(e));
                }
            }
        });
    }
}
