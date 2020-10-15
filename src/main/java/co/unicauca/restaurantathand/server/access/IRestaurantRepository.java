package co.unicauca.restaurantathand.server.access;

import co.unicauca.restaurantathand.commons.domain.Restaurant;

/**
 *@author Mannuel Fernando Granoble
 *         Michel Andrea Gutierrez Vallejo
 *         Ximena Quijano Gutierrez
 *         Nathalia Ruiz Menses
 */
public interface IRestaurantRepository {

    public String createRestaurant(Restaurant restaurant);

    public Restaurant findRestaurant(String prmNitRest);
    
    
}
