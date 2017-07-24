package com.example.jon_2.wormies;

import android.graphics.Point;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import java.util.ArrayDeque;
import java.util.Deque;


public class GameplayWindow extends AppCompatActivity {

    public enum Direction {
        UP, DOWN, LEFT, RIGHT
    }

    int score;
    Direction wormDirection;
    Point  wormPosition;
    Deque<Point> wormHistory;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gameplay_window);

        wormHistory = new ArrayDeque();

        wormHistory.add( new Point(10,10));
        wormPosition = new Point(10,10);
        setDirection (Direction.DOWN);
        score=0;

        //https://stackoverflow.com/questions/5161951/android-only-the-original-thread-that-created-a-view-hierarchy-can-touch-its-vi
        new Thread() {
            @Override public void run() {
                while (true) {
                    try {
                        Thread.sleep(500);
                        advance();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                update();

                            }
                        });



                    } catch ( InterruptedException e ) {}
                }
            }
        }.start();

    }

    private void update() {

        score++;
        TextView scoreView= (TextView) findViewById(R.id.scoreView);
        scoreView.setText("Score:" +score);
    }

    public void setDirection(Direction dir){
        this.wormDirection=dir;
    }

    //Add old position to history and advance player toward currect direction
    //Die if current position is occupied by a worm section or wall
    public void advance(){



        switch (wormDirection){
            case UP:
                wormPosition.set(wormPosition.x,wormPosition.y+1);
            case DOWN:
                wormPosition.set(wormPosition.x,wormPosition.y-1);
            case LEFT:
                wormPosition.set(wormPosition.x-1,wormPosition.y);
            case RIGHT:
                wormPosition.set(wormPosition.x+1,wormPosition.y);

        }

        if(wormHistory.size()-1>score){
            wormHistory.removeLast();
        }

        for (Point e : wormHistory) {
            if(e.toString()==wormPosition.toString()) gameOver();
        }

    }


    public void gameOver(){

    }

}
