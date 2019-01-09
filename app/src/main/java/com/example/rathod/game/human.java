package com.example.rathod.game;


import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class human extends Fragment {


    public human() {
        // Required empty public constructor
    }

    int ttl_moves = 0 ,player = 0;
    against_system as = new against_system(0);
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_game_page, container, false);

        Button go = (Button)view.findViewById(R.id.go);
        go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                as.showDialog(getContext(),getFragmentManager());
            }
        });


        final ImageButton[][] btn = new ImageButton[3][3];
        btn[0][0] = (ImageButton) view.findViewById(R.id.button1);
        btn[0][1] = (ImageButton) view.findViewById(R.id.button2);
        btn[0][2] = (ImageButton) view.findViewById(R.id.button3);
        btn[1][0] = (ImageButton) view.findViewById(R.id.button4);
        btn[1][1] = (ImageButton) view.findViewById(R.id.button5);
        btn[1][2] = (ImageButton) view.findViewById(R.id.button6);
        btn[2][0] = (ImageButton) view.findViewById(R.id.button7);
        btn[2][1] = (ImageButton) view.findViewById(R.id.button8);
        btn[2][2] = (ImageButton) view.findViewById(R.id.button9);
        final min_max m = new min_max();
        int row=0, col=0;
        final TextView p1 = (TextView)view.findViewById(R.id.p1);
        final TextView p2 = (TextView)view.findViewById(R.id.p2);
        p1.setText(R.string.p1);
        p2.setText(R.string.p2);
        p1.setTextColor(Color.BLUE);
        p2.setTextColor(Color.RED);

        for (row = 0; row < 3; row++) {
            for (col = 0; col < 3; col++) {
                final int finalRow = row;
                final int finalCol = col;
                btn[row][col].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(  m.grid_values[finalRow][finalCol] == '1' )
                        {
                            Log.e("index",String.valueOf(finalRow)+" "+String.valueOf(finalCol));
                            ttl_moves++;
                            if (player == 0)
                            {

                                btn[finalRow][finalCol].setImageResource(R.drawable.x_);

                                m.mark(finalRow, finalCol,'x');

                            player = 1 ;
                                p1.setTextColor(Color.RED);
                                p2.setTextColor(Color.BLUE);

                            }
                            else
                            {

                                btn[finalRow][finalCol].setImageResource(R.drawable.o_);
                                btn[finalRow][finalCol].setBackgroundColor(Color.WHITE);
                                m.mark(finalRow, finalCol,'o');
                                p1.setTextColor(Color.BLUE);
                                p2.setTextColor(Color.RED);

                                player = 0 ;
                            }

                            btn[finalRow][finalCol].setBackgroundColor(Color.WHITE);
                        }
                            as.checkResult(ttl_moves,m,getContext(),getFragmentManager(),btn,(TextView)view.findViewById(R.id.result));
                        /*
                        if(ttl_moves >= 5)
                        {
                            char winner = m.winner();
                            if (winner == 'o')
                            {
                                Toast.makeText(getContext(),String.valueOf(winner),Toast.LENGTH_LONG).show();
                            }
                            else
                            {
                                if(winner == 'x')
                                {
                                    Toast.makeText(getContext(),String.valueOf(winner),Toast.LENGTH_LONG).show();
                                }
                                else
                                    if(ttl_moves == 9)
                                {
                                    Toast.makeText(getContext(),"Match is drawn",Toast.LENGTH_LONG).show();
                                }
                            }
                        }*/
                    }
                });
            }
        }
        return view;
    }
}
