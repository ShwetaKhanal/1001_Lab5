package com.example.giphyapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class GifAdapter extends RecyclerView.Adapter<GifAdapter.GifViewHolder> {

    private List<GiphyResponse.Data> gifs = new ArrayList<>();

    public void setGifs(List<GiphyResponse.Data> gifs) {
        this.gifs = gifs;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public GifViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_gif, parent, false);
        return new GifViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GifViewHolder holder, int position) {
        GiphyResponse.Data gif = gifs.get(position);
        Glide.with(holder.itemView.getContext())
                .load(gif.getImages().getOriginal().getUrl())
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return gifs.size();
    }

    public static class GifViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        public GifViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.gifImage);
        }
    }
}