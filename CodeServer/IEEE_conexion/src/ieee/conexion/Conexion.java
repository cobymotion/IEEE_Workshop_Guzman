
package ieee.conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 *
 * @author coby
 */
public class Conexion {
    
    private static Connection con; 
    
    public static Connection getConexion()
    {
        if(con!=null)
            return con;
        ResourceBundle rb = ResourceBundle
                .getBundle("jdbc");
        String driver = rb.getString("driver");
        String url = rb.getString("url"); 
        String usr = rb.getString("usr");
        String pwd = rb.getString("pwd");
        try{
        Class.forName(driver); 
        con = DriverManager.
                getConnection(url,usr,pwd);
            System.out.println("Conecto corrcto");
            return con;
        }catch(Exception e){
            //sout
            System.out.println
                 ("NO pues no funciono");
            return null;
        }
    }
    public static void main(String[] args) {
        //new Conexion().agregarPersona
       // (new Persona(0,"Pedrio","Conocdi"));
        List<Persona> pers = new Conexion()
                .getRegistros();
        for (Persona per : pers) {
            System.out.println(per.getNombre());
        }
    }
    
    public List<Persona> getRegistros()
    {
        Connection con;
        PreparedStatement ps; 
        List<Persona> registros = 
                new ArrayList<>();
        try {
            String sentencia = 
                    "SELECT * FROM persona";
            con = Conexion.getConexion();
            ps = con.prepareCall(sentencia);
            ResultSet  datos = ps.executeQuery();
            while(datos.next())
            {
                int id = datos.getInt(1);
                String nombre= datos.getString(2);
                String dmicilio= datos.getString(3);
                Persona persona = new 
                            Persona();
                persona.setId(id);
                persona.setNombre(nombre);
                persona.setDomicilio(dmicilio);
                registros.add(persona);
            }
            return registros;
        } catch (Exception e) {
            System.out.println("No leyo");
            return null;
        }
    }
    
    public boolean agregarPersona(Persona p){
        Connection con; 
        PreparedStatement ps;
        try {
            con = Conexion.getConexion(); 
            String sql = "INSERT INTO persona VALUES(?,?,?)";
            ps = con.prepareCall(sql);
            ps.setInt(1, p.getId());
            ps.setString(2, p.getNombre());
            ps.setString(3,p.getDomicilio());
            ps.executeUpdate();
            System.out.println("Se grabo");
            return true;
        } catch (Exception e) {
            System.out.println("No pus fallo");
            return false;
        }
    }
            
}






