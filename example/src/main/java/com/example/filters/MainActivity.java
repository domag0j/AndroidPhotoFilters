package com.example.filters;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

import com.zomato.photofilters.SampleFilters;
import com.zomato.photofilters.imageprocessors.ImageProcessor;

import java.util.List;

public class MainActivity extends AppCompatActivity implements ThumbnailCallback {
    static {
        System.loadLibrary("NativeImageProcessor");
    }

    private Activity activity;
    private RecyclerView thumbListView;
    private ImageView placeHolderImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        activity = this;
        initUIWidgets();
    }

    private void initUIWidgets() {
        thumbListView = (RecyclerView) findViewById(R.id.thumbnails);
        placeHolderImageView = (ImageView) findViewById(R.id.place_holder_imageview);
        placeHolderImageView.setImageBitmap(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(this.getApplicationContext().getResources(), R.drawable.photo), 640, 640, false));
        initHorizontalList();
    }

    private void initHorizontalList() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        layoutManager.scrollToPosition(0);
        thumbListView.setLayoutManager(layoutManager);
        thumbListView.setHasFixedSize(true);
        bindDataToAdapter();
    }

    private void bindDataToAdapter() {
        final Context context = this.getApplication();
        Handler handler = new Handler();
        Runnable r = new Runnable() {
            public void run() {
                Bitmap thumbImage = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.photo), 640, 640, false);
                ThumbnailItem t1 = new ThumbnailItem();
                ThumbnailItem t2 = new ThumbnailItem();
                ThumbnailItem t3 = new ThumbnailItem();
                ThumbnailItem t4 = new ThumbnailItem();
                ThumbnailItem t5 = new ThumbnailItem();
                ThumbnailItem t6 = new ThumbnailItem();
                ThumbnailItem t7 = new ThumbnailItem();

                t1.image = thumbImage;
                t2.image = thumbImage;
                t3.image = thumbImage;
                t4.image = thumbImage;
                t5.image = thumbImage;
                t6.image = thumbImage;
                t7.image = thumbImage;
                ThumbnailsManager.clearThumbs();
                ThumbnailsManager.addThumb(t1); // Original Image
//
//                t2.filter = SampleFilters.getHueRotateSubfilter(30);
//                ThumbnailsManager.addThumb(t2);
//
//                t3.filter = SampleFilters.getHueRotateSubfilter(60);
//                ThumbnailsManager.addThumb(t3);
//
//                t4.filter = SampleFilters.getHueRotateSubfilter(90);
//                ThumbnailsManager.addThumb(t4);
//
//                t5.filter = SampleFilters.getHueRotateSubfilter(120);
//                ThumbnailsManager.addThumb(t5);
//
//                t6.filter = SampleFilters.getHueRotateSubfilter(150);
//                ThumbnailsManager.addThumb(t6);
//
//                t7.filter =  SampleFilters.getHueRotateSubfilter(180);
//               ThumbnailsManager.addThumb(t7);





//                t2.testSoftLight = true;
//                ThumbnailsManager.addThumb(t2);
//
//                t3.filter = SampleFilters.getSephia(0.2f);
//                ThumbnailsManager.addThumb(t3);
//
//                t4.filter = SampleFilters.getSephia(0.5f);
//                ThumbnailsManager.addThumb(t4);
//
//                t5.filter = SampleFilters.getSephia(0.7f);
//                ThumbnailsManager.addThumb(t5);
//
//                t6.filter = SampleFilters.getSephia(1.0f);
//                ThumbnailsManager.addThumb(t6);
//
//                t7.filter =  SampleFilters.getHueRotateSubfilter(180);
//                ThumbnailsManager.addThumb(t7);

                t2.testSoftLight = true;
                ThumbnailsManager.addThumb(t2);

                t3.filter = SampleFilters.getBrightnessSubfilter(30);
                ThumbnailsManager.addThumb(t3);

                t4.filter = SampleFilters.getBrightnessSubfilter(140);
                ThumbnailsManager.addThumb(t4);

                t5.filter = SampleFilters.getContrastSubfilter(1.0f);
                ThumbnailsManager.addThumb(t5);

                t6.filter = SampleFilters.getContrastSubfilter(0.1f);
                ThumbnailsManager.addThumb(t6);

                t7.filter =  SampleFilters.getContrastSubfilter(1.3f);
                ThumbnailsManager.addThumb(t7);


                List<ThumbnailItem> thumbs = ThumbnailsManager.processThumbs(context);

                ThumbnailsAdapter adapter = new ThumbnailsAdapter(thumbs, (ThumbnailCallback) activity);
                thumbListView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }
        };
        handler.post(r);
    }

    @Override
    public void onThumbnailClick(ThumbnailItem thumbnailItem) {
        if (thumbnailItem.testSoftLight) {
            Bitmap blend_mask = Bitmap.createBitmap((int) 640, (int) 640, Bitmap.Config.ARGB_8888);
            blend_mask.eraseColor(Color.argb(127, 72, 03, 57));
            Bitmap base = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(this.getApplicationContext().getResources(), R.drawable.photo), 640, 640, false);
            try {
                ImageProcessor.blend(base, blend_mask, ImageProcessor.BLEND_MODE_EXCLUSION);
            } catch (Exception e) {
                e.printStackTrace();
            }
            placeHolderImageView.setImageBitmap(base);
        }else {
            placeHolderImageView.setImageBitmap(thumbnailItem.filter.processFilter(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(this.getApplicationContext().getResources(), R.drawable.photo), 640, 640, false)));
        }
    }
}
