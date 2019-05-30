package com.laacompany.galleryapplication;

import android.net.Uri;
import android.os.Debug;
import android.os.Environment;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecyclerView = findViewById(R.id.id_recyclerview);

        mLayoutManager = new GridLayoutManager(this,2 );
        mRecyclerView.setHasFixedSize(true);


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
                showBasicList();
                break;

            case R.id.id_advance_directory:
                showAdvanceList();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showBasicList(){

        mRecyclerView.setLayoutManager(mLayoutManager);

        ArrayList<Spacecraft> mSpacecrafts = getExternalData("Basic");
        Log.d("TAG","BASIC " + mSpacecrafts.size());

        mAdapter = new RecyclerAdapter(mSpacecrafts, this);
        mRecyclerView.setAdapter(mAdapter);
    }

    private void showAdvanceList(){

        mRecyclerView.setLayoutManager(mLayoutManager);

        ArrayList<Spacecraft> mSpacecrafts = getExternalData("Advance");
        Log.d("TAG","ADVANCE " + mSpacecrafts.size());

        mAdapter = new RecyclerAdapter(mSpacecrafts, this);
        mRecyclerView.setAdapter(mAdapter);
    }
}
