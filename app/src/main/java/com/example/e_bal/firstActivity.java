package com.example.e_bal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class firstActivity extends AppCompatActivity {
    Button btnSeco , btnSinsc ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);


        btnSeco= (Button) findViewById(R.id.btnSEco) ;
        btnSinsc=(Button) findViewById(R.id.btnSins) ;


        btnSeco.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(firstActivity.this,MainActivity.class) ;
                startActivity(intent);
            }
        });

        btnSinsc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(firstActivity.this,RegisterActivity.class) ;
                startActivity(intent);
            }
        });

    }
}