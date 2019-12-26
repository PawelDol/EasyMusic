package com.example.easymusic;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class NextLevelMenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_next_level_menu);

        Button menu = findViewById(R.id.menu);
        Button again = findViewById(R.id.more);
        Button next = findViewById(R.id.next);

        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent menu = new Intent(NextLevelMenu.this, LevelMenu.class);
                startActivity(menu);
            }
        });

        final Intent play = new Intent(NextLevelMenu.this, PlayLevel.class);
        final Intent final_l = new Intent(NextLevelMenu.this, FinalLevel.class);

        again.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
        // tu nie dziala
                if (LevelMenu.wannaplay_level<6) startActivity(play);
                else startActivity(final_l);
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LevelMenu.wannaplay_level++;
                if (LevelMenu.wannaplay_level<5) startActivity(play);
                else startActivity(final_l);
            }
        });
    }
}
