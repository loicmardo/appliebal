package com.example.e_bal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class EmailActivity extends AppCompatActivity {

    EditText email1 ;
    Button OK ;
    TextView AfficheEmail ;
    ImageView back2 ;

    DatabaseReference db3 ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email);

        back2=(ImageView)findViewById(R.id.ivBackBatterie) ;
        email1=(EditText) findViewById(R.id.etEmail) ;
        OK=(Button) findViewById(R.id.btnSend) ;
        AfficheEmail=(TextView) findViewById(R.id.tvEmail) ;
        db3=  FirebaseDatabase.getInstance("https://ebal-bad85-default-rtdb.europe-west1.firebasedatabase.app/").getReference().child("MAIL");
        Afficherlemail() ;
        OK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EnvoyerFirebase() ;

            }
        });


        back2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EmailActivity.this,InfoActivity.class) ;
                startActivity(intent);
            }
        });
    }

    private void Afficherlemail() {

        try {
            db3.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    // This method is called once with the initial value and again
                    // whenever data at this location is updated.
                    //String valeur = dataSnapshot.child("MAIL").getValue().toString();
                    String valeur = dataSnapshot.getValue(String.class);

                    AfficheEmail.setText(valeur);



                }

                @Override
                public void onCancelled(DatabaseError error) {
                    // Failed to read value

                }
            });
        }
        catch(Exception e)
        {

        }


    }


    private void EnvoyerFirebase() {




        String SEmail = email1.getText().toString().trim();

        if (SEmail.isEmpty())
        {
            email1.setError("Vous devez mettre votre E-mail !");
            email1.requestFocus();
            return;
        }
        /*if (!Patterns.EMAIL_ADDRESS.matcher(SEmail).matches())
        {
            email1.setError("Vous devez mettre une adresse mail valide !");
            email1.requestFocus();
            return;
        }:*/

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("MAIL");
        myRef.setValue(SEmail);

        email1.setText("");


    }
}