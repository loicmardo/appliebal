package com.example.e_bal;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class InfoActivity extends AppCompatActivity {
    
    // Identification des notifs
    private static final String CHANNEL_ID = "channel_id01";
    public static final int NOTIFICATION_ID = 01;
    ImageView btnNotification ,btnDeepSleep ,btnlogout , ivEmail ,  ivInfo , IvBatterie;
    TextView EtValeur , EtHeure ;
    DatabaseReference db1 ;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        btnNotification =(ImageView) findViewById(R.id.BTNO1);
        btnDeepSleep =(ImageView) findViewById(R.id.btnDeepSleep);
        btnlogout=(ImageView)findViewById(R.id.logout) ;
        ivEmail= (ImageView)findViewById(R.id.IvEmail) ;
        ivInfo=(ImageView)findViewById(R.id.IvInfo) ;
        IvBatterie=(ImageView) findViewById(R.id.ivBatterie)  ;
        EtValeur=(TextView) findViewById(R.id.TvValeur) ;
        EtHeure=(TextView) findViewById(R.id.TvHeure) ;
        mAuth = FirebaseAuth.getInstance();
        db1=  FirebaseDatabase.getInstance("https://ebal-bad85-default-rtdb.europe-west1.firebasedatabase.app/").getReference().child("BAL");
        try {
            db1.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    // This method is called once with the initial value and again
                    // whenever data at this location is updated.
                    String valeur = dataSnapshot.child("valeur").getValue().toString();
                    String heure = dataSnapshot.child("heure").getValue().toString();
                    EtValeur.setText(valeur);
                    EtHeure.setText(heure);


                    if(valeur.equals("oui")){
                        showNotification();

                        Toast.makeText(getApplicationContext(), "Le facteur est passé !", Toast.LENGTH_SHORT).show();
                        EnvoyerLesValeurs() ;
                    }

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

        /*if(val == 1) {
            //showNotification();
            Toast.makeText(getApplicationContext(), "Nouvelle notification", Toast.LENGTH_SHORT).show();
        }*/

        btnNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(InfoActivity.this,HistoriqueActivity.class) ;
                startActivity(intent );

            }
        });

        btnDeepSleep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(InfoActivity.this,DeepSleepActivity.class) ;
                startActivity(intent );
            }
        });
        btnlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(InfoActivity.this,MainActivity.class) ;
                startActivity(intent );
            }
        });

        IvBatterie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(InfoActivity.this,BatterieActivity.class) ;
                startActivity(intent );
            }
        });
        ivInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(InfoActivity.this,MoreInformationActivity.class) ;
                startActivity(intent );
            }
        });
        ivEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(InfoActivity.this,EmailActivity.class) ;
                startActivity(intent );
            }
        });






    }

    public void EnvoyerLesValeurs() {
        Intent i = new Intent(getApplicationContext(),HistoriqueActivity.class) ;
        i.putExtra("valeur",EtValeur.getText().toString()) ;
        i.putExtra("heure",EtHeure.getText().toString()) ;
        startActivity(i);
    }


    private  void  showNotification()  {

        createNotificationchannel () ;

        //commencer une activité lorsquon appuie sur la notif

        Intent mainIntent = new Intent(this , HistoriqueActivity.class) ;
        mainIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent mainPIntent =PendingIntent.getActivities(this,0, new Intent[]{mainIntent},PendingIntent.FLAG_ONE_SHOT ) ;


        //CREATION NOtIFICATION

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID) ;
        builder.setSmallIcon(R.drawable.ic_baseline) ;
        builder.setContentTitle("Nouveau depot ");
        builder.setContentText("vous venez de recevoir un nouveau depot ") ;
        builder.setPriority(NotificationCompat.PRIORITY_DEFAULT) ;
        builder.setAutoCancel(true) ;
        builder.setContentIntent(mainPIntent) ;

        //Notification Manager
        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(this) ;
        notificationManagerCompat.notify(NOTIFICATION_ID,builder.build()) ;



    }

    private void createNotificationchannel() {
        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.O) {
            CharSequence  name = "My notification "  ;
            String description = "My notification  description "  ;
            int importance = NotificationManager.IMPORTANCE_DEFAULT ;

            NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID , name,importance) ;
            notificationChannel.setDescription(description);

            NotificationManager notificationManager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE) ;
            notificationManager.createNotificationChannel(notificationChannel);
        }
    }
}