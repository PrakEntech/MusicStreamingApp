package com.prakentech.music;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.IOException;

public class MainActivity3 extends AppCompatActivity {
    data dat;
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        dat = new data();
        String s = getIntent().getStringExtra("string");
        intent = new Intent(MainActivity3.this,MainActivity.class);
        LinearLayout linearLayout = findViewById(R.id.linearlayout);
        Button b = findViewById(R.id.button);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity3.this,MainActivity.class);
                i.putExtra("string", s);
                finish();
                startActivity(i);
            }
        });

        for (int i = 0; i < dat.mname.length; i++) {
            if(s.charAt(i)=='1'){
                Button textView = new Button(this);
                int j = i;
                textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        intent.putExtra("value", Integer.toString(j));
                        intent.putExtra("string", s);
                        finish();
                        startActivity(intent);
                    }
                });
                textView.setBackgroundColor(Color.parseColor("#00FFFFFF"));
                textView.setText(dat.mname[i]);
                textView.setTextSize(18);
                linearLayout.addView(textView);
            }
        }
    }
}
