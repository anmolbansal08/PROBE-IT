package com.amitshekhar.tflite;

import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class Main3Activity extends AppCompatActivity {
    Button b1,b2;
    ImageView im;
    Bitmap bitmap1;
    Classifier classifier;
    HashMap<String,Integer> list=new HashMap<>();
    private static final String MODEL_PATH = "converted_model.tflite";
    private static final boolean QUANT = false;
    private static final String LABEL_PATH = "labels.txt";
    private static final int INPUT_SIZE = 224;
    private Executor executor = Executors.newSingleThreadExecutor();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        String line="";
        AssetManager am = getApplicationContext().getAssets();
        int count=0;
        try {
            InputStream is = am.open("list.txt");
            InputStreamReader inputreader = new InputStreamReader(is);
            BufferedReader buffreader = new BufferedReader(inputreader);
            try
            {
                while ((line = buffreader.readLine()) != null)
                    list.put(line.toLowerCase(),count++);
            }catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        catch (Exception e)
        {
            String error="";
            error=e.getMessage();
        }
        Intent b = new Intent();
        b2=findViewById(R.id.pagal1);
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent b=new Intent(Main3Activity.this,decision.class);
                startActivity(b);
            }
        });
        Intent intent = getIntent();
        im = findViewById(R.id.img);
        b1=findViewById(R.id.pagal);
        String image_path= intent.getStringExtra("bitmap");
        Uri fileUri = Uri.parse(image_path);
        im.setImageURI(fileUri);
        bitmap1 =((BitmapDrawable)im.getDrawable()).getBitmap();
    b1.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Bitmap bitmap = Bitmap.createScaledBitmap(bitmap1, 224, 224, false);
            final List<Classifier.Recognition> results = classifier.recognizeImage(bitmap);
            Intent a = new Intent(Main3Activity.this, Main2Activity.class);
            String id=results.get(0).getTitle();
//                Toast.makeText(getApplicationContext(),id,Toast.LENGTH_SHORT).show();
            int id1=list.get(id);
            a.putExtra("id",id1);
            startActivity(a);
          }
    });
        initTensorFlowAndLoadModel();
    }
    private void initTensorFlowAndLoadModel() {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    classifier = TensorFlowImageClassifier.create(
                            getAssets(),
                            MODEL_PATH,
                            LABEL_PATH,
                            INPUT_SIZE,
                            QUANT);
                    makeButtonVisible();
                } catch (final Exception e) {
                    throw new RuntimeException("Error initializing TensorFlow!", e);
                }
            }
        });
    }
    private void makeButtonVisible() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                b1.setVisibility(View.VISIBLE);
            }
        });
    }
}
