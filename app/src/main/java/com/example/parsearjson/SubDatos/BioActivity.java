package com.example.parsearjson.SubDatos;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.parsearjson.MainActivity;
import com.example.parsearjson.R;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import WebServices.Asynchtask;
import WebServices.WebService;

public class BioActivity extends AppCompatActivity implements Asynchtask {
    private List<subDatos> items=new ArrayList<>();
    private RecyclerView recycle;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager lManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bio);

        String number=(getIntent().getExtras().getString("curNumber"));
        String volum=(getIntent().getExtras().getString("curVolum"));

        Map<String, String> datos = new HashMap<String, String>();
        WebService ws= new WebService("http://revistas.uteq.edu.ec/ws/getarticles.php?volumen="+volum+"&num="+number, datos,
                BioActivity.this, BioActivity.this  );
        ws.execute();

    }

    @Override
    public void processFinish(String result) throws JSONException {
        JSONObject jresults = new JSONObject(result);
        JSONArray jarray = jresults.getJSONArray("articles");
        for (int i = 0; i < jarray.length(); i++) {
            JSONObject c = jarray.getJSONObject(i);
            String title = c.getString("title");
            String date = c.getString("date_published");
            String pdf = c.getString("pdf");
            items.add(new subDatos(title,date,pdf));
        }
        recycle = (RecyclerView)findViewById(R.id.subReciclador);
        recycle.setHasFixedSize(true);

        lManager=new LinearLayoutManager(this);
        recycle.setLayoutManager(lManager);

        adapter= new SubDatosAdapter(items);
        recycle.setAdapter(adapter);
    }
}
