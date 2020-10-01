package com.bookkaroShop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import com.bookkaroShop.MainHelper.HomeFragment;
import com.bookkaroShop.MainHelper.MenuFragment;
import com.bookkaroShop.MainHelper.ProfileFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener{

    BottomNavigationView bottomNavigationView;
    String restaurantType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        bottomNavigationView.setSelectedItemId(R.id.orders);

        FirebaseFirestore db;
        db = FirebaseFirestore.getInstance();
        db.collection("ServiceProviders").document(FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                DocumentSnapshot documentSnapshot = task.getResult();
                restaurantType = documentSnapshot.getString("restaurantType");
            }
        });
    }

    HomeFragment homeFragment = new HomeFragment();
    MenuFragment menuFragment = new MenuFragment();
    ProfileFragment profileFragment = new ProfileFragment();

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.orders:
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction= fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.main_frame,homeFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
                return true;

            case R.id.menu:
                FragmentManager fragmentManager1 = getSupportFragmentManager();

                Bundle bundle = new Bundle();
                bundle.putString("restaurantType",restaurantType);
                menuFragment.setArguments(bundle);

                FragmentTransaction fragmentTransaction1= fragmentManager1.beginTransaction();
                fragmentTransaction1.replace(R.id.main_frame,menuFragment);
                fragmentTransaction1.addToBackStack(null);
                fragmentTransaction1.commit();
                return true;

            case R.id.profile:

                Bundle bundle2 = new Bundle();
                bundle2.putString("restaurantType",restaurantType);
                profileFragment.setArguments(bundle2);

                FragmentManager fragmentManager3 = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction3 = fragmentManager3.beginTransaction();
                fragmentTransaction3.replace(R.id.main_frame,profileFragment);
                fragmentTransaction3.addToBackStack(null);
                fragmentTransaction3.commit();

                return true;

        }
        return false;
    }


}