package com.example.signuploginfirebase;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class AdminLoginActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    private EditText loginEmail, loginPassword;
    private TextView signupRedirectText;
    private Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_admin);

        auth = FirebaseAuth.getInstance();
        loginButton = findViewById(R.id.login_button);
        loginEmail = findViewById(R.id.login_email_admin);
        loginPassword = findViewById(R.id.login_password_admin);
        signupRedirectText = findViewById(R.id.signupRedirectText);

        loginButton.setOnClickListener(view -> {
            String email = loginEmail.getText().toString();
            String pass = loginPassword.getText().toString();

            if (!email.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                if (!pass.isEmpty()) {
                    auth.signInWithEmailAndPassword(email,pass)
                            .addOnSuccessListener(authResult -> {
                                checkUserRole(authResult.getUser().getUid());
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(AdminLoginActivity.this, "Wrong username or password", Toast.LENGTH_SHORT).show();
                                }
                            });
                } else {
                    loginPassword.setError("Wrong Password");
                }
            } else if(email.isEmpty()) {
                loginEmail.setError("Email cannot be empty");
            } else {
                loginEmail.setError("Your email not valid");
            }
        });
        signupRedirectText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdminLoginActivity.this, SignUpAdminActivity.class));
            }
        });
    }

    private void checkUserRole(String userId) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("user")
                .document(userId)
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        String role = documentSnapshot.getString("role");

                        if (role != null && role.equals("admin")) {
                            // User is an admin, proceed to AdminMainActivity
                            startActivity(new Intent(AdminLoginActivity.this, AdminMainActivity.class));
                            finish();
                        } else {
                            // User is not an admin, log out and redirect to UserLoginActivity
                            Toast.makeText(AdminLoginActivity.this, "Please login as user", Toast.LENGTH_SHORT).show();
                            auth.signOut();
                            startActivity(new Intent(AdminLoginActivity.this, LoginActivity.class));
                            finish();
                        }
                    } else {
                        // Handle the case when user data is not available in Firestore
                        Toast.makeText(AdminLoginActivity.this, "User data not found", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(e -> {
                    // Handle failures while checking user role
                    Toast.makeText(AdminLoginActivity.this, "Failed to check user role", Toast.LENGTH_SHORT).show();
                });
    }


    @Override
    protected void onResume() {
        super.onResume();
        if(auth.getCurrentUser() != null){
            startActivity(new Intent(this, AdminMainActivity.class));
        }
    }
}
