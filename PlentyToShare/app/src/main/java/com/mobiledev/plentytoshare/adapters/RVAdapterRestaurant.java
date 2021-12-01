package com.mobiledev.plentytoshare.adapters;


import android.annotation.SuppressLint;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mobiledev.plentytoshare.R;
import com.mobiledev.plentytoshare.models.Orders;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;

public class RVAdapterRestaurant extends RecyclerView.Adapter<RVAdapterRestaurant.OrderViewHolder> {

    private final ArrayList<Orders> orders;
    private OrderViewHolder.OnOrderListener mOnOrderListener;
    String username;

    public RVAdapterRestaurant(ArrayList<Orders> orders, OrderViewHolder.OnOrderListener onOrderListener, String username) {
        this.orders=orders;
        this.mOnOrderListener=onOrderListener;
        this.username = username;
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new OrderViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(
                        R.layout.recycler_view_restaurant_event_item,
                        parent,
                        false
                ), mOnOrderListener
        );
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        Orders order = orders.get(position);
        String status = order.getStatus();
        if(status.trim().equals(username.trim())){
            holder.itemView.setBackgroundColor(Color.parseColor("#0FA3A0"));
        }
        holder.setOrder(orders.get(position));
    }

    @Override
    public int getItemCount() {
        return orders.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public static class OrderViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView date, servingsAvailable, pickupTime, status, type, expiry;
        OnOrderListener onOrderListener;

        OrderViewHolder(@NonNull View itemView, OnOrderListener onOrderListener) {
            super(itemView);
            date = itemView.findViewById(R.id.date);
            servingsAvailable = itemView.findViewById(R.id.servingsAvailable);
            status = itemView.findViewById(R.id.status);
            type = itemView.findViewById(R.id.type);
            expiry = itemView.findViewById(R.id.expiry);
            this.onOrderListener = onOrderListener;
            itemView.setOnClickListener(this);
        }

        // Render address and coordinates as list items visible on main activity view
        @SuppressLint("SetTextI18n")
        void setOrder(Orders order) {
            String dateString = "Order Date: " + convertDate(order.getDate());
            date.setText(dateString);
            servingsAvailable.setText("Servings: " + order.getNumOfServings());
            status.setText("Status: " + order.getStatus());
            type.setText("Type: " + order.getFoodType());
            expiry.setText("Food Expiry: " + order.getExpiryDate());
        }

        public String convertDate(String str){
            LocalDateTime datetime = LocalDateTime.parse(str, DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss"));
            String newString = datetime.format(DateTimeFormatter.ofPattern("dd/MM/YYYY"));
            return newString;

        }

        // onClick listener configuration
        @Override
        public void onClick(View view) {
            onOrderListener.onOrderClick(getAdapterPosition());
        }

        public interface OnOrderListener {
            void onOrderClick(int position);
        }
    }
}
