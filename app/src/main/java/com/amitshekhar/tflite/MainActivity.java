package com.amitshekhar.tflite;

import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.wonderkiln.camerakit.CameraKitError;
import com.wonderkiln.camerakit.CameraKitEvent;
import com.wonderkiln.camerakit.CameraKitEventListener;
import com.wonderkiln.camerakit.CameraKitImage;
import com.wonderkiln.camerakit.CameraKitVideo;
import com.wonderkiln.camerakit.CameraView;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {
    private static final String MODEL_PATH = "converted_model.tflite";
    private static final boolean QUANT = false;
    private static final String LABEL_PATH = "labels.txt";
    private static final int INPUT_SIZE = 224;
    private Classifier classifier;
    private Executor executor = Executors.newSingleThreadExecutor();
    private TextView textViewResult;
    private ImageView btnDetectObject, btnToggleCamera;
    private ImageView imageViewResult;
    private CameraView cameraView;
    private static HashMap<String,Integer> list=new HashMap<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        cameraView = findViewById(R.id.cameraView);
        imageViewResult = findViewById(R.id.imageViewResult);
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
//       final ImageView img11=findViewById(R.id.img1);
//        textViewResult.setMovementMethod(new ScrollingMovementMethod());
        final List<Classifier.Recognition> results;
        btnToggleCamera = findViewById(R.id.btnToggleObject);
        btnDetectObject = findViewById(R.id.btnDetectObject);



        cameraView.addCameraKitListener(new CameraKitEventListener() {
            @Override
            public void onEvent(CameraKitEvent cameraKitEvent) {

            }

            @Override
            public void onError(CameraKitError cameraKitError) {

            }

            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onImage(CameraKitImage cameraKitImage) {
//               BitmapBitmap bitmap=((BitmapDrawable)img11.getDrawable()).getBitmap();
                Bitmap bitmap=cameraKitImage.getBitmap();
//                Intent a=new Intent(MainActivity.this,Main3Activity.class);
//                a.putExtra("bitmap",bitmap);
//                startActivity(a);
                bitmap = Bitmap.createScaledBitmap(bitmap, INPUT_SIZE, INPUT_SIZE, false);
                imageViewResult.setImageBitmap(bitmap);
                final List<Classifier.Recognition> results = classifier.recognizeImage(bitmap);
//                textViewResult.setText((results.toString())+"\n"+results.size());
//                imageViewResult.setImageBitmap(bitmap);

                Intent a = new Intent(MainActivity.this, Main2Activity.class);
               String id=results.get(0).getTitle();
               int id1=list.getOrDefault(id,-1);
               if(id1==-1){
                   Toast.makeText(MainActivity.this, "mapping error with  "+id, Toast.LENGTH_LONG).show();
                   return;
               }
                Toast.makeText(MainActivity.this, results.toString(), Toast.LENGTH_LONG).show();
                a.putExtra("id",id1);
                startActivity(a);
            }
            @Override
            public void onVideo(CameraKitVideo cameraKitVideo) {

            }
        });

        btnToggleCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cameraView.toggleFacing();
            }
        });

        btnDetectObject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                float deg=btnDetectObject.getRotation()+30F;
                btnDetectObject.animate().rotation(deg).setInterpolator(new AccelerateDecelerateInterpolator());

                cameraView.captureImage();


            }
        });

        initTensorFlowAndLoadModel();
    }

    @Override
    protected void onResume() {
        super.onResume();
        cameraView.start();
    }

    @Override
    protected void onPause() {
        cameraView.stop();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        executor.execute(new Runnable() {
            @Override
            public void run() {
                classifier.close();
            }
        });
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
                btnDetectObject.setVisibility(View.VISIBLE);
            }
        });
    }
}
//import android.app.Activity;
//        import android.content.Intent;
//        import android.graphics.Bitmap;
//        import android.os.Bundle;
//        import android.view.View;
//        import android.widget.Button;
//        import android.widget.ImageView;
//
//public class MyCameraActivity extends Activity
//{
//    private static final int CAMERA_REQUEST = 1888;
//    private ImageView imageView;
//    private static final int MY_CAMERA_PERMISSION_CODE = 100;
//
//    @Override
//    public void onCreate(Bundle savedInstanceState)
//    {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.main);
//        this.imageView = (ImageView)this.findViewById(R.id.imageView1);
//        Button photoButton = (Button) this.findViewById(R.id.button1);
//        photoButton.setOnClickListener(new View.OnClickListener()
//        {
//            @Override
//            public void onClick(View v)
//            {
//                if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
//                {
//                    requestPermissions(new String[]{Manifest.permission.CAMERA}, MY_CAMERA_PERMISSION_CODE);
//                }
//                else
//                {
//                    Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
//                    startActivityForResult(cameraIntent, CAMERA_REQUEST);
//                }
//            }
//        });
//    }
//
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
//    {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        if (requestCode == MY_CAMERA_PERMISSION_CODE)
//        {
//            if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
//            {
//                Toast.makeText(this, "camera permission granted", Toast.LENGTH_LONG).show();
//                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
//                startActivityForResult(cameraIntent, CAMERA_REQUEST);
//            }
//            else
//            {
//                Toast.makeText(this, "camera permission denied", Toast.LENGTH_LONG).show();
//            }
//        }
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data)
//    {
//        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK)
//        {
//            Bitmap photo = (Bitmap) data.getExtras().get("data");
//            imageView.setImageBitmap(photo);
//        }
//    }