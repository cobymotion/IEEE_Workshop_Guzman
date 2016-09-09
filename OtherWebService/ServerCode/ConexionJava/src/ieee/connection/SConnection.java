package ieee.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ResourceBundle;

/**
 * Esta clase permite traer una conexión activa dentro del servidor 
 * Siguiendo un singleton pattern que significa que una sola instancia 
 * se creara para darle soporte a las peticiones
 * @author coby
 */
public class SConnection {
    
    private static Connection con = null; 
    
    public static Connection getConnection() {
        try{
            if(con==null)
            {
                //Tipo listener que permite verificar si la aplicación se cierra
                //Tambien cierre la conexion
                Runtime.getRuntime().addShutdownHook(new DownConnection());
                
                ResourceBundle rb = ResourceBundle.getBundle("jdbc"); 
                String driver = rb.getString("driver"); 
                String url = rb.getString("url");
                String pwd = rb.getString("pwd"); 
                String usr = rb.getString("usr");
                
                Class.forName(driver); 
                con = DriverManager.getConnection(url,usr,pwd);
                System.out.println("Conexión Correcta");
            }
            return con; 
        }catch(Exception e )
        {
            e.printStackTrace();
            throw new RuntimeException("Error al cerrar la conexion",e); 
        }
    }
    static class DownConnection extends Thread {
        public void run() {
            try{
                Connection con = SConnection.getConnection(); 
                con.close();
            }catch(Exception e )
            {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }
    }
}
