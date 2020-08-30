package com.amitshekhar.tflite;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

public class login extends AppCompatActivity {
    Button b, b1;
    EditText email;
    EditText pass;
    ImageView im;
    CheckBox c1;
    databasehelper db;
    Intent a;
    Context context;
    SharedPreferences.Editor editor;
    sharedpreferenceconfig sf;
    SharedPreferences sf1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        b = findViewById(R.id.btnlogin);
        b1 = findViewById(R.id.btnregister);
        email = findViewById(R.id.email);
        pass = findViewById(R.id.pass);
        context = getApplicationContext();
        sf = new sharedpreferenceconfig(this);
        sf1 = context.getSharedPreferences(context.getResources().getString(R.string.shared_preferences), Context.MODE_PRIVATE);
        editor = sf1.edit();
        im = findViewById(R.id.bear);
        db = new databasehelper(this);
        c1 = findViewById(R.id.check);
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
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (email.getText().toString().trim().matches("") || pass.getText().toString().matches("")) {
                    if (email.getText().toString().trim().matches(""))
                        email.setError("Input Mandatory");
                    if (pass.getText().toString().matches(""))
                        pass.setError("Input Mandatory");
                } else {
                    Cursor cr = null;
                    try {
                        cr = db.ViewData(email.getText().toString().trim().toLowerCase());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    if (cr.getCount() == 0) {
                        Toast.makeText(getApplicationContext(), "Incorrect Email ID ", Toast.LENGTH_SHORT).show();
                    } else {
                        String data = "";
                        String b = "";
                        try {
                            if (cr.moveToNext()) {
                                data = cr.getString(3);
                                if (pass.getText().toString().equals(data)) {
                                    a = new Intent(login.this, decision.class);
                                    b = cr.getString(1);
                                    sf.writeloginstatus(true);
                                    editor.putString(context.getResources().getString(R.string.name), b);
                                    editor.commit();
                                    a.putExtra("name", b);
                                    startActivity(a);
                                } else
                                    Toast.makeText(getApplicationContext(), "Incorrect Password", Toast.LENGTH_LONG).show();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent a = new Intent(login.this, Register.class);
                startActivity(a);
            }
        });
    }

    //                Cursor cr = db.ViewAllData();
//                if (cr.getCount() == 0) {
//                    ShowMessage("ERROR!!!", "No Data Found");
//                    return;
//                }
//                StringBuffer data = new StringBuffer();
//                while (cr.moveToNext()) {
//                    data.append("ID= " + cr.getString(0) + "\nName= " + cr.getString(1) + "\nEmail= " + cr.getString(2) + "\nCourse Count= " + cr.getString(3) + "\n\n");
//                }
//                ShowMessage("Viewing Data", data.toString());            }
//        });
//    }
    private void ShowMessage(String title, String message) {
        AlertDialog.Builder ab = new AlertDialog.Builder(this);
        ab.create();
        ab.setCancelable(true);
        ab.setTitle(title);
        ab.setMessage(message);
        ab.show();
    }
}
