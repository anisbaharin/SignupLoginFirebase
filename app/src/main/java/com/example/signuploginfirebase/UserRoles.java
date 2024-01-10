package com.example.signuploginfirebase;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.signuploginfirebase.databinding.UserRolesBinding;

public class UserRoles extends AppCompatActivity {

    private UserRolesBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = UserRolesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnAdmin.setOnClickListener(v -> openAdminLogin());
        binding.btnCustomer.setOnClickListener(v -> openCustomerLogin());

    }

    private void openAdminLogin() {
        startActivity(new Intent(UserRoles.this, AdminLoginActivity.class));
    }

    private void openCustomerLogin() {
        startActivity(new Intent(UserRoles.this, LoginActivity.class));
    }

}