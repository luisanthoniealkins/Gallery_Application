package com.laacompany.galleryapplication;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Debug;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import java.io.File;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    private RecyclerView mRecyclerView;

    private RecyclerAdapter mAdapter;

    private RecyclerView.LayoutManager mLayoutManager;

    private static final int LOAD_IMAGE_PERMISSION_CODE = 1;

    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecyclerView = findViewById(R.id.id_recyclerview);

        context = MainActivity.this;

        mLayoutManager = new GridLayoutManager(this,2 );
        mRecyclerView.setHasFixedSize(true);

        mRecyclerView.setLayoutManager(mLayoutManager);

    }

    @Override
    protected void onStart() {
        super.onStart();

        if (checkPermission())
            showBasicList();

    }


    public ArrayList<Spacecraft> getExternalData(String path) {

        ArrayList<Spacecraft> spacecrafts = new ArrayList<>();
        spacecrafts.clear();

        File downloadFolder = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES + "/" + path);

        if (downloadFolder.exists())
        {
            File files[] = downloadFolder.listFiles();

            for (File file : files)
            {
                Spacecraft s = new Spacecraft();
                s.setTitle(file.getName());
                s.setUri(Uri.fromFile(file));
                spacecrafts.add(s);
            }
        }

        return spacecrafts;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.id_basic_directory:
                if (checkPermission())
                    showBasicList();
                break;

            case R.id.id_advance_directory:
                if (checkPermission())
                    showAdvanceList();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showBasicList(){

        ArrayList<Spacecraft> mSpacecrafts = getExternalData("Basic");

        mAdapter = new RecyclerAdapter(mSpacecrafts, this);
        mRecyclerView.setAdapter(mAdapter);
    }

    private void showAdvanceList(){

        ArrayList<Spacecraft> mSpacecrafts = getExternalData("Advance");

        mAdapter = new RecyclerAdapter(mSpacecrafts, this);
        mRecyclerView.setAdapter(mAdapter);
    }


    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public boolean checkPermission()
    {
        int currentAPIVersion = Build.VERSION.SDK_INT;
        if(currentAPIVersion>=android.os.Build.VERSION_CODES.M)
        {
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) context, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context);
                    alertBuilder.setCancelable(true);
                    alertBuilder.setTitle("Permission necessary");
                    alertBuilder.setMessage("Permission is needed to load images");
                    alertBuilder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions((Activity)context, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, LOAD_IMAGE_PERMISSION_CODE);
                        }
                    });
                    AlertDialog alert = alertBuilder.create();
                    alert.show();
                } else {
                    ActivityCompat.requestPermissions((Activity)context, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, LOAD_IMAGE_PERMISSION_CODE);
                }
                return false;
            } else {
                return true;
            }
        } else {
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case LOAD_IMAGE_PERMISSION_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    showBasicList();
                } else {
                    //code for deny
                }
                break;
        }
    }

}
