package com.example.parsearjson;

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

import com.example.parsearjson.SubDatos.BioActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

public class DatosAdapter extends RecyclerView.Adapter<DatosAdapter.PersonViewHolder> {
    private List<Datos> items;

    public static class PersonViewHolder extends RecyclerView.ViewHolder {
        public CardView DatosCardView;
        public ImageView imagen;
        public TextView volumen;
        public TextView number;

        public PersonViewHolder(View v){
            super(v);
            DatosCardView=(CardView)v.findViewById(R.id.datos_card);
            imagen = (ImageView)v.findViewById(R.id.txtImagen);
            volumen=(TextView)v.findViewById(R.id.txtVolumen);
            number=(TextView)v.findViewById(R.id.txtNumer);

        }

    }
    @Override
    public PersonViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.datos_card,viewGroup,false);
        return new PersonViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull PersonViewHolder viewHolder, final int i) {
            //viewHolder.imagen.setImageResource(items.get(i).getImagen());
            Picasso.with(viewHolder.imagen.getContext())
                .load(items.get(i).getPortada()).into(viewHolder.imagen);

            viewHolder.volumen.setText("volumen"+items.get(i).getVolume());
            viewHolder.number.setText("number: "+items.get(i).getNumber());
            viewHolder.DatosCardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Bundle bundle =new Bundle();
                    //bundle.putInt("curImagen",items.get(i).getImagen());

                    bundle.putString("curImagen",items.get(i).getPortada());
                    bundle.putString("curVolum",items.get(i).getVolume());
                    bundle.putString("curNumber",items.get(i).getNumber());
                    Intent iconIntent =new Intent(view.getContext(), BioActivity.class);
                    iconIntent.putExtras(bundle);
                    view.getContext().startActivity(iconIntent);
                }
            });

    }

    @Override
    public int getItemCount() {
        return this.items.size();
    }
    public DatosAdapter(List<Datos>items){
        this.items=items;
    }

    public List<Datos> getItems(){
        return this.items;
    }

}
