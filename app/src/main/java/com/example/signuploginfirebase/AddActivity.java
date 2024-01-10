package com.example.signuploginfirebase;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.signuploginfirebase.databinding.ActivityAddBinding;
import com.google.firebase.firestore.FirebaseFirestore;

public class AddActivity extends AppCompatActivity {

    private ActivityAddBinding binding;

    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        db = FirebaseFirestore.getInstance();

        binding.saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveCourt();
            }
        });
    }

    private void saveCourt() {
        String courtNumber = binding.addCourtNo.getText().toString();
        String courtName = binding.addCourtName.getText().toString();
        String courtDetails = binding.addCourtDetails.getText().toString();
        String courtPrice = binding.addCourtPrice.getText().toString();

        if (courtNumber.isEmpty()) {
            binding.addCourtNo.setError("Court number cannot be empty");
            return;
        }
        if (courtName.isEmpty()) {
            binding.addCourtName.setError("Court name cannot be empty");
            return;
        }
        if (courtDetails.isEmpty()) {
            binding.addCourtDetails.setError("Court details cannot be empty");
            return;
        }
        if (courtPrice.isEmpty()) {
            binding.addCourtPrice.setError("Court price cannot be empty");
            return;
        }

        Court newCourt = new Court(courtNumber,courtName,courtDetails,courtPrice);

        db.collection("courts")
                .add(newCourt)
                .addOnSuccessListener(documentReference -> {
                    startActivity(new Intent(getApplicationContext(), AdminMainActivity.class));
                    finish();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(getApplicationContext(), "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });

    }
    public void showBackBtn(View view) {
        Intent intent = new Intent(AddActivity.this, AdminMainActivity.class);
        startActivity(intent);
    }
}