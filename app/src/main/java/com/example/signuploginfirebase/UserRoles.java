package com.example.signuploginfirebase;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class UserRoles extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_roles);

    }
        public void openAdminSignup(View view){
            Intent intent = new Intent(this,SignUpAdminActivity.class);
            startActivity(intent);
        }

        public void openCustomerSignup(View view){
            Intent intent = new Intent(this,SignUpActivity.class);
            startActivity(intent);
        }
}