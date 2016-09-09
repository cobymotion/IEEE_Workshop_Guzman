package client.ieee.adhoc.webserviceclient;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;


public class FragmentConsulta extends Fragment {


    RecyclerView rv;

    public FragmentConsulta() {
        // Required empty public constructor
    }

    public static FragmentConsulta newInstance() {
        FragmentConsulta fragment = new FragmentConsulta();
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View viewroot = inflater.inflate(R.layout.fragment_consulta, container, false);
        rv = (RecyclerView) viewroot.findViewById(R.id.listaUsuarios);

        ConsumoWebService ws = new ConsumoWebService();
        List<Persona> datos = ws.traerDatosWS();

        rv.setAdapter(new RecyclerAdapter(datos));
        rv.hasFixedSize();
        LinearLayoutManager llm = new LinearLayoutManager(this.getContext());
        rv.setLayoutManager(llm);

        return viewroot;
    }

}
