package com.example.easymusic;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

/** struktura poziomu finałowego */
public class FinalLevel extends Level {

    /** maksymalny wynik gracza */
    public static int max_score;

    /** ostatni wynik gracza */
    public static int recent_score;

    /** ilość aktualnie zdobytych punktów */
    int curr_score;

    /** aktualny segment */
    int segment;

    /** flaga informująca o poprawnie zagranym dźwięku - uniemożliwia zdobycie kilku punktów za jedną nutę */
    int flag = 0;

    /** obsługa timera */
    Handler handler = new Handler();

    /** timer odpowiedzialny za synchroniczną zmianę położenia kursora */
    Timer timer = new Timer();

    /**
     * generuj układ graficzny,
     * inicjacja poziomu,
     * inicjacja timera
     * @param savedInstanceState informacje o stanie instancji
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_final_level);

        curr_score =  0;
        segment = 1;
        level_notes = LevelMenu.notes[LevelMenu.wannaplay_level-1];

        init_level();

        // zmień położenie kursora co 10ms
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        changeLoc();
                    }
                });
            }
        },0,10);
    }

    /**
     * funkcja sprawdzająca
     * @param v naciśnięty klawisz
     */
    @Override
    public void check(View v) {
        // sprawdź poprawność zagranego dźwięku tylko, gdy pozostały jakieś dźwięki do zagrania
        if (curr_note<level_notes.length&&curr_note>=0) check_sound(v);
    }

    /**
     * sprawdź poprawność zagranego dźwięku,
     * wyświetl aktualny wynik
     * @param v naciśnięty klawisz
     */
    public void check_sound(View v) {
        Button b = (Button)v;
        TextView score = findViewById(R.id.score);

        // jeżeli dźwięk jest poprawny...
        if(b.getText().equals(level_notes[curr_note])){

            // ...i jest możliwość zdobycia punktu...
            if(flag == 0) {

                // ... zwiększ aktualny wynik
                curr_score++;

                // ustaw flagę - zablokuj możliwość ponownego zdobycia punktu za ten dźwięk
                flag = 1;
            }
            // wyświetl aktualny wynik
            score.setText(String.format("%d/16",curr_score));

        }
    }

    /**
     * zmień położenie kursora,
     * określ aktualnie wskazywaną nutę,
     * wykonaj odpowiednią akcję, gdy kursor znajdzie się na końcu ekranu
     */
    public void changeLoc(){
        ImageView cursor = findViewById(R.id.cursor);

        // zmień położenie kursora
        location += screen_width/1000;
        cursor.setX(location);

        // określ aktualnie wskazywaną nutę 
        if (curr_note < level_notes.length) curr_note = Math.round((location/screen_width)*9)-1;

        // zwolnij flagę, gdy zmieni się aktualnie wskazywany dźwięk - daj graczowi możliwość zgrania dźwięku
        if(curr_note!=(Math.round((location+screen_width/1000)/screen_width*9)-1)) flag = 0;


        // wykonaj odpowiednią akcję
        if (location>=screen_width) {

            // gdy jesteś w połowie poziomu, przejdź do następnego segmentu
            if(segment == 1) {
                ImageView notes = findViewById(R.id.imageView);

                // zmień wyświetlane nuty
                notes.setImageResource(R.drawable.sheet5_2);

                // zresetuj położenie kursora
                location = 0;

                // zmień dźwięki
                curr_note = 0;
                level_notes = LevelMenu.notes[LevelMenu.wannaplay_level];

                segment = 2;
            }
            // gdy ukończyłeś poziom
            else if (segment == 2) {

                // określ wyniki wyświetlane na ekranie końcowym
                if(curr_score>max_score) max_score=curr_score;
                recent_score=curr_score;

                // odblokuj ekran końcowy
                if (LevelMenu.unlocked_level < 6) LevelMenu.unlocked_level++;

                // zapisz do pliku dane:
                // maksymalny wynik
                SharedPreferences m_score = getSharedPreferences(LevelMenu.DATA, 0);
                SharedPreferences.Editor editor0 = m_score.edit();
                editor0.putInt("max_score", max_score);
                editor0.commit();

                // ostatni wynik
                SharedPreferences rec_score = getSharedPreferences(LevelMenu.DATA, 0);
                SharedPreferences.Editor editor1 = rec_score.edit();
                editor1.putInt("recent_score", recent_score);
                editor1.commit();

                // liczba odblokowanych poziomów
                SharedPreferences unl_level = getSharedPreferences(LevelMenu.DATA, 0);
                SharedPreferences.Editor editor2 = unl_level.edit();
                editor2.putInt("unlocked_level", LevelMenu.unlocked_level);
                editor2.commit();

                // zatrzymaj timer i przejdź do ekranu końcowego
                timer.cancel();
                Intent end = new Intent(FinalLevel.this, EndGame.class);
                startActivity(end);
            }
        }
    }

    /** wykonaj odpowiednią akcję po wybraniu opcji z menu pauzy i zatrzymaj timer
     * @param item opcja wybrana w menu
     * @return true jeżeli wybrano opcję z menu
     */
    @Override
    public boolean onMenuItemClick(MenuItem item) {
        Intent final_l = new Intent(FinalLevel.this, FinalLevel.class);

        // zatrzymaj timer
        timer.cancel();

        // wyzeruj aktualny wynik
        curr_score = 0;

        switch (item.getItemId())
        {
            // uruchom poziom ponownie
            case R.id.again:
                level_notes = LevelMenu.notes[LevelMenu.wannaplay_level-1];
                startActivity(final_l);
                return true;
            // przejdź do menu głównego
            case R.id.menu:
                Intent menu = new Intent(FinalLevel.this, LevelMenu.class);
                startActivity(menu);
                return true;
            default:
                return false;
        }
    }

}
