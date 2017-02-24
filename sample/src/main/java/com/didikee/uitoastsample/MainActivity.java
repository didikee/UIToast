package com.didikee.uitoastsample;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.didikee.uitoast.UIToast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private View bt_normal;
    private View bt_theme_color;
    private View bt_custom_color;
    private View bt_location_Screen;
    private View bt_location_view;
    private View bt_location_random;
    private View bt_animation_1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bt_normal = findViewById(R.id.bt_normal);
        bt_theme_color = findViewById(R.id.bt_theme_color);
        bt_custom_color = findViewById(R.id.bt_custom_color);
        bt_location_Screen = findViewById(R.id.bt_location_Screen);
        bt_location_view = findViewById(R.id.bt_location_view);
        bt_location_random = findViewById(R.id.bt_location_random);
        bt_animation_1 = findViewById(R.id.bt_animation_1);

        bt_normal.setOnClickListener(this);
        bt_theme_color.setOnClickListener(this);
        bt_custom_color.setOnClickListener(this);
        bt_location_Screen.setOnClickListener(this);
        bt_location_view.setOnClickListener(this);
        bt_location_random.setOnClickListener(this);
        bt_animation_1.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_normal:
                Toast.makeText(this, "This is Normal Toast", Toast.LENGTH_SHORT).show();
                break;
            case R.id.bt_theme_color:
                UIToast.showStyleToast(this,"Toast with theme color");
//                new UIToastBuild.Builder(this)
//                        .setText("Toast with theme color")
//                        .setTextColor(Color.GRAY)
//                        .setBackgroundColor(Color.WHITE)
//                        .show();
                break;
            case R.id.bt_custom_color:
                UIToast.showStyleToast(this,"Toast with custom color",false, Color.RED,Color.GREEN, UIToast.NONE);
                break;
            case R.id.bt_location_Screen:
                UIToast.showLocationToast(this,"Toast below this button",false,v, UIToast.NONE,0,0);
                break;
            case R.id.bt_location_view:
                Toast.makeText(this, "This is Normal Toast", Toast.LENGTH_SHORT).show();
                break;
            case R.id.bt_location_random:
                Toast.makeText(this, "This is Normal Toast", Toast.LENGTH_SHORT).show();
                break;
            case R.id.bt_animation_1:
                Toast.makeText(this, "This is Normal Toast", Toast.LENGTH_SHORT).show();
                break;


        }
    }
}
