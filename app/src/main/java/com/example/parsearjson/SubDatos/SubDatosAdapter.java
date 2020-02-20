package com.example.parsearjson.SubDatos;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.parsearjson.Datos;
import com.example.parsearjson.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class SubDatosAdapter extends RecyclerView.Adapter<SubDatosAdapter.PersonViewHolder> {
    private List<subDatos> items;

    public static class PersonViewHolder extends RecyclerView.ViewHolder {
        public CardView SubDatosCardView;
        public ImageView imagen;
        public TextView title;
        public TextView date;

        public PersonViewHolder(View v){
            super(v);
            SubDatosCardView=(CardView)v.findViewById(R.id.sub_datos_card);
            title=(TextView)v.findViewById(R.id.txtTitle);
            date=(TextView)v.findViewById(R.id.txtDate);
        }

    }
    @Override
    public PersonViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.sub_datos_card,viewGroup,false);
        return new PersonViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull PersonViewHolder viewHolder, final int i) {
            viewHolder.title.setText(""+items.get(i).getTitle());
            viewHolder.date.setText(""+items.get(i).getDate());
            viewHolder.SubDatosCardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Bundle bundle =new Bundle();
                    bundle.putString("curTitle",items.get(i).getTitle());
                    bundle.putString("curDate",items.get(i).getDate());
                    bundle.putString("curPdf",items.get(i).getPdf());
                    Intent iconIntent =new Intent(view.getContext(), BioActivity2.class);
                    iconIntent.putExtras(bundle);
                    view.getContext().startActivity(iconIntent);
                }
            });
    }

    @Override
    public int getItemCount() {
        return this.items.size();
    }
    public SubDatosAdapter(List<subDatos>items){
        this.items=items;
    }

    public List<subDatos> getItems(){
        return this.items;
    }

}
