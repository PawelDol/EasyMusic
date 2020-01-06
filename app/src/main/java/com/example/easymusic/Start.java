package com.example.easymusic;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

/** wyświetlanie ekranu startowego */
public class Start extends AppCompatActivity {

    /**
     * generuje układ graficzny,
     * przekierowuje gracza do menu głównego po kliknięciu w logo gry
     * @param savedInstanceState informacje o stanie instancji
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        ImageView btn_start = findViewById(R.id.start);

        btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent menu = new Intent(Start.this, LevelMenu.class);
                startActivity(menu);
            }
        });
    }
}
