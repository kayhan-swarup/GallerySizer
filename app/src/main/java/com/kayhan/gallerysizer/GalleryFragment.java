package com.kayhan.gallerysizer;

import android.app.Fragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.kayhan.gallerypick.GalleryListener;
import com.kayhan.gallerypick.GalleryPicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


public class GalleryFragment extends Fragment implements GalleryListener {

    Button button;
    TextView text;
    ImageView imageView;
    GalleryPicker picker = GalleryPicker.getInstance();

    int size = 8000000;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_gallery,null);
        button = v.findViewById(R.id.button);
        text=v.findViewById(R.id.text);
        imageView=v.findViewById(R.id.imageView);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                picker.startGalleryIntent(getActivity(),GalleryFragment.this);

            }
        });

        return v;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        picker.onRequestPermissionsResult(requestCode,permissions,grantResults);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        picker.onResult(requestCode,resultCode,data,size);
        if (requestCode==GalleryPicker.REQUEST_GALLERY) {

        }
    }

    @Override
    public void onBitmapCreated(Bitmap bitmap) {
        imageView.setImageBitmap(bitmap);

    }

    @Override
    public void onByteArrayCreated(byte[] bytes) {

        text.setText("Compress to : "+bytes.length);
    }
}
