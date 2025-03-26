package com.example.se1753net_gr2_onlineshoes.ui.activities.marketing_activities;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.example.se1753net_gr2_onlineshoes.R;
import com.example.se1753net_gr2_onlineshoes.data.local.entities.Slider;
import com.example.se1753net_gr2_onlineshoes.data.repository.SliderRepository;
import com.example.se1753net_gr2_onlineshoes.viewmodel.factory.MarketingSliderDetailViewModelFactory;
import com.example.se1753net_gr2_onlineshoes.viewmodel.marketing_viewmodel.MarketingSliderDetailViewModel;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Date;

public class MarketingSliderDetailActivity extends AppCompatActivity {
    private MarketingSliderDetailViewModel marketingSliderDetailViewModel;
    private TextInputEditText titleField, imageUrlField, backlinkField, noteField;
    private AutoCompleteTextView statusField;
    private ImageView imageViewSliderDetail;
    Toolbar toolbar;

    Button saveButton;
    private String sliderId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_marketing_slider_detail);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        toolbar = findViewById(R.id.toolbarSliderDetail);
        setSupportActionBar(toolbar); // Important! This enables the ActionBar

        // Get sliderId from intent
        sliderId = getIntent().getStringExtra("SLIDER_ID");

        // Initialize UI components correctly
        imageViewSliderDetail = findViewById(R.id.imageViewSliderDetail);
        titleField = findViewById(R.id.textFieldSliderTitle);
        statusField = findViewById(R.id.textFieldSliderStatus);
        imageUrlField = findViewById(R.id.textFieldSliderImageUrl);
        backlinkField = findViewById(R.id.textFieldSliderBacklinkUrl);
        noteField = findViewById(R.id.textFieldSliderNote);
        saveButton = findViewById(R.id.btnSaveSlider);

        // Get an instance of the repository
        SliderRepository repository = new SliderRepository(getApplication());

        // Create ViewModel using the Factory
        MarketingSliderDetailViewModelFactory factory = new MarketingSliderDetailViewModelFactory(repository, sliderId);
        marketingSliderDetailViewModel = new ViewModelProvider(this, factory).get(MarketingSliderDetailViewModel.class);

        // Observe LiveData
        marketingSliderDetailViewModel.getSliderLiveData().observe(this, this::populateUI);
        marketingSliderDetailViewModel.getIsUpdating().observe(this, isUpdating -> {
            saveButton.setEnabled(!isUpdating);
            if (isUpdating) {
                Toast.makeText(this, "Updating...", Toast.LENGTH_SHORT).show();
            }
        });

        // Load slider details
        if (sliderId != null) {
            marketingSliderDetailViewModel.loadSliderDetails(sliderId);
        }

        // Save button click listener
        saveButton.setOnClickListener(v -> saveSlider());

        // Set status dropdown items
        setupStatusDropdown();
    }

    private void populateUI(Slider slider) {
        if (slider != null) {
            titleField.setText(slider.title);
            statusField.setText(slider.status, false);
            imageUrlField.setText(slider.imageUrl);
            backlinkField.setText(slider.backlink);
            noteField.setText(slider.notes);

            // Load the image using Glide
            Glide.with(this)
                    .load(slider.imageUrl)
                    .placeholder(R.drawable.ic_launcher_background) // Optional: placeholder while loading
                    .error(R.drawable.button_background) // Optional: error image if loading fails
                    .into(imageViewSliderDetail);
        }
    }


    private void saveSlider() {
        Slider existingSlider = marketingSliderDetailViewModel.getSliderLiveData().getValue();

        Slider updatedSlider = new Slider();
        updatedSlider.sliderId = sliderId;
        updatedSlider.title = titleField.getText().toString();
        updatedSlider.status = statusField.getText().toString();
        updatedSlider.imageUrl = imageUrlField.getText().toString();
        updatedSlider.backlink = backlinkField.getText().toString();
        updatedSlider.notes = noteField.getText().toString();

        // Preserve the original createdAt date
        if (existingSlider != null) {
            updatedSlider.createdAt = existingSlider.createdAt;
        } else {
            updatedSlider.createdAt = new Date(); // If new, set createdAt to now
        }

        // Update updatedAt field
        updatedSlider.updatedAt = new Date();

        // Update in ViewModel
        marketingSliderDetailViewModel.updateSlider(updatedSlider);

        // Set result and finish activity
        setResult(RESULT_OK); // Indicate success
        finish(); // Close activity and return to list
    }

    private void setupStatusDropdown() {
        String[] statuses = {"Active", "Inactive"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, statuses);
        statusField.setAdapter(adapter);
    }
}