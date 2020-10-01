package com.bookkaroShop.OrdersHelper.ViewHolders;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bookkaroShop.R;

public class DeliveredOrderViewHolder extends RecyclerView.ViewHolder {

    public TextView orderId;
    public RecyclerView orderItems;
    public TextView orderTotal;
    public TextView orderAddress;

    public DeliveredOrderViewHolder(@NonNull View itemView) {
        super(itemView);

        orderId = itemView.findViewById(R.id.customer_id);
        orderItems = itemView.findViewById(R.id.item_recycler);
        orderTotal = itemView.findViewById(R.id.order_total);
        orderAddress = itemView.findViewById(R.id.order_details);

    }
}
