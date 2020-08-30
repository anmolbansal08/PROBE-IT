package com.amitshekhar.tflite;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;


/**
 * A simple {@link Fragment} subclass.
 */
public class BlankFragment extends Fragment {

    TextView tv;
    TextView tv1;
    TextView tv2;

    public BlankFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_blank, container, false);
//        tv = view.findViewById(R.id.textViewResults1);
//        tv1 = view.findViewById(R.id.textViewResults2);
//        tv2 = view.findViewById(R.id.textViewResults3);
//        tv.setText(getArguments().getString("1"));
//        tv1.setText(getArguments().getString("2"));
//        tv2.setText(getArguments().getString("3"));
        // Inflate the layout for this fragment
        return view; }
}
