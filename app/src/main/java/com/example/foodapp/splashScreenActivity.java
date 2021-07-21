package com.example.foodapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class splashScreenActivity extends AppCompatActivity {


private ViewPager slideViewPger;
private LinearLayout dotLayout;
private TextView[] mdots;
private  SliderAdapter sliderAdapter;
private Button nxtBtn,bckBtn;
private int currentPage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_splash_screen);
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();

        slideViewPger=(ViewPager)findViewById(R.id.slideViewPager);
        dotLayout=(LinearLayout)findViewById(R.id.dotLayout);
        bckBtn=(Button)findViewById(R.id.prevBtn);
        nxtBtn=(Button)findViewById(R.id.nextBtn);


        sliderAdapter=new SliderAdapter(this);
        slideViewPger.setAdapter(sliderAdapter);

        addDotsIndicator(0);
        slideViewPger.addOnPageChangeListener(viewListner);

        nxtBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(currentPage==2){
                    Intent intent =new Intent(splashScreenActivity.this ,MainActivity.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);

                }
                slideViewPger.setCurrentItem(currentPage+1);
            }
        });
        bckBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                slideViewPger.setCurrentItem(currentPage-1);
            }
        });
    }
    @SuppressLint("ResourceAsColor")
    public  void  addDotsIndicator(int position) {
        mdots = new TextView[3];
        dotLayout.removeAllViews();
        for (int i = 0; i < mdots.length; i++) {
            mdots[i] = new TextView(this);
            mdots[i].setText(Html.fromHtml("&#8226;"));
            mdots[i].setTextSize(70);
            mdots[i].setTextColor(R.color.colorPrimary);
            dotLayout.addView(mdots[i]);

        }
        if (mdots.length > 0) {
            mdots[position].setTextColor(getResources().getColor(R.color.colorAccent));
        }
    }
        ViewPager.OnPageChangeListener viewListner =new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
              addDotsIndicator(position);
            currentPage=position;
          if(position==0){
          nxtBtn.setEnabled(true);
          bckBtn.setEnabled(false);
          bckBtn.setVisibility(View.INVISIBLE);
          nxtBtn.setText("Next");
          bckBtn.setText("");
      }
      else if(position==mdots.length-1){
          nxtBtn.setEnabled(true);
          bckBtn.setEnabled(true);
          bckBtn.setVisibility(View.VISIBLE);
          nxtBtn.setText("Finish");
          bckBtn.setText("Prev");


      }
      else {
          nxtBtn.setEnabled(true);
          bckBtn.setEnabled(true);
          bckBtn.setVisibility(View.VISIBLE);
          nxtBtn.setText("Next");
          bckBtn.setText("Prev");


      }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }

    };
}