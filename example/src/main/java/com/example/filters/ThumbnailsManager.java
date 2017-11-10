package com.example.filters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;

import com.zomato.photofilters.imageprocessors.ImageProcessor;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Varun on 30/06/15.
 *         <p/>
 *         Singleton Class Used to Manage filters and process them all at once
 */
public final class ThumbnailsManager {
    private static List<ThumbnailItem> filterThumbs = new ArrayList<ThumbnailItem>(10);
    private static List<ThumbnailItem> processedThumbs = new ArrayList<ThumbnailItem>(10);

    private ThumbnailsManager() {
    }

    public static void addThumb(ThumbnailItem thumbnailItem) {
        filterThumbs.add(thumbnailItem);
    }

    public static List<ThumbnailItem> processThumbs(Context context) {
        for (ThumbnailItem thumb : filterThumbs) {
            // scaling down the image
            float size = context.getResources().getDimension(R.dimen.thumbnail_size);
            thumb.image = Bitmap.createScaledBitmap(thumb.image, (int) size, (int) size, false);
            if (thumb.testSoftLight){
                Bitmap blend_mask = Bitmap.createBitmap((int) size, (int) size, Bitmap.Config.ARGB_8888);
                blend_mask.eraseColor(Color.argb(127, 125, 105, 124));
                try {
                    ImageProcessor.blend(thumb.image, blend_mask, ImageProcessor.BLEND_MODE_EXCLUSION);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }else {
                thumb.image = thumb.filter.processFilter(thumb.image);
            }
            //cropping circle
            thumb.image = GeneralUtils.generateCircularBitmap(thumb.image);
            processedThumbs.add(thumb);
        }
        return processedThumbs;
    }

    public static void clearThumbs() {
        filterThumbs = new ArrayList<>();
        processedThumbs = new ArrayList<>();
    }
}
