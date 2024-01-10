package com.example.signuploginfirebase;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;

import com.example.signuploginfirebase.databinding.CourtFirstActivityBinding;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class CourtFirstActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private CourtFirstActivityBinding binding;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = CourtFirstActivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mAuth = FirebaseAuth.getInstance();

        setSupportActionBar(binding.toolbar);

        binding.navView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this,
                binding.drawerLayout,
                binding.toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close
        );

        binding.drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        binding.navView.setNavigationItemSelectedListener(this);
        binding.navView.setCheckedItem(R.id.nav_home);
    }

    @Override
    public void onBackPressed() {

        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        int itemId = item.getItemId();

        if (itemId == R.id.nav_home) {
            // Handle home click
        } else if (itemId == R.id.nav_location) {
            Intent intent = new Intent(CourtFirstActivity.this, LocationActivity.class);
            startActivity(intent);
        } else if (itemId == R.id.nav_inbox) {
            Intent intent = new Intent(CourtFirstActivity.this, ReceiptAcitivity.class);
            startActivity(intent);
        } else if (itemId == R.id.nav_cart) {
            Intent intent = new Intent(CourtFirstActivity.this, PaymentActivity.class);
            startActivity(intent);
        } else if (itemId == R.id.nav_profile) {
            Intent intent = new Intent(CourtFirstActivity.this, ProfileActivity.class);
            startActivity(intent);
        } else if (itemId == R.id.nav_logout) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Confirmation Exit")
                    .setMessage("Are you sure want to exit?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // Perform logout action here
                            // For example, you can sign the user out and navigate to the login screen
                            // Replace the following with your actual logout logic
                            mAuth.signOut();
                            startActivity(new Intent(CourtFirstActivity.this, UserRoles.class));
                            finish();
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).show();
        }

        binding.drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    public void showBookBtn(View view) {
        Intent intent = new Intent(CourtFirstActivity.this, PaymentActivity.class);
        startActivity(intent);
    }

    public void showBackBtn(View view) {
        Intent intent = new Intent(CourtFirstActivity.this, MainActivity.class);
        startActivity(intent);
    }
}
