package com.zomato.photofilters.imageprocessors.subfilters;

import android.graphics.Bitmap;
import com.zomato.photofilters.imageprocessors.ImageProcessor;
import com.zomato.photofilters.imageprocessors.SubFilter;


/**
 * @author varun on 28/07/15.
 */
public class SephiaSubFilter implements SubFilter {
    private static String tag = "";

    // The Level value is float, where level = 1 has no effect on the image
    private float level;

    public SephiaSubFilter(float level) {
        this.level = level;
    }

    @Override
    public Bitmap process(Bitmap inputImage) {
        return ImageProcessor.doSephia(inputImage, level);
    }

    @Override
    public Object getTag() {
        return tag;
    }

    @Override
    public void setTag(Object tag) {
        SephiaSubFilter.tag = (String) tag;
    }

    public void setLevel(float level) {
        this.level = level;
    }
}
