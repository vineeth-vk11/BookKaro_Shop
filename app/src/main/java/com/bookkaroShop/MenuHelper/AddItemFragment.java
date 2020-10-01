package com.bookkaroShop.MenuHelper;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.bookkaroShop.MainHelper.MenuFragment;
import com.bookkaroShop.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.UUID;


public class AddItemFragment extends Fragment {

    Button saveItem;

    EditText item_name, item_desc, item_price;

    AutoCompleteTextView categories;

    FirebaseDatabase db;

    int i = 0;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_item, container, false);

        db = FirebaseDatabase.getInstance();

        item_name = view.findViewById(R.id.item_name_edit);
        item_desc = view.findViewById(R.id.description_edit);
        item_price = view.findViewById(R.id.price_edit);

        String[] CATEGORIES = new String[]{"Rice", "Soaps"};

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                getActivity(),
                R.layout.dropdown_menu_popup_item,
                CATEGORIES
        );

        categories = view.findViewById(R.id.category_dropdown);
        categories.setAdapter(adapter);

        saveItem = view.findViewById(R.id.data_save);
        saveItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveItem();
            }
        });


        return view;
    }

    private void saveItem(){

        i = 0;

        String itemName = item_name.getText().toString();
        String itemDesc = item_desc.getText().toString();
        String itemPrice = item_price.getText().toString();
        String category = categories.getText().toString();

        if(itemName.isEmpty()){
            Toast.makeText(getActivity(),"Enter item name",Toast.LENGTH_SHORT).show();
        }
        else if(itemDesc.isEmpty()){
            Toast.makeText(getActivity(),"Enter item description",Toast.LENGTH_SHORT).show();
        }
        else if(itemPrice.isEmpty()){
            Toast.makeText(getActivity(),"Enter item price",Toast.LENGTH_SHORT).show();
        }
        else if(category.isEmpty()){
            Toast.makeText(getActivity(),"Enter item category",Toast.LENGTH_SHORT).show();
        }
        else {

            DatabaseReference databaseReference =  db.getReference().child("shops").child("1020").child(FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber()).child("Menu")
                    .child(categories.getText().toString()).child("items");

            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                        i += 1;
                    }

                    HashMap<String, Object> itemData = new HashMap<>();
                    itemData.put("itemName",itemName);
                    itemData.put("itemDesc",itemDesc);
                    itemData.put("itemPrice",itemPrice);
                    itemData.put("itemIcon","https://images-na.ssl-images-amazon.com/images/I/61VGYi4fkzL._SL1000_.jpg");
                    itemData.put("itemId", UUID.randomUUID().toString());
                    itemData.put("itemCategoryId",i);

                    itemData.put("itemCategory",categories.getText().toString());
                    itemData.put("itemStatus","0");

                    db.getReference().child("shops").child("1020").child(FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber()).child("Menu")
                            .child(categories.getText().toString()).child("categoryName").setValue(categories.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            db.getReference().child("shops").child("1020").child(FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber()).child("Menu")
                                    .child(categories.getText().toString()).child("items").child(String.valueOf(i)).setValue(itemData).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    db.getReference().child("shops").child("1020").child(FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber())
                                            .child("AllItems").push().setValue(itemData).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            MenuFragment menuFragment = new MenuFragment();

                                            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                            fragmentTransaction.replace(R.id.main_frame, menuFragment);
                                            fragmentTransaction.addToBackStack(null);
                                            fragmentTransaction.commit();
                                        }
                                    });
                                }
                            });
                        }
                    });
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        }
    }
}