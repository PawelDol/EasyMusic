package com.example.easymusic;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/** menu opcji po przegranym poziomie */
public class GameOver extends AppCompatActivity {

    /**
     * generuje układ graficzny,
     * wykonuje odpowiednią akcję w zależności od wybranej opcji
     * @param savedInstanceState informacje o stanie instancji
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);

        Button menu = findViewById(R.id.menu);
        Button again = findViewById(R.id.again);

        // przejdź do menu głównego
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent menu = new Intent(GameOver.this, LevelMenu.class);
                startActivity(menu);
            }
        });

        // powtórz poziom
        again.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent play = new Intent(GameOver.this, PlayLevel.class);
                startActivity(play);
            }
        });
    }
}
