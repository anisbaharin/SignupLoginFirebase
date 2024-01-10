package com.example.signuploginfirebase;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.signuploginfirebase.adapter.CourtListAdapter;
import com.example.signuploginfirebase.databinding.ActivityCourtAdminBinding;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;

import android.widget.Toast;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class AdminCourtActivity extends AppCompatActivity {

    private ActivityCourtAdminBinding binding;

    private FirebaseFirestore db;
    private CourtListAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCourtAdminBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        db = FirebaseFirestore.getInstance();
        mAdapter = new CourtListAdapter();

        binding.courtRV.setLayoutManager(new LinearLayoutManager(this));
        binding.courtRV.setAdapter(mAdapter);

        // Load Court Data
        loadCourt();

    }

    private void loadCourt() {
        CollectionReference courtRef = db.collection("courts");

        courtRef.get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        QuerySnapshot documents = task.getResult();

                        if (!documents.isEmpty()) {

                            mAdapter.submitList(getCourtList(documents));

                        } else {
                            Toast.makeText(getApplicationContext(), "Error: No such document", Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        Toast.makeText(getApplicationContext(), "Error: User could not be found", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private List<Court> getCourtList(QuerySnapshot documents) {
        List<Court> list = new ArrayList<>();

        // Loop through all the documents in the collection
        for (DocumentSnapshot document : documents) {

            String courtName = document.get("courtName").toString();
            String courtNumber = document.get("courtNumber").toString();
            String courtDetail = document.get("courtDetail").toString();
            String courtPrice = document.get("courtPrice").toString();

            Court c = new Court(
                    courtNumber,
                    courtName,
                    courtDetail,
                    courtPrice
            );

            list.add(c);
        }

        return list;
    }

    public void showBackBtn(View view) {
        Intent intent = new Intent(AdminCourtActivity.this, AdminMainActivity.class);
        startActivity(intent);
        finishAffinity();
    }
}
