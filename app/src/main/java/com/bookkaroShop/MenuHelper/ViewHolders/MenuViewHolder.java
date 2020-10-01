package com.bookkaroShop.MenuHelper.ViewHolders;

import android.view.View;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bookkaroShop.R;

public class MenuViewHolder extends RecyclerView.ViewHolder {

    public TextView itemDesc;
    public TextView itemPrice;
    public TextView itemName;

    public Switch onOff;

    public MenuViewHolder(@NonNull View itemView) {
        super(itemView);

        itemName = itemView.findViewById(R.id.itemName);
        itemPrice = itemView.findViewById(R.id.itemPrice);
        itemDesc = itemView.findViewById(R.id.itemDesc);

        onOff = itemView.findViewById(R.id.item_switch);
    }
}
