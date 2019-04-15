package com.kayhan.gallerypick;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.io.ByteArrayOutputStream;


public class GalleryPicker {

    public static final int REQUEST_GALLERY = 34;
    private static GalleryPicker galleryPicker;
    GalleryListener listener = null;
    Fragment fragment;
    AppCompatActivity activity;
    Uri selectedImage = null;
    Intent data;
    int size = 1000000000;

    private GalleryPicker() {

    }

    public static GalleryPicker getInstance() {
        if (galleryPicker == null)
            galleryPicker = new GalleryPicker();
        return galleryPicker;
    }

    public AppCompatActivity getActivity() {
        return activity;
    }

    public void startGalleryIntent(AppCompatActivity activity, Fragment fragment) {
        this.activity = activity;
        this.fragment = fragment;
        try {
            listener = (GalleryListener) fragment;
        } catch (Exception e) {

        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (activity.checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) !=
                    PackageManager.PERMISSION_GRANTED) {
                fragment.requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_GALLERY);
                return;
            }
        }
        Intent getIntent = new Intent(Intent.ACTION_GET_CONTENT);
        getIntent.setType("image/*");

        Intent pickIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        pickIntent.setType("image/*");

        Intent chooserIntent = Intent.createChooser(getIntent, "Select Image");
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[]{pickIntent});

        fragment.startActivityForResult(chooserIntent, REQUEST_GALLERY);
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode == REQUEST_GALLERY) {
            boolean galleryDeny = false;
            for (int result : grantResults) {
                if (result == PackageManager.PERMISSION_DENIED) {
                    galleryDeny = true;
                }
            }
            if (!galleryDeny) {
                onResult(requestCode, -1, data, size);
            }
        }


    }

    public void onResult(int requestCode, int resultCode, Intent data, int size) {
        Log.i("ServerC", "Rached result");
        this.data = data;
        this.size = size;
        Bitmap bitmap = null;
        if (requestCode == REQUEST_GALLERY) {
            try {
                selectedImage = data.getData();
                try {
                    float test = 0;
                    bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), selectedImage);

                    int siz = 0;
                    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB_MR1) {
                        test = bitmap.getRowBytes() * bitmap.getHeight();

                    } else {
                        test = bitmap.getByteCount();
                    }
                    Log.i("ServerC", "Test: " + test);

                    test = 100 * size / test;
                    if (test < 1f)
                        test = 1f;
                    siz = (int) test;
                    if (siz < 1) siz = 1;
                    if (siz > 100) siz = 100;
                    Log.i("ServerC", "Percentage: " + siz);
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, siz, baos);
                    if (listener != null) {
                        Log.i("ServerC", "Found listener");
                        listener.onBitmapCreated(bitmap);
                        listener.onByteArrayCreated(baos.toByteArray());
                    }


                } catch (Exception e) {
                    Log.e("ServerC", "onResult gallery: " + e.getMessage());
                }
            } catch (Exception e) {
                Log.e("ServerC", "onResult 2 gallery: " + e.getMessage());
            }

        }
        return;


    }

}
