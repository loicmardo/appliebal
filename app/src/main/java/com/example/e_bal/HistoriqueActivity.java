package com.example.e_bal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.HashMap;

public class HistoriqueActivity extends AppCompatActivity {
    ListView listView ;
    String val , he ;
    HashMap<String,String> map ;
    Params p = new Params() ;
    ImageView back ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historique);

        back=(ImageView)findViewById(R.id.ivBack) ;

        listView=(ListView)findViewById(R.id.listview);


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HistoriqueActivity.this,InfoActivity.class);
                startActivity(intent);
            }
        });

        Bundle extras = getIntent().getExtras();
        if(extras!=null)
        {
            val =extras.getString("valeur") ;
            he =extras.getString("heure") ;
            map= new  HashMap<String,String>() ;
            map.put("valeur",val) ;
            map.put("heure",he) ;
            p.values.add(map) ;

        }





//Create Adapter
        SimpleAdapter adapter = new SimpleAdapter(HistoriqueActivity.this,p.values,R.layout.item,
                new String[]{"valeur","heure"},
                new int[]{R.id.tvValeur,R.id.tvHe}
        );
//assign adapter to listview
        listView.setAdapter(adapter);

         /*add listener to listview
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(HistoriqueActivity.this, "Vous venez de cliquer sur:"+i+" "+arrayList.get(i).toString(), Toast.LENGTH_SHORT).show();
            }
        });*/





    }
}