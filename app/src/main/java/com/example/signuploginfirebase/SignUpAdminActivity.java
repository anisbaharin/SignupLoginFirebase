package com.example.signuploginfirebase;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class SignUpAdminActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    private FirebaseFirestore db;
    private EditText signupEmail, signupPhone, signupUsername, signupPassword;
    private TextView loginRedirectText;
    private Button signupButton;
    String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_admin);

        auth = FirebaseAuth.getInstance();
        signupEmail = findViewById(R.id.signup_email_admin);
        signupPhone = findViewById(R.id.signup_phonenumber_admin);
        signupUsername = findViewById(R.id.signup_username_admin);
        signupPassword = findViewById(R.id.signup_password_admin);
        signupButton = findViewById(R.id.signup_button);
        loginRedirectText = findViewById(R.id.loginRedirectText);
        db = FirebaseFirestore.getInstance();

        signupButton.setOnClickListener(view -> {
            String user = signupEmail.getText().toString().trim();
            String pass = signupPassword.getText().toString().trim();

            if (user.isEmpty()) {
                signupEmail.setError("Email cannot be empty");
                return;
            }
            if (pass.isEmpty()) {
                signupPassword.setError("Password cannot be empty");
                return;
            }

            auth.createUserWithEmailAndPassword(user, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        userId = auth.getCurrentUser().getUid();

                        Map<String, Object> userMap = new HashMap<>();
                        userMap.put("email", signupEmail.getText().toString().trim());
                        userMap.put("phone", signupPhone.getText().toString().trim());
                        userMap.put("username", signupUsername.getText().toString().trim());
                        userMap.put("password", signupPassword.getText().toString().trim());

                        db.collection("admin")
                                .document(userId)
                                .set(userMap)
                                .addOnSuccessListener(aVoid -> {
                                    startActivity(new Intent(getApplicationContext(), AdminLoginActivity.class));
                                    finish();
                                })
                                .addOnFailureListener(e ->
                                        Toast.makeText(getApplicationContext(), "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show());
                    } else {
                        Toast.makeText(SignUpAdminActivity.this, "Your email or phone number not valid", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        });

        loginRedirectText.setOnClickListener(view ->
                startActivity(new Intent(SignUpAdminActivity.this, AdminLoginActivity.class)));
    }
}