package com.example.signuploginfirebase;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import com.google.firebase.firestore.DocumentSnapshot;
import android.widget.Toast;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class ProfileAdminActivity extends AppCompatActivity {

    TextView username, phonenumber, email;
    private FirebaseFirestore db;
    private FirebaseAuth auth;
    String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_activity);

        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        username = findViewById(R.id.textViewUsername);
        phonenumber = findViewById(R.id.textViewPhone);
        email = findViewById(R.id.textViewEmail);

        loadUserProfile();
    }

    private void loadUserProfile() {
        userId = auth.getCurrentUser().getUid();
        db.collection("admin").document(userId)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            username.setText(document.getString("username"));
                            email.setText(document.getString("email"));
                            phonenumber.setText(document.getString("phone"));
                        } else {
                            Toast.makeText(getApplicationContext(), "Error: No such document", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "Error: User could not be found", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void openEditProfile(View view) {
        Intent intent = new Intent(this, EditProfileAdminActivity.class);
        startActivity(intent);
    }
    public void showBackBtn(View view) {
        Intent intent = new Intent(ProfileAdminActivity.this, AdminMainActivity.class);
        startActivity(intent);
    }
}
