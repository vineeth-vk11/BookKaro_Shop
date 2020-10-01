package com.bookkaroShop.OrdersHelper.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bookkaroShop.OrdersHelper.Models.OrderItemModel;
import com.bookkaroShop.OrdersHelper.ViewHolders.OrderItemsViewHolder;
import com.bookkaroShop.R;

import java.util.ArrayList;

public class OrderItemsAdapter extends RecyclerView.Adapter<OrderItemsViewHolder> {

    Context context;
    ArrayList<OrderItemModel> orderItemModelArrayList;

    public OrderItemsAdapter(Context context, ArrayList<OrderItemModel> orderItemModelArrayList) {
        this.context = context;
        this.orderItemModelArrayList = orderItemModelArrayList;
    }

    @NonNull
    @Override
    public OrderItemsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.list_item_order_item, parent, false);
        return new OrderItemsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderItemsViewHolder holder, int position) {
        holder.itemName.setText(orderItemModelArrayList.get(position).getItemName());
        holder.itemQuantity.setText(String.valueOf(orderItemModelArrayList.get(position).getItemQuantity()));
    }

    @Override
    public int getItemCount() {
        return orderItemModelArrayList.size();
    }
}
