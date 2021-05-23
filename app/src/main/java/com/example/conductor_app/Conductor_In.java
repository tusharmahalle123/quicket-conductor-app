package com.example.conductor_app;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class Conductor_In extends AppCompatActivity {

//    EditText name_edt,to_loc_edt,from_loc_edt,bus_no_edt;
//    Button start_btn;
//    FirebaseFirestore db;
//    private String name, to_loc, from_loc,bus_no;

    private EditText nameEdt, to_loc_Edt, bus_no_Edt, from_loc_Edt, set_location_Edt;

    // creating variable for button
    private Button start_btn;

    // creating a strings for storing
    // our values from edittext fields.
    private String name, tolocation, fromlocation, busnumber, set_location;

    FusedLocationProviderClient fusedLocationProviderClient;

    // creating a variable
    // for firebasefirestore.
    private FirebaseFirestore db;

    private static final String TAG = "Conductor_In";

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.conductor_journey);

        db = FirebaseFirestore.getInstance();

        //Location
        set_location_Edt = findViewById(R.id.set_location);

        //Initialize fusedLocationProviderClient
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        // initializing our edittext and buttons
        nameEdt = findViewById(R.id.name_edt);
        to_loc_Edt = findViewById(R.id.to_loc_edt);
        from_loc_Edt = findViewById(R.id.from_loc_edt);
        bus_no_Edt = findViewById(R.id.bus_no_edt);
        start_btn = findViewById(R.id.start_btn);

        // adding on click listener for button
        start_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //location
                if (ActivityCompat.checkSelfPermission(Conductor_In.this
                        , Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    //when permission granted
                    getLocation();
                } else {
                    //When permission denied
                    ActivityCompat.requestPermissions(Conductor_In.this
                            , new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
                }

            }
        });
    }


    private void addDataToFirestore(String set_location) {

        // getting data from edittext fields.
        name = nameEdt.getText().toString();
        tolocation = to_loc_Edt.getText().toString();
        fromlocation = from_loc_Edt.getText().toString();
        busnumber = bus_no_Edt.getText().toString();


        // validating the text fields if empty or not.
        if (TextUtils.isEmpty(name)) {
            nameEdt.setError("Please enter Name");
        } else if (TextUtils.isEmpty(tolocation)) {
            to_loc_Edt.setError("Please enter to location");
        } else if (TextUtils.isEmpty(fromlocation)) {
            from_loc_Edt.setError("Please enter from location");
        } else if (TextUtils.isEmpty(busnumber)) {
            bus_no_Edt.setError("Please enter bus number");
        } else {
            DocumentReference dbCourses = db.collection("Running_Buses").document(busnumber);
            // adding our data to our courses object class.
            Courses courses = new Courses(name, tolocation, fromlocation, busnumber, set_location);
            Log.d(TAG, "addDataToFirestore: set_location = " + set_location);
            dbCourses.set(courses).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Toast.makeText(Conductor_In.this, "Your Journey Started Successfully", Toast.LENGTH_LONG).show();
                    openHomeActivity();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(Conductor_In.this, "Fail to start \n" + e, Toast.LENGTH_SHORT).show();

                }
            });
        }


//        // below method is use to add data to Firebase Firestore.
//        dbCourses.add(courses).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
//            @Override
//            public void onSuccess(DocumentReference documentReference) {
//                // after the data addition is successful
//                // we are displaying a success toast message.
//                Toast.makeText(Conductor_In.this, "Your Journey Started Successfully", Toast.LENGTH_SHORT).show();
//                openHomeActivity();
//
//            }
//        }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//                // this method is called when the data addition process is failed.
//                // displaying a toast message when data addition is failed.
//                Toast.makeText(Conductor_In.this, "Fail to start \n" + e, Toast.LENGTH_SHORT).show();
//            }
//        });
//


    }

    public void openHomeActivity() {
        Intent intent = new Intent(this, HomePageActivity.class);
        startActivity(intent);
    }


    @SuppressLint("MissingPermission")

    private void getLocation() {
        fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
            @Override
            public void onComplete(@NonNull Task<Location> task) {
                //Initialize Location
                Location location = task.getResult();
                if (location != null) {

                    try {
                        //Initialize geoCoder
                        Geocoder geocoder = new Geocoder(Conductor_In.this,
                                Locale.getDefault());
                        //Initialize address List
                        List<Address> addresses = geocoder.getFromLocation(
                                location.getLatitude(), location.getLongitude(), 1
                        );

                        String addressLine = addresses.get(0).getAddressLine(0);
                        String[] split = addressLine.split(",");
                        //addressLine=split[2].trim();
                        addressLine = split[0].trim() + ", " + split[1].trim() + ", " + split[2].trim();

                        set_location_Edt.setText(addressLine);
                        addDataToFirestore(addressLine);
//                            if (split.length >= 3) {
//                                addressLine= split[1].trim() + ", " + split[2].trim();
//                            } else if (split.length == 2) {
//                                addressLine= split[1].trim();
//                            } else addressLine= split[0].trim();


                    } catch (IOException e) {
                        e.printStackTrace();
                    }
//                        .setText(Html.fromHtml(
//                                "<font color='#6200EE'><b>AddressSplit :</b><br></font>"
//                                        + addressLine
//
//                        ));
                }
            }
        });
    }

}
