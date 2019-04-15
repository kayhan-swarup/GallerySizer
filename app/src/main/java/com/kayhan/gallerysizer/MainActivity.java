package com.kayhan.gallerysizer;

import android.app.Activity;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getFragmentManager().beginTransaction()
                .add(R.id.container,new GalleryFragment())
                .commit();
    }
}
