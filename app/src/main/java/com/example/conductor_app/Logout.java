package com.example.conductor_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class Logout extends AppCompatActivity {
    private Button logout_btn;
    private EditText bus_number;


    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.logout_activity);

        logout_btn = (Button)findViewById(R.id.logout_btn);
        bus_number = (EditText)findViewById(R.id.bus_number);


        db = FirebaseFirestore.getInstance();

        logout_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clear_data();
            }
        });
    }

    private void clear_data() {
        String busNumber =  bus_number.getText().toString();
        db.collection("Running_Buses").document(busNumber).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Intent intent = new Intent(Logout.this, HomePageActivity.class);
                startActivity(intent);
                finishAffinity();
            }
        });
    }
}
