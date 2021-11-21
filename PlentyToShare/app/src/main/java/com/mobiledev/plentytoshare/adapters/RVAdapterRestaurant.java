package com.mobiledev.plentytoshare.adapters;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mobiledev.plentytoshare.R;
import com.mobiledev.plentytoshare.models.CalendarEvent;

import java.util.ArrayList;

public class RVAdapterRestaurant extends RecyclerView.Adapter<RVAdapterRestaurant.RestaurantEventViewHolder> {

    private final ArrayList<CalendarEvent> restaurantEvents;
    private RestaurantEventViewHolder.OnEventItemListener  evListener;

    public RVAdapterRestaurant(ArrayList<CalendarEvent> restaurantEvents, RestaurantEventViewHolder.OnEventItemListener evListener) {
        this.restaurantEvents = restaurantEvents;
        this.evListener = evListener;
    }

    @NonNull
    @Override
    public RestaurantEventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new RestaurantEventViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(
                        R.layout.recycler_view_restaurant_event_item,
                        parent,
                        false
                ), evListener
        );
    }

    @Override
    public void onBindViewHolder(@NonNull RestaurantEventViewHolder holder, int position) {
        holder.setEvent(restaurantEvents.get(position));

    }

    @Override
    public int getItemCount() {
        return restaurantEvents.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    static class RestaurantEventViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView date, servingsAvailable, pickupTime, status;
        OnEventItemListener evListener;

        RestaurantEventViewHolder(@NonNull View itemView, OnEventItemListener evListener) {
            super(itemView);
            date = itemView.findViewById(R.id.date);
            servingsAvailable = itemView.findViewById(R.id.servingsAvailable);
            this.evListener = evListener;
            itemView.setOnClickListener(this);
        }

        // Render address and coordinates as list items visible on main activity view
        void setEvent(CalendarEvent calEvent) {
            date.setText(String.valueOf(calEvent.getDate()));
            servingsAvailable.setText(calEvent.getNumOfServings());
            pickupTime.setText(String.valueOf(calEvent.getPickupTime()));
            status.setText(calEvent.getStatus());
        }

        // onClick listener configuration
        @Override
        public void onClick(View view) {
            evListener.onEventItemClick(getAdapterPosition());
        }

        public interface OnEventItemListener {
            void onEventItemClick(int position);
        }
    }
}
