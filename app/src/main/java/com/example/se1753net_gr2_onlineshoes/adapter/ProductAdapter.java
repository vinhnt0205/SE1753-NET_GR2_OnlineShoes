package com.example.se1753net_gr2_onlineshoes.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.se1753net_gr2_onlineshoes.activity.activity_product_detail;
import com.example.se1753net_gr2_onlineshoes.R;
import com.example.se1753net_gr2_onlineshoes.data.local.dao.CartItemDao;
import com.example.se1753net_gr2_onlineshoes.data.local.dao.ShoppingCartDao;
import com.example.se1753net_gr2_onlineshoes.data.local.entities.CartItem;
import com.example.se1753net_gr2_onlineshoes.data.local.entities.Product;
import com.example.se1753net_gr2_onlineshoes.data.local.entities.ProductWithImages;
import com.example.se1753net_gr2_onlineshoes.data.local.entities.ShoppingCart;
import com.example.se1753net_gr2_onlineshoes.data.session.SessionManager;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {

    private Context context;

    private List<Product> productList;

    private List<ProductWithImages> productWithImagesList;

    private ShoppingCartDao shoppingCartDao;

    private CartItemDao cartItemDao;

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
//        Product product = productList.get(position);
        ProductWithImages product = productWithImagesList.get(position);
//        Product product = productWithImages.product;

        Log.d(product.image_url, "onBindViewHolder: ");


        holder.tvTitle.setText(product.name);
        holder.tvDescription.setText(product.description);
        holder.tvOriginalPrice.setText(String.format("$%.2f", product.price));
        if(product.sale_price >0) {
            holder.tvSalePrice.setText(String.format("$%.2f", product.sale_price));
            holder.tvSalePrice.setVisibility(View.VISIBLE);
        } else {
            holder.tvSalePrice.setVisibility(View.GONE);
        }
        // Giả sử product có thuộc tính thumbnailUrl
//        Glide.with(context).load(product.).into(holder.ivThumbnail);


//        if(productWithImages.images != null && !productWithImages.images.isEmpty()){
//            Glide.with(context)
//                    .load(productWithImages.images.get(0).imageUrl)
//                    .into(holder.ivThumbnail);
//        }
        // Khi click vào item sản phẩm chuyển sang màn hình chi tiết

        Log.d(product.image_url, "onBindViewHolder: ");
        // Load ảnh sản phẩm với Glide (sử dụng image_url mới)
        if (product.image_url != null && !product.image_url.isEmpty()) {
            Glide.with(context)
                    .load(product.image_url)
//                    .placeholder(R.drawable.placeholder_image)
//                    .error(R.drawable.error_image)
                    .into(holder.ivThumbnail);
        } else {
//            holder.ivThumbnail.setImageResource(R.drawable.placeholder_image);
        }

        // Khi click vào item sản phẩm → Chuyển sang màn hình chi tiết
        View.OnClickListener openProductDetail = v -> {
            Context itemContext = holder.itemView.getContext(); // Lấy context từ ViewHolder
            Intent intent = new Intent(itemContext, activity_product_detail.class);
            intent.putExtra("product_id", product.product_id);
            intent.putExtra("product_name", product.name);
            intent.putExtra("product_description", product.description);
            intent.putExtra("product_price", product.sale_price);
            intent.putExtra("product_image", product.image_url);
            context.startActivity(intent);
        };

        holder.tvTitle.setOnClickListener(openProductDetail);
        holder.ivThumbnail.setOnClickListener(openProductDetail);



        // Xử lý nút "Buy"
        holder.btnBuy.setOnClickListener(v ->
                addToCart(new Product(product.product_id, product.name, product.description, product.price, product.sale_price,new Date(), new Date())));
            // Thêm sản phẩm vào giỏ hàng hoặc chuyển đến màn hình thanh toán



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

    private void addToCart(Product product) {
        SessionManager sessionManager = new SessionManager(context);
        String userId = sessionManager.getUserId();

        Log.d(">>>>>>UserId", userId);

        if (userId == null) {
            Toast.makeText(context, "Bạn cần đăng nhập để thêm sản phẩm vào giỏ hàng!", Toast.LENGTH_SHORT).show();
            return;
        }

        ShoppingCart cart = getOrCreateCart(userId);

        Log.d(">>>>>>>Cart", cart.cartId);
        CartItem existingItem = cartItemDao.getCartItem(cart.getCartId(), product.productId);

        if (existingItem != null) {
            // Sản phẩm đã có trong giỏ hàng, tăng số lượng
            existingItem.quantity += 1;
            cartItemDao.updateCartItem(existingItem);
        } else {
            // Thêm sản phẩm mới vào giỏ hàng
            CartItem cartItem = new CartItem(UUID.randomUUID().toString(), cart.getCartId(), product.productId, 1);
            cartItemDao.insertCartItem(cartItem);
        }

        Toast.makeText(context, "Đã thêm vào giỏ hàng!", Toast.LENGTH_SHORT).show();
    }

    private ShoppingCart getOrCreateCart(String userId) {
        ShoppingCart cart = shoppingCartDao.getCartByUser(userId);
        if (cart == null) {
            cart = new ShoppingCart(UUID.randomUUID().toString(), userId, new Date());
            shoppingCartDao.insertCart(cart);
        }
        return cart;
    }

}
