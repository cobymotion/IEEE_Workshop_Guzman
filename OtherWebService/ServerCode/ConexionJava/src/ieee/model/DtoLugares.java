package ieee.model;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Pojo, clase que permite crear un tipo de dato abstracto que permita 
 * almacenar los datos que vienen referentes a la base de datos
 * @author coby
 */

@XmlRootElement
public class DtoLugares implements Serializable{
    
    private int id; 
    private String nombre; 
    private double latitud; 
    private double longitud; 

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getLatitud() {
        return latitud;
    }

    public void setLatitud(double latitud) {
        this.latitud = latitud;
    }

    public double getLongitud() {
        return longitud;
    }

    public void setLongitud(double longitud) {
        this.longitud = longitud;
    }
    
    
    
}
