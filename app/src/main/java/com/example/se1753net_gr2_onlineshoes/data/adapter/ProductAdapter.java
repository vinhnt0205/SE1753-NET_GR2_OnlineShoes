package com.example.se1753net_gr2_onlineshoes.data.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Looper;
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
import com.example.se1753net_gr2_onlineshoes.ProductDetailActitivty;
import com.example.se1753net_gr2_onlineshoes.R;
import com.example.se1753net_gr2_onlineshoes.activity.activity_product_detail;
import com.example.se1753net_gr2_onlineshoes.data.local.dao.CartItemDao;
import com.example.se1753net_gr2_onlineshoes.data.local.dao.ShoppingCartDao;
import com.example.se1753net_gr2_onlineshoes.data.local.database.ShoeShopDatabase;
import com.example.se1753net_gr2_onlineshoes.data.local.entities.CartItem;
import com.example.se1753net_gr2_onlineshoes.data.local.entities.Product;
import com.example.se1753net_gr2_onlineshoes.data.local.entities.ProductWithImages;
import com.example.se1753net_gr2_onlineshoes.data.local.entities.ShoppingCart;
import com.example.se1753net_gr2_onlineshoes.data.session.SessionManager;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
import android.os.Handler;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {

    private Context context;

    private List<Product> productList;

    private List<ProductWithImages> productWithImagesList;

    private ShoppingCartDao shoppingCartDao;

    private CartItemDao cartItemDao;

    public  ProductAdapter(Context context, List<ProductWithImages> productWithImagesList) {
        this.context = context;
//        this.productList = productList;
        this.productWithImagesList = productWithImagesList;
        this.shoppingCartDao = ShoeShopDatabase.getInstance(context).shoppingCartDao();
        this.cartItemDao = ShoeShopDatabase.getInstance(context).cartItemDao();
    }

    public interface OnCartResultListener {
        void onSuccess(ShoppingCart cart);
        void onError(Exception e);
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View view = LayoutInflater.from(context).inflate(R.layout.item_product, parent, false);
        return new ProductViewHolder(view);
    }

//    @Override
//    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position){
//        Product product = productList.get(position);
//        holder.tvTitle.setText(product.name);
//        holder.tvDescription.setText(product.description);
//        holder.tvOriginalPrice.setText(String.format("$%.2f", product.price));
//        if(product.salePrice != null) {
//            holder.tvSalePrice.setText(String.format("$%.2f", product.salePrice));
//            holder.tvSalePrice.setVisibility(View.VISIBLE);
//        } else {
//            holder.tvSalePrice.setVisibility(View.GONE);
//        }
//        // Giả sử product có thuộc tính thumbnailUrl
////        Glide.with(context).load(product.).into(holder.ivThumbnail);
//
//        // Khi click vào item sản phẩm chuyển sang màn hình chi tiết
//        holder.itemView.setOnClickListener(v -> {
//            Intent intent = new Intent(context, activity_product_detail.class);
//            intent.putExtra("productId", product.productId);
//            intent.putExtra("product_name", product.name);
//            intent.putExtra("product_description", product.description);
//            intent.putExtra("product_price", product.price);
//            intent.putExtra("sale_price", product.salePrice);
////            intent.putExtra("product_image", product.thumbnailUrl);
//
//            context.startActivity(intent);
//        });
//
//        // Xử lý nút "Buy"
//        holder.btnBuy.setOnClickListener(v -> {
//            // Thêm sản phẩm vào giỏ hàng hoặc chuyển đến màn hình thanh toán
//            addToCart(new Product(product.productId, product.name, product.description, product.price, product.salePrice,new Date(), new Date()));
//
//        });
//
//        // Xử lý nút "Feedback"
//        holder.btnFeedback.setOnClickListener(v -> {
//            // Chuyển sang màn hình gửi feedback cho sản phẩm
//        });
//    }


    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position){
        // Lấy đối tượng ProductWithImages tại vị trí hiện tại
        ProductWithImages productWithImages = productWithImagesList.get(position);
        // Lấy thông tin sản phẩm từ đối tượng Product bên trong ProductWithImages
        Product product = productWithImages.product;

        holder.tvTitle.setText(product.name);
        holder.tvDescription.setText(product.description);
        holder.tvOriginalPrice.setText(String.format("$%.2f", product.price));

        // Hiển thị giá sale nếu có
        if(product.salePrice > 0) {
            holder.tvSalePrice.setText(String.format("$%.2f", product.salePrice));
            holder.tvSalePrice.setVisibility(View.VISIBLE);
        } else {
            holder.tvSalePrice.setVisibility(View.GONE);
        }

        // Sử dụng Glide để load ảnh từ URL của ảnh đầu tiên trong danh sách ảnh
        if (productWithImages.images != null && !productWithImages.images.isEmpty()) {
            String thumbnailUrl = productWithImages.images.get(0).imageUrl;
            Glide.with(context).load(thumbnailUrl).into(holder.ivThumbnail);
        } else {
            // Nếu không có ảnh, bạn có thể đặt ảnh mặc định
            holder.ivThumbnail.setImageResource(R.drawable.avatar_default);
        }

        // Khi click vào item sản phẩm chuyển sang màn hình chi tiết
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, activity_product_detail.class);
            intent.putExtra("productId", product.productId);
            intent.putExtra("product_name", product.name);
            intent.putExtra("product_description", product.description);
            intent.putExtra("product_price", product.price);
            intent.putExtra("sale_price", product.salePrice);
            // Chuyển thêm URL ảnh nếu cần (ví dụ ảnh đầu tiên)
            if (productWithImages.images != null && !productWithImages.images.isEmpty()) {
                intent.putExtra("product_image", productWithImages.images.get(0).imageUrl);
            }
            context.startActivity(intent);
        });

        // Xử lý nút "Add Cart"
        holder.btnBuy.setOnClickListener(v -> {
            addToCart(product);
        });

        // Xử lý nút "Feedback" (chưa được triển khai)
        holder.btnFeedback.setOnClickListener(v -> {
            // Chuyển sang màn hình feedback nếu cần
        });
    }

    @Override
    public int getItemCount() {
//        return productList.size();
        return productWithImagesList.size();
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

//        Log.d(">>>>> userID", userId);

        if (userId == null) {
            Toast.makeText(context, "Bạn cần đăng nhập để thêm sản phẩm vào giỏ hàng!", Toast.LENGTH_SHORT).show();
            return;
        }


        getOrCreateCart(userId, new OnCartResultListener() {
            @Override
            public void onSuccess(ShoppingCart cart) {
                // Xử lý logic thêm sản phẩm vào giỏ hàng sau khi lấy được cart
                Log.d(">>>>>>>Cart", cart.cartId);

                Executors.newSingleThreadExecutor().execute(() -> {
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

                    // Hiển thị thông báo trên UI thread
                    Handler handler = new Handler(Looper.getMainLooper());
                    handler.post(() ->
                            Toast.makeText(context, "Đã thêm vào giỏ hàng!", Toast.LENGTH_SHORT).show()
                    );
                });
            }

            @Override
            public void onError(Exception e) {
                Log.e("Cart", "Lỗi khi lấy giỏ hàng", e);
                new Handler(Looper.getMainLooper()).post(() ->
                        Toast.makeText(context, "Lỗi khi thêm vào giỏ hàng!", Toast.LENGTH_SHORT).show()
                );
            }
        });
    }


    private void getOrCreateCart(String userId, OnCartResultListener listener) {
        Executors.newSingleThreadExecutor().execute(() -> {
            try {
                ShoppingCart cart = shoppingCartDao.getCartByUser(userId);
                if (cart == null) {
                    cart = new ShoppingCart(UUID.randomUUID().toString(), userId, new Date());
                    shoppingCartDao.insertCart(cart);
                    Intent intent = new Intent();
                    intent.putExtra("cartId", cart.cartId);
                   context.startActivity(intent);
                }
                listener.onSuccess(cart); // Gửi kết quả qua callback
            } catch (Exception e) {
                listener.onError(e); // Gửi lỗi qua callback
            }
        });
    }




}
