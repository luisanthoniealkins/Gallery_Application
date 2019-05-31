package com.laacompany.galleryapplication;

import android.content.Context;
import android.content.Intent;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;


import android.net.Uri;

import com.squareup.picasso.Picasso;

public class ImageActivity extends AppCompatActivity {

    private static final String EXTRA_IMAGE_URI = "extra_image_uri";

    private ImageView mImageView;

    public static Intent newIntent(Context packageContext, Uri image_uri) {
        Intent intent = new Intent(packageContext, ImageActivity.class);
        intent.putExtra(EXTRA_IMAGE_URI,image_uri.toString());
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_image);

        mImageView = findViewById(R.id.id_image);

        String image_uri = getIntent().getStringExtra(EXTRA_IMAGE_URI);
        Uri uri = Uri.parse(image_uri);

        Picasso.with(this).load(uri).placeholder(R.drawable.color1).into(mImageView);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
