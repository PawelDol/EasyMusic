package com.example.easymusic;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.media.MediaPlayer;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.ProgressBar;
import android.widget.Toast;

public class PlayLevel extends AppCompatActivity implements View.OnClickListener, PopupMenu.OnMenuItemClickListener {

    MediaPlayer player;
    int curr_note = 0;
    char[] notes = {'c','d','e','f','g','a','b'};
    int lives = 3;
    int cursor_pos = 0;

    // pobierz obraz w zależności od wybranego poziomu
    //Intent intent = getIntent();

    //String pieciolinia = intent.getStringExtra("pieciolinia");
    //char notes[] = intent.getCharArrayExtra("notes");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_play_level);

        Button c = findViewById(R.id.c);
        Button cis = findViewById(R.id.cis);
        Button d = findViewById(R.id.d);
        Button dis = findViewById(R.id.dis);
        Button e = findViewById(R.id.e);
        Button f = findViewById(R.id.f);
        Button fis = findViewById(R.id.fis);
        Button g = findViewById(R.id.g);
        Button gis = findViewById(R.id.gis);
        Button a = findViewById(R.id.a);
        Button ais = findViewById(R.id.ais);
        Button b = findViewById(R.id.b);

        c.setOnClickListener(this);
        cis.setOnClickListener(this);
        d.setOnClickListener(this);
        dis.setOnClickListener(this);
        e.setOnClickListener(this);
        f.setOnClickListener(this);
        fis.setOnClickListener(this);
        g.setOnClickListener(this);
        gis.setOnClickListener(this);
        a.setOnClickListener(this);
        ais.setOnClickListener(this);
        b.setOnClickListener(this);

        // ProgressBar life = findViewById(R.id.life);
        // life.setMax(3);
        // life.setProgress(lives);

        Button pause = findViewById(R.id.pause);
    }



    @Override
    public void onClick(View v) {
        if (player != null) {
            player.release();
            player = null;
        }

        switch(v.getId()){
            case R.id.c:
                player = MediaPlayer.create(this,R.raw.c);
                player.start();
                check_sound(v);
                check_life();
                break;
            case R.id.cis:
                break;
            case R.id.d:
                break;
            case R.id.dis:
                break;
            case R.id.e:
                break;
            case R.id.f:
                break;
            case R.id.fis:
                break;
            case R.id.g:
                break;
            case R.id.gis:
                break;
            case R.id.a:
                break;
            case R.id.ais:
                break;
            case R.id.b:
                break;
        }

    }

    public void check_sound(View v) {
        Button b = (Button)v;
        if (b.getText().toString().equals(String.format("%c",notes[curr_note]))) {
            curr_note++;
            cursor_pos++;
        }
        else {
            lives--;
            //life.setProgress(lives);
        }
    }

    public void check_life() {
        if (lives == 0) {
            Intent game_over = new Intent(PlayLevel.this, GameOver.class);
            startActivity(game_over);
        }
    }

    public void pause_menu(View v){
        PopupMenu pause = new PopupMenu(this, v);
        pause.setOnMenuItemClickListener(this);
        pause.inflate((R.menu.pause_menu));
        pause.show();
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.again:
                Intent play = new Intent(PlayLevel.this, PlayLevel.class);
                startActivity(play);
                return true;
            case R.id.menu:
                Intent menu = new Intent(PlayLevel.this, LevelMenu.class);
                startActivity(menu);
                return true;
            case R.id.next:
                //next level
                return true;
            default:
                return false;
        }
    }
}
