package com.example.giphyapp;

import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

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

        // Load GIF into the ImageView using Glide
        Glide.with(holder.itemView.getContext())
                .load(gif.getImages().getOriginal().getUrl())
                .into(holder.imageView);

        // Handle download button click
        holder.downloadButton.setOnClickListener(v -> {
            Context context = holder.itemView.getContext();
            String gifUrl = gif.getImages().getOriginal().getUrl();
            String fileName = gifUrl.substring(gifUrl.lastIndexOf('/') + 1);

            // Use DownloadManager to download the GIF
            DownloadManager downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
            if (downloadManager != null) {
                DownloadManager.Request request = new DownloadManager.Request(Uri.parse(gifUrl));
                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, fileName);

                // Enqueue the download request
                downloadManager.enqueue(request);
                Toast.makeText(context, "Downloading " + fileName, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, "DownloadManager not available", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return gifs.size();
    }

    public static class GifViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        ImageView downloadButton;

        public GifViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.gifImage);
            downloadButton = itemView.findViewById(R.id.btnDownload);
        }
    }
}
