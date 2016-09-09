package client.ieee.adhoc.webserviceclient;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by coby on 9/9/16.
 */
public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.VistaPersonas> {


    List<Persona> datos;

    public RecyclerAdapter(List<Persona> datos){
        this.datos = datos;
    }

    @Override
    public RecyclerAdapter.VistaPersonas onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_persona, parent, false);
        VistaPersonas vista = new VistaPersonas(v);
        return vista;
    }

    @Override
    public void onBindViewHolder(RecyclerAdapter.VistaPersonas holder, int position) {

        holder.nombre.setText(datos.get(position).getNombre());
        holder.domicilio.setText(datos.get(position).getDomicilio());
    }

    @Override
    public int getItemCount() {
        return datos.size();
    }

    public static class VistaPersonas extends RecyclerView.ViewHolder{

        TextView nombre;
        TextView domicilio;

        public VistaPersonas(View iteamView){
            super(iteamView);
            nombre = (TextView) iteamView.findViewById(R.id.nombrepersona);
            domicilio = (TextView) iteamView.findViewById(R.id.domiciliopersona);
        }



    }

}
