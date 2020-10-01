package com.bookkaroShop.OrdersHelper.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bookkaroShop.OrdersHelper.Models.AddressModel;
import com.bookkaroShop.OrdersHelper.Models.OrderModel;
import com.bookkaroShop.OrdersHelper.ViewHolders.AcceptedOrderViewHolder;
import com.bookkaroShop.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class AcceptedOrderAdapter extends RecyclerView.Adapter<AcceptedOrderViewHolder> {

    Context context;
    ArrayList<OrderModel> orderModelArrayList;

    public AcceptedOrderAdapter(Context context, ArrayList<OrderModel> orderModelArrayList) {
        this.context = context;
        this.orderModelArrayList = orderModelArrayList;
    }

    @NonNull
    @Override
    public AcceptedOrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.list_item_accepted_order, parent, false);
        return new AcceptedOrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AcceptedOrderViewHolder holder, int position) {

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

        holder.delivered.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase db;
                db = FirebaseDatabase.getInstance();
                db.getReference().child("shopOrders").child(FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber())
                        .child("Orders").child("DeliveredOrders").child(orderKey).setValue(orderModelArrayList.get(position)).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        db.getReference().child("userData").child(orderModelArrayList.get(position).getUserPhoneNumber()).child("shopOrders").child(orderKey).child("orderStatus").setValue(101).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                db.getReference().child("shopOrders").child(FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber())
                                        .child("Orders").child("AcceptedOrders").child(orderKey).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        Toast.makeText(context, "Order Delivered Successfully", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        });
                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return orderModelArrayList.size();
    }
}
