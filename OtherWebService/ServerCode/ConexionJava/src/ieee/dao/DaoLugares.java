package ieee.dao;

import ieee.connection.SConnection;
import ieee.model.DtoLugares;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase que permite tener todas las tareas referentes a los datos
 * @author coby
 */
public class DaoLugares {
    /**
     * Metodo que permite agregar el lugar 
     * @param lugar objeto con los datos que se persistiran a la base de datos
     * @return retorna un verdadero si se grabo o retorna un falso si no se graba
     */
    public boolean addLugar(DtoLugares lugar)
    {
        Connection con = null; 
        PreparedStatement ps = null; 
        try {
            con = SConnection.getConnection(); 
            String sentencia = "INSERT INTO lugares VALUE(?,?,?,?)";
            ps = con.prepareCall(sentencia); 
            ps.setInt(1, lugar.getId());
            ps.setString(2, lugar.getNombre());
            ps.setDouble(3, lugar.getLatitud());
            ps.setDouble(4, lugar.getLongitud());
            ps.executeUpdate(); 
            System.out.println("Se grabo correctamente");
            return true; 
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e); 
        }
    }
    
    public List<DtoLugares> getLugares() 
    {
        Connection con = null; 
        PreparedStatement ps = null; 
        List<DtoLugares> lugares = new ArrayList<>(); 
        try {
            con = SConnection.getConnection(); 
            String sentencia = "SELECT * FROM lugares";
            ps = con.prepareCall(sentencia); 
            ResultSet result = ps.executeQuery(); 
            while(result.next())
            {
                DtoLugares lugar = new DtoLugares();
                lugar.setId(result.getInt(1));
                lugar.setNombre(result.getString(2));
                lugar.setLatitud(result.getDouble(3));
                lugar.setLongitud(result.getDouble(4));
                lugares.add(lugar);
            }
            System.out.println("Termino la consulta");
            return lugares;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e); 
        }
    }
    
}
