package com.amitshekhar.tflite;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

public class Register extends AppCompatActivity {
    Button register,login;
    EditText email,pass,name;
    ImageView im;
    CheckBox c1;
    databasehelper db;
    sharedpreferenceconfig sf;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = new databasehelper(this);
        setContentView(R.layout.activity_register);
        login = findViewById(R.id.btnlogin);
//        db.DeleteAllData();
        email = findViewById(R.id.email);
        pass = findViewById(R.id.pass);
        db = new databasehelper(this);
        name = findViewById(R.id.name);
        im = findViewById(R.id.bear);
        c1 = findViewById(R.id.check);
        sf=new sharedpreferenceconfig(this);
        email.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                Glide.with(getApplicationContext())
                        .asGif()
                        .load(R.drawable.emailgif)
                        .into(im);
            }
        });
        pass.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                Glide.with(getApplicationContext())
                        .asGif()
                        .load(R.drawable.password)
                        .into(im);
            }
        });
        name.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                Glide.with(getApplicationContext())
                        .asGif()
                        .load(R.drawable.emailgif)
                        .into(im);
            }
        });
        c1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (c1.isChecked()) {
                    Glide.with(getApplicationContext())
                            .asGif()
                            .load(R.drawable.showpass)
                            .into(im);
                    pass.setTransformationMethod(null);
                } else {
                    Glide.with(getApplicationContext())
                            .asGif()
                            .load(R.drawable.password)
                            .into(im);
                    pass.setTransformationMethod(new PasswordTransformationMethod());
                }
            }
        });
        register =findViewById(R.id.btnregister);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                db.DeleteAllData();
                if(email.getText().toString().trim().matches("")||name.getText().toString().trim().matches("")||pass.getText().toString().matches("")) {
                    if (email.getText().toString().trim().matches(""))
                        email.setError("Input Mandatory");
                    if (name.getText().toString().trim().matches(""))
                        name.setError("Input Mandatory");
                    if (pass.getText().toString() .matches(""))
                        pass.setError("Input Mandatory");
                }else{

                    Cursor cr=db.ViewData(email.getText().toString().trim().toLowerCase());
                    if(cr.getCount()==0) {
                        Boolean isinserted = db.InsertData("0", name.getText().toString(), email.getText().toString().trim().toLowerCase(), pass.getText().toString());
                        if (isinserted == true)
                            Toast.makeText(getApplicationContext(), "Registered Successfully", Toast.LENGTH_SHORT).show();
                        else
                            Toast.makeText(getApplicationContext(),"Something went wrong",Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText(getApplicationContext(), "Account Already Exists", Toast.LENGTH_SHORT).show();
                    }
                    }
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent b=new Intent(Register.this,login.class);
                startActivity(b);
                finish();
            }
        });

    }
}
