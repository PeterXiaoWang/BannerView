package com.afun.bannerviewdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BannerView bannerView = findViewById(R.id.banner_view);

        ArrayList<View> imageViewList = new ArrayList<>();

        ImageView imageView1 = new ImageView(this);
        imageView1.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));
        imageView1.setImageResource(R.mipmap.one);
        imageView1.setScaleType(ImageView.ScaleType.FIT_XY);
        imageViewList.add(imageView1);

        ImageView firstImage = new ImageView(this);
        firstImage.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));
        firstImage.setImageResource(R.mipmap.one);
        firstImage.setScaleType(ImageView.ScaleType.FIT_XY);

        ImageView imageView2 = new ImageView(this);
        imageView2.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));
        imageView2.setImageResource(R.mipmap.two);
        imageView2.setScaleType(ImageView.ScaleType.FIT_XY);
        imageViewList.add(imageView2);

        ImageView imageView3 = new ImageView(this);
        imageView3.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));
        imageView3.setImageResource(R.mipmap.three);
        imageView3.setScaleType(ImageView.ScaleType.FIT_XY);
        imageViewList.add(imageView3);

        ImageView imageView4 = new ImageView(this);
        imageView4.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));
        imageView4.setImageResource(R.mipmap.four);
        imageView4.setScaleType(ImageView.ScaleType.FIT_XY);
        imageViewList.add(imageView4);

        ImageView imageView5 = new ImageView(this);
        imageView5.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));
        imageView5.setImageResource(R.mipmap.five);
        imageView5.setScaleType(ImageView.ScaleType.FIT_XY);
        imageViewList.add(imageView5);

        ImageView lastImage = new ImageView(this);
        lastImage.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));
        lastImage.setImageResource(R.mipmap.five);
        lastImage.setScaleType(ImageView.ScaleType.FIT_XY);

        imageViewList.add(0, lastImage);
        imageViewList.add(firstImage);

        bannerView.updateBannerView(imageViewList, true);
    }
}
