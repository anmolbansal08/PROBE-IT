package com.amitshekhar.tflite;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

public class decision extends AppCompatActivity {
    private static final String TAG = "";
    ImageView gal, cam;
    static final int SELECT_IMAGE = 1000;
    static final int SELECT_PICTURE = 100;
    private int PICK_IMAGE_REQUEST = 1;
    public static final int RequestCapturePermissionCode = 2;
    ImageView imgView;
    TextView tv2;
    Context context;
    Button b1;
    SharedPreferences.Editor editor;
    sharedpreferenceconfig sf;
    SharedPreferences sf1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_decision);
        imgView = findViewById(R.id.hidden);
        cam = findViewById(R.id.camera);
        context=getApplicationContext();
        sf=new sharedpreferenceconfig(this);
        b1=findViewById(R.id.cap);
        sf1=context.getSharedPreferences(context.getResources().getString(R.string.shared_preferences),Context.MODE_PRIVATE) ;
        editor=sf1.edit();
        gal = findViewById(R.id.gallery);
        tv2 = findViewById(R.id.textView6);

        if(sf.readloginstatus()==true){
            tv2.setText("Welcome "+sf1.getString(getString(R.string.name),null).toString());
        }
        else {
            Intent b = getIntent();
            String c = b.getStringExtra("name");
            tv2.setText("Welcome " + c);
        } cam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent b = new Intent(decision.this, MainActivity.class);
                startActivity(b);
            }
        });
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sf.writeloginstatus(false);
                Intent a=new Intent(decision.this,login.class);
                startActivity(a);
            }
        });
        gal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chooseImage();
            }
        });
    }

    public void EnableRuntimePermission() {

        if (ActivityCompat.shouldShowRequestPermissionRationale(decision.this,
                Manifest.permission.CAMERA)) {

            Toast.makeText(decision.this, "CAMERA permission allows us to Access CAMERA app", Toast.LENGTH_LONG).show();

        } else {

            ActivityCompat.requestPermissions(decision.this, new String[]{
                    Manifest.permission.CAMERA}, RequestCapturePermissionCode);

        }
    }

    public void chooseImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 7 && resultCode == RESULT_OK) {

            Bitmap bitmap = (Bitmap) data.getExtras().get("data");

            imgView.setImageBitmap(bitmap);
        }
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {

            Uri uri = data.getData();
            Intent a = new Intent(decision.this, Main3Activity.class);
            a.putExtra("bitmap", uri.toString());
            startActivity(a);

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                // Log.d(TAG, String.valueOf(bitmap));


                imgView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    @Override
    public void onRequestPermissionsResult(int RC, String per[], int[] PResult) {
        switch (RC) {

            case RequestCapturePermissionCode:

                if (PResult.length > 0 && PResult[0] == PackageManager.PERMISSION_GRANTED) {

                    Toast.makeText(decision.this, "Permission Granted, Now your application can access CAMERA.", Toast.LENGTH_LONG).show();

                } else {

                    Toast.makeText(decision.this, "Permission Canceled, Now your application cannot access CAMERA.", Toast.LENGTH_LONG).show();

                }
                break;
        }

    }

}


