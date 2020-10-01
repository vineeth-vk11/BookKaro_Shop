package com.bookkaroShop.MenuHelper.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bookkaroShop.MenuHelper.Models.ItemModel;
import com.bookkaroShop.MenuHelper.ViewHolders.MenuViewHolder;
import com.bookkaroShop.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class MenuAdapter extends RecyclerView.Adapter<MenuViewHolder> {

    Context context;
    ArrayList<ItemModel> itemModelArrayList;

    public MenuAdapter(Context context, ArrayList<ItemModel> itemModelArrayList) {
        this.context = context;
        this.itemModelArrayList = itemModelArrayList;
    }

    @NonNull
    @Override
    public MenuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.list_item_menu, parent, false);
        return new MenuViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MenuViewHolder holder, int position) {
        holder.itemName.setText(itemModelArrayList.get(position).getItemName());
        holder.itemPrice.setText(itemModelArrayList.get(position).getItemPrice());
        holder.itemDesc.setText(itemModelArrayList.get(position).getItemDesc());

        String status = itemModelArrayList.get(position).getItemStatus();

        String key = itemModelArrayList.get(position).getKey();
        String category = itemModelArrayList.get(position).getItemCategory();
        String categoryId = String.valueOf(itemModelArrayList.get(position).getItemCategoryId());

        if(status.equals("0")){
            holder.onOff.setChecked(false);
        }
        else {
            holder.onOff.setChecked(true);
        }

        holder.onOff.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    FirebaseDatabase db;
                    db = FirebaseDatabase.getInstance();
                    db.getReference().child("shops").child("1020").child(FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber())
                            .child("AllItems").child(key).child("itemStatus").setValue("1").addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            db.getReference().child("shops").child("1020").child(FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber())
                                    .child("Menu").child(category).child("items").child(categoryId).child("itemStatus").setValue("1").addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    Toast.makeText(context, "Item made available",Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    });
                }
                else {
                    FirebaseDatabase db;
                    db = FirebaseDatabase.getInstance();
                    db.getReference().child("shops").child("1020").child(FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber())
                            .child("AllItems").child(key).child("itemStatus").setValue("0").addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            db.getReference().child("shops").child("1020").child(FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber())
                                    .child("Menu").child(category).child("items").child(categoryId).child("itemStatus").setValue("0").addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    Toast.makeText(context, "Item made available",Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    });
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return itemModelArrayList.size();
    }
}
