package com.zomato.photofilters.imageprocessors;

import android.graphics.Bitmap;

/**
 * @author Varun on 29/06/15.
 */
public final class ImageProcessor {
    public final static int BLEND_MODE_SOFT_LIGHT = 1;
    public final static int BLEND_MODE_EXCLUSION = 2;


    private ImageProcessor() {
    }

    public static Bitmap applyCurves(int[] rgb, int[] red, int[] green, int[] blue, Bitmap inputImage) {
        // create output bitmap
        Bitmap outputImage = inputImage;

        // get image size
        int width = inputImage.getWidth();
        int height = inputImage.getHeight();

        int[] pixels = new int[width * height];
        outputImage.getPixels(pixels, 0, width, 0, 0, width, height);

        if (rgb != null) {
            pixels = NativeImageProcessor.applyRGBCurve(pixels, rgb, width, height);
        }

        if (!(red == null && green == null && blue == null)) {
            pixels = NativeImageProcessor.applyChannelCurves(pixels, red, green, blue, width, height);
        }

        try {
            outputImage.setPixels(pixels, 0, width, 0, 0, width, height);
        } catch (IllegalStateException ise) {
        }
        return outputImage;
    }

    public static Bitmap doBrightness(int value, Bitmap inputImage) {
        int width = inputImage.getWidth();
        int height = inputImage.getHeight();
        int[] pixels = new int[width * height];

        inputImage.getPixels(pixels, 0, width, 0, 0, width, height);
        NativeImageProcessor.doBrightness(pixels, value, width, height);
        inputImage.setPixels(pixels, 0, width, 0, 0, width, height);

        return inputImage;
    }

    public static Bitmap doContrast(float value, Bitmap inputImage) {
        int width = inputImage.getWidth();
        int height = inputImage.getHeight();
        int[] pixels = new int[width * height];

        inputImage.getPixels(pixels, 0, width, 0, 0, width, height);
        NativeImageProcessor.doContrast(pixels, value, width, height);
        inputImage.setPixels(pixels, 0, width, 0, 0, width, height);

        return inputImage;
    }


    public static Bitmap doColorOverlay(int depth, float red, float green, float blue, Bitmap inputImage) {
        int width = inputImage.getWidth();
        int height = inputImage.getHeight();
        int[] pixels = new int[width * height];

        inputImage.getPixels(pixels, 0, width, 0, 0, width, height);
        NativeImageProcessor.doColorOverlay(pixels, depth, red, green, blue, width, height);
        inputImage.setPixels(pixels, 0, width, 0, 0, width, height);

        return inputImage;
    }

    public static Bitmap doSaturation(Bitmap inputImage, float level) {
        int width = inputImage.getWidth();
        int height = inputImage.getHeight();
        int[] pixels = new int[width * height];

        inputImage.getPixels(pixels, 0, width, 0, 0, width, height);
        NativeImageProcessor.doSaturation(pixels, level, width, height);
        inputImage.setPixels(pixels, 0, width, 0, 0, width, height);
        return inputImage;
    }

    public static Bitmap doHueRotation(Bitmap inputImage, int angle) {
        int width = inputImage.getWidth();
        int height = inputImage.getHeight();
        int[] pixels = new int[width * height];

        inputImage.getPixels(pixels, 0, width, 0, 0, width, height);
        NativeImageProcessor.doRotateHue(pixels, angle, width, height);
        inputImage.setPixels(pixels, 0, width, 0, 0, width, height);
        return inputImage;
    }

    public static Bitmap doSephia(Bitmap inputImage, float level) {
        int width = inputImage.getWidth();
        int height = inputImage.getHeight();
        int[] pixels = new int[width * height];

        inputImage.getPixels(pixels, 0, width, 0, 0, width, height);
        NativeImageProcessor.doSephia(pixels, level, width, height);
        inputImage.setPixels(pixels, 0, width, 0, 0, width, height);
        return inputImage;
    }

    public static void blend(Bitmap base, Bitmap top, int blend_mode) throws Exception {
        int width = base.getWidth();
        int height = base.getHeight();
        if (top.getWidth()!=width || top.getHeight()!=height){
            throw new Exception("bitmaps do not match");
        }
        int[] pixels_base = new int[width * height];
        int[] pixels_top = new int[width * height];
        base.getPixels(pixels_base, 0, width, 0, 0, width, height);
        top.getPixels(pixels_top, 0, width, 0, 0, width, height);
        //for now only soft light
        if (blend_mode==BLEND_MODE_EXCLUSION){
            NativeImageProcessor.blendExclusion(pixels_base, pixels_top, width, height);
        }else {
            NativeImageProcessor.blendSoftLight(pixels_base, pixels_top, width, height);
        }
        base.setPixels(pixels_base, 0, width, 0, 0, width, height);

    }

}
