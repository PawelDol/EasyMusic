package com.example.easymusic;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class LevelMenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level_menu);

        Button l1 = (Button)findViewById(R.id.l1);
        final char notes[] = {'c','d','e','f','g','a','b'};

        l1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent play = new Intent(LevelMenu.this, PlayLevel.class);
               // play.putExtra("pieciolinia", "@drawable/pieciolinia1");
                play.putExtra("notes", notes);
                startActivity(play);
            }
        });
    }
}
