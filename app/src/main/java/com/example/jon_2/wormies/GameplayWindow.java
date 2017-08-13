package com.example.jon_2.wormies;

import android.graphics.Point;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.concurrent.ThreadLocalRandom;


public class GameplayWindow extends AppCompatActivity {

    public enum Direction {
        UP, DOWN, LEFT, RIGHT
    }

    int score;
    int arenaX;
    int arenaY;
    Direction wormDirection;
    Point  wormPosition;
    Point foodLocation;
    Deque<Point> wormHistory;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gameplay_window);

        /*
        Deque<Point> testQueue = new ArrayDeque();
        testQueue.add(new Point(25,25));
        Point foo = new Point(25,25);
        Point foo2 = new Point(20,20);
        System.out.println("Does this contain 2525?" +testQueue.contains(foo));
        System.out.println("Does this contain 2020?" +testQueue.contains(foo2));

        */

       // startGame();
        wormHistory = new ArrayDeque();

        arenaX = 15;
        arenaY = 10;
        wormHistory.add( new Point(10,10));
        wormPosition = new Point(10,10);
        wormDirection = Direction.DOWN;
        placeFood();
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

        TextView scoreView= (TextView) findViewById(R.id.scoreView);
        scoreView.setText("Score:" +score);
        TextView dirView= (TextView) findViewById(R.id.dirView);
        dirView.setText("dir:" +wormDirection.toString());
        TextView posView= (TextView) findViewById(R.id.posView);
        posView.setText("PosL"+wormPosition.toString());
        TextView foodView= (TextView) findViewById(R.id.foodView);
        foodView.setText("PosL"+foodLocation.toString());

    }

    public void setDirection(View view){
        switch (view.getId()) {
            case  R.id.upButton:
                wormDirection=Direction.UP;
                break;
            case R.id.downButton:
                wormDirection=Direction.DOWN;
                break;
            case R.id.leftButton:
                wormDirection=Direction.LEFT;
                break;
            case R.id.rightButton:
                wormDirection=Direction.RIGHT;
                break;


        }
    }

    //Add old position to history and advance player toward currect direction
    //Die if current position is occupied by a worm section or wall
    public void advance(){



        switch (wormDirection){
            case UP:
                wormPosition.set(wormPosition.x,wormPosition.y+1);
                break;
            case DOWN:
                wormPosition.set(wormPosition.x,wormPosition.y-1);
                break;
            case LEFT:
                wormPosition.set(wormPosition.x-1,wormPosition.y);
                break;
            case RIGHT:
                wormPosition.set(wormPosition.x+1,wormPosition.y);
                break;

        }

        if(wormHistory.size()-1>score){
            wormHistory.removeLast();
        }

        //if your current position is an area containing your tail, game over
        for (Point e : wormHistory) {
            if(e.toString()==wormPosition.toString()) gameOver();
        }

        //if your current position is out of bounds, game over
        if(wormPosition.x>arenaX||wormPosition.x<0||wormPosition.y>arenaY||wormPosition.y<0) gameOver();

        //if you entered the food space, expand worm
        if(wormPosition.equals(foodLocation)){
            score++;
            placeFood();
        }


    }


    public void gameOver(){
        System.out.println("Dead "+wormPosition);
    }

    public void placeFood(){
        Point foodPoint = new Point(0,0);
        boolean valid=false;
        while(valid==false){
            foodPoint= new Point(ThreadLocalRandom.current().nextInt(0, arenaX + 1), ThreadLocalRandom.current().nextInt(0, arenaY + 1));
            valid=validFood(foodPoint);
        }
        foodLocation=foodPoint;
    }

    boolean validFood(Point foodPoint)
    {
        if(foodPoint.toString()==wormPosition.toString()) return false;
        else if (wormHistory.contains(foodPoint)) return false;

        return true;
    }

}
