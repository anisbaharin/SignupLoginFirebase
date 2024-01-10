package com.example.signuploginfirebase;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

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
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class CourtActivity extends AppCompatActivity {

    TextView courtDetail, courtName, courtNumber, courtPrice;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_court_admin);

        db = FirebaseFirestore.getInstance();
        courtDetail = findViewById(R.id.textViewNumber1);
        courtName = findViewById(R.id.textViewName1);
        courtNumber = findViewById(R.id.textViewDetails1);
        courtPrice = findViewById(R.id.textViewPrice1);
        loadCourt();

    }

    private void loadCourt() {
        CollectionReference courtRef = db.collection("courts");

        courtRef
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        QuerySnapshot document = task.getResult();
                        if (document.exists()) {
                            courtName.setText(document.toString("courtName"));
                            courtNumber.setText(document.toString("courtNumber"));
                            courtDetail.setText(document.toString("courtDetail"));
                            courtPrice.setText(document.toString("courtPrice"));
                        } else {
                            Toast.makeText(getApplicationContext(), "Error: No such document", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "Error: User could not be found", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void showBackBtn(View view) {
        Intent intent = new Intent(CourtActivity.this, AdminMainActivity.class);
        startActivity(intent);
    }
}
