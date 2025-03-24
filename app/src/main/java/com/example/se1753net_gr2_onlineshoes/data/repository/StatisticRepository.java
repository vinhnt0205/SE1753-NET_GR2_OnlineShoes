package com.example.se1753net_gr2_onlineshoes.data.repository;

import android.app.Application;

import com.example.se1753net_gr2_onlineshoes.data.local.dao.ProductStatisticsDao;
import com.example.se1753net_gr2_onlineshoes.data.local.database.ShoeShopDatabase;
import com.example.se1753net_gr2_onlineshoes.data.local.databaseview.ProductStatisticsView;
import com.example.se1753net_gr2_onlineshoes.data.local.entities.ProductStatistics;

import java.util.List;

import io.reactivex.rxjava3.core.Flowable;

public class StatisticRepository {
    private final ProductStatisticsDao productStatisticsDao;

    public StatisticRepository(Application application) {
        ShoeShopDatabase database = ShoeShopDatabase.getInstance(application);
        this.productStatisticsDao = database.productStatisticsDao();
    }

    public Flowable<List<ProductStatisticsView>> getAllProductStatisticsViews() {
        return productStatisticsDao.getProductStatisticsView();
    }

    public Flowable<List<ProductStatistics>> getStatisticsByProductId(String productId ) {
        return productStatisticsDao.getStatisticsByProductId(productId);

    }
}