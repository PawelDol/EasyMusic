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

public class FinalLevel extends Level {

    public static int max_score;
    int curr_score;
    public static int recent_score;

    int segment;    // two segment level
    int flag = 0;   // player cant't gain multiple points for one note

    Handler handler = new Handler();
    Timer timer = new Timer();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_final_level);

        curr_score =  0;
        segment = 1;
        level_notes = LevelMenu.notes[LevelMenu.wannaplay_level-1];

        init_level();

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
    public void check(View v) {
        if (curr_note<level_notes.length&&curr_note>=0) check_sound(v); // check sound only when are notes left to play
    }

    public void changeLoc(){
        ImageView cursor = findViewById(R.id.cursor);

        location += screen_width/1000;

        cursor.setX(location);

        if (curr_note < level_notes.length) curr_note = Math.round((location/screen_width)*10)-1;

        if(curr_note!=(Math.round((location+screen_width/1000)/screen_width*10)-1)) flag = 0;

        if (location>=screen_width) {

            if(segment == 1) {
                ImageView notes = findViewById(R.id.imageView);
                notes.setImageResource(R.drawable.sheet5_2);
                location = 0;
                curr_note = 0;
                level_notes = LevelMenu.notes[LevelMenu.wannaplay_level];

                segment = 2;
            }
            else if (segment == 2) {
                if(curr_score>max_score) max_score=curr_score;
                recent_score=curr_score;
                LevelMenu.unlocked_level++;

                // save maximum score to file
                SharedPreferences m_score = getSharedPreferences(LevelMenu.DATA, 0);
                SharedPreferences.Editor editor0 = m_score.edit();
                editor0.putInt("max_score", max_score);
                editor0.commit();

                // save recent score to file
                SharedPreferences rec_score = getSharedPreferences(LevelMenu.DATA, 0);
                SharedPreferences.Editor editor1 = rec_score.edit();
                editor1.putInt("recent_score", recent_score);
                editor1.commit();

                // save unlocked levels to file
                SharedPreferences unl_level = getSharedPreferences(LevelMenu.DATA, 0);
                SharedPreferences.Editor editor2 = unl_level.edit();
                editor2.putInt("unlocked_level", LevelMenu.unlocked_level);
                editor2.commit();

                timer.cancel();
                Intent end = new Intent(FinalLevel.this, EndGame.class);
                startActivity(end);
            }
        }
    }

    public void check_sound(View v) {
        Button b = (Button)v;
        TextView score = findViewById(R.id.score);

        if(b.getText().equals(level_notes[curr_note])){
            if(flag == 0) {
                curr_score++;
                flag = 1;
            }

            score.setText(String.format("%d/15",curr_score));

        }
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        Intent final_l = new Intent(FinalLevel.this, FinalLevel.class);
        timer.cancel();
        curr_score = 0;

        switch (item.getItemId())
        {
            case R.id.again:
                level_notes = LevelMenu.notes[LevelMenu.wannaplay_level-1];
                startActivity(final_l);
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
