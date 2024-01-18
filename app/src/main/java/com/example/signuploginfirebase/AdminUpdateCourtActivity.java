package com.example.signuploginfirebase;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.signuploginfirebase.databinding.ActivityUpdateCourtAdminBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class AdminUpdateCourtActivity extends AppCompatActivity {

    private ActivityUpdateCourtAdminBinding binding;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private String documentId; // Added variable to store the document ID

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUpdateCourtAdminBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        Intent intent = getIntent();
        if (intent != null) {
            documentId = intent.getStringExtra("documentId");
            loadCourtData(documentId);
        }

        binding.btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newNumber = binding.edtNumber.getText().toString();
                String newName = binding.edtName.getText().toString();
                String newDetails = binding.edtDetails.getText().toString();
                String newPrice = binding.edtPrice.getText().toString();

                updateCourt(documentId, newNumber, newName, newDetails, newPrice);
            }
        });
    }

    private void loadCourtData(String documentId) {
        db.collection("courts")
                .document(documentId)
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        String number = documentSnapshot.getString("courtNumber");
                        String name = documentSnapshot.getString("courtName");
                        String details = documentSnapshot.getString("courtDetail");
                        String price = documentSnapshot.getString("courtPrice");

                        binding.edtNumber.setText(number);
                        binding.edtName.setText(name);
                        binding.edtDetails.setText(details);
                        binding.edtPrice.setText(price);
                    }
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(getApplicationContext(), "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }

    private void updateCourt(String documentId, String newNumber, String newName, String newDetails, String newPrice) {
        db.collection("courts")
                .document(documentId)
                .update("courtNumber", newNumber, "courtName", newName, "courtDetail", newDetails, "courtPrice", newPrice)
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(getApplicationContext(), "Court updated successfully", Toast.LENGTH_SHORT).show();

                    // Navigate back to AdminCourtActivity
                    Intent intent = new Intent(AdminUpdateCourtActivity.this, AdminCourtActivity.class);
                    startActivity(intent);

                    // Finish the current activity
                    finish();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(getApplicationContext(), "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }

    public void showBackBtn(View view) {
        // Navigate back to AdminCourtActivity
        Intent intent = new Intent(this, AdminCourtActivity.class);
        startActivity(intent);
    }
}
