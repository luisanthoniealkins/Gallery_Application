package com.laacompany.galleryapplication;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ImageViewHolder>{

    private ArrayList<Spacecraft> mSpacecrafts;
    private Context mContext;

    public RecyclerAdapter(ArrayList<Spacecraft> spacecrafts, Context packageContext)
    {
        mSpacecrafts = spacecrafts;
        mContext = packageContext;
    }

    @NonNull
    public ImageViewHolder onCreateViewHolder(ViewGroup parent, int i) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.album_layout,parent, false);

        ImageViewHolder imageViewHolder = new ImageViewHolder(view);

        return imageViewHolder;
    }

    @Override
    public void onBindViewHolder(final ImageViewHolder viewHolder, int i) {

        Spacecraft s = mSpacecrafts.get(i);

        final Uri image_uri = s.getUri();

        Glide.with(mContext).load(image_uri).placeholder(R.drawable.color1).centerInside().into(viewHolder.Album);
        viewHolder.AlbumTitle.setText(s.getTitle());


        DisplayMetrics displayMetrics = mContext.getResources().getDisplayMetrics();
        float screenWidthDp = displayMetrics.widthPixels / 2;

        ViewGroup.LayoutParams layoutParams = viewHolder.Album.getLayoutParams();
        layoutParams.width = (int)screenWidthDp-16;
        layoutParams.height = (int)screenWidthDp-16;
        viewHolder.Album.setLayoutParams(layoutParams);

        viewHolder.Album.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = ImageActivity.newIntent(mContext, image_uri);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mSpacecrafts.size();
    }

    public static class ImageViewHolder extends RecyclerView.ViewHolder
    {
        ImageView Album;
        TextView AlbumTitle;

        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);

            Album = itemView.findViewById(R.id.id_album);
            AlbumTitle = itemView.findViewById(R.id.id_album_title);
        }
    }


}
