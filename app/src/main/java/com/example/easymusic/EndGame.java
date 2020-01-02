package com.example.easymusic;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class EndGame extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end_game);

        Button more = findViewById(R.id.again);
        Button menu = findViewById(R.id.menu);

        SharedPreferences r_score = getSharedPreferences(LevelMenu.DATA, 0);
        FinalLevel.recent_score = r_score.getInt("recent_score", FinalLevel.recent_score);

        SharedPreferences m_score = getSharedPreferences(LevelMenu.DATA, 0);
        FinalLevel.max_score = m_score.getInt("max_score", FinalLevel.max_score);

        TextView score = findViewById(R.id.end2);
        score.setText(String.format("Recent score: %d/16", FinalLevel.recent_score));

        TextView max = findViewById(R.id.end3);
        max.setText(String.format("Max score: %d/16", FinalLevel.max_score));

        more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent link = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.musictheory.net/lessons"));
                startActivity(link);
            }
        });

        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent level_menu = new Intent(EndGame.this, LevelMenu.class);
                startActivity(level_menu);
            }
        });
    }
}
