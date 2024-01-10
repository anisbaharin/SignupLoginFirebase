package com.example.signuploginfirebase;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;

import com.example.signuploginfirebase.databinding.PaymentActivityBinding;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class PaymentActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private PaymentActivityBinding binding;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = PaymentActivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mAuth = FirebaseAuth.getInstance();
        findViewById(R.id.time_spinner);
        findViewById(R.id.duration_spinner);

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

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(
                this,
                R.array.time_array,
                android.R.layout.simple_spinner_item
        );

        // Specify the layout to use when the list of choices appears
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
        binding.timeSpinner.setAdapter(adapter1);

        // Next Array
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(
                this,
                R.array.duration_array,
                android.R.layout.simple_spinner_item
        );

        // Specify the layout to use when the list of choices appears
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
        binding.durationSpinner.setAdapter(adapter2);
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

        if (itemId == R.id.nav_cart) {
            // Handle home click
        } else if (itemId == R.id.nav_home) {
            Intent intent = new Intent(PaymentActivity.this, MainActivity.class);
            startActivity(intent);
        } else if (itemId == R.id.nav_location) {
            Intent intent = new Intent(PaymentActivity.this, LocationActivity.class);
            startActivity(intent);
        } else if (itemId == R.id.nav_inbox) {
            Intent intent = new Intent(PaymentActivity.this, ReceiptActivity.class);
            startActivity(intent);
        } else if (itemId == R.id.nav_profile) {
            Intent intent = new Intent(PaymentActivity.this, ProfileActivity.class);
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
                            mAuth.getInstance().signOut();
                            startActivity(new Intent(PaymentActivity.this, LoginActivity.class));
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

        binding.drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    public void showProceedBtn(View view) {
        Intent intent = new Intent(PaymentActivity.this, ReceiptActivity.class);
        startActivity(intent);
    }

    public void showBackBtn(View view) {
        Intent intent = new Intent(PaymentActivity.this, MainActivity.class);
        startActivity(intent);
    }
}
