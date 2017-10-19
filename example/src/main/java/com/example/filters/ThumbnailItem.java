package com.example.filters;

import android.graphics.Bitmap;

import com.zomato.photofilters.imageprocessors.Filter;

/**
 * @author Varun on 01/07/15.
 */
public class ThumbnailItem {
    public Bitmap image;
    public Filter filter;
    public boolean testSoftLight;

    public ThumbnailItem() {
        image = null;
        testSoftLight = false;
        filter = new Filter();
    }
}
