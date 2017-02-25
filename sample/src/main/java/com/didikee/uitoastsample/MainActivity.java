package com.didikee.uitoastsample;

import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;

import com.didikee.uitoast.UIToast;
import com.didikee.uitoast.UIToast2;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private View bt_normal;
    private View bt_theme_color;
    private View bt_custom_color;
    private View bt_location_Screen;
    private View bt_location_view;
    private View bt_location_random;
    private View bt_animation;
    private View bt_builder;

    private Resources res;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        res = getResources();

        bt_normal = findViewById(R.id.bt_normal);
        bt_theme_color = findViewById(R.id.bt_theme_color);
        bt_custom_color = findViewById(R.id.bt_custom_color);
        bt_location_Screen = findViewById(R.id.bt_location_Screen);
        bt_location_view = findViewById(R.id.bt_location_view);
        bt_location_random = findViewById(R.id.bt_location_random);
        bt_animation = findViewById(R.id.bt_animation);
        bt_builder = findViewById(R.id.bt_builder);

        bt_normal.setOnClickListener(this);
        bt_theme_color.setOnClickListener(this);
        bt_custom_color.setOnClickListener(this);
        bt_location_Screen.setOnClickListener(this);
        bt_location_view.setOnClickListener(this);
        bt_location_random.setOnClickListener(this);
        bt_animation.setOnClickListener(this);
        bt_builder.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_normal:
                UIToast.showToast(this,res.getString(R.string.normal));
                break;
            case R.id.bt_theme_color:
                UIToast.showStyleToast(this,res.getString(R.string.theme_color));
//                new UIToast2.Builder(this)
//                        .setText("Toast with theme color")
//                        .setTextColor(Color.GRAY)
//                        .setBackgroundColor(Color.WHITE)
//                        .show();
                break;
            case R.id.bt_custom_color:
                UIToast.showStyleToast(this,res.getString(R.string.custom_color),false, Color.GRAY,Color.GREEN, UIToast.NONE);
                break;
            case R.id.bt_location_Screen:
                UIToast.showLocationToast(this,res.getString(R.string.location_Screen),false,null, Gravity.START|Gravity.TOP,100,100);
                break;
            case R.id.bt_location_view:
                UIToast.showLocationToast(this,res.getString(R.string.location_view),false,v, UIToast.NONE,0,0);
                break;
            case R.id.bt_location_random:
                DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
                int widthPixels = displayMetrics.widthPixels;
                int heightPixels = displayMetrics.heightPixels;
                int x = (int) (widthPixels * Math.random());
                int y = (int) (heightPixels * Math.random());
                UIToast.showLocationToast(this,res.getString(R.string.location_random),false,null,Gravity.START|Gravity.TOP,x,y);
                break;
            case R.id.bt_animation:
                UIToast.showBaseToast(this,res.getString(R.string.animation),true,UIToast.NONE,UIToast.NONE,null,UIToast.NONE,UIToast.NONE,UIToast.NONE,R.style.AnimationToast);
                break;
            case R.id.bt_builder:
                DisplayMetrics displayMetrics2 = getResources().getDisplayMetrics();
                int widthPixels2 = displayMetrics2.widthPixels;
                int heightPixels2 = displayMetrics2.heightPixels;
                new UIToast2.Builder(this)
                        .setText("UIToast2 with Builder")
                        .setTextColor(Color.WHITE)
                        .setBackgroundColor(res.getColor(R.color.colorPrimaryDark))
                        .setAnimations(R.style.AnimationToast)
                        .gravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL)
                        .offset(widthPixels2/4, (int) (heightPixels2*0.6))
                        .show();
                break;

        }
    }
}
