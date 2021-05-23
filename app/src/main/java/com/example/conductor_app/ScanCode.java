package com.example.conductor_app;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.conductor_app.model.Ticket;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class ScanCode extends AppCompatActivity {
    String Result;
    public TextView resulttextview;
    Button scanbutton, buttontoast, btn_fetch, verify_btn;
    TextView tv;
    FirebaseFirestore db;
    String isVerified;
    String createdAt;


    private static final String TAG = "ScanCode";

    private String resultString = "";
    int REQUEST_QR_RESULT = 1;

    private TextView route, passengerName, ticketStatus, fare;
    private LinearLayout ticketLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.barcode_scanner);

        verify_btn = (Button) findViewById(R.id.update_status);
        tv = (TextView) findViewById(R.id.routeTxt);

        db = FirebaseFirestore.getInstance();

        scanbutton = findViewById(R.id.buttonscan);

        route = findViewById(R.id.routeTxt);
        passengerName = findViewById(R.id.passengerName);
        ticketStatus = findViewById(R.id.ticketStatus);
        fare = findViewById(R.id.fare);
        ticketLayout = findViewById(R.id.ticketLayout);


//        btn_fetch.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (!TextUtils.isEmpty(resultString)) {
//                    fetchdata();
//                }
//
//            }
//        });

        verify_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                update_ticket_status();
            }
        });


        scanbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivityForResult(new Intent(getApplicationContext(), ScanCodeActivity.class), 1);
            }
        });


//        buttontoast.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Log.d(TAG, "onClick: " + resulttextview.getText().toString());
//                Toast.makeText(ScanCode.this, resulttextview.getText().toString(), Toast.LENGTH_SHORT).show();
//            }
//        });


    }

    private void update_ticket_status() {
        if (resultString != null) {
            db.collection("tickets").document(resultString)
                    .update("isVerified", true);
            Toast.makeText(getApplicationContext(), "Ticket Verify...Done!", Toast.LENGTH_LONG).show();
            openHomeActivity();
        }


    }

    public void openHomeActivity() {
        Intent intent = new Intent(this, HomePageActivity.class);
        startActivity(intent);
    }

    public void fetchdata() {
        final DocumentReference document = db.collection("tickets").document(resultString);

        document.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()) {


                    Ticket ticket = documentSnapshot.toObject(Ticket.class);
                    ticket.setVerified(documentSnapshot.getBoolean("isVerified"));
                    route.setText(ticket.getFrom() + " ➔ " + ticket.getTo());
                    fare.setText(ticket.getTicketFare() + " Rs.");


                    if (ticket.isVerified()) {
                        verify_btn.setVisibility(View.GONE);
                        ticketStatus.setText("Expired");
                        ticketStatus.setTextColor(Color.RED);
                    } else {
                        ticketStatus.setText("Active");
                        ticketStatus.setTextColor(Color.GREEN);
                        verify_btn.setVisibility(View.VISIBLE);
                    }
                    ticketLayout.setVisibility(View.VISIBLE);
                } else {
                    Toast.makeText(getApplicationContext(), "Didn't find any ticket", Toast.LENGTH_LONG).show();
                    ticketLayout.setVisibility(View.GONE);
                }


            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(), "Failed to fetch", Toast.LENGTH_LONG).show();
                    }
                });
    }


//    Activity Result

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 1:
                if (data != null) {
                    resultString = data.getStringExtra("resultString");
                    Log.d(TAG, "onActivityResult: " + resultString);
                    fetchdata();
                }
                break;

            default:
                break;
        }

    }
}
