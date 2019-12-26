package com.example.easymusic;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.media.MediaPlayer;
import android.util.DisplayMetrics;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.ProgressBar;
import android.widget.Toast;

public class PlayLevel extends AppCompatActivity implements View.OnClickListener, PopupMenu.OnMenuItemClickListener {

    MediaPlayer player;
    int curr_note = 0;
    int lives = 3;
    int screen_width;
    String[] level_notes = LevelMenu.notes[LevelMenu.wannaplay_level-1];
    float location = 0;

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

        ImageView notes = findViewById(R.id.imageView);

        switch(LevelMenu.wannaplay_level) {
            case 1:
                notes.setImageResource(R.drawable.sheet1);
                break;
            case 2:
                notes.setImageResource(R.drawable.sheet2);
                break;
            case 3:
                notes.setImageResource(R.drawable.sheet3);
                break;
            case 4:
                notes.setImageResource(R.drawable.sheet4);
                break;
            case 5:
                notes.setImageResource(R.drawable.sheet1);
                break;
        }

        DisplayMetrics display = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(display);
        screen_width = display.widthPixels;
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
                break;
            case R.id.cis:
                player = MediaPlayer.create(this,R.raw.cis);
                break;
            case R.id.d:
                player = MediaPlayer.create(this,R.raw.d);
                break;
            case R.id.dis:
                player = MediaPlayer.create(this,R.raw.dis);
                break;
            case R.id.e:
                player = MediaPlayer.create(this,R.raw.e);
                break;
            case R.id.f:
                player = MediaPlayer.create(this,R.raw.f);
                break;
            case R.id.fis:
                player = MediaPlayer.create(this,R.raw.fis);
                break;
            case R.id.g:
                player = MediaPlayer.create(this,R.raw.g);
                break;
            case R.id.gis:
                player = MediaPlayer.create(this,R.raw.gis);
                break;
            case R.id.a:
                player = MediaPlayer.create(this,R.raw.a);
                break;
            case R.id.ais:
                player = MediaPlayer.create(this,R.raw.ais);
                break;
            case R.id.b:
                player = MediaPlayer.create(this,R.raw.b);
                break;
        }
        player.start();

        check_sound(v);
        check_life();
        check_finish();

    }

    public void check_sound(View v) {
        Button b = (Button)v;
        ImageView cursor = findViewById(R.id.cursor);
        ProgressBar life = findViewById(R.id.life);

        if (b.getText().equals(level_notes[curr_note])) {
            curr_note++;
            location = cursor.getX();
            if ((curr_note)%4==0) cursor.setX(location + 14*screen_width/100);
            else cursor.setX(location + 10 *screen_width/100);
        }
        else {
            lives--;
            life.setProgress(lives);
            Toast.makeText(this, "Wrong note!", Toast.LENGTH_SHORT).show();
        }
    }

    public void check_life() {
        if (lives == 0) {
            Intent game_over = new Intent(PlayLevel.this, GameOver.class);
            startActivity(game_over);
        }
    }

    public void check_finish() {
        if ((curr_note) == level_notes.length) {
            if (LevelMenu.unlocked_level == LevelMenu.wannaplay_level) {
                LevelMenu.unlocked_level++;
            }
            Intent next = new Intent(PlayLevel.this, NextLevelMenu.class);
            startActivity(next);
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
        Intent play = new Intent(PlayLevel.this, PlayLevel.class);
        switch (item.getItemId())
        {
            case R.id.more:
                startActivity(play);
                return true;
            case R.id.menu:
                Intent menu = new Intent(PlayLevel.this, LevelMenu.class);
                startActivity(menu);
                return true;
            default:
                return false;
        }
    }
}
