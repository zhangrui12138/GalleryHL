package com.example.pc252.expendabletextview;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.PageTransformer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ViewPager myViewPager;
    private MyViewpagerAdapter myViewpagerAdapter;
    private MyTrasform myTramform;
    private int imageList[];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myViewPager=(ViewPager)findViewById(R.id.myViewPager);
        myViewPager.setOffscreenPageLimit(3);
        imageList = new int[]{R.drawable.preview0,R.drawable.preview1,R.drawable.preview2,
                R.drawable.preview3,R.drawable.preview4,R.drawable.preview5};
        myViewpagerAdapter=new MyViewpagerAdapter(getApplicationContext());
        myTramform=new MyTrasform();
        myViewPager.setPageTransformer(true,myTramform);
        myViewPager.setAdapter(myViewpagerAdapter);
     }

     private class MyTrasform implements PageTransformer {
        private static final float min_scale = 0.85f;

         @Override
         public void transformPage(View page, float position) {
             float scaleFactor = Math.max(min_scale, 1 - Math.abs(position));
             float rotate = 20 * Math.abs(position);
             Log.d("zhangrui","position="+position);
             if (position < -1) {

             } else if (position < 0) {
                 page.setScaleX(scaleFactor);
                 page.setScaleY(scaleFactor);
                 page.setRotationY(rotate);
             } else if (position >= 0 && position < 1) {
                 page.setScaleX(scaleFactor);
                 page.setScaleY(scaleFactor);
                 page.setRotationY(-rotate);
             } else if (position >= 1) {
                 page.setScaleX(scaleFactor);
                 page.setScaleY(scaleFactor);
                 page.setRotationY(-rotate);
             }
         }
     }

    private class MyViewpagerAdapter extends PagerAdapter{
         private Context pagerAdapterContext;
        public MyViewpagerAdapter(Context cotext) {
            super();
            pagerAdapterContext = cotext;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView imageviewr=new ImageView(pagerAdapterContext);
            imageviewr.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageviewr.setBackgroundResource(imageList[position]);
            container.addView(imageviewr);
            return imageviewr;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View)object);
        }

        @Override
        public int getCount() {
            return imageList == null ? 0 : imageList.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
    }

}
