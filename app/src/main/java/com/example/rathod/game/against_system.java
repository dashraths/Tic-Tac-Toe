package com.example.rathod.game;


import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;


@SuppressLint("ValidFragment")
public class against_system extends Fragment {
    String result ;
    char []dst ;
    int player = 0 ;
    int flag = 0 ;
    final min_max m = new min_max();


    int first ,second ;

    public against_system(int plyr) {
        // Required empty public constructor
        player= plyr ;
        result = "No Result";
    }
    ImageButton[][]btn = new ImageButton[3][3];
    int ttl_moves  = 0 ;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view =  inflater.inflate(R.layout.fragment_game_page, container, false);

        Button go = (Button)view.findViewById(R.id.go);
        go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(getContext(),getFragmentManager());
            }
        });



        btn[0][0] = (ImageButton) view.findViewById(R.id.button1);
        btn[0][1] = (ImageButton) view.findViewById(R.id.button2);
        btn[0][2] = (ImageButton) view.findViewById(R.id.button3);
        btn[1][0] = (ImageButton) view.findViewById(R.id.button4);
        btn[1][1] = (ImageButton) view.findViewById(R.id.button5);
        btn[1][2] = (ImageButton) view.findViewById(R.id.button6);
        btn[2][0] = (ImageButton) view.findViewById(R.id.button7);
        btn[2][1] = (ImageButton) view.findViewById(R.id.button8);
        btn[2][2] = (ImageButton) view.findViewById(R.id.button9);


        int row,col ;

        //if the human is playing second
        if (player == 2)
        {
            first = R.drawable.x_;
            second = R.drawable.o_;
            m.next_optimal_move(0, 0,ttl_moves,2);
            int rw = m.m ;
            int cl = m.n ;
            ttl_moves++;
            btn[rw][cl].setImageResource(first);

            btn[rw][cl].setBackgroundColor(Color.WHITE);
            /*
            btn[rw][cl].setText(R.string.o);
            btn[rw][cl].setTextColor(Color.GREEN);
            btn[rw][cl].setBackgroundColor(Color.BLACK);*/
        }
        else
        {

            first = R.drawable.o_;
            second = R.drawable.x_;
        }






        for(row=0 ;row < 3;row++)
        {
            for(col = 0 ;col< 3 ;col++)
            {

                final int finalRow = row;
                final int finalCol = col;
                btn[row][col].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if( m.grid_values[finalRow][finalCol] == '1')// !btn[finalRow][finalCol].getText().toString().equals("X") && !btn[finalRow][finalCol].getText().toString().equals("O") )
                        {
                            ttl_moves++;
                            btn[finalRow][finalCol].setImageResource(second);

                            btn[finalRow][finalCol].setBackgroundColor(Color.WHITE);
                           /* btn[finalRow][finalCol].setText(R.string.x);
                            btn[finalRow][finalCol].setTextColor(Color.GREEN);
                            btn[finalRow][finalCol].setBackgroundColor(Color.WHITE);
                            */Log.w(String.valueOf(finalRow),"-"+String.valueOf(finalCol));
                            //finalRow =
                            m.next_optimal_move(finalRow, finalCol,ttl_moves,player);
                            int rw = m.m ;
                            int cl = m.n ;

                            ttl_moves++;
                            btn[rw][cl].setImageResource(first);

                            btn[rw][cl].setBackgroundColor(Color.WHITE);
                                  /*btn[rw][cl].setText(R.string.o);
                            btn[rw][cl].setTextColor(Color.GREEN);
                            btn[rw][cl].setBackgroundColor(Color.BLACK);
                                */
                            checkResult(ttl_moves,m,getContext(),getFragmentManager(),btn,(TextView)view.findViewById(R.id.result));



                            m.print();

                        }
                    }
                });

            }
        }


        return view ;
    }
    void checkResult(int ttl_moves, min_max m, Context ctx, final FragmentManager fm,ImageButton[][]btn,TextView res)
    {

        if(ttl_moves >= 5)
        {
            char winner = m.winner();
            if (winner == 'o')
            {
                result = "O WINS!";
                Toast.makeText(ctx,String.valueOf(winner),Toast.LENGTH_LONG).show();
                flag = 1 ;
            }
            else
            {
                if(winner == 'x')
                {
                    result = "X WINS!";
                    Toast.makeText(ctx,String.valueOf(winner),Toast.LENGTH_LONG).show();
                    flag = 1 ;
                }

            }
        }

        if(ttl_moves >= 9 && flag == 0)
        {
            result = "Match is drawn";


            flag = 1 ;
            Toast.makeText(ctx,"Match is drawn",Toast.LENGTH_LONG).show();
        }

        Log.w("value" ,String.valueOf(flag));
        if(flag == 1)
        {

            dst = new char[result.length()];
            result.getChars(0,result.length(),dst,0);

            for(int i=0;i<3;i++)
            {
                for(int j=0;j<3;j++)
                {
                    btn[i][j].setEnabled(false);
                }
            }

        res.setText(dst,0,dst.length);
        }
    }
        void showDialog(Context ctx, final FragmentManager fm)
        {
            final Dialog d = new Dialog(ctx);
            d.setContentView(R.layout.fragment_choose);
            ConstraintLayout constraintLayout = (ConstraintLayout) d.findViewById(R.id.choose_layout);
            constraintLayout.setBackgroundResource(R.drawable.result);
            Button first = (Button) d.findViewById(R.id.first);
            Button second = (Button) d.findViewById(R.id.second);
            first.setText(R.string.home);
            second.setText(R.string.exit);
            first.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    fm.beginTransaction().replace(R.id.fragment_container,new front_page()).commit();
                    d.dismiss();
                }
            });

            second.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getActivity().finish();
                    System.exit(0);
                    d.dismiss();
                }
            });
            d.show();

        }
}
