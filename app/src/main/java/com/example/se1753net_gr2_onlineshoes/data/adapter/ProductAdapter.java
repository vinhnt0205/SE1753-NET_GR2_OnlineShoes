package com.example.se1753net_gr2_onlineshoes.data.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.se1753net_gr2_onlineshoes.ProductDetailActitivty;
import com.example.se1753net_gr2_onlineshoes.R;
import com.example.se1753net_gr2_onlineshoes.data.local.entities.Product;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {

    private Context context;

    private List<Product> productList;

    public  ProductAdapter(Context context, List<Product> productList) {
        this.context = context;
        this.productList = productList;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View view = LayoutInflater.from(context).inflate(R.layout.item_product, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position){
        Product product = productList.get(position);
        holder.tvTitle.setText(product.name);
        holder.tvDescription.setText(product.description);
        holder.tvOriginalPrice.setText(String.format("$%.2f", product.price));
        if(product.salePrice != null) {
            holder.tvSalePrice.setText(String.format("$%.2f", product.salePrice));
            holder.tvSalePrice.setVisibility(View.VISIBLE);
        } else {
            holder.tvSalePrice.setVisibility(View.GONE);
        }
        // Giả sử product có thuộc tính thumbnailUrl
//        Glide.with(context).load(product.).into(holder.ivThumbnail);

        // Khi click vào item sản phẩm chuyển sang màn hình chi tiết
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, ProductDetailActitivty.class);
            intent.putExtra("productId", product.productId);
            context.startActivity(intent);
        });

        // Xử lý nút "Buy"
        holder.btnBuy.setOnClickListener(v -> {
            // Thêm sản phẩm vào giỏ hàng hoặc chuyển đến màn hình thanh toán
        });

        // Xử lý nút "Feedback"
        holder.btnFeedback.setOnClickListener(v -> {
            // Chuyển sang màn hình gửi feedback cho sản phẩm
        });
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }
    static class ProductViewHolder extends RecyclerView.ViewHolder{
        ImageView ivThumbnail;
        TextView tvTitle, tvDescription, tvOriginalPrice, tvSalePrice;
        Button btnBuy, btnFeedback;

        public ProductViewHolder(@NonNull View itemView){
            super(itemView);
            ivThumbnail = itemView.findViewById(R.id.ivProductThumbnail);
            tvTitle = itemView.findViewById(R.id.tvProductTitle);
            tvDescription = itemView.findViewById(R.id.tvProductDescription);
            tvOriginalPrice = itemView.findViewById(R.id.tvOriginalPrice);
            tvSalePrice = itemView.findViewById(R.id.tvSalePrice);
            btnBuy = itemView.findViewById(R.id.btnBuy);
            btnFeedback = itemView.findViewById(R.id.btnFeedback);
        }
    }

}
