package com.example.e_bal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DeepSleepActivity extends AppCompatActivity {
    Button btnReveil ;
    Button btnSommeil ;
    DatabaseReference db2 ;
    ImageView back3 ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deep_sleep);

        back3=(ImageView)findViewById(R.id.ivBackDeep) ;
        btnReveil=(Button) findViewById(R.id.btnReveil);
        btnSommeil=(Button) findViewById(R.id.btnVeille) ;
        db2=  FirebaseDatabase.getInstance("https://ebal-bad85-default-rtdb.europe-west1.firebasedatabase.app/").getReference().child("BAL");

        btnReveil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference("RESTART");
                myRef.setValue(1);

            }
        });

        btnSommeil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference("RESTART");
                myRef.setValue(0);

            }
        });


        back3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DeepSleepActivity.this,InfoActivity.class) ;
                startActivity(intent);
            }
        });
    }



}