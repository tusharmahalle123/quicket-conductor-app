package com.example.conductor_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class HomePageActivity extends AppCompatActivity {
    private Button scan_btn;
    private Button journey_start_btn;
    private Button logout_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        scan_btn = (Button) findViewById(R.id.scan_btn);
        journey_start_btn = (Button) findViewById(R.id.journey_start_btn);
        logout_btn = (Button)findViewById(R.id.logout_btn);




        journey_start_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity1();
            }
        });

        scan_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity2();
            }
        });

        logout_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity3();
            }
        });

    }



    public void openActivity1() {
        Intent intent = new Intent(this, Conductor_In.class);
        startActivity(intent);
    }

    public void openActivity2() {
        Intent intent = new Intent(this, ScanCode.class);
        startActivity(intent);
    }

    private void openActivity3() {

        Intent intent = new Intent(this, Logout.class);
        startActivity(intent);
    }
}



