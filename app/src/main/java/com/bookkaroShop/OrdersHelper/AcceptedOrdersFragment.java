package com.bookkaroShop.OrdersHelper;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bookkaroShop.OrdersHelper.Adapters.AcceptedOrderAdapter;
import com.bookkaroShop.OrdersHelper.Adapters.NewOrderAdapter;
import com.bookkaroShop.OrdersHelper.Models.AddressModel;
import com.bookkaroShop.OrdersHelper.Models.OrderItemModel;
import com.bookkaroShop.OrdersHelper.Models.OrderModel;
import com.bookkaroShop.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class AcceptedOrdersFragment extends Fragment {

    RecyclerView acceptedOrdersRecycler;
    ArrayList<OrderModel> orderModelArrayList;
    FirebaseDatabase db;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_accepted_orders, container, false);

        acceptedOrdersRecycler = view.findViewById(R.id.acceptedOrdersRecycler);
        acceptedOrdersRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        acceptedOrdersRecycler.setHasFixedSize(true);

        orderModelArrayList = new ArrayList<>();

        db = FirebaseDatabase.getInstance();

        db.getReference("shopOrders").child(FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber()).child("Orders").child("AcceptedOrders").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                getAcceptedOrders();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return view;
    }

    private void getAcceptedOrders(){
        db.getReference().child("shopOrders").child(FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber()).child("Orders").child("AcceptedOrders").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                orderModelArrayList.clear();
                for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                    OrderModel orderModel = new OrderModel();

                    GenericTypeIndicator<ArrayList<OrderItemModel>> genericTypeIndicator = new GenericTypeIndicator<ArrayList<OrderItemModel>>() {};
                    orderModel.setOrderItems(dataSnapshot.child("orderItems").getValue(genericTypeIndicator));


                    orderModel.setOrderStatus(Integer.parseInt(dataSnapshot.child("orderStatus").getValue().toString()));
                    orderModel.setOrderTotal(Integer.parseInt(dataSnapshot.child("orderTotal").getValue().toString()));
                    orderModel.setOrderType(Integer.parseInt(dataSnapshot.child("orderType").getValue().toString()));
                    orderModel.setOrderKey(dataSnapshot.getKey());

                    GenericTypeIndicator<AddressModel> genericTypeIndicator1 = new GenericTypeIndicator<AddressModel>() {};
                    orderModel.setUserAddress(dataSnapshot.child("userAddress").getValue(genericTypeIndicator1));

                    orderModel.setUserPhoneNumber(dataSnapshot.child("userPhoneNumber").getValue().toString());
                    orderModel.setVendorPhoneNumber(dataSnapshot.child("vendorPhoneNumber").getValue().toString());
                    orderModel.setVendorName(dataSnapshot.child("vendorName").getValue().toString());

                    orderModelArrayList.add(orderModel);
                }

                AcceptedOrderAdapter acceptedOrderAdapter = new AcceptedOrderAdapter(getContext(), orderModelArrayList);
                acceptedOrdersRecycler.setAdapter(acceptedOrderAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}