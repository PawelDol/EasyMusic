package com.example.easymusic;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class EndGame extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end_game);

        Button more = findViewById(R.id.more);
        Button menu = findViewById(R.id.menu);

        TextView score = findViewById(R.id.end2);
        score.setText(String.format("Score: %d/%d",FinalLevel.curr_score,FinalLevel.level_notes.length));

    }
}
