package com.example.easymusic;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.media.MediaPlayer;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class FinalLevel extends AppCompatActivity implements View.OnClickListener, PopupMenu.OnMenuItemClickListener {

    MediaPlayer player;
    int curr_note = 0;
    int screen_width;
    public static String[] level_notes = LevelMenu.notes[LevelMenu.wannaplay_level-1];
    float location = 0;
    public static int max_score;
    public static int curr_score=0;
    double note_loc;

    Handler handler = new Handler();
    Timer timer = new Timer();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_final_level);

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
        note_loc = 0.12*screen_width;

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
    }

    public void changeLoc(){
        ImageView cursor = findViewById(R.id.cursor);

        if (location < screen_width) location += screen_width/1000;
        else location=0;

        cursor.setX(location);

        curr_note = Math.round((location/screen_width)*10)-1;

        if (location>=screen_width) {
            if(curr_score>max_score) max_score=curr_score;

            Intent end = new Intent(FinalLevel.this, EndGame.class);
            startActivity(end);
        }
    }

    public void check_sound(View v) {
        Button b = (Button)v;
        TextView score = findViewById(R.id.score);

        if(b.getText().equals(level_notes[curr_note])){
            curr_score++;
            score.setText(String.format("%d/%d",curr_score, level_notes.length));
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
        Intent play = new Intent(FinalLevel.this, PlayLevel.class);
        switch (item.getItemId())
        {
            case R.id.more:
                startActivity(play);
                return true;
            case R.id.menu:
                Intent menu = new Intent(FinalLevel.this, LevelMenu.class);
                startActivity(menu);
                return true;
            default:
                return false;
        }
    }
}
