package com.example.se1753net_gr2_onlineshoes.ui.adapter.marketing_adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.se1753net_gr2_onlineshoes.R;
import com.example.se1753net_gr2_onlineshoes.data.local.entities.Product;
import com.example.se1753net_gr2_onlineshoes.data.local.entities.ProductImage;
import com.example.se1753net_gr2_onlineshoes.ui.activities.marketing_activities.MarketingProductDetailActivity;

import java.util.List;

public class MarketingSliderListAdapter extends RecyclerView.Adapter<MarketingSliderListAdapter.SliderViewHolder> {
    Context context;
    List<String> imageList;
    OnItemClickListener onItemClickListener;


    @NonNull
    @Override
    public SliderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(context)
                .inflate(R.layout.recyclerview_marketing_slider_carousel, parent, false);
        return new SliderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SliderViewHolder holder, int position) {
        if (imageList != null && !imageList.isEmpty()) {
            String imageUrl = imageList.get(position); // Store item reference

            Glide.with(context)
                    .load(imageUrl)
                    .placeholder(R.drawable.ic_launcher_background) // Placeholder while loading
                    .error(R.drawable.button_background) // Fallback if loading fails
                    .listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            Log.e("GlideError", "Failed to load image: " + imageUrl, e);
                            return false; // Let Glide handle the error
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            return false; // Let Glide handle the successful load
                        }
                    })
                    .into(holder.imageView);

            holder.itemView.setOnClickListener(v ->
                    onItemClickListener.onClick(holder.imageView, imageUrl) // Use stored item reference
            );
        }
    }



    @Override
    public int getItemCount() {
        return imageList.size();
    }

    static class SliderViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        public SliderViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.ivCarouselItem);
        }
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setImageList(List<String> imageList) {
        this.imageList = imageList;
    }

    public interface  OnItemClickListener {
        void onClick(ImageView imageView, String url);
    }
}