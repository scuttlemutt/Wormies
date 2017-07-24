package com.example.jon_2.wormies;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        System.out.println("aaaa \n aaaaa");
    }

    public void startGame(View view){
        System.out.println("start");

        Intent intent = new Intent(getBaseContext(), GameplayWindow.class);
        //intent.putExtra("GAME_DATA", gameData);
        startActivity(intent);
    }


}
