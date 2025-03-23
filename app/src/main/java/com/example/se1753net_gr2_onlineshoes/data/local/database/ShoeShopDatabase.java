package com.example.se1753net_gr2_onlineshoes.data.local.database;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import android.content.Context;

import com.example.se1753net_gr2_onlineshoes.data.local.dao.*;
import com.example.se1753net_gr2_onlineshoes.data.local.entities.*;

@Database(
        entities = {
                User.class, UserRole.class, Brand.class, Product.class, ProductImage.class,
                Order.class, OrderDetail.class, ShoppingCart.class, CartItem.class,
                Category.class, ProductCategory.class, Feedback.class, Slider.class,
                ProductStatistics.class, AdminSettings.class, CustomerActivitySummary.class
        },
        version = 3, // ⚠️ Tăng version lên 3
        exportSchema = false
)
@TypeConverters(Converters.class)
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
    public abstract CustomerActivitySummaryDao customerActivitySummaryDao();

    // Singleton instance
    private static volatile ShoeShopDatabase INSTANCE;

    public static ShoeShopDatabase getInstance(final Context context) {
        if (INSTANCE == null) {
            synchronized (ShoeShopDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    ShoeShopDatabase.class, "shoeShopDB")
                            //.createFromAsset("databases/shoeShop.db") // ⚠️ Chỉ bật nếu dùng pre-packaged DB
                            .addMigrations(MIGRATION_1_2, MIGRATION_2_3) // ⚠️ Thêm migration mới
                            .fallbackToDestructiveMigration() // ⚠️ Xóa nếu lỗi schema
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    // ⚠️ Migration từ version 1 -> 2 (Sửa lại schema)
    static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE Users ADD COLUMN phoneNumber TEXT DEFAULT NULL");
            database.execSQL("ALTER TABLE Users ADD COLUMN address TEXT DEFAULT NULL");
        }
    };

    // ⚠️ Migration từ version 2 -> 3 (Sửa kiểu dữ liệu created_at và updated_at)
    static final Migration MIGRATION_2_3 = new Migration(2, 3) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            // Tạo bảng Users mới với đúng schema
            database.execSQL("CREATE TABLE IF NOT EXISTS Users_new (" +
                    "user_id TEXT NOT NULL PRIMARY KEY, " +
                    "username TEXT, " +
                    "email TEXT, " +
                    "phoneNumber TEXT, " +
                    "address TEXT, " +
                    "avatar_url TEXT, " +
                    "role_id TEXT, " +
                    "password_hash TEXT, " +
                    "created_at INTEGER DEFAULT 0, " + // ⚠️ Đổi thành INTEGER
                    "updated_at INTEGER DEFAULT 0" +   // ⚠️ Đổi thành INTEGER
                    ")");

            // Copy dữ liệu từ bảng cũ sang bảng mới
            database.execSQL("INSERT INTO Users_new (user_id, username, email, phoneNumber, address, avatar_url, role_id, password_hash, created_at, updated_at) " +
                    "SELECT user_id, username, email, phoneNumber, address, avatar_url, role_id, password_hash, created_at, updated_at FROM Users");

            // Xóa bảng cũ và đổi tên bảng mới thành `Users`
            database.execSQL("DROP TABLE Users");
            database.execSQL("ALTER TABLE Users_new RENAME TO Users");
        }
    };
}
