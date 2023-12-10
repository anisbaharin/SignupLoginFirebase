package com.example.signuploginfirebase;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.firestore.FirebaseFirestore;

public class AddActivity extends AppCompatActivity {

    private FirebaseFirestore db;
    private EditText addCourtNumber, addCourtName, addCourtDetails, addCourtPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        db = FirebaseFirestore.getInstance();

        addCourtNumber = findViewById(R.id.add_court_no);
        addCourtName = findViewById(R.id.add_court_name);
        addCourtDetails = findViewById(R.id.add_court_details);
        addCourtPrice = findViewById(R.id.add_court_price);

        Button saveButton = findViewById(R.id.addBtn);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveCourt();
            }
        });
    }

    private void saveCourt() {
        String courtNumber = addCourtNumber.getText().toString();
        String courtName = addCourtName.getText().toString();
        String courtDetails = addCourtDetails.getText().toString();
        String courtPrice = addCourtPrice.getText().toString();

        if (courtNumber.isEmpty()) {
            addCourtNumber.setError("Court number cannot be empty");
            return;
        }
        if (courtName.isEmpty()) {
            addCourtNumber.setError("Court name cannot be empty");
            return;
        }
        if (courtDetails.isEmpty()) {
            addCourtNumber.setError("Court details cannot be empty");
            return;
        }
        if (courtPrice.isEmpty()) {
            addCourtNumber.setError("Court price cannot be empty");
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