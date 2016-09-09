package client.ieee.adhoc.webserviceclient;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by coby on 9/9/16.
 */
public class ConsumoWebService {



    public boolean addPersonaWs(Persona persona)
    {
        try {
            URL url =
                    new URL("http://192.168.1.69:8080/webservice/webresources/ws");

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");

            String input = "{\"id\":0,\"nombre\":\"" + persona.getNombre() + "\",\"domicilio\":\"" + persona.getDomicilio() + "\"}";


            OutputStream os = conn.getOutputStream();
            os.write(input.getBytes());
            os.flush();

            BufferedReader br = new BufferedReader(new InputStreamReader(
                    (conn.getInputStream())));

            String output;
            Log.w("Output Server","Revisando salida");
            while ((output = br.readLine()) != null) {
                Log.w("Salida",output);
            }
            conn.disconnect();
            return true;
        }catch(Exception e)
        {
            Log.e("Eroror","Hay un error");
            return false;
        }

    }

    public List<Persona> traerDatosWS() {
        try {
            URL url =
                    new URL("http://192.168.1.69:8080/webservice/webresources/ws");
            HttpURLConnection con =
                    (HttpURLConnection) url.openConnection();
            BufferedReader rd = new BufferedReader
                    (new InputStreamReader(con.getInputStream()));
            String line = "";
            String resultado ="";
            while ((line = rd.readLine()) != null) {
                resultado+=line;
            }
            rd.close();

            //resultado+="]";
            List<Persona> datos= procesaJSON(resultado);
            con.disconnect();
            return datos;
        } catch (Exception e) {
            Log.e("error", e.toString());
        }
        return null;

    }

    private List<Persona> procesaJSON(String resultado) {
        JSONArray jsonArray = null;
        List<Persona> registros= new ArrayList<>();
        try {
            jsonArray = new JSONArray(resultado);
        } catch (JSONException e) {
            Log.e("JSON", e.toString());
        }



        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                JSONObject jsonObject =
                        jsonArray.getJSONObject(i);
                Persona persona = new Persona();
                persona.setId(jsonObject.getInt("id"));
                persona.setNombre(jsonObject.getString("nombre"));
                persona.setDomicilio(jsonObject.getString("domicilio"));
                registros.add(persona);
            } catch (JSONException e) {
                Log.e("JSON", e.toString());
            }
        }

        return registros;
    }

}
