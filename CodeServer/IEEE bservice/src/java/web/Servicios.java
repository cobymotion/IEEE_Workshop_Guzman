package web;

import ieee.conexion.Conexion;
import ieee.conexion.Persona;
import java.util.List;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Stateless
@Path("/ws")
public class Servicios {
    
    @GET
    @Produces("application/json")
    public List<Persona> getPersonas()
    {
        Conexion con = new Conexion();
        List<Persona> registros = con.getRegistros();
        return registros;
    }      
    
    @POST
    @Consumes("application/json")
    @Produces("text/html")
    public String addPersona(Persona p)
    {
        Conexion con = new Conexion(); 
        con.agregarPersona(p);
        return "ok"; 
    }
    
}
