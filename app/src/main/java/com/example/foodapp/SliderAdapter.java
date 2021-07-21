package com.example.foodapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;



import androidx.viewpager.widget.PagerAdapter;


public class SliderAdapter extends PagerAdapter {
    Context context;
    LayoutInflater layoutInflater;
    public SliderAdapter(Context context) {
        this.context = context;
    }
        public int[] slide_images={
                R.drawable.aslide,
                R.drawable.bslide,
                R.drawable.cslide,
        };
        public String[] slide_Headings={
                "Dine and enjoy!",
                "Get food delivery at your footstep!",
                "100% hygiene!"
        };
        public String[] slide_desc={
                "The foondest Memories are made when Gathered around the table!...........................",
                "We are fast then your call!.................................",
                "The first wealth is health!............................................."
        };
    @Override
    public int getCount() {
        return slide_Headings.length;
    }

    @Override
    public boolean isViewFromObject( View view,  Object object) {
        return view ==(RelativeLayout) object;
    }
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            layoutInflater=(LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            View view =layoutInflater.inflate(R.layout.slide,container,false);

            ImageView slideImage =(ImageView) view.findViewById(R.id.images);
            TextView slideHeading=(TextView) view.findViewById(R.id.heading);
            TextView slideDiscription=(TextView) view.findViewById(R.id.descrption);

            slideImage.setImageResource(slide_images[position]);
            slideHeading.setText(slide_Headings[position]);
            slideDiscription.setText(slide_desc[position]);

            container.addView(view);



            return view;
        }

    public void destroyItem(ViewGroup container,int position,Object object) {
        container.removeView((RelativeLayout) object);
    }
}
