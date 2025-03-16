package com.example.se1753net_gr2_onlineshoes.data;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import android.content.Context;

import com.example.se1753net_gr2_onlineshoes.data.local.dao.AdminSettingsDao;
import com.example.se1753net_gr2_onlineshoes.data.local.dao.BrandDao;
import com.example.se1753net_gr2_onlineshoes.data.local.dao.CartItemDao;
import com.example.se1753net_gr2_onlineshoes.data.local.dao.CategoryDao;
import com.example.se1753net_gr2_onlineshoes.data.local.dao.CustomerActivityDao;
import com.example.se1753net_gr2_onlineshoes.data.local.dao.FeedbackDao;
import com.example.se1753net_gr2_onlineshoes.data.local.dao.OrderDao;
import com.example.se1753net_gr2_onlineshoes.data.local.dao.OrderDetailDao;
import com.example.se1753net_gr2_onlineshoes.data.local.dao.ProductCategoryDao;
import com.example.se1753net_gr2_onlineshoes.data.local.dao.ProductDao;
import com.example.se1753net_gr2_onlineshoes.data.local.dao.ProductImageDao;
import com.example.se1753net_gr2_onlineshoes.data.local.dao.ProductStatisticsDao;
import com.example.se1753net_gr2_onlineshoes.data.local.dao.ShoppingCartDao;
import com.example.se1753net_gr2_onlineshoes.data.local.dao.SliderDao;
import com.example.se1753net_gr2_onlineshoes.data.local.dao.UserDao;
import com.example.se1753net_gr2_onlineshoes.data.local.dao.UserRoleDao;
import com.example.se1753net_gr2_onlineshoes.data.local.entities.AdminSettings;
import com.example.se1753net_gr2_onlineshoes.data.local.entities.Brand;
import com.example.se1753net_gr2_onlineshoes.data.local.entities.CartItem;
import com.example.se1753net_gr2_onlineshoes.data.local.entities.Category;
import com.example.se1753net_gr2_onlineshoes.data.local.entities.CustomerActivity;
import com.example.se1753net_gr2_onlineshoes.data.local.entities.Feedback;
import com.example.se1753net_gr2_onlineshoes.data.local.entities.Order;
import com.example.se1753net_gr2_onlineshoes.data.local.entities.OrderDetail;
import com.example.se1753net_gr2_onlineshoes.data.local.entities.Product;
import com.example.se1753net_gr2_onlineshoes.data.local.entities.ProductCategory;
import com.example.se1753net_gr2_onlineshoes.data.local.entities.ProductImage;
import com.example.se1753net_gr2_onlineshoes.data.local.entities.ProductStatistics;
import com.example.se1753net_gr2_onlineshoes.data.local.entities.ShoppingCart;
import com.example.se1753net_gr2_onlineshoes.data.local.entities.Slider;
import com.example.se1753net_gr2_onlineshoes.data.local.entities.User;
import com.example.se1753net_gr2_onlineshoes.data.local.entities.UserRole;

// Import all entity classes
@Database(
        entities = {
                User.class, UserRole.class, Brand.class, Product.class, ProductImage.class,
                Order.class, OrderDetail.class, ShoppingCart.class, CartItem.class,
                Category.class, ProductCategory.class, Feedback.class, Slider.class,
                ProductStatistics.class, AdminSettings.class, CustomerActivity.class
        },
        version = 1, // Increment when making schema changes
        exportSchema = false
)
public abstract class ShoeShopDatabase extends RoomDatabase {

    // Define abstract methods for each DAO
    public abstract UserDao userDao();
    public abstract UserRoleDao userRoleDao();
    public abstract BrandDao brandDao();
    public abstract ProductDao productDao();
    public abstract ProductImageDao productImageDao();
    public abstract OrderDao orderDao();
    public abstract OrderDetailDao orderDetailDao();
    public abstract ShoppingCartDao shoppingCartDao();
    public abstract CartItemDao cartItemDao();
    public abstract CategoryDao categoryDao();
    public abstract ProductCategoryDao productCategoryDao();
    public abstract FeedbackDao feedbackDao();
    public abstract SliderDao sliderDao();
    public abstract ProductStatisticsDao productStatisticsDao();
    public abstract AdminSettingsDao adminSettingsDao();
    public abstract CustomerActivityDao customerActivityDao();

    // Singleton instance
    private static volatile ShoeShopDatabase INSTANCE;

    public static ShoeShopDatabase getInstance(final Context context) {
        if (INSTANCE == null) {
            synchronized (ShoeShopDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                            context.getApplicationContext(),
                            ShoeShopDatabase.class, "shoes_db"
                    ).fallbackToDestructiveMigration().build();
                }
            }
        }
        return INSTANCE;
    }
}

