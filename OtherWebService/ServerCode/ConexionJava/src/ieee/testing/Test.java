
package ieee.testing;

import ieee.dao.DaoLugares;
import ieee.model.DtoLugares;
import java.util.List;

/**
 * Clase para hacer la prueba con la conexion y las operaciones que requerimos
 * @author coby
 */
public class Test {
    
    public static void main(String[] args) {
        //Connection con = SConnection.getConnection();
        
        DtoLugares lugar = new DtoLugares();
        /*lugar.setId(0);
        lugar.setNombre("Piramide de Guiza");
        lugar.setAltitud(29.9766817);
        lugar.setLongitud(31.1293557);
        DaoLugares dao = new DaoLugares(); 
        dao.addLugar(lugar);*/
        
        DaoLugares dao = new DaoLugares(); 
        List<DtoLugares> lugares= dao.getLugares(); 
        
        for (DtoLugares lugare : lugares) {
            System.out.println(lugare.getId());
            System.out.println(lugare.getNombre());
            System.out.println(lugare.getLatitud());
            System.out.println(lugare.getLongitud());
        }
    }
    
}
