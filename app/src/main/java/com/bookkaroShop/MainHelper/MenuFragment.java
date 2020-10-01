package com.bookkaroShop.MainHelper;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.bookkaroShop.MenuHelper.Adapters.MenuAdapter;
import com.bookkaroShop.MenuHelper.AddItemFragment;
import com.bookkaroShop.MenuHelper.Models.ItemModel;
import com.bookkaroShop.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class MenuFragment extends Fragment {

    FloatingActionButton addItem;

    RecyclerView menuRecycler;
    FirebaseDatabase db;
    ArrayList<ItemModel> itemModelArrayList;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_menu, container, false);

        addItem = view.findViewById(R.id.add_new_item);
        addItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AddItemFragment addItemFragment = new AddItemFragment();

                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.main_frame, addItemFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        menuRecycler = view.findViewById(R.id.items_recycler);
        menuRecycler.setHasFixedSize(true);
        menuRecycler.setLayoutManager(new LinearLayoutManager(getContext()));

        itemModelArrayList = new ArrayList<>();

        db = FirebaseDatabase.getInstance();

        db.getReference().child("shops").child("1020").child(FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber())
                .child("AllItems").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                itemModelArrayList.clear();
                for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                    ItemModel itemModel = new ItemModel();

                    itemModel.setItemCategory(dataSnapshot.child("itemCategory").getValue().toString());
                    itemModel.setItemCategoryId(Integer.parseInt(dataSnapshot.child("itemCategoryId").getValue().toString()));
                    itemModel.setItemDesc(dataSnapshot.child("itemCategory").getValue().toString());
                    itemModel.setItemIcon(dataSnapshot.child("itemIcon").getValue().toString());
                    itemModel.setItemName(dataSnapshot.child("itemName").getValue().toString());
                    itemModel.setItemId(dataSnapshot.child("itemId").getValue().toString());
                    itemModel.setItemPrice(dataSnapshot.child("itemPrice").getValue().toString());
                    itemModel.setItemStatus(dataSnapshot.child("itemStatus").getValue().toString());
                    itemModel.setKey(dataSnapshot.getKey());

                    itemModelArrayList.add(itemModel);
                }

                MenuAdapter menuAdapter = new MenuAdapter(getContext(), itemModelArrayList);
                menuRecycler.setAdapter(menuAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return view;
    }
}