package co.unicauca.restaurantathand.server.access;

import co.unicauca.restaurantathand.commons.domain.Person;
import co.unicauca.restaurantathand.commons.infra.Utilities;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Mannuel Fernando Granoble
 *         Michel Andrea Gutierrez Vallejo
 *         Ximena Quijano Gutierrez
 *         Nathalia Ruiz Menses
 */
public class PersonRepositoryImplMysql implements IPersonRepository {

    /**
     * Objeto de tipo Connection.
     */
    private Connection conn;
    
    public PersonRepositoryImplMysql() {

    }

    /**
     * Metodo para crear usuarios, Este metodo se sobre escribe debido a que es
     * implementado de la interfaz IUserRepository.
     *
     * @param parUser Objeto de tipo User.
     * @return cadena de texto con el valor de getUserName.
     */
    @Override
    public String createUser(Person parUser) {
        try {
            this.connect();
            String sql = "INSERT INTO restaurantathand.person (usernameper,idper,nameper,lasnameper,passwordper,cityper,addressper,phoneper,typeper) VALUES (?,?,?,?,?,?,?,?,?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, parUser.getAtrUserName());
            pstmt.setString(2, parUser.getAtrIdentification());
            pstmt.setString(3, parUser.getAtrName());
            pstmt.setString(4, parUser.getAtrLastName());
            pstmt.setString(5, parUser.getAtrPassword());
            pstmt.setString(6, parUser.getAtrCity());
            pstmt.setString(7, parUser.getAtrAddress());
            pstmt.setString(8, parUser.getAtrPhone());
            pstmt.setString(9, parUser.getAtrType());

            pstmt.executeUpdate();
            pstmt.close();
            this.disconnect();
        } catch (SQLException ex) {
            Logger.getLogger(IPersonRepository.class.getName()).log(Level.SEVERE, "Error al insertar el registro", ex);
        }
        return parUser.getAtrUserName();
    }

    /**
     * Metodo encargado de realizar la conexion con la base de datos.
     *
     * @return 1, si la conexion fue exitosa, -1 si la conexion fue fallida.
     */
    public int connect() {
        try {
            Class.forName(Utilities.loadProperty("server.db.driver"));
            //crea una instancia de la controlador de la base de datos
            String url = Utilities.loadProperty("server.db.url");
            String username = Utilities.loadProperty("server.db.username");
            String pwd = Utilities.loadProperty("server.db.password");
            conn = DriverManager.getConnection(url, username, pwd);
            return 1;
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(PersonRepositoryImplMysql.class.getName()).log(Level.SEVERE, "Error al consultar Administrador en la base de datos", ex);
        }
        return -1;
    }

    /**
     * Metodo encargado de desconectar la aplicacion de la base de datos.
     */
    public void disconnect() {
        try {
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(RestaurantRepositoryImplMysql.class.getName()).log(Level.FINER, "Error al cerrar Connection", ex);
        }
    }

    /**
     * Metodo encargado de encontrar un usuario
     *
     * @param parUserName cadena de texto, servira para encontrar un usuario
     * especifico.
     * @return Objeto de tipo User.
     */
    @Override
    public Person findUser(String parUserName) {
        Person user = null;
        this.connect();
        try {
            String sql = "SELECT * from person where usernameper=? ";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, parUserName);
            ResultSet res = pstmt.executeQuery();

            if (res.next()) {
                user = new Person();
                user.setAtrUserName(res.getString("usernameper"));
                user.setAtrIdentification(res.getString("idper"));
                user.setAtrName(res.getString("nameper"));
                user.setAtrLastName(res.getString("lastnameper"));
                user.setAtrPassword(res.getString("passwordper"));
                user.setAtrCity(res.getString("cityper"));
                user.setAtrAddress(res.getString("addressper"));
                user.setAtrPhone(res.getString("phoneper"));
                user.setAtrType(res.getString("typeper"));
            }
            pstmt.close();
            this.disconnect();
        } catch (SQLException ex) {
            Logger.getLogger(RestaurantRepositoryImplMysql.class.getName()).log(Level.SEVERE, "Error al consultar el Uusuario de la base de datos", ex);
        }
        return user;
    }

}
