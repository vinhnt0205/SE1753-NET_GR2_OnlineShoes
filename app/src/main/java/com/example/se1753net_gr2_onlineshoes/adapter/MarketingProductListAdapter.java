package com.example.se1753net_gr2_onlineshoes.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
                .inflate(R.layout.item_product, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Product product = productList.get(position);

        holder.textViewIndex.setText(String.valueOf(position + 1));
        holder.textViewCode.setText(product.productId);
        holder.textViewName.setText(product.name);
        holder.textViewQuantity.setText(String.valueOf(product.price));
        holder.textViewPrice.setText(String.valueOf(product.price));
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    static class ProductViewHolder extends RecyclerView.ViewHolder {
        TextView textViewIndex, textViewCode, textViewName, textViewQuantity, textViewPrice;
        Button buttonDelete;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewIndex = itemView.findViewById(R.id.textViewIndex);
            textViewCode = itemView.findViewById(R.id.textViewCode);
            textViewName = itemView.findViewById(R.id.textViewName);
            textViewQuantity = itemView.findViewById(R.id.textViewQuantity);
            textViewPrice = itemView.findViewById(R.id.textViewPrice);
            buttonDelete = itemView.findViewById(R.id.buttonDelete);
        }
    }
}
