package com.example.signuploginfirebase;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.signuploginfirebase.databinding.ActivityAddBinding;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.progressindicator.LinearProgressIndicator;
import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.UUID;

public class AddActivity extends AppCompatActivity {

    StorageReference storageReference;
    LinearProgressIndicator progress;
    Uri image;
    MaterialButton selectImage, uploadImage;
    private ActivityAddBinding binding;
    private FirebaseFirestore db;

    private final ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            if (result.getResultCode() == RESULT_OK) {
                if (result.getData() != null) {
                    image = result.getData().getData();
                    uploadImage.setEnabled(true);
                    Glide.with(getApplicationContext()).load(image).into(binding.imageView);
                }
            } else {
                Toast.makeText(AddActivity.this, "Please select an image", Toast.LENGTH_SHORT).show();
            }
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        FirebaseApp.initializeApp(AddActivity.this);
        storageReference = FirebaseStorage.getInstance().getReference();

        progress = findViewById(R.id.progress);
        selectImage = findViewById(R.id.selectImage);
        uploadImage = findViewById(R.id.uploadImage);

        selectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                activityResultLauncher.launch(intent);
            }
        });

        uploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadImage(image);
            }
        });

        db = FirebaseFirestore.getInstance();

        binding.saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // You don't need to call saveCourt here. It will be called in the uploadImage method
                // after successfully uploading the image.
            }
        });
    }

    private void uploadImage(Uri image) {
        StorageReference reference = storageReference.child("/image*" + UUID.randomUUID().toString());

        reference.putFile(image)
                .addOnSuccessListener(taskSnapshot -> {
                    // Get the download URL of the uploaded image
                    reference.getDownloadUrl().addOnSuccessListener(uri -> {
                        // Save the download URL along with other court details in Firestore
                        saveCourt(uri.toString());
                    }).addOnFailureListener(e -> {
                        Toast.makeText(AddActivity.this, "Failed to get download URL", Toast.LENGTH_SHORT).show();
                    });
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(AddActivity.this, "There was an error while uploading image", Toast.LENGTH_SHORT).show();
                })
                .addOnProgressListener(snapshot -> {
                    progress.setMax(Math.toIntExact(snapshot.getTotalByteCount()));
                    progress.setProgress(Math.toIntExact(snapshot.getBytesTransferred()));
                });
    }

    private void saveCourt(String imageURL) {
        String courtNumber = binding.addCourtNo.getText().toString();
        String courtName = binding.addCourtName.getText().toString();
        String courtDetails = binding.addCourtDetails.getText().toString();
        String courtPrice = binding.addCourtPrice.getText().toString();

        if (courtNumber.isEmpty()) {
            binding.addCourtNo.setError("Court number cannot be empty");
            return;
        }
        if (courtName.isEmpty()) {
            binding.addCourtName.setError("Court name cannot be empty");
            return;
        }
        if (courtDetails.isEmpty()) {
            binding.addCourtDetails.setError("Court details cannot be empty");
            return;
        }
        if (courtPrice.isEmpty()) {
            binding.addCourtPrice.setError("Court price cannot be empty");
            return;
        }

        Court newCourt = new Court(courtNumber, courtName, courtDetails, courtPrice, imageURL);

        db.collection("courts")
                .add(newCourt)
                .addOnSuccessListener(documentReference -> {
                    showToast("Added new court success!");
                    startActivity(new Intent(getApplicationContext(), AdminMainActivity.class));
                    finish();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(getApplicationContext(), "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }

    private void showToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    public void showBackBtn(View view) {
        Intent intent = new Intent(AddActivity.this, AdminMainActivity.class);
        startActivity(intent);
    }
}
