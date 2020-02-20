package com.example.parsearjson;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import WebServices.Asynchtask;
import WebServices.WebService;

public class MainActivity extends AppCompatActivity implements Asynchtask {

    private List<Datos> items=new ArrayList<>();
    private RecyclerView recycle;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager lManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Map<String, String> datos = new HashMap<String, String>();
        WebService ws= new WebService("http://revistas.uteq.edu.ec/ws/getrevistas.php", datos,
                MainActivity.this, MainActivity.this  );
        ws.execute();
    }

    @Override
    public void processFinish(String result) throws JSONException {
        JSONObject jresults = new JSONObject(result);
        JSONArray jarray = jresults.getJSONArray("issues");
        for (int i = 0; i < jarray.length(); i++) {
            JSONObject c = jarray.getJSONObject(i);
            String portada = c.getString("portada");
            String number = c.getString("number");
            String volume = c.getString("volume");
            items.add(new Datos(portada,number,volume));
        }
        recycle = (RecyclerView)findViewById(R.id.reciclador);
        recycle.setHasFixedSize(true);

        lManager=new LinearLayoutManager(this);
        recycle.setLayoutManager(lManager);

        adapter= new DatosAdapter(items);
        recycle.setAdapter(adapter);

    }
}
