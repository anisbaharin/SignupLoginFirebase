package com.example.signuploginfirebase;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.signuploginfirebase.databinding.ActivitySignupAdminBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class SignUpAdminActivity extends AppCompatActivity {

    private ActivitySignupAdminBinding binding;

    private FirebaseAuth auth;
    private FirebaseFirestore db;

    String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignupAdminBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        auth = FirebaseAuth.getInstance();

        db = FirebaseFirestore.getInstance();

        binding.signupButton.setOnClickListener(view -> {
            String user = binding.signupEmailAdmin.getText().toString().trim();
            String pass = binding.signupPasswordAdmin.getText().toString().trim();

            if (user.isEmpty()) {
                binding.signupEmailAdmin.setError("Email cannot be empty");
                return;
            }
            if (pass.isEmpty()) {
                binding.signupPasswordAdmin.setError("Password cannot be empty");
                return;
            }

            auth.createUserWithEmailAndPassword(user, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        userId = auth.getCurrentUser().getUid();

                        Map<String, Object> userMap = new HashMap<>();
                        userMap.put("email", binding.signupEmailAdmin.getText().toString().trim());
                        userMap.put("phone", binding.signupPhonenumberAdmin.getText().toString().trim());
                        userMap.put("username", binding.signupUsernameAdmin.getText().toString().trim());
                        userMap.put("password", binding.signupPasswordAdmin.getText().toString().trim());
                        userMap.put("role", "admin");

                        db.collection("users")
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

        binding.loginRedirectText.setOnClickListener(view ->
                startActivity(new Intent(SignUpAdminActivity.this, AdminLoginActivity.class)));
    }
}