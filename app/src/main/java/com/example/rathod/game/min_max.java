package com.example.rathod.game;

import android.util.Log;

import java.util.Random;

public class min_max {


    int dim;
    char [][]grid_values = new char[3][3];
    final int INF = 999999;
    public int m, n ;
    min_max()
    {
        dim = 3;
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                grid_values[i][j] = '1';
            }
        }
    }


    char winner(int v) {
        char win;

        win = this.check_row() ;

        if (win != '1')
            return win;
        win = this.check_col() ;

        if (win != '1')
            return win;

        win = this.check_first_diag();

        if (win != '1')
            return win;

        win = this.check_second_diag();

        return win;

    }

    public char winner() {
        char win;

        win = this.check_row() ;

        if (win != '1')
            return win;
        win = this.check_col() ;

        if (win != '1')
            return win;

        win = this.check_first_diag();

        if (win != '1')
            return win;

        win = this.check_second_diag();

        return win;

    }

    char check_row() {
        ////cout << "Row\n" ;
        for (int row = 0; row < dim; row++) {
            int col = 0;
            for (; col < dim - 1; col++) {
                if (grid_values[row][col] == '1' || (grid_values[row][col] != grid_values[row][col + 1])) {
                    break;
                }
            }
            if (col == dim - 1) {
                return grid_values[row][0];
            }
        }
        return '1';
    }


    char check_col()
    {
        //cout << "column";
        for (int col = 0; col < dim; col++) {
            int row = 0;
            for (; row < dim - 1; row++) {
                //cout << grid_values[row][col] << " "<< grid_values[row+1][col] << " ";
                if (grid_values[row][col] == '1' || (grid_values[row][col] != grid_values[row + 1][col])) {
                    break;
                }
            }
            if (row == dim - 1) {
                //	cout << col << " " ;
                return grid_values[0][col];
            }
        }
        return '1';
    }

    char check_first_diag() {

        //	//cout << "1st\n";
        int i;
        for (i = 0; i < dim - 1; i++) {
            if (grid_values[i][i] == '1' || grid_values[i][i] != grid_values[i + 1][i + 1])
                break;
        }
        if (i == dim - 1) {

            //cout << "!st di" << " " ;
            return grid_values[0][0];
        } else
            return '1';
    }

    char check_second_diag() {
        ////cout << "2nd";
        int i = 0;
        for (; i < dim - 1; i++) {
            //dim-1-(i+1) next grid
            if (grid_values[i][dim - 1 - i] == '1' || grid_values[i][dim - 1 - i] != grid_values[i + 1][dim - i - 2])
                break;
        }
        if (i == dim - 1) {

            //cout << "2nd diag" << " " ;
            return grid_values[0][dim - 1];
        } else
            return '1';
    }

    boolean pruning(int alpha, int beta) {
        return ((alpha >= beta) ? true : false);
    }

    int find_best_move( boolean tern, int ttl_moves, int depth, int alpha, int beta)
        {
            if (ttl_moves >= 5) {
                char ans = this.winner();
                if (ans == 'x')
                    return 10 - depth;
                if (ans == 'o')
                    return -10 + depth;
            }
            //no one wins
            if (ttl_moves == 9) {
                return 0;
            }
            // alpha's tern
            if (tern == true) {
                int best = -INF;

                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 3; j++) {
                        //empty cell
                        if (grid_values[i][j] == '1') {
                            grid_values[i][j] = 'x';
                            int v = find_best_move(false, ttl_moves + 1, depth + 1, alpha, beta);
                            best = (best > v ? best : v);
                            alpha = (alpha > best ? alpha : best);
                            grid_values[i][j] = '1';
                            if (pruning(alpha, beta)) {
                                return best;
                            }
                        }
                    }
                }

                return best;
            } else {
                int best = INF;
                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 3; j++) {
                        //empty cell
                        if (grid_values[i][j] == '1') {
                            grid_values[i][j] = 'o';
                            int v = find_best_move(true, ttl_moves + 1, depth + 1, alpha, beta);
                            best = (beta < v ? best : v);
                            beta = (beta < best ? beta : best);
                            grid_values[i][j] = '1';
                            if (pruning(alpha, beta)) {
                                return best;
                            }
                        }
                    }
                }
                return best;
            }
        }

    void next_optimal_move(int row,int col, int ttl_moves, int plyr)
        {

            if (plyr == 2) {
                if (ttl_moves == 0) {
                    Random rand = new Random();

                    // Generate random integers in range 0 to 999
                    int rndm = rand.nextInt(1000);
                    m = rndm % 3 ;
                    rndm = rand.nextInt(1000);
                    n = rndm % 3 ;
                    grid_values[m][n] = 'x';
                    return;
                }
                int mx = -999999;
                //row and col where the human fill

                grid_values[row][col]= 'o';
                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 3; j++) {
                        if (grid_values[i][j] == '1') {
                            grid_values[i][j] = 'x';

                            int val = find_best_move(false, ttl_moves + 1, 0, -INF, INF);
                            if (mx < val) {
                                mx = val;
                                m = i;
                                n = j;
                            }
                            grid_values[i][j] = '1';

                        }
                    }
                }
                grid_values[m][n] = 'x';
            }
            else {
                grid_values[row][col] = 'x';
                int mn = 999999;
                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 3; j++) {
                        if (grid_values[i][j] == '1') {
                            grid_values[i][j] = 'o';
                            int val = find_best_move(true, ttl_moves + 1, 0, -INF, INF);
                            if (mn > val)
                            {
                                mn = val;
                                m = i; n = j;
                            }
                            grid_values[i][j] = '1';
                        }
                    }
                }
                //when the ans will come
                grid_values[m][n] = 'o' ;
            }
        }


    void mark(int row,int col,char chr)
    {
        grid_values[row][col] = chr ;
    }

    void print() {
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                Log.w(String.valueOf(i)+String.valueOf(j), String.valueOf(grid_values[i][j]));

                ////cout << grid_values[i][j]<<" ";
            }
            //cout << "\n";
        }
    }

}