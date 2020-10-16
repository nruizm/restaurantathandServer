/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.unicauca.restaurantathand.server.access;

import co.unicauca.restaurantathand.commons.domain.Menu;
import co.unicauca.restaurantathand.commons.infra.Utilities;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Repositorio de Menu en MySWL
 * 
 * @author Mannuel Fernando Granoble
 *         Michel Andrea Gutierrez Vallejo
 *         Ximena Quijano Gutierrez
 *         Nathalia Ruiz Menses
 */
public class MenuRepositoryImplMysql implements IMenuRepository{

    
    /**
     * Conección con Mysql
     */
    private Connection conn;
    
    public MenuRepositoryImplMysql (){}
    
    /**
     * Busca en la bd un Menu
     * @param prmMenu  id
     * @return objeto Menu, null si no lo encuentra
     */
    @Override
    public String createMenu(Menu prmMenu) {
        try {

            this.connect();
            String sql = "INSERT INTO menu(idmenu, namemenu, typemenu) VALUES (?,?,?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, prmMenu.getAtrIdMenu());
            pstmt.setString(2, prmMenu.getAtrNomMenu());
            pstmt.setString(3, prmMenu.getAtrTypeMenu());
            


            pstmt.executeUpdate();
            pstmt.close();
            this.disconnect();
        } catch (SQLException ex) {
            Logger.getLogger(MenuRepositoryImplMysql.class.getName()).log(Level.SEVERE, "Error al insertar el registro", ex);
        }
        return prmMenu.getAtrIdMenu();
    }

    
    @Override
    public Menu findMenu(String prmIdMenu) {
        Menu varMenu = null;

        this.connect();
        try {
            String sql = "SELECT * from customers where id=? ";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, prmIdMenu);
            ResultSet res = pstmt.executeQuery();
            if (res.next()) {
                varMenu = new Menu();
                varMenu.setAtrIdMenu(res.getString("idMenu"));
                //varMenu.set(res.getString("nitRest"));
                varMenu.setAtrNomMenu(res.getString("namemenu"));
                //varMenu.setAtrFecVimenu(Dateres.getString("fechaMenu"));
                varMenu.setAtrTypeMenu(res.getString("typemenu"));
                

            }
            pstmt.close();
            this.disconnect();
        } catch (SQLException ex) {
            Logger.getLogger(MenuRepositoryImplMysql.class.getName()).log(Level.SEVERE, "Error al consultar Customer de la base de datos", ex);
        }
        return varMenu;
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
            Logger.getLogger(MenuRepositoryImplMysql.class.getName()).log(Level.SEVERE, "Error al consultar Customer de la base de datos", ex);
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
            Logger.getLogger(MenuRepositoryImplMysql.class.getName()).log(Level.FINER, "Error al cerrar Connection", ex);
        }
    }
}
