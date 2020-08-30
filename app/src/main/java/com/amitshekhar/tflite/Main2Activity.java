package com.amitshekhar.tflite;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import static android.view.View.GONE;

public class Main2Activity extends AppCompatActivity {
    TextView tv,tv1,tv2,tv3,tv4;
    Button b12;
    ImageView im;
    Fragment f;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        addfragment();
        tv=findViewById(R.id.textView2);
        tv1=findViewById(R.id.textView3);
        tv2=findViewById(R.id.textView4);
        tv3=findViewById(R.id.textView5);
        tv4=findViewById(R.id.tv);
        im=findViewById(R.id.imageView);
        im.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm=getSupportFragmentManager();
                Fragment fm1= fm.findFragmentById(R.id.nearby);
                FragmentTransaction ft=fm.beginTransaction();
                if(fm1!=null) {
                    ft.remove(fm1);
                    ft.commit();
                }
                im.setVisibility(GONE);
                tv4.setVisibility(GONE);
            }
        });
        AssetManager am = getApplicationContext().getAssets();
        String line,line1 = "";
        int c=getIntent().getIntExtra("id",-1);
        c++;
        String c1=c+"";
        try {
            InputStream is = am.open(c1+"_name.txt");
            InputStreamReader inputreader = new InputStreamReader(is);
            BufferedReader buffreader = new BufferedReader(inputreader);
            try
            {
                while ((line = buffreader.readLine()) != null)
                    line1+=line+"\n";
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
        tv.setMovementMethod(new ScrollingMovementMethod());
        tv.setText(line1);
        line1 = "";
        try {
            InputStream is = am.open(c1+"_description.txt");
            InputStreamReader inputreader = new InputStreamReader(is);
            BufferedReader buffreader = new BufferedReader(inputreader);
            try
            {
                while ((line = buffreader.readLine()) != null)
                    line1+=line+"\n";
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
        tv1.setMovementMethod(new ScrollingMovementMethod());
        tv1.setText(line1);
        ArrayList<String> nearby=new ArrayList<>();
        line1 = "";
        try {
            InputStream is = am.open(c1+"_nearby.txt");
            InputStreamReader inputreader = new InputStreamReader(is);
            BufferedReader buffreader = new BufferedReader(inputreader);
            try
            {
                while ((line = buffreader.readLine()) != null)
                    nearby.add(line);
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
        String [] line2=new String[3];
       c=0;
        try {
            InputStream is = am.open(c1+"_nearby.txt");
            InputStreamReader inputreader = new InputStreamReader(is);
            BufferedReader buffreader = new BufferedReader(inputreader);
            try
            {
                while ((line = buffreader.readLine()) != null)
                    line2[c++]=line+"\n";
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
//        Bundle bundle = new Bundle();
//        bundle.putString("1", nearby.get(0));
//        bundle.putString("2", nearby.get(1));
//        bundle.putString("3", nearby.get(2));
//        BlankFragment fragobj = new BlankFragment();
//        fragobj.setArguments(bundle);
//        tv2.setMovementMethod(new ScrollingMovementMethod());
//        tv2.setText(line1);
//        line1 = "";
//        try {
//            InputStream is = am.open(c1+"_longitude.txt");
//            InputStreamReader inputreader = new InputStreamReader(is);
//            BufferedReader buffreader = new BufferedReader(inputreader);
//            try
//            {
//                while ((line = buffreader.readLine()) != null)
//                    line1+=line+"\n";
//            }catch (Exception e)
//            {
//                e.printStackTrace();
//            }
//
//        }
//        catch (Exception e)
//        {
//            String error="";
//            error=e.getMessage();
//        }
//        tv3.setMovementMethod(new ScrollingMovementMethod());
//        tv3.setText(line1);
//       b12.setOnClickListener(new View.OnClickListener() {
//           @Override
//           public void onClick(View view) {
//               Intent v=new Intent(Main2Activity.this,Main2Activity.class);
//               finish();
//               String id="2";
//               v.putExtra("id",id );
//               startActivity(v);
//           }
//       });
    }
    private void addfragment(){
        FragmentManager fm=getSupportFragmentManager();
        FragmentTransaction ft=fm.beginTransaction();
        BlankFragment blankFragment=new BlankFragment();
        ft.add(R.id.nearby,blankFragment);
        ft.commit();
    }
}
