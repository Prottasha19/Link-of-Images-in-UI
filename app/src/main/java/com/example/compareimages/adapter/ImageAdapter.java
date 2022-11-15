package com.example.compareimages.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.compareimages.R;
import com.example.compareimages.model.Image;

import java.util.List;


public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ViewHolder> {

    List<Image> images;
    Context context;

    public ImageAdapter(List<Image> images, Context context) {
        this.images = images;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.model_design_image,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Image image = images.get(position);

        Log.d("link",image.getImgLink());

        Glide.with(context).load(image.getImgLink()).into(holder.imageView);
        holder.textView.setText(image.getImageID());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(image.getImgLink()));
                context.startActivity(browserIntent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return images.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.modelDesignImageImageView);
            textView = itemView.findViewById(R.id.modelDesignImageTextView);
        }
    }
}
