package com.example.jon_2.wormies;

import android.graphics.Point;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

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


        wormHistory.push(new Point(10,10));
        setDirection (Direction.DOWN);
        score=0;

        new Thread() {
            @Override public void run() {
                while (true) {
                    try {
                        Thread.sleep(1000);
                        advance();
                    } catch ( InterruptedException e ) {}
                }
            }
        }.start();

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

        if(wormHistory.size()>score){
            wormHistory.removeLast();
        }

        for (Point e : wormHistory) {
            if(e.toString()==wormPosition.toString()) gameOver();
        }

    }


    public void gameOver(){

    }

}
