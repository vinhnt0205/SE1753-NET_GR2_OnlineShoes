package com.example.se1753net_gr2_onlineshoes.data.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.se1753net_gr2_onlineshoes.R;
import com.example.se1753net_gr2_onlineshoes.data.local.entities.Slider;

import java.time.Instant;
import java.util.List;

public class SliderAdapter extends RecyclerView.Adapter<SliderAdapter.SliderViewHolder> {
    private Context context;
    private List<Slider> sliderList;

    public SliderAdapter(Context context, List<Slider> sliderList) {
        this.context = context;
        this.sliderList = sliderList;
    }

    @NonNull
    @Override
    public SliderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_slider, parent, false);
        return new SliderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SliderViewHolder holder, int position) {
        Slider slider = sliderList.get(position);
        holder.title.setText(slider.title);
//        Glide.with(context).load(slider.imageUrl).into(holder.image);

//        holder.itemView.setOnClickListener(v -> {
//            Intent intent = new Intent(context, WebViewActivity.class);
//            intent.putExtra("URL", slider.backlink);
//            context.startActivity(intent);
//        });
    }

    @Override
    public int getItemCount() {
        return sliderList.size();
    }

    static class SliderViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView title;

        public SliderViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.sliderViewPager);
//            title = itemView.findViewById(R.id);
        }
    }
}
