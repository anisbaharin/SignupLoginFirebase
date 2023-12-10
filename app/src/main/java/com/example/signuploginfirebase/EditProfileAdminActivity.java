package com.example.signuploginfirebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class EditProfileAdminActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private EditText editUsername, editPhone, editEmail;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_profile_activity);

        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        editUsername = findViewById(R.id.textViewUsername);
        editPhone = findViewById(R.id.textViewPhone);
        editEmail = findViewById(R.id.textViewEmail);

        FirebaseUser currentUser=mAuth.getCurrentUser();

        if (currentUser != null) {
            String userID = currentUser.getUid();

            db.collection("admin")
                    .document(userID)
                    .get()
                    .addOnSuccessListener(documentSnapshot -> {
                        if (documentSnapshot.exists()) {
                            String username = documentSnapshot.getString("username");
                            String email = documentSnapshot.getString("email");
                            String phone = documentSnapshot.getString("phone");

                            editUsername.setText(username);
                            editEmail.setText(email);
                            editPhone.setText(phone);
                        }
                    })
                    .addOnFailureListener(e -> {
                        Toast.makeText(getApplicationContext(), "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    });
        }

        Button saveButton = findViewById(R.id.retrievebtn);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newUsername = editUsername.getText().toString();
                String newEmail = editEmail.getText().toString();
                String newPhone = editPhone.getText().toString();

                updateProfile(currentUser.getUid(),newUsername,newEmail,newPhone);
            }
        });
    }

    private void updateProfile(String userID, String newUsername, String newEmail, String newPhone){
        db.collection("admin")
                .document(userID)
                .update("username",newUsername,"email", newEmail, "phone", newPhone)
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
