package com.example.se1753net_gr2_onlineshoes.ui.activities.admin_activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.se1753net_gr2_onlineshoes.R;
import com.example.se1753net_gr2_onlineshoes.data.local.dao.UserDao;
import com.example.se1753net_gr2_onlineshoes.data.local.database.ShoeShopDatabase;
import com.example.se1753net_gr2_onlineshoes.data.local.entities.User;
import com.example.se1753net_gr2_onlineshoes.ui.adapter.admin_adapter.AdminUserListAdapter;

import java.util.ArrayList;
import java.util.List;

public class AdminUserListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private Button btnAddUser;
    private AdminUserListAdapter adapter;
    private UserDao userDao;
    private List<User> userList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_admin_user_list);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //toolBar();

        initRecyclerView();
        userDao = ShoeShopDatabase.getInstance(this).userDao();

        new Thread(()-> {
            userList = userDao.getAllUsers();
            runOnUiThread(() -> adapter.setUserList(userList));
        }).start();

        btnAddUser = findViewById(R.id.btnAddUser);
        btnAddUser.setOnClickListener(v -> {
            Intent intent = new Intent(AdminUserListActivity.this, AdminAddUserActivity.class);
            startActivity(intent);

        });
    }

    private void initRecyclerView() {
        recyclerView = findViewById(R.id.recyclerViewUserList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new AdminUserListAdapter(this, userDao);
        recyclerView.setAdapter(adapter);
    }

    private void toolBar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar); // Important! This enables the ActionBar

        // Enable back button
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back); // Custom back icon (optional)
        }
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed(); // Handle back button action
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        new Thread(() -> {
            userList = userDao.getAllUsers();
            runOnUiThread(() -> adapter.setUserList(userList));
        }).start();
    }
}