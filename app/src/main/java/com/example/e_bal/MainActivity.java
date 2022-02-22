package com.example.e_bal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    private EditText Username ;
    private EditText Password ;
    private Button Login ;
    private TextView Register ;
    private  TextView Info ;
    private FirebaseAuth mAuth;
    private ProgressBar pgb ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();


        Username=(EditText) findViewById(R.id.etNomUtilisateur) ;
        pgb=(ProgressBar)findViewById(R.id.Probar)  ;
        Password=(EditText) findViewById(R.id.etMot2passe) ;
        Login=(Button) findViewById(R.id.btnSeConnecter) ;
        Register=(TextView) findViewById(R.id.tvSinscrire) ;
       // Info =(TextView) findViewById(R.id.tvInfos) ;


        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,RegisterActivity.class) ;
                startActivity(intent );

            }
        });
        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                userlogin() ;
            }
        });






    }

    private void userlogin() {
        String mail = Username.getText().toString().trim();
        String motdepasse = Password.getText().toString().trim();

        if (mail.isEmpty())
        {
            Username.setError("Veillez saisir votre adresse mail!");
            Username.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(mail).matches())
        {
            Username.setError("Veillez saisir  une adresse  valide !");
            Username.requestFocus();
            return;
        }

        if (motdepasse.isEmpty())
        {
            Password.setError("Veillez saisir  un mot de passe svp !");
            Password.requestFocus();
            return;
        }
        if (motdepasse.length() < 6)
        {
            Password.setError("Veillez saisir un mot de passe de plus de 6 caracyères !");
            Password.requestFocus();
        }

        pgb.setVisibility(View.VISIBLE);

        mAuth.signInWithEmailAndPassword(mail,motdepasse).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    pgb.setVisibility(View.VISIBLE);
                    Intent intent = new Intent(MainActivity.this,InfoActivity.class) ;
                    startActivity(intent );

                }
                else {
                    Toast.makeText(MainActivity.this, "Echec , réessayer ", Toast.LENGTH_LONG).show();
                    pgb.setVisibility(View.INVISIBLE);
                }
            }
        });
    }
}