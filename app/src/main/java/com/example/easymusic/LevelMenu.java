package com.example.easymusic;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

/** menu główne, wybór poziomu */
public class LevelMenu extends AppCompatActivity implements View.OnClickListener {

    /** plik przechowujący dane */
    public static final String DATA = "DataFile";

    /** liczba aktualnie odblokowanych pozimów */
    public static int unlocked_level=1;

    /** numer aktualnie wybranego poziomu */
    public static int wannaplay_level;

    /** zmienna przechowująca nazwy dźwięków znajdujących się w każdym poziomie */
    public static String[][] notes = {{"c","d","e","f","g","a","b"},{"cis","dis","f","fis","gis","ais"},
            {"cis","d","f","fis","g","ais","b"}, {"d","fis","a","b","gis","f","a","fis"},
            {"e","cis","e","fis","a","e","fis","e"},{"a","e","fis","gis","b","e","a","fis"}};

    /**
     * generuj układ graficzny,
     * definicja przycisków i odczytanie danych z pliku
     * @param savedInstanceState informacje o stanie instancji
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level_menu);

        Button start = findViewById(R.id.start);
        Button end = findViewById(R.id.end);
        Button l1 = findViewById(R.id.l1);
        Button l2 = findViewById(R.id.l2);
        Button l3 = findViewById(R.id.l3);
        Button l4 = findViewById(R.id.l4);
        Button l5 = findViewById(R.id.l5);

        start.setOnClickListener(this);
        end.setOnClickListener(this);
        l1.setOnClickListener(this);
        l2.setOnClickListener(this);
        l3.setOnClickListener(this);
        l4.setOnClickListener(this);
        l5.setOnClickListener(this);

        // odczytaj z pliku informację o liczbie odblokowanych poziomów
        SharedPreferences unl_level = getSharedPreferences(DATA, 0);
        unlocked_level = unl_level.getInt("unlocked_level", unlocked_level);
    }

    /**
     * przejdź do odpowiedniej aktywności w zależności od wciśniętego przycisku
     * @param v naciśnięty przycisk
     */
    @Override
    public void onClick(View v) {
        Intent play = new Intent(LevelMenu.this, PlayLevel.class);
        Intent final_l = new Intent(LevelMenu.this, FinalLevel.class);
        Intent info = new Intent(LevelMenu.this, Info.class);
        Intent end = new Intent(LevelMenu.this, EndGame.class);

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
            case R.id.start:
                wannaplay_level = 0;
                break;
            case R.id.end:
                wannaplay_level = 6;
                break;
        }

        // uruchom poziom tylko jeżeli jest odblokowany
        if (wannaplay_level<=unlocked_level) {
            if (wannaplay_level == 0) startActivity(info);
            else if (wannaplay_level == 6) startActivity(end);
            else if (wannaplay_level == 5) startActivity(final_l);
            else startActivity(play);
        }
        // wyświetl komunikat jeżeli nie jest
        else Toast.makeText(this, "You have to pass previous level!", Toast.LENGTH_SHORT).show();

    }
}
