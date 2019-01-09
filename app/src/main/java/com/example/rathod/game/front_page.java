package com.example.rathod.game;


import android.app.Dialog;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class front_page extends Fragment {


    public front_page() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_front_page, container, false);

        Button system = (Button) view.findViewById(R.id.against_computer);
        Button humnan = (Button) view.findViewById(R.id.simple);

        system.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Dialog d = new Dialog(getContext());


                d.setContentView(R.layout.fragment_choose);

                Button first = (Button) d.findViewById(R.id.first);
                Button second = (Button) d.findViewById(R.id.second);

                first.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        getFragmentManager().beginTransaction().replace(R.id.fragment_container,new against_system(1)).addToBackStack(null).commit();
                        d.dismiss();
                    }
                });

                second.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        getFragmentManager().beginTransaction().replace(R.id.fragment_container,new against_system(2)).addToBackStack(null).commit();
                        d.dismiss();
                    }
                });
                d.show();


            }
        });
        humnan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.fragment_container,new human()).addToBackStack(null).commit();
            }
        });

        return view ;
    }

}
