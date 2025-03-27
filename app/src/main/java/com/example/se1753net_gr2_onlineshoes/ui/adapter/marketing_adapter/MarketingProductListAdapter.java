package com.example.se1753net_gr2_onlineshoes.ui.adapter.marketing_adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.se1753net_gr2_onlineshoes.R;
import com.example.se1753net_gr2_onlineshoes.data.local.entities.Product;
import com.example.se1753net_gr2_onlineshoes.data.local.entities.ProductImage;
import com.example.se1753net_gr2_onlineshoes.ui.activities.marketing_activities.MarketingProductDetailActivity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class MarketingProductListAdapter extends RecyclerView.Adapter<MarketingProductListAdapter.ProductViewHolder> implements Filterable {
    private List<Product> productList = new ArrayList<>();
    private List<Product> productListAll = new ArrayList<>();
    private List<ProductImage> productImages = new ArrayList<>(); // Store images


    public MarketingProductListAdapter(List<Product> productListAll) {
        this.productListAll = productListAll;
    }

    public void setProductImages(List<ProductImage> images) {
        this.productImages = images;
        notifyDataSetChanged();
    }

    public void setProducts(List<Product> products) {
        this.productList = products;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recyclerview_marketing_item_product, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Product product = productList.get(position);
        holder.nameTextView.setText(product.name);
        holder.descTextView.setText(product.description);
        holder.dateTextView.setText(product.createdAt.toString());

        // Find the first image for this product
        String imageUrl = null;
        for (ProductImage image : productImages) {
            if (image.productId.equals(product.productId)) {
                imageUrl = image.imageUrl;
                break;
            }
        }

        // Load the image using Glide
        if (imageUrl != null && !imageUrl.isEmpty()) {
            Glide.with(holder.itemView.getContext())
                    .load(imageUrl)
                    .placeholder(R.drawable.ic_launcher_background) // Default placeholder
                    .into(holder.imageView);
        } else {
            holder.imageView.setImageResource(R.drawable.shoes_dashboard_icon); // Default image
        }

        // Set OnClickListener to navigate to MarketingProductDetailActivity
        holder.itemView.setOnClickListener(v -> {
            Context context = holder.itemView.getContext();
            Intent intent = new Intent(context, MarketingProductDetailActivity.class);
            intent.putExtra("product_id", product.productId); // Pass productId
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    @Override
    public Filter getFilter() {
        return filter;
    }

    Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Product> filterdList = new ArrayList<>();

            if(constraint.toString().isEmpty()) {
                filterdList.addAll(productListAll);
            }else {
                for(Product product: productListAll) {
                    if(product.name.toLowerCase().contains(constraint.toString().toLowerCase())) {
                        filterdList.add(product);
                    }
                }
            }

            FilterResults filterResults = new FilterResults();
            filterResults.values = filterdList;

            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            productList.clear();
            productList.addAll((Collection<? extends Product>) results.values);
            notifyDataSetChanged();
        }
    };

    static class ProductViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView, descTextView, dateTextView;
        ImageView imageView;
        Button buttonDelete;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.productName);
            descTextView = itemView.findViewById(R.id.productDesc);
            dateTextView = itemView.findViewById(R.id.productDate);
            imageView = itemView.findViewById(R.id.productImage);
        }
    }
}
