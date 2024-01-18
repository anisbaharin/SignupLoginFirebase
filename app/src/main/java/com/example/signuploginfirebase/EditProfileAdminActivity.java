package com.example.signuploginfirebase;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.signuploginfirebase.databinding.ActivityEditProfileAdminBinding;
import com.example.signuploginfirebase.databinding.EditProfileActivityBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class EditProfileAdminActivity extends AppCompatActivity {

    private ActivityEditProfileAdminBinding binding;

    private FirebaseAuth mAuth;

    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding =ActivityEditProfileAdminBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        FirebaseUser currentUser = mAuth.getCurrentUser();

        if (currentUser != null) {
            String userID = currentUser.getUid();

            db.collection("users")
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

        binding.updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newUsername = binding.edtUsername.getText().toString();
                String newEmail = binding.edtEmail.getText().toString();
                String newPhone = binding.edtPhone.getText().toString();

                if (mAuth.getCurrentUser() != null) {
                    updateProfile(currentUser.getUid(), newUsername, newEmail, newPhone);
                }
            }
        });
    }

    private void updateProfile(String userID, String newUsername, String newEmail, String newPhone) {
        db.collection("users")
                .document(userID)
                .update("username", newUsername, "email", newEmail, "phone", newPhone)
                .addOnSuccessListener(aVoid -> {
                    finish();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(getApplicationContext(), "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }

    public void openEditProfile(View view) {
        Intent intent = new Intent(this, ProfileAdminActivity.class);
        startActivity(intent);
    }
}
