package com.example.se1753net_gr2_onlineshoes.ui.activities.marketing_activities;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.se1753net_gr2_onlineshoes.R;
import com.example.se1753net_gr2_onlineshoes.ui.adapter.marketing_adapter.MarketingSliderListAdapter;
import com.google.android.material.appbar.MaterialToolbar;

import java.util.ArrayList;
import java.util.List;

public class MarketingSliderListActivity extends AppCompatActivity {

    private RecyclerView carouselRecyclerView;
    MaterialToolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_marketing_slider_list);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        carouselRecyclerView = findViewById(R.id.carouselRecyclerView);
        toolbar = findViewById(R.id.toolbarSliderList);
        setSupportActionBar(toolbar);

        List<String> imageList = new ArrayList<>();
        imageList.add("https://plus.unsplash.com/premium_photo-1742353866584-27c87d42da99?q=80&w=2670&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D");
        imageList.add("https://plus.unsplash.com/premium_photo-1741194732698-e3b5609edebb?w=700&auto=format&fit=crop&q=60&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxmZWF0dXJlZC1waG90b3MtZmVlZHwxN3x8fGVufDB8fHx8fA%3D%3D");
        imageList.add("https://images.unsplash.com/photo-1742730709723-5dbb59ada518?q=80&w=2670&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D");
        imageList.add("https://plus.unsplash.com/premium_photo-1694557637761-4bf2467c261b?q=80&w=2574&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D");
        imageList.add("https://images.unsplash.com/photo-1742218410181-9304b5548653?w=700&auto=format&fit=crop&q=60&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxmZWF0dXJlZC1waG90b3MtZmVlZHwzNnx8fGVufDB8fHx8fA%3D%3D");

        MarketingSliderListAdapter adapter = new MarketingSliderListAdapter();
        adapter.setImageList(imageList);
        adapter.setOnItemClickListener(new MarketingSliderListAdapter.OnItemClickListener() {
            @Override
            public void onClick(ImageView imageView, String url) {
                Intent intent = new Intent(MarketingSliderListActivity.this, MarketingSliderImageViewActivity.class);
                intent.putExtra("image", url);

                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(
                        MarketingSliderListActivity.this, imageView, "image"
                );

                startActivity(intent, options.toBundle()); // Corrected line
            }
        });
        carouselRecyclerView.setAdapter(adapter);

    }
}