package com.example.se1753net_gr2_onlineshoes.ui.activities.marketing_activities;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.se1753net_gr2_onlineshoes.R;
import com.example.se1753net_gr2_onlineshoes.data.local.entities.Slider;
import com.example.se1753net_gr2_onlineshoes.data.repository.SliderRepository;
import com.example.se1753net_gr2_onlineshoes.ui.adapter.marketing_adapter.MarketingSliderListAdapter;
import com.example.se1753net_gr2_onlineshoes.viewmodel.factory.MarketingSliderListViewModelFactory;
import com.example.se1753net_gr2_onlineshoes.viewmodel.marketing_viewmodel.MarketingSliderListViewModel;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.button.MaterialButtonToggleGroup;
import com.google.android.material.carousel.CarouselLayoutManager;
import com.google.android.material.carousel.CarouselStrategy;
import com.google.android.material.carousel.HeroCarouselStrategy;
import com.google.android.material.search.SearchBar;

public class MarketingSliderListActivity extends AppCompatActivity {

    private RecyclerView carouselRecyclerView;
    MaterialToolbar toolbar;

    private MarketingSliderListViewModel marketingSliderListViewModel;

    TextView titleTextView , statusTextView, createdAtTextView, updatedAtTextView, filterTypeTextView;

    private MaterialButton btnFilterInactiveSlider, btnFilterActiveSlider;

    MaterialButtonToggleGroup toggleButtonGroup;
    private SearchBar searchBar;

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
        filterTypeTextView = findViewById(R.id.tvSliderFilterType);
        btnFilterInactiveSlider = findViewById(R.id.btnFilterInactiveSlider);
        btnFilterActiveSlider = findViewById(R.id.btnFilterActiveSlider);
        toggleButtonGroup = findViewById(R.id.toggleButtonGroup);
        //searchBar = findViewById(R.id.searchBar);
        setSupportActionBar(toolbar);

        // 1. Create HeroCarouselStrategy
        CarouselStrategy heroCarouselStrategy = new HeroCarouselStrategy();

        // 2. Create CarouselLayoutManager with the strategy
        CarouselLayoutManager carouselLayoutManager = new CarouselLayoutManager(heroCarouselStrategy);

        // 3. Set the LayoutManager
        carouselRecyclerView.setLayoutManager(carouselLayoutManager);

        MarketingSliderListAdapter adapter = new MarketingSliderListAdapter();
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

        // Get an instance of the repository
        SliderRepository repository = new SliderRepository(getApplication());

        // Create ViewModel using the Factory
        MarketingSliderListViewModelFactory factory = new MarketingSliderListViewModelFactory(repository);
        marketingSliderListViewModel = new ViewModelProvider(this, factory).get(MarketingSliderListViewModel.class);

        // Observe filtered data
        marketingSliderListViewModel.getSliderListLiveData().observe(this, adapter::setSliderList);

        LinearSnapHelper snapHelper = new LinearSnapHelper();
        snapHelper.attachToRecyclerView(carouselRecyclerView);

        carouselRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (newState == RecyclerView.SCROLL_STATE_IDLE) { // Only detect when scrolling stops
                    View centerView = snapHelper.findSnapView(recyclerView.getLayoutManager());
                    if (centerView != null) {
                        int centerPosition = recyclerView.getChildAdapterPosition(centerView);

                        if (centerPosition != RecyclerView.NO_POSITION && centerPosition < adapter.getItemCount()) {
                            Slider focusedSlider = adapter.getSliderAt(centerPosition);

                            // Update UI with focused slider info
                            updateFocusedSliderInfo(focusedSlider);
                        }
                    }
                }
            }
        });


        toggleButtonGroup.addOnButtonCheckedListener((group, checkedId, isChecked) -> {
            if (!isChecked) {
                // If no button is selected, show all sliders
                marketingSliderListViewModel.setFilterStatus(null);
                filterTypeTextView.setText("All Sliders");
                return;
            }

            if (checkedId == R.id.btnFilterInactiveSlider) {
                marketingSliderListViewModel.setFilterStatus("Inactive");
                filterTypeTextView.setText("Inactive Sliders");
            } else if (checkedId == R.id.btnFilterActiveSlider) {
                marketingSliderListViewModel.setFilterStatus("Active");
                filterTypeTextView.setText("Active Sliders");
            }
        });


    }

    // Method to update UI with focused slider info
    private void updateFocusedSliderInfo(Slider slider) {
        titleTextView = findViewById(R.id.tvSliderTitle);
        statusTextView = findViewById(R.id.tvSliderStatus);
        createdAtTextView = findViewById(R.id.tvSliderCreatedAt);
        updatedAtTextView = findViewById(R.id.tvSliderUpdateAt);
        filterTypeTextView = findViewById(R.id.tvSliderFilterType);

        titleTextView.setText(slider.title);
        statusTextView.setText("Status: " + slider.status);
        createdAtTextView.setText("Created At: " + slider.createdAt.toString());
        updatedAtTextView.setText("Updated At: " + slider.updatedAt.toString());
    }
}