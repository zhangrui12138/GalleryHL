package com.example.pc252.expendabletextview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.PageTransformer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener{
    private ViewPager myViewPager;
    private MyViewpagerAdapter myViewpagerAdapter;
    private MyTrasform myTramform;
    private int imageList[];
    private ImageView myImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myImage = (ImageView)findViewById(R.id.backer);
        myViewPager=(ViewPager)findViewById(R.id.myViewPager);
        myViewPager.setOffscreenPageLimit(3);
        imageList = new int[]{R.drawable.preview0,R.drawable.preview1,R.drawable.preview2,
                R.drawable.preview3,R.drawable.preview4,R.drawable.preview5,
                R.drawable.preview6,R.drawable.preview7,R.drawable.preview8,
                R.drawable.preview9,R.drawable.preview10,R.drawable.preview11,
                R.drawable.preview12,R.drawable.preview13,R.drawable.preview14,
                R.drawable.preview15,R.drawable.preview16,R.drawable.preview17,
                R.drawable.preview18,R.drawable.preview19,R.drawable.preview20,
                R.drawable.preview21,R.drawable.preview22,R.drawable.preview23};
        myViewpagerAdapter=new MyViewpagerAdapter(getApplicationContext());
        myTramform=new MyTrasform();
        myViewPager.setOnPageChangeListener(this);
        myViewPager.setPageTransformer(true,myTramform);
        myViewPager.setAdapter(myViewpagerAdapter);
        //添加虚化背景图
        myImage.setImageBitmap(readBitMap(getApplicationContext(),imageList[0]));
        if(myImage != null)  myImage.setAlpha(0.5f);
        //添加虚化背景图
     }

     private class MyTrasform implements PageTransformer {
        private static final float min_scale = 0.85f;

         @Override
         public void transformPage(View page, float position) {
             float scaleFactor = Math.max(min_scale, 1 - Math.abs(position));
             float rotate = 20 * Math.abs(position);
             if (position < -1) {// [-Infinity,-1)//This page is way off-screen to the left.
                 page.setAlpha(0.5f);
             } else if (position < 0) { // [-1,0]Use //the default slide transition when moving to the left page
                 page.setAlpha(0.5f);
                 page.setScaleX(scaleFactor);
                 page.setScaleY(scaleFactor);
                 page.setRotationY(rotate);
             } else if (position >= 0 && position < 1) { // (0,1]// Fade the page out.
                 page.setAlpha(1 - position);
                 page.setScaleX(scaleFactor);
                 page.setScaleY(scaleFactor);
                 page.setRotationY(-rotate);
             } else if (position >= 1) {// (1,+Infinity]
                 // This page is way off-screen to the right.
                 page.setAlpha(0.5f);
                 page.setScaleX(scaleFactor);
                 page.setScaleY(scaleFactor);
                 page.setRotationY(-rotate);
             }else {
                 // (1,+Infinity]
                 // This page is way off-screen to the right.
                 page.setAlpha(0.5f);
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
            Log.d("zhangrui","======instantiateItem======");
            ImageView imageviewr=new ImageView(pagerAdapterContext);
            imageviewr.setScaleType(ImageView.ScaleType.CENTER);
//            imageviewr.setBackgroundResource(imageList[position]);
            Bitmap ZjBitmap = readBitMap(pagerAdapterContext,imageList[position]);
            imageviewr.setImageBitmap(ZjBitmap);
            container.addView(imageviewr);
            return imageviewr;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            Log.d("zhangrui","======destroyItem======");
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


    public Bitmap readBitMap(Context context, int resId) {
         BitmapFactory.Options opt = new BitmapFactory.Options();
         opt.inPreferredConfig = Bitmap.Config.RGB_565;
         opt.inPurgeable = true;
         opt.inInputShareable = true;
         InputStream is = context.getResources().openRawResource(resId);//获取资源图片
         Bitmap temp = BitmapFactory.decodeStream(is, null, opt);
         Bitmap newtemp = setImgSize(temp,400,700);
        if(temp != null){
            temp.recycle();
            temp = null;
        }
         return /*BitmapFactory.decodeStream(is, null, opt)*/newtemp;
         }

    public Bitmap setImgSize(Bitmap bm, int newWidth ,int newHeight){
        // 获得图片的宽高.
        int width = bm.getWidth();
        int height = bm.getHeight();
        // 计算缩放比例.
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // 取得想要缩放的matrix参数.
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        // 得到新的图片.
        Bitmap newbm = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, true);
        return newbm;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
      Log.d("zhangrui","onPageSelected position"+position);
        //添加虚化背景图
        myImage.setImageBitmap(readBitMap(getApplicationContext(),imageList[position]));
        if(myImage != null)  myImage.setAlpha(0.5f);
        //添加虚化背景图
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
