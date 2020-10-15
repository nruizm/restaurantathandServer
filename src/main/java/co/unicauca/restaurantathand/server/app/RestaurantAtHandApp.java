package co.unicauca.restaurantathand.server.app;

import co.unicauca.restaurantathand.server.infra.RestaurantServerSocket;

/**
 *Aplicación principal que lanza el servidor en un hilo
 *@author Mannuel Fernando Granoble
 *         Michel Andrea Gutierrez Vallejo
 *         Ximena Quijano Gutierrez
 *         Nathalia Ruiz Menses
 */
public class RestaurantAtHandApp {
     public static void main(String args[]){
        RestaurantServerSocket server = new RestaurantServerSocket();
        server.start();
    }
}
