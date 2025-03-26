package com.example.se1753net_gr2_onlineshoes.data.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.se1753net_gr2_onlineshoes.R;
import com.example.se1753net_gr2_onlineshoes.data.local.entities.CartItem;
import com.example.se1753net_gr2_onlineshoes.data.local.entities.CartItemWithProduct;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {

    public interface CartItemListener {


        void onQuantityChanged();

        void onItemDeleted(CartItem item);
    }

//    private List<CartItem> cartItems;

    private List<CartItemWithProduct> cartItems;
    private CartItemListener listener;


    public CartAdapter(List<CartItemWithProduct> cartItems, CartItemListener listener) {
        this.cartItems = cartItems;
        this.listener = listener;
    }

    @NonNull
    @Override
    public CartAdapter.CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_item, parent, false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartAdapter.CartViewHolder holder, int position) {
//        CartItem item = cartItems.get(position);
        CartItemWithProduct item = cartItems.get(position);
//        holder.tvProductName.setText(item);
//        holder.tvProductPrice.setText(item.getPrice() + " VND");
        holder.tvQuantity.setText(String.valueOf(item.cartItem.getQuantity()));
        holder.tvProductName.setText(String.valueOf(item.product.name));
        holder.tvProductPrice.setText(String.valueOf(item.product.salePrice) + "$");
        holder.tvTotalCost.setText(String.format("%.2f$", item.cartItem.quantity * item.product.salePrice) + "$");
//        holder.tvTotalPrice.setText("Tổng tiền: " + item.cartItem.quantity * item.product.salePrice + "$");

        holder.btnIncrease.setOnClickListener(v -> {
            item.cartItem.setQuantity(item.cartItem.getQuantity() + 1);
            holder.tvQuantity.setText(String.valueOf(item.cartItem.getQuantity()));
            holder.tvTotalCost.setText(String.format("%.2f$", item.cartItem.quantity * item.product.salePrice) + "$");
            listener.onQuantityChanged();
        });

        holder.btnDecrease.setOnClickListener(v -> {
            if (item.cartItem.getQuantity() > 1) {
                item.cartItem.setQuantity(item.cartItem.getQuantity() - 1);
                holder.tvQuantity.setText(String.valueOf(item.cartItem.getQuantity()));
                holder.tvTotalCost.setText(String.format("%.2f$", item.cartItem.quantity * item.product.salePrice) + "$");
                listener.onQuantityChanged();
            }
        });

        holder.btnDelete.setOnClickListener(v -> {
            listener.onItemDeleted(item.cartItem);
        });
    }



    @Override
    public int getItemCount() {
        return cartItems.size();
    }


    static class CartViewHolder extends RecyclerView.ViewHolder {
        TextView tvProductName, tvProductPrice, tvQuantity, tvTotalCost, tvTotalPrice;
//        Button btnIncrease, btnDecrease ;
        ImageView btnDelete ,btnIncrease, btnDecrease;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            tvProductName = itemView.findViewById(R.id.tv_product_name);
            tvProductPrice = itemView.findViewById(R.id.tv_product_price);
            tvQuantity = itemView.findViewById(R.id.tv_quantity);
            tvTotalCost = itemView.findViewById(R.id.tv_total_cost);
            btnIncrease = itemView.findViewById(R.id.btn_increase);
            btnDecrease = itemView.findViewById(R.id.btn_decrease);
            btnDelete = itemView.findViewById(R.id.btn_delete);
            tvTotalPrice = itemView.findViewById(R.id.tv_total_price);
        }
    }
}
