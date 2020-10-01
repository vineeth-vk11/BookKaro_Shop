package com.bookkaroShop.OrdersHelper.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bookkaroShop.OrdersHelper.DeliveredOrdersFragment;
import com.bookkaroShop.OrdersHelper.Models.AddressModel;
import com.bookkaroShop.OrdersHelper.Models.OrderModel;
import com.bookkaroShop.OrdersHelper.ViewHolders.AcceptedOrderViewHolder;
import com.bookkaroShop.OrdersHelper.ViewHolders.DeliveredOrderViewHolder;
import com.bookkaroShop.R;

import java.util.ArrayList;

public class DeliveredOrderAdapter extends RecyclerView.Adapter<DeliveredOrderViewHolder> {

    Context context;
    ArrayList<OrderModel> orderModelArrayList;

    public DeliveredOrderAdapter(Context context, ArrayList<OrderModel> orderModelArrayList) {
        this.context = context;
        this.orderModelArrayList = orderModelArrayList;
    }

    @NonNull
    @Override
    public DeliveredOrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.list_item_delivered_order, parent, false);
        return new DeliveredOrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DeliveredOrderViewHolder holder, int position) {

        String orderKey = orderModelArrayList.get(position).getOrderKey();

        holder.orderId.setText(orderModelArrayList.get(position).getUserPhoneNumber());
        AddressModel address = orderModelArrayList.get(position).getUserAddress();
        String userAddress = address.getAddress();
        holder.orderAddress.setText(userAddress);
        holder.orderTotal.setText(String.valueOf(orderModelArrayList.get(position).getOrderTotal()));

        OrderItemsAdapter orderItemsAdapter = new OrderItemsAdapter(context, orderModelArrayList.get(position).getOrderItems());
        holder.orderItems.setHasFixedSize(true);
        holder.orderItems.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false));
        holder.orderItems.setNestedScrollingEnabled(false);
        holder.orderItems.setAdapter(orderItemsAdapter);
    }

    @Override
    public int getItemCount() {
        return orderModelArrayList.size();
    }
}
