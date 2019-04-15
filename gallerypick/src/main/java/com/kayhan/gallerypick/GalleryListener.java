package com.kayhan.gallerypick;

import android.graphics.Bitmap;

public interface GalleryListener {
    void onBitmapCreated(Bitmap bitmap);

    void onByteArrayCreated(byte[] bytes);
}
