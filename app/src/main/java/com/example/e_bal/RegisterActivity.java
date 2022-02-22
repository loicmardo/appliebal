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
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {

    private EditText RNom , RPrenom , REmail ,RMotdepasse ;
    private Button RCreerCompte ;
    private TextView RConnecter ;
    private FirebaseAuth mAuth;
    private ProgressBar pbr ;


    public RegisterActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mAuth = FirebaseAuth.getInstance();

        RNom=(EditText) findViewById(R.id.etRNom) ;
        pbr=(ProgressBar)findViewById(R.id.prob) ;
        REmail=(EditText) findViewById(R.id.etREmail) ;
        RMotdepasse=(EditText) findViewById(R.id.etRMotDePasse) ;
        RPrenom=(EditText) findViewById(R.id.etPrenom) ;
        RCreerCompte=(Button) findViewById(R.id.btnRCompte) ;
        RConnecter=(TextView) findViewById(R.id.tvRConnecter) ;


        RConnecter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(RegisterActivity.this,MainActivity.class) ;
                startActivity(intent);
            }
        });

        RCreerCompte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //  Intent intent= new Intent(RegisterActivity.this,HistoriqueActivity.class) ;
                registerUser();

            }});

        }

    private void registerUser() {
        String Nom = RNom.getText().toString().trim();
        String Prenom = RPrenom.getText().toString().trim();
        String email = REmail.getText().toString().trim();
        String Motdepasse = RMotdepasse.getText().toString().trim();

        if (Nom.isEmpty())
        {
            RNom.setError("Vous devez mettre votre Nom !");
            RNom.requestFocus();
            return;
        }

        if (Prenom.isEmpty())
        {
            RPrenom.setError("Vous devez mettre votre Prénom !");
            RPrenom.requestFocus();
            return;
        }
        if (email.isEmpty())
        {
            REmail.setError("Vous devez mettre votre E-mail !");
            REmail.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {
            REmail.setError("Vous devez mettre une adresse mail valide !");
            REmail.requestFocus();
            return;
        }
        if (Motdepasse.isEmpty())
        {
            RMotdepasse.setError("Veillez mettre un mot de passe svp !");
            RMotdepasse.requestFocus();
            return;
        }
        if (Motdepasse.length() < 6)
        {
            RMotdepasse.setError("Veillez mettre un mot de passe de plus de 6 caracyères !");
            RMotdepasse.requestFocus();
        }


        pbr.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(email,Motdepasse)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            User user = new User(Nom, Prenom,email) ;
                            FirebaseDatabase.getInstance().getReference("Utilisateur")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if ((task.isSuccessful())){
                                        Toast.makeText(RegisterActivity.this, "Utilsateur créé avec succes !", Toast.LENGTH_LONG).show();
                                        pbr.setVisibility(View.VISIBLE);
                                        Intent intent = new Intent(RegisterActivity.this,MainActivity.class) ;
                                        startActivity(intent );


                                    }
                                    else {
                                        Toast.makeText(RegisterActivity.this, "Echec , réessayer ", Toast.LENGTH_LONG).show();
                                        pbr.setVisibility(View.INVISIBLE);
                                    }
                                }
                            });

                        }else{

                                Toast.makeText(RegisterActivity.this, "Echec , réessayer ", Toast.LENGTH_LONG).show();
                                pbr.setVisibility(View.INVISIBLE);
                        }
                    }
                });
    }
}