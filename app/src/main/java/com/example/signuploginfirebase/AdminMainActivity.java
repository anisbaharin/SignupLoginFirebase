package com.example.signuploginfirebase;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class AdminMainActivity extends AppCompatActivity {

    CardView addCard, courtCard, profileCard, signoutCard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_admin);

        addCard = findViewById(R.id.addCard);
        courtCard = findViewById(R.id.courtCard);
        profileCard = findViewById(R.id.profileCard);
        signoutCard = findViewById(R.id.signoutCard);

        addCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminMainActivity.this, AddActivity.class);
                startActivity(intent);
            }
        });

        courtCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminMainActivity.this, AdminCourtActivity.class);
                startActivity(intent);
            }
        });

        profileCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminMainActivity.this, ProfileAdminActivity.class);
                startActivity(intent);
            }
        });

        signoutCard.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view){
                    // Sign out the user
                    FirebaseAuth.getInstance().signOut();

                    // Navigate to the desired activity (in this case, UserRoles)
                    Intent intent = new Intent(AdminMainActivity.this, UserRoles.class);
                    startActivity(intent);

                    // Finish the current activity to prevent the user from coming back with the back button
                    finish();
            }
        });
            }
}
