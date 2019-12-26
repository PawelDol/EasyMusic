package com.example.easymusic;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class LevelMenu extends AppCompatActivity implements View.OnClickListener {

    public static int unlocked_level = 5; // to będzie z pliku mam nadzieje
    public static int wannaplay_level;
    public static String[][] notes = {{"c","d","e","f","g","a","b"},{"cis","dis","f","fis","gis","ais"},
            {"cis","d","f","fis","g","ais","b"}, {"d","fis","a","b","gis","f","a","fis"},{"c","d","e","f","g","a","b"}};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level_menu);

        Button l1 = findViewById(R.id.l1);
        Button l2 = findViewById(R.id.l2);
        Button l3 = findViewById(R.id.l3);
        Button l4 = findViewById(R.id.l4);
        Button l5 = findViewById(R.id.l5);

        l1.setOnClickListener(this);
        l2.setOnClickListener(this);
        l3.setOnClickListener(this);
        l4.setOnClickListener(this);
        l5.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent play = new Intent(LevelMenu.this, PlayLevel.class);
        Intent final_l = new Intent(LevelMenu.this, FinalLevel.class);

        switch(v.getId()){
            case R.id.l1:
                wannaplay_level = 1;
                break;
            case R.id.l2:
                wannaplay_level = 2;
                break;
            case R.id.l3:
                wannaplay_level = 3;
                break;
            case R.id.l4:
                wannaplay_level = 4;
                break;
            case R.id.l5:
                wannaplay_level = 5;
                break;
        }

        if (wannaplay_level<=unlocked_level) {
            if (wannaplay_level != 5) startActivity(play);
            else startActivity(final_l);
        }
        else Toast.makeText(this, "You have to pass previous level first!", Toast.LENGTH_SHORT).show();

    }
}
