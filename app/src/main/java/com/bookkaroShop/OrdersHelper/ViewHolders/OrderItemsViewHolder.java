package com.bookkaroShop.OrdersHelper.ViewHolders;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bookkaroShop.R;

public class OrderItemsViewHolder extends RecyclerView.ViewHolder {

    public TextView itemName;
    public TextView itemQuantity;

    public OrderItemsViewHolder(@NonNull View itemView) {
        super(itemView);

        itemName = itemView.findViewById(R.id.item_name);
        itemQuantity = itemView.findViewById(R.id.item_quantity);
    }
}
