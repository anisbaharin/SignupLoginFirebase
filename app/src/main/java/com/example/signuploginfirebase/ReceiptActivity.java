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
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class ReceiptActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    NavigationView navigationView;
    Toolbar toolbar;
    DrawerLayout drawerLayout;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.receipt_activity);

        mAuth = FirebaseAuth.getInstance();
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        navigationView.setCheckedItem(R.id.nav_home);
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();

        if (itemId == R.id.nav_inbox) {
            // Handle home click
        } else if (itemId == R.id.nav_home) {
            Intent intent = new Intent(ReceiptActivity.this, MainActivity.class);
            startActivity(intent);
        } else if (itemId == R.id.nav_location) {
            Intent intent = new Intent(ReceiptActivity.this, LocationActivity.class);
            startActivity(intent);
        } else if (itemId == R.id.nav_cart) {
            Intent intent = new Intent(ReceiptActivity.this, PaymentActivity.class);
            startActivity(intent);
        } else if (itemId == R.id.nav_profile) {
            Intent intent = new Intent(ReceiptActivity.this, ProfileActivity.class);
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
                            startActivity(new Intent(ReceiptActivity.this, UserRoles.class));
                            finish();
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .show();
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    //    public void showProceedBtn(View view) {
//        Intent intent = new Intent(ReceiptAcitivity.this, DetailsActivity.class);
//        startActivity(intent);
//    }
    public void showBackBtn(View view) {
        Intent intent = new Intent(ReceiptActivity.this, PaymentActivity.class);
        startActivity(intent);
    }
}
