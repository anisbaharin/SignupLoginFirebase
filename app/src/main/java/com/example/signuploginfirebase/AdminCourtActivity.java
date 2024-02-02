package com.example.signuploginfirebase;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.signuploginfirebase.adapter.CourtListAdapter;
import com.example.signuploginfirebase.databinding.ActivityCourtAdminBinding;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
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
        mAdapter = new CourtListAdapter(this::onCourtItemClick);

        binding.courtRV.setLayoutManager(new LinearLayoutManager(this));
        binding.courtRV.setAdapter(mAdapter);

        // Load Court Data
        loadCourt();
    }

    private void onCourtItemClick(Court clickedCourt) {
        // Handle the item click here
        Intent intent = new Intent(AdminCourtActivity.this, AdminUpdateCourtActivity.class);

        // Pass the selected court data as extras to the intent
        intent.putExtra("courtName", clickedCourt.getCourtName());
        intent.putExtra("courtNumber", clickedCourt.getCourtNumber());
        intent.putExtra("courtDetail", clickedCourt.getCourtDetail());
        intent.putExtra("courtPrice", clickedCourt.getCourtPrice());

        // Pass the document ID to AdminUpdateCourtActivity
        intent.putExtra("documentId", clickedCourt.getDocumentId());

        // Start the AdminUpdateCourtActivity
        startActivity(intent);
        finishAffinity();
    }

    private void loadCourt() {
        CollectionReference courtRef = db.collection("courts");

        courtRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                QuerySnapshot documents = task.getResult();

                if (documents != null && !documents.isEmpty()) {
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
            String courtName = document.getString("courtName");
            String courtNumber = document.getString("courtNumber");
            String courtDetail = document.getString("courtDetail");
            String courtPrice = document.getString("courtPrice");
            String imageUrl = document.getString("imageUrl");

            Court c = new Court(courtNumber, courtName, courtDetail, courtPrice, imageUrl);
            c.setDocumentId(document.getId()); // Set the document ID

            list.add(c);
        }

        return list;
    }

    public void showBackBtn(View view) {
        Intent intent = new Intent(AdminCourtActivity.this, AdminMainActivity.class);
        startActivity(intent);
        finishAffinity();
    }

    public void updateBtn(View view) {
        Intent intent = new Intent(AdminCourtActivity.this, AdminMainActivity.class);
        startActivity(intent);
        finishAffinity();
    }
}
