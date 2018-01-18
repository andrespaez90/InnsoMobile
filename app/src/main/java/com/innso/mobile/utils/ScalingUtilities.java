package com.innso.mobile.utils;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

public class ScalingUtilities {

    public static Bitmap decodeResource(Resources res, int resId, int dstWidth, int dstHeight, ScalingUtilities.ScalingLogic scalingLogic) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);
        options.inJustDecodeBounds = false;
        options.inSampleSize = calculateSampleSize(options.outWidth, options.outHeight, dstWidth, dstHeight, scalingLogic);
        return BitmapFactory.decodeResource(res, resId, options);
    }

    public static Bitmap decodeFile(String path, int dstWidth, int dstHeight, ScalingUtilities.ScalingLogic scalingLogic) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);
        options.inJustDecodeBounds = false;
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        options.inSampleSize = calculateSampleSize(options.outWidth, options.outHeight, dstWidth, dstHeight, scalingLogic);
        return BitmapFactory.decodeFile(path, options);
    }

    public static Bitmap createScaledBitmap(Bitmap unscaledBitmap, int dstWidth, int dstHeight, ScalingUtilities.ScalingLogic scalingLogic) {
        Rect srcRect = calculateSrcRect(unscaledBitmap.getWidth(), unscaledBitmap.getHeight(), dstWidth, dstHeight, scalingLogic);
        Rect dstRect = calculateDstRect(unscaledBitmap.getWidth(), unscaledBitmap.getHeight(), dstWidth, dstHeight, scalingLogic);
        Bitmap scaledBitmap = Bitmap.createBitmap(dstRect.width(), dstRect.height(), Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(scaledBitmap);
        canvas.drawBitmap(unscaledBitmap, srcRect, dstRect, new Paint(2));
        return scaledBitmap;
    }

    public static int calculateSampleSize(int srcWidth, int srcHeight, int dstWidth, int dstHeight, ScalingUtilities.ScalingLogic scalingLogic) {
        float srcAspect;
        float dstAspect;
        if(scalingLogic == ScalingUtilities.ScalingLogic.FIT) {
            srcAspect = (float)srcWidth / (float)srcHeight;
            dstAspect = (float)dstWidth / (float)dstHeight;
            return srcAspect > dstAspect?srcWidth / dstWidth:srcHeight / dstHeight;
        } else {
            srcAspect = (float)srcWidth / (float)srcHeight;
            dstAspect = (float)dstWidth / (float)dstHeight;
            return srcAspect > dstAspect?srcHeight / dstHeight:srcWidth / dstWidth;
        }
    }

    public static Rect calculateSrcRect(int srcWidth, int srcHeight, int dstWidth, int dstHeight, ScalingUtilities.ScalingLogic scalingLogic) {
        if(scalingLogic == ScalingUtilities.ScalingLogic.CROP) {
            float srcAspect = (float)srcWidth / (float)srcHeight;
            float dstAspect = (float)dstWidth / (float)dstHeight;
            int srcRectHeight;
            int scrRectTop;
            if(srcAspect > dstAspect) {
                srcRectHeight = (int)((float)srcHeight * dstAspect);
                scrRectTop = (srcWidth - srcRectHeight) / 2;
                return new Rect(scrRectTop, 0, scrRectTop + srcRectHeight, srcHeight);
            } else {
                srcRectHeight = (int)((float)srcWidth / dstAspect);
                scrRectTop = (srcHeight - srcRectHeight) / 2;
                return new Rect(0, scrRectTop, srcWidth, scrRectTop + srcRectHeight);
            }
        } else {
            return new Rect(0, 0, srcWidth, srcHeight);
        }
    }

    public static Rect calculateDstRect(int srcWidth, int srcHeight, int dstWidth, int dstHeight, ScalingUtilities.ScalingLogic scalingLogic) {
        if(scalingLogic == ScalingUtilities.ScalingLogic.FIT) {
            float srcAspect = (float)srcWidth / (float)srcHeight;
            float dstAspect = (float)dstWidth / (float)dstHeight;
            return srcAspect > dstAspect?new Rect(0, 0, dstWidth, (int)((float)dstWidth / srcAspect)):new Rect(0, 0, (int)((float)dstHeight * srcAspect), dstHeight);
        } else {
            return new Rect(0, 0, dstWidth, dstHeight);
        }
    }

    public static enum ScalingLogic {
        CROP,
        FIT;

        private ScalingLogic() {
        }
    }

}
