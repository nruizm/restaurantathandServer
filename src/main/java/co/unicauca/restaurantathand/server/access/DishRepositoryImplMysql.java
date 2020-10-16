package co.unicauca.restaurantathand.server.access;

import co.unicauca.restaurantathand.commons.domain.Dish;
import co.unicauca.restaurantathand.commons.infra.Utilities;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *  Repositorio de Plato en MySWL
 * 
 * @author Mannuel Fernando Granoble
 *         Michel Andrea Gutierrez Vallejo
 *         Ximena Quijano Gutierrez
 *         Nathalia Ruiz Menses
 */
public class DishRepositoryImplMysql implements IDishRepository{
    
    /**
     * Conección con Mysql
     */
    private Connection conn;
    
    public DishRepositoryImplMysql (){}
    
    /**
     * Busca en la bd un Plato
     * @param prmDish  id
     * @return objeto plato, null si no lo encuentra
     */
    @Override
    public String createDish(Dish prmDish) {
         try {

            this.connect();
            String sql = "INSERT INTO dish(iddish, namedish , descriptiondish, picedish,typedish) VALUES (?,?,?,?,?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, prmDish.getAtrIdDish());
            pstmt.setString(2, prmDish.getAtrNameDish());
            pstmt.setString(3, prmDish.getAtrDescriptionDish());
            pstmt.setString(4, prmDish.getAtrPriceDish() + "");
            pstmt.setString(5, prmDish.getAtrTypeDish()); 
            
            pstmt.executeUpdate();
            pstmt.close();
            this.disconnect();
        } catch (SQLException ex) {
            Logger.getLogger(DishRepositoryImplMysql.class.getName()).log(Level.SEVERE, "Error al insertar el registro", ex);
        }
        return prmDish.getAtrIdDish();

    }

    @Override
    public Dish findDish(String prmIdDish) {
        Dish varDish = null;

        this.connect();
        try {
            String sql = "SELECT * from customers where id=? ";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, prmIdDish);
            ResultSet res = pstmt.executeQuery();
            if (res.next()) {
                varDish = new Dish();
                varDish.setAtrIdDish(res.getString("iddish"));
                varDish.setAtrNameDish(res.getString("namedish"));
                varDish.setAtrDescriptionDish(res.getString("descriptiondish"));
                varDish.setAtrPriceDish( Double.parseDouble(res.getString("pricedish")) );
                varDish.setAtrTypeDish(res.getString("typedish"));
                

            }
            pstmt.close();
            this.disconnect();
        } catch (SQLException ex) {
            Logger.getLogger(DishRepositoryImplMysql.class.getName()).log(Level.SEVERE, "Error al consultar Customer de la base de datos", ex);
        }
        return varDish;
    }
    
    
    /**
     * Permite hacer la conexion con la base de datos
     *
     * @return
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
            Logger.getLogger(DishRepositoryImplMysql.class.getName()).log(Level.SEVERE, "Error al consultar Customer de la base de datos", ex);
        }
        return -1;
    }
    
    /**
     * Cierra la conexion con la base de datos
     *
     */
    public void disconnect() {
        try {
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(DishRepositoryImplMysql.class.getName()).log(Level.FINER, "Error al cerrar Connection", ex);
        }
    }
}
