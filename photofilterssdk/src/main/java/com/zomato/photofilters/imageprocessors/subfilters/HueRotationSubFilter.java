package com.zomato.photofilters.imageprocessors.subfilters;

import android.graphics.Bitmap;
import com.zomato.photofilters.imageprocessors.ImageProcessor;
import com.zomato.photofilters.imageprocessors.SubFilter;


/**
 * @author varun on 28/07/15.
 */
public class HueRotationSubFilter implements SubFilter {
    private static String tag = "";

    // The rotation angle degrees
    private int angle;

    public HueRotationSubFilter(int angle) {
        this.angle = angle;
    }

    @Override
    public Bitmap process(Bitmap inputImage) {
        return ImageProcessor.doHueRotation(inputImage, angle);
    }

    @Override
    public Object getTag() {
        return tag;
    }

    @Override
    public void setTag(Object tag) {
        HueRotationSubFilter.tag = (String) tag;
    }

    public void setAngle(int angle) {
        this.angle = angle;
    }
}
