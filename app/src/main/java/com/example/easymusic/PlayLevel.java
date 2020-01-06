package com.example.easymusic;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

/** struktura poziomów treningowych */
public class PlayLevel extends Level {

    /** życia gracza */
    int lives = 3;

    /**
     * generuje układ graficzny,
     * inicjacja poziomu
     * @param savedInstanceState informacje o stanie instancji
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_play_level);

        init_level();
    }

    /**
     * wywołuje funkcje sprawdzające
     * @param v naciśnięty klawisz
     */
    @Override
    public void check(View v) {
        check_sound(v);
        check_life();
        check_finish();
    }

    /**
     * sprawdzenie poprawności zagranego dźwięku
     * @param v naciśnięty klawisz
     */
    public void check_sound(View v) {
        Button b = (Button)v;
        ImageView cursor = findViewById(R.id.cursor);
        ProgressBar life = findViewById(R.id.life);

        // jeżeli zagrany dźwięk jest poprawny, przejdź do następnego
        if (b.getText().equals(level_notes[curr_note])) {
            curr_note++;
            location = cursor.getX();
            cursor.setX(location + 105 *screen_width/1000);
        }
        // jeżeli zagrany dźwięk nie jest poprawny. zabierz jedno życie
        else {
            lives--;
            life.setProgress(lives);
            Toast.makeText(this, "Wrong note!", Toast.LENGTH_SHORT).show();
        }
    }

    /** przerwanie rozgrywki, gdy nie pozostało graczowi żadne życie */
    public void check_life() {
        if (lives == 0) {
            Intent game_over = new Intent(PlayLevel.this, GameOver.class);
            startActivity(game_over);
        }
    }

    /** pomyślne ukończenie poziomu, gdy gracz zagra poprawnie wszystkie dźwięki */
    public void check_finish() {
        if ((curr_note) == level_notes.length) {
            // odblokuj następny poziom
            if (LevelMenu.unlocked_level == LevelMenu.wannaplay_level) {
                LevelMenu.unlocked_level++;

                // zapisz liczbę odblokowanych poziomów do pliku
                SharedPreferences unl_level = getSharedPreferences(LevelMenu.DATA, 0);
                SharedPreferences.Editor editor = unl_level.edit();
                editor.putInt("unlocked_level", LevelMenu.unlocked_level);
                editor.commit();
            }
            Intent next = new Intent(PlayLevel.this, NextLevelMenu.class);
            startActivity(next);
        }
    }

}
