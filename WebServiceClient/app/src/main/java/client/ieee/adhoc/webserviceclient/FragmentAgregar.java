package client.ieee.adhoc.webserviceclient;


import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class FragmentAgregar extends Fragment {


    View viewRoot;
    TextView editNombre;
    TextView editDomicilio;

    FloatingActionButton button;

    public FragmentAgregar() {
        // Required empty public constructor
    }

    public static FragmentAgregar newInstance() {
        FragmentAgregar fragment = new FragmentAgregar();
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        viewRoot = inflater.inflate(R.layout.fragment_agregar, container, false);

        editNombre = (TextView) viewRoot.findViewById(R.id.editNombre);
        editDomicilio = (TextView) viewRoot.findViewById(R.id.editDomicilio);

        button = (FloatingActionButton) viewRoot.findViewById(R.id.btnAgregar);

        button.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Persona persona = new Persona();
                persona.setId(0);
                persona.setNombre(editNombre.getText().toString());
                persona.setDomicilio(editDomicilio.getText().toString());
                if(persona.getNombre().isEmpty())
                    return false;
                ConsumoWebService con = new ConsumoWebService();
                boolean ok = con.addPersonaWs(persona);
                String mensaje = "";
                if(ok)
                    mensaje = "Se grabo correctamente";
                else
                    mensaje = "Hubo un error al guardar";

                Snackbar.make(viewRoot,mensaje,Snackbar.LENGTH_LONG).show();
                editNombre.setText("");
                editDomicilio.setText("");
                return false;
            }
        });


        return viewRoot;
    }

}
