/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.unicauca.restaurantathand.server.access;

import co.unicauca.restaurantathand.commons.domain.Restaurant;
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
public class RestaurantRepositoryImplMysql implements IRestaurantRepository {

    /**
     * Coneccion con Mysql
     */
    public Connection conn;

        public RestaurantRepositoryImplMysql() {

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
            Logger.getLogger(RestaurantRepositoryImplMysql.class.getName()).log(Level.SEVERE, "Error al consultar Restaurante de la base de datos", ex);
        }
        return -1;
    }
    /**
     * 
     * @param prmRestaurant
     * @return 
     */
   @Override
    public String createRestaurant(Restaurant prmRestaurant) {
        try {
             this.connect();
            String sql ="INSERT INTO restaurantathand.restaurant (nitrest,usernameper,namerest,addressrest, emailrest, cityrest, phonerest)values(?,?,?,?,?,?,?);";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, prmRestaurant.getAtrNitRest());
            pstmt.setString(2, prmRestaurant.getAtrAdmiRest());
            pstmt.setString(3, prmRestaurant.getAtrNameRest());
            pstmt.setString(4, prmRestaurant.getAtrAddressRest());
            pstmt.setString(5, prmRestaurant.getAtrEmailRest());
            pstmt.setString(6, prmRestaurant.getAtrCityRest());
            pstmt.setString(7, prmRestaurant.getAtrMobileRest());

            pstmt.executeUpdate();
            pstmt.close();
            this.disconnect();
        } catch (SQLException ex) {
            Logger.getLogger(RestaurantRepositoryImplMysql.class.getName()).log(Level.SEVERE, "Error al insertar el registro", ex);
        }
        return prmRestaurant.getAtrNitRest();
    }

    
    /**
     * Busca en la bd un restaurante
     * @param prmNit Nit 
     * @return objeto Restaurant, null si no lo encuentra
     */
    @Override
    public Restaurant findRestaurant(String prmNit) {
        Restaurant restaurant = null;
        try {
            this.connect();
            String sql = "SELECT * from Restaurante where nit=? ";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, prmNit ); 
            ResultSet res = pstmt.executeQuery();

            if (res.next()) {
                restaurant = new Restaurant();
                restaurant.setAtrNitRest(res.getString("nit"));
                //restaurant.setAdminId(Integer.parseInt(res.getString("AdminId")));
                restaurant.setAtrNameRest(res.getString("nombre"));
                restaurant.setAtrAddressRest(res.getString("address"));
                restaurant.setAtrMobileRest(res.getString("mobile"));
                restaurant.setAtrEmailRest(res.getString("email"));
                //restaurant.setResCiudad(res.getString("ResCiudad"));
                //restaurant.setResTematicaComida(res.getString("ResTematicaComida"));
            }
            pstmt.close();
            this.disconnect();
        } catch (SQLException ex) {
            Logger.getLogger(RestaurantRepositoryImplMysql.class.getName()).log(Level.SEVERE, "Error al consultar el restaurante de la base de datos", ex);
        }
        return restaurant;
    }

 
    private void disconnect() {
        try {
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(RestaurantRepositoryImplMysql.class.getName()).log(Level.FINER, "Error al cerrar Connection", ex);
        }
    }

   
}
