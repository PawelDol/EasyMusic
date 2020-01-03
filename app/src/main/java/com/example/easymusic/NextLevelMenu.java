package com.example.easymusic;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/** menu wyboru opcji po ukończeniu poziomu */
public class NextLevelMenu extends AppCompatActivity {

    /**
     * generuj układ graficzny,
     * wykonaj odpowiednią akcję w zależności od wybranej opcji
     * @param savedInstanceState informacje o stanie instancji
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_next_level_menu);

        Button menu = findViewById(R.id.menu);
        Button again = findViewById(R.id.again);
        Button next = findViewById(R.id.next);

        // przejdź do menu głównego
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent menu = new Intent(NextLevelMenu.this, LevelMenu.class);
                startActivity(menu);
            }
        });

        final Intent play = new Intent(NextLevelMenu.this, PlayLevel.class);
        final Intent final_l = new Intent(NextLevelMenu.this, FinalLevel.class);

        // powtórz poziom
        again.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(play);
            }
        });

        // przejdź do następnego poziomu
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LevelMenu.wannaplay_level++;

                // wybór rodzaju poziomu w zależności od jego numeru
                if (LevelMenu.wannaplay_level<5) startActivity(play);
                else startActivity(final_l);
            }
        });
    }
}
