package com.example.e_bal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class BatterieActivity extends AppCompatActivity {
    // Identification des notifs
    private static final String CHANNEL_ID = "channel_id02";
    public static final int NOTIFICATION_ID = 02;

    TextView tvNiveauBatterie , tvIndication  ;
    DatabaseReference db4 ;
    ImageView warning , back1 ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_batterie);
        db4=  FirebaseDatabase.getInstance("https://ebal-bad85-default-rtdb.europe-west1.firebasedatabase.app/").getReference().child("BAL1");

        tvNiveauBatterie=(TextView) findViewById(R.id.tvBatterie) ;
        tvIndication=(TextView) findViewById(R.id.tvIndication)  ;
        warning =(ImageView) findViewById(R.id.ivWarning)  ;
        back1=(ImageView)findViewById(R.id.ivBackColis)  ;

        back1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BatterieActivity.this,InfoActivity.class) ;
                startActivity(intent);
            }
        });

        AfficheNiveauBatterie() ;

    }

    private void AfficheNiveauBatterie() {
        try {
            db4.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    // This method is called once with the initial value and again
                    // whenever data at this location is updated.
                    String valeur1 = dataSnapshot.child("NIVEAUBATTERIE").getValue().toString();
                    //String valeur1 = dataSnapshot.getValue(String.class);

                    tvNiveauBatterie.setText(valeur1);

                    if(Integer.parseInt(valeur1) <= 10 ){

                        tvIndication.setText("NIVEAU DE BATTERIE CRITIQUE ! ");
                        Toast.makeText(getApplicationContext(), "Batterie !", Toast.LENGTH_SHORT).show();
                        AfficherNotif() ;
                        warning.setVisibility(View.VISIBLE);
                        tvIndication.setVisibility(View.VISIBLE);

                    }

                    if(Integer.parseInt(valeur1) > 10 )
                    {

                    tvIndication.setText("");

                    warning.setVisibility(View.INVISIBLE);
                    tvIndication.setVisibility(View.INVISIBLE);


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
    }

    private void AfficherNotif() {

        createNotificationchannel1 () ;

        //commencer une activité lorsquon appuie sur la notif

        Intent mainIntent = new Intent(this , BatterieActivity.class) ;
        mainIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent mainPIntent =PendingIntent.getActivities(this,0, new Intent[]{mainIntent},PendingIntent.FLAG_ONE_SHOT ) ;


        //CREATION NOtIFICATION

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID) ;
        builder.setSmallIcon(R.drawable.ic_baseline) ;
        builder.setContentTitle("Niveau de batterie bas  ");
        builder.setContentText("Niveau Batterie <10%  ") ;
        builder.setPriority(NotificationCompat.PRIORITY_DEFAULT) ;
        builder.setAutoCancel(true) ;
        builder.setContentIntent(mainPIntent) ;

        //Notification Manager
        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(this) ;
        notificationManagerCompat.notify(NOTIFICATION_ID,builder.build()) ;



    }

    private void createNotificationchannel1() {
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