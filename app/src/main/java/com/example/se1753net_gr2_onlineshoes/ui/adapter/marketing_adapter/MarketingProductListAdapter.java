package com.example.se1753net_gr2_onlineshoes.ui.adapter.marketing_adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.se1753net_gr2_onlineshoes.R;
import com.example.se1753net_gr2_onlineshoes.data.local.entities.Product;

import java.util.ArrayList;
import java.util.List;

public class MarketingProductListAdapter extends RecyclerView.Adapter<MarketingProductListAdapter.ProductViewHolder> {
    private List<Product> productList = new ArrayList<>();

    public interface OnDeleteClickListener {
        void onDelete(Product product);
    }

    public MarketingProductListAdapter() {

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
        //holder.imageView.setImageResource(product);
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

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
