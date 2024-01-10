package com.example.signuploginfirebase;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;

import com.example.signuploginfirebase.databinding.EditProfileActivityBinding;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class EditProfileActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private EditProfileActivityBinding binding;

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = EditProfileActivityBinding.inflate(getLayoutInflater());
        setContentView(R.layout.edit_profile_activity);

        db = FirebaseFirestore.getInstance();
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
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if (currentUser != null) {
            String userID = currentUser.getUid();

            db.collection("user")
                    .document(userID)
                    .get()
                    .addOnSuccessListener(documentSnapshot -> {
                        if (documentSnapshot.exists()) {
                            String username = documentSnapshot.getString("username");
                            String email = documentSnapshot.getString("email");
                            String phone = documentSnapshot.getString("phone");

                            binding.edtUsername.setText(username);
                            binding.edtEmail.setText(email);
                            binding.edtPhone.setText(phone);
                        }
                    })
                    .addOnFailureListener(e -> {
                        Toast.makeText(getApplicationContext(), "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    });
        }

        binding.saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newUsername = binding.edtUsername.getText().toString();
                String newEmail = binding.edtEmail.getText().toString();
                String newPhone = binding.edtPhone.getText().toString();

                if (currentUser != null) {
                    updateProfile(currentUser.getUid(), newUsername, newEmail, newPhone);
                } else {
                    Toast.makeText(
                            EditProfileActivity.this,
                            "No user found",
                            Toast.LENGTH_SHORT
                    ).show();
                }
            }
        });
    }

    private void updateProfile(String userID, String newUsername, String newEmail, String newPhone) {
        db.collection("user")
                .document(userID)
                .update("username", newUsername, "email", newEmail, "phone", newPhone)
                .addOnSuccessListener(aVoid -> {
                    finish();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(getApplicationContext(), "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
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

        if (itemId == R.id.nav_profile) {
            // Handle home click
        } else if (itemId == R.id.nav_home) {
            Intent intent = new Intent(EditProfileActivity.this, MainActivity.class);
            startActivity(intent);
        } else if (itemId == R.id.nav_location) {
            Intent intent = new Intent(EditProfileActivity.this, LocationActivity.class);
            startActivity(intent);
        } else if (itemId == R.id.nav_inbox) {
            Intent intent = new Intent(EditProfileActivity.this, ReceiptAcitivity.class);
            startActivity(intent);
        } else if (itemId == R.id.nav_cart) {
            Intent intent = new Intent(EditProfileActivity.this, PaymentActivity.class);
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
                            startActivity(new Intent(EditProfileActivity.this, UserRoles.class));
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

    public void openEditProfile(View view) {
        Intent intent = new Intent(this, ProfileActivity.class);
        startActivity(intent);
        finishAffinity();
    }

    public void showBackBtn(View view) {
        Intent intent = new Intent(EditProfileActivity.this, AdminMainActivity.class);
        startActivity(intent);
        finishAffinity();
    }
}
