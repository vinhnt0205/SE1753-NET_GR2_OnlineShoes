package com.example.se1753net_gr2_onlineshoes.ui.adapter.admin_adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.se1753net_gr2_onlineshoes.R;
import com.example.se1753net_gr2_onlineshoes.data.local.dao.UserDao;
import com.example.se1753net_gr2_onlineshoes.data.local.entities.User;
import com.example.se1753net_gr2_onlineshoes.ui.activities.admin_activities.AdminUserDetailActivity;

import java.util.ArrayList;
import java.util.List;

public class AdminUserListAdapter extends RecyclerView.Adapter<AdminUserListAdapter.UserViewHolder> {
    private List<User> userList = new ArrayList<>();
    private Context context;
    private UserDao userDao;

    public AdminUserListAdapter(Context context, UserDao userDao) {
        this.context = context;
        this.userDao = userDao;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recyclerview_admin_item_user, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        User user = userList.get(position);
        holder.userName.setText(user.username);
        holder.email.setText(user.email);
        holder.phoneNumber.setText(user.phoneNumber);
        holder.avatar.setImageResource(R.drawable.ic_launcher_background); // Default image

        // Click on item → Go to detail page
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, AdminUserDetailActivity.class);
            intent.putExtra("user_id", user.userId);
            context.startActivity(intent);
        });

        // Click delete button
        holder.btnDelete.setOnClickListener(v -> showDeleteDialog(user, position));
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    private void showDeleteDialog(User user, int position) {
        new AlertDialog.Builder(context)
            .setTitle("Delete User")
            .setMessage("Are you sure you want to delete this user?")
            .setPositiveButton("Yes", (dialog, which) -> {
                new Thread(() -> {
                    userDao.deleteUserById(user.userId);
                }).start();
                userList.remove(position);
                notifyItemRemoved(position);
                Toast.makeText(context, "User deleted successfully", Toast.LENGTH_SHORT).show();
            })
            .setNegativeButton("No", (dialog, which) -> dialog.dismiss())
            .show();
    }

    static class UserViewHolder extends RecyclerView.ViewHolder {
        TextView userName, email, phoneNumber;
        ImageView avatar;
        ImageButton btnDelete;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            avatar = itemView.findViewById(R.id.avatar);
            userName = itemView.findViewById(R.id.userName);
            email = itemView.findViewById(R.id.email);
            phoneNumber = itemView.findViewById(R.id.phoneNumber);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
    }
}


