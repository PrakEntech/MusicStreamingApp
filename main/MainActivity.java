package com.prakentech.music;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    SeekBar pb,s2;
    String s;
    MediaPlayer m=new MediaPlayer();
    AudioManager aM;
    ImageButton im,im6,im3;
    ImageView i;
    TextView tv7,tv3,tv2,tv1,tv6;
    int c=0,r=0,b=0,min=0,sec=0,min2=0,sec2=0,y=0,tmp=0,tmp2,p=0;
    boolean isRunning = true;
    Mythread thread = new Mythread();
    Random random = new Random();
    data dat = new data();

    public class SwipeTouchListener implements View.OnTouchListener {
        private final GestureDetector gestureDetector;

        public SwipeTouchListener(Context context) {
            gestureDetector = new GestureDetector(context, new GestureListener());
        }

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            return gestureDetector.onTouchEvent(event);
        }

        private final class GestureListener extends GestureDetector.SimpleOnGestureListener {
            private static final int SWIPE_THRESHOLD = 100;
            private static final int SWIPE_VELOCITY_THRESHOLD = 100;

            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                float diffY = e2.getY() - e1.getY();
                float diffX = e2.getX() - e1.getX();

                if (Math.abs(diffX) > Math.abs(diffY) &&
                        Math.abs(diffX) > SWIPE_THRESHOLD &&
                        Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
                    if (diffX > 0) {
                        onSwipeRight();
                    } else {
                        onSwipeLeft();
                    }
                    return true;
                }
                if (Math.abs(diffY) > Math.abs(diffX) &&
                        Math.abs(diffY) > SWIPE_THRESHOLD &&
                        Math.abs(velocityY) > SWIPE_VELOCITY_THRESHOLD) {
                    if (diffY < 0) {
                        onSwipeUp();
                    } else{
                        onSwipeDown();
                    }
                    return true;
                }

                return false;
            }
        }
        public void onSwipeUp() {
            im3.setImageResource(R.drawable.baseline_task_alt_25);
            dat.like[p]=1;
            c=1;
        }
        public void onSwipeDown() {
            im3.setImageResource(R.drawable.baseline_task_alt_24);
            dat.like[p]=0;
            c=0;
        }
        public void onSwipeRight() {
            findViewById(R.id.imageButton5).performClick();
        }
        public void onSwipeLeft() {
            findViewById(R.id.imageButton7).performClick();
        }
    }

    public void playThat(View v){
        im = findViewById(R.id.imageButton2);
        if(m.isPlaying()){
            m.pause();
            im.setImageResource(R.drawable.baseline_play_circle_outline_24);
            y=0;
        }
        else{
            m.start();
            im.setImageResource(R.drawable.baseline_pause_circle_outline_24);
            y=1;
        }
    }
    public void twox(View v){
        im6 = findViewById(R.id.imageButton6);
        if(r==0){
            m.setLooping(true);
            im6.setImageResource(R.drawable.baseline_repeat_one_24);
            r=1;
        }
        else if(r==1){
            m.setLooping(false);
            im6.setImageResource(R.drawable.baseline_loop_25);
            r=2;
        }
        else if(r==2){
            m.setLooping(false);
            im6.setImageResource(R.drawable.baseline_shuffle_24);
            r=3;
        }
        else if(r==3){
            m.setLooping(false);
            im6.setImageResource(R.drawable.baseline_loop_24);
            r=0;
        }
    }
    public void prev(View v){
        m.seekTo(m.getCurrentPosition()-10000);
    }
    public void front(View v){
        m.seekTo(m.getCurrentPosition()+10000);
    }
    public void songm(View v) throws InterruptedException {
        if(r==3){
            if(!m.isPlaying()){
                im.setImageResource(R.drawable.baseline_pause_circle_outline_24);
            }
            int r=p-1+1;
            while(r==p){
                r = random.nextInt(dat.lenMusic);
            }
            p=r;
        }
        else {
            if (p == 0) {
                p = dat.lenMusic;
            } else p--;
        }
        i.setImageResource(dat.img[p]);
        tv1.setText(dat.mname[p]);
        tv2.setText(dat.ly[p]);
        tv1.setText(dat.mname[p]);
        tv2.setText(dat.ly[p]);
        tv2.scrollTo(0,0);
        tv6.setText(dat.mname2[p]);
        tv6.setText(dat.mname2[p]);
        if(dat.like[p]==1) im3.setImageResource(R.drawable.baseline_task_alt_25);
        else if(dat.like[p]==0) im3.setImageResource(R.drawable.baseline_task_alt_24);
        m.reset();
        try{m.setDataSource(MainActivity.this,Uri.parse(dat.links[p]));
        } catch (IOException e) {e.printStackTrace();}
        m.prepareAsync();
        m.seekTo(0);
        if(!m.isPlaying()){m.start();}
    }
    public void songp(View v){
        if(r==3){
            if(!m.isPlaying()){
                im.setImageResource(R.drawable.baseline_pause_circle_outline_24);
            }
            int r=p-1+1;
            while(r==p){
                r = random.nextInt(dat.lenMusic);
            }
            p=r;
        }
        else {
            if (p == dat.lenMusic) {
                p = 0;
            } else p++;
        }
        i.setImageResource(dat.img[p]);
        tv1.setText(dat.mname[p]);
        tv2.setText(dat.ly[p]);
        tv1.setText(dat.mname[p]);
        tv2.setText(dat.ly[p]);
        tv2.scrollTo(0,0);
        tv6.setText(dat.mname2[p]);
        tv6.setText(dat.mname2[p]);
        if(dat.like[p]==1) im3.setImageResource(R.drawable.baseline_task_alt_25);
        else if(dat.like[p]==0) im3.setImageResource(R.drawable.baseline_task_alt_24);
        m.reset();
        try{m.setDataSource(MainActivity.this,Uri.parse(dat.links[p]));
        } catch (IOException e) {e.printStackTrace();}
        m.prepareAsync();
        m.seekTo(0);
        if(!m.isPlaying()){m.start();}
    }

    public void open(View v){
        m.reset();
        Intent i = new Intent(MainActivity.this, MainActivity2.class);
        String s ="";
        for(int j=0;j<dat.mname.length;j++){
            s=s+ dat.like[j];
        }
        i.putExtra("string",s);
        finish();
        startActivity(i);
    }

    public void open2(View v){
        m.reset();
        Intent i = new Intent(MainActivity.this, MainActivity3.class);
        String s ="";
        for(int j=0;j<dat.mname.length;j++){
            s=s+ dat.like[j];
        }
        i.putExtra("string",s);
        finish();
        startActivity(i);
    }

    public void writeData(String data){
        String directoryName = "like";
        String fileName = "data.txt";

        try {
            File directory = new File(getFilesDir(), directoryName);
            if (!directory.exists()) {
                directory.mkdir();
            }

            File file = new File(directory, fileName);
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(fileOutputStream));
            writer.write(data);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String readData(){
        String directoryName = "like";
        String fileName = "data.txt";
        String s="";
        try {
            File directory = new File(getFilesDir(), directoryName);
            File file = new File(directory, fileName);
            if (file.exists()) {
                FileInputStream fileInputStream = new FileInputStream(file);
                BufferedReader reader = new BufferedReader(new InputStreamReader(fileInputStream));

                StringBuilder stringBuilder = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    stringBuilder.append(line);
                }
                s= String.valueOf(stringBuilder);
                reader.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return s;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        View myView = findViewById(R.id.imageView);
        SwipeTouchListener swipeTouchListener = new SwipeTouchListener(this);
        myView.setOnTouchListener(swipeTouchListener);
        try{
            s = readData();
            for(int i=0;i<s.length();i++){
                dat.like[i] = Integer.parseInt(String.valueOf(s.charAt(i)));
            }
        } catch (Exception e){
            for(int i=0;i<dat.mname.length;i++){
                s += dat.like[i];
            }
            writeData(s);
        }
        try{
            s = getIntent().getStringExtra("string");
            for(int i=0;i<dat.mname.length;i++){
                dat.like[i] = Integer.parseInt(Character.toString(s.charAt(i)));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        dat.lenMusic--;
        i=findViewById(R.id.imageView);
        im3=findViewById(R.id.imageButton);
        tv1 = findViewById(R.id.textView);
        tv2=findViewById(R.id.textView2);
        tv2.setMovementMethod(new ScrollingMovementMethod());
        tv2.setText(dat.ly[p]);
        tv6 = findViewById(R.id.textView6);
        aM = (AudioManager) getSystemService(AUDIO_SERVICE);
        Uri uri= Uri.parse(dat.links[p]);
        m.setAudioStreamType(AudioManager.STREAM_MUSIC);
        m.reset();
        try{m.setDataSource(MainActivity.this,uri);
        } catch (IOException e) {e.printStackTrace();}
        m.prepareAsync();
        m.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                pb.setMax(m.getDuration());
                m.start();
            }
        });
        m.setOnBufferingUpdateListener(new MediaPlayer.OnBufferingUpdateListener() {
            @Override
            public void onBufferingUpdate(MediaPlayer mediaPlayer, int percent) {
                pb.setSecondaryProgress((int)(m.getDuration()*(percent/100.0)));
            }
        });

        pb = findViewById(R.id.seekBar);
        s2 = findViewById(R.id.seekBar2);
        s2.setMax(aM.getStreamMaxVolume(AudioManager.STREAM_MUSIC));
        s2.setProgress(aM.getStreamVolume(AudioManager.STREAM_MUSIC));


        im3.setOnClickListener(view -> {
            if(c==0){
                im3.setImageResource(R.drawable.baseline_task_alt_25);
                dat.like[p]=1;
                c=1;
            }
            else if(c==1){
                im3.setImageResource(R.drawable.baseline_task_alt_24);
                dat.like[p]=0;
                c=0;
            }
        });
        s2.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean input) {
                if(input){
                    aM.setStreamVolume(AudioManager.STREAM_MUSIC, progress, 0);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
        pb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean input) {
               if(input){
                   m.seekTo(progress);
                   tmp=m.getCurrentPosition();
                   min=tmp/60000;
                   sec=(tmp/1000)-min*60;
                   min2=(m.getDuration()-tmp)/60000;
                   sec2=((m.getDuration()-tmp)/1000)-min2*60;

                   if(sec<10) tv7.setText(Integer.toString(min)+":0"+Integer.toString(sec));
                   else tv7.setText(Integer.toString(min)+":"+Integer.toString(sec));
                   if(sec2<10) tv3.setText(Integer.toString(min2)+":0"+Integer.toString(sec2));
                   else tv3.setText(Integer.toString(min2)+":"+Integer.toString(sec2));
               }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        tv1=findViewById(R.id.textView);
        tv7=findViewById(R.id.textView7);
        tv3=findViewById(R.id.textView3);
        im = findViewById(R.id.imageButton2);
        try{p = Integer.parseInt(getIntent().getStringExtra("value"));}
        catch(Exception e){
            p = random.nextInt(dat.lenMusic);
        }
        i.setImageResource(dat.img[p]);
        tv1.setText(dat.mname[p]);
        tv2.setText(dat.ly[p]);
        tv1.setText(dat.mname[p]);
        tv2.setText(dat.ly[p]);
        tv2.scrollTo(0,0);
        tv6.setText(dat.mname2[p]);
        tv6.setText(dat.mname2[p]);
        if(dat.like[p]==1) im3.setImageResource(R.drawable.baseline_task_alt_25);
        else if(dat.like[p]==0) im3.setImageResource(R.drawable.baseline_task_alt_24);
        m.reset();
        try{m.setDataSource(MainActivity.this,Uri.parse(dat.links[p]));
        } catch (IOException e) {e.printStackTrace();}
        m.prepareAsync();
        m.seekTo(0);
        if(!m.isPlaying()){m.start();}

        thread.start();
    }

    //This is where i have used UI Threads for smooth and responsive UI
    class Mythread extends Thread{
        @Override
        public void run() {
            while(isRunning){
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(m.isPlaying()) im.setImageResource(R.drawable.baseline_pause_circle_outline_24);
                        else im.setImageResource(R.drawable.baseline_play_circle_outline_24);

                        if(!m.isPlaying() & y==1){
                            im.setImageResource(R.drawable.baseline_play_circle_outline_24);
                            y=0;
                        }
                        tmp=m.getCurrentPosition();
                        tmp2=tmp/1000;
                        min=tmp/60000;
                        sec=(tmp/1000)-min*60;
                        min2=(m.getDuration()-tmp)/60000;
                        sec2=((m.getDuration()-tmp)/1000)-min2*60;

                        if(r==2 && tv3.getText().toString().equals("0:00")){
                            if(!m.isPlaying()){
                                im.setImageResource(R.drawable.baseline_pause_circle_outline_24);
                                if(p==dat.lenMusic){
                                    p=0;
                                }
                                else p++;
                                i.setImageResource(dat.img[p]);
                                tv1.setText(dat.mname[p]);
                                tv2.setText(dat.ly[p]);
                                tv1.setText(dat.mname[p]);
                                tv2.setText(dat.ly[p]);
                                tv2.scrollTo(0,0);
                                tv6.setText(dat.mname2[p]);
                                tv6.setText(dat.mname2[p]);
                                if(dat.like[p]==1) im3.setImageResource(R.drawable.baseline_task_alt_25);
                                else if(dat.like[p]==0) im3.setImageResource(R.drawable.baseline_task_alt_24);
                                m.reset();
                                try{m.setDataSource(MainActivity.this,Uri.parse(dat.links[p]));
                                } catch (IOException e) {e.printStackTrace();}
                                m.prepareAsync();
                                m.seekTo(0);
                                if(!m.isPlaying()){m.start();}
                            }
                        }

                        if(r==3 && tv3.getText().toString().equals("0:00")){
                            if(!m.isPlaying()){
                                im.setImageResource(R.drawable.baseline_pause_circle_outline_24);

                                int r=p-1+1;
                                while(r==p){
                                    r = random.nextInt(dat.lenMusic);
                                }
                                p=r;

                                i.setImageResource(dat.img[p]);
                                tv1.setText(dat.mname[p]);
                                tv2.setText(dat.ly[p]);
                                tv1.setText(dat.mname[p]);
                                tv2.setText(dat.ly[p]);
                                tv2.scrollTo(0,0);
                                tv6.setText(dat.mname2[p]);
                                tv6.setText(dat.mname2[p]);
                                if(dat.like[p]==1) im3.setImageResource(R.drawable.baseline_task_alt_25);
                                else if(dat.like[p]==0) im3.setImageResource(R.drawable.baseline_task_alt_24);
                                m.reset();
                                try{m.setDataSource(MainActivity.this,Uri.parse(dat.links[p]));
                                } catch (IOException e) {e.printStackTrace();}
                                m.prepareAsync();
                                m.seekTo(0);
                                if(!m.isPlaying()){m.start();}
                            }
                        }

                        if(sec2==-1) sec2=0;
                        if(sec<10) tv7.setText(Integer.toString(min)+":0"+Integer.toString(sec));
                        else tv7.setText(Integer.toString(min)+":"+Integer.toString(sec));
                        if(sec2<10) tv3.setText(Integer.toString(min2)+":0"+Integer.toString(sec2));
                        else tv3.setText(Integer.toString(min2)+":"+Integer.toString(sec2));

                        pb.setProgress(m.getCurrentPosition());
                        s2.setProgress(aM.getStreamVolume(AudioManager.STREAM_MUSIC));
                        if(b==0){
                            tv1.setTextColor(Color.parseColor("#F4F3F3"));
                            b=1;
                        }
                        else if(b==1){
                            tv1.setTextColor(Color.parseColor("#FF1E1E"));
                            b=2;
                        }
                        else if(b==2){
                            tv1.setTextColor(Color.parseColor("#4E4C4C"));
                            b=0;
                        }
                        s="";
                        for(int i=0;i<dat.mname.length;i++){
                            s += dat.like[i];
                        }
                        writeData(s);
                    }
                });
                try{
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
                super.run();
        }
    }
}
