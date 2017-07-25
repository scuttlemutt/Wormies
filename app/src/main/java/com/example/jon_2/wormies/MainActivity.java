package com.example.jon_2.wormies;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        System.out.println("aaaa \n aaaaa");

        Bitmap b = Bitmap.createBitmap(100, 100, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(b);

        ShapeDrawable mDrawable;
        mDrawable = new ShapeDrawable(new OvalShape());
        // If the color isn't set, the shape uses black as the default.
        mDrawable.getPaint().setColor(0xff74AC23);
        // If the bounds aren't set, the shape can't be drawn.
        mDrawable.setBounds(10,10,30,30);

        mDrawable.draw(c);

    }

    public void startGame(View view){
        System.out.println("start");

        Intent intent = new Intent(getBaseContext(), GameplayWindow.class);
        //intent.putExtra("GAME_DATA", gameData);
        startActivity(intent);
    }


}
