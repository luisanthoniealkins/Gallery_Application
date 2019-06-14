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
import android.support.annotation.NonNull;
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
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    private RecyclerView recyclerView;
    private RecyclerAdapter adapter;
    private boolean isBasicFolder;

    Context context;

    private RecyclerView.LayoutManager layoutManager;

    private static final int LOAD_IMAGE_PERMISSION_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = MainActivity.this;

        recyclerView = findViewById(R.id.id_recyclerview);
        layoutManager = new GridLayoutManager(this, 2);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);

        isBasicFolder = true;

    }

    @Override
    protected void onStart() {
        super.onStart();

        updateFolder();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);

        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId())
        {
            case R.id.id_basic_directory:
                isBasicFolder = true;
                invalidateOptionsMenu();
                updateFolder();
                return true;
            case R.id.id_advance_directory:
                isBasicFolder = false;
                invalidateOptionsMenu();
                updateFolder();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void updateFolder()
    {

        if (checkPermission())
        {
            String path;
            if (isBasicFolder)
                path = "Basic";
            else
                path = "Advance";

            adapter = new RecyclerAdapter(getFolderData(path), this);
            recyclerView.setAdapter(adapter);
        }

    }

    public ArrayList<Spacecraft> getFolderData(String path)
    {
        ArrayList<Spacecraft> imageObjects = new ArrayList<>();

        File downloadFolder = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES + "/" + path);

        if (downloadFolder.exists())
        {
            File files[] = downloadFolder.listFiles();

            for (File file : files)
            {
                Spacecraft spacecraft = new Spacecraft();
                spacecraft.setTitle(file.getName());
                spacecraft.setUri(Uri.fromFile(file));
                imageObjects.add(spacecraft);
            }
        }

        return imageObjects;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public boolean checkPermission()
    {
        int currentAPIVersion = Build.VERSION.SDK_INT;
        if(currentAPIVersion>=android.os.Build.VERSION_CODES.M)
        {
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions((Activity)context, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, LOAD_IMAGE_PERMISSION_CODE);

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
                    updateFolder();
                } else {
                    //code for deny
                    finish();
                }
                break;
        }
    }
}
