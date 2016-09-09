
package ieee.ws;

import ieee.dao.DaoLugares;
import ieee.model.DtoLugares;
import java.util.List;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

/**
 * Restful para poder invocar las actividades de la base de datos
 * @author coby
 */
@Stateless
@Path("/ws/lugares")
public class WebServicesLugares {
    
    @GET
    @Produces("application/json")
    public List<DtoLugares> getLugares(){
        DaoLugares dao = new DaoLugares(); 
        List<DtoLugares> lugares = dao.getLugares(); 
        return lugares;
    }
    
    @POST
    @Consumes("application/json")
    @Produces("text/html")
    public String addPersona(DtoLugares lugar)
    {
        DaoLugares dao = new DaoLugares(); 
        dao.addLugar(lugar);
        return "ok"; 
    }
    
}
