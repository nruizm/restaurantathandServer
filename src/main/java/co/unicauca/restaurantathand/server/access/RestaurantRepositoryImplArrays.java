package co.unicauca.restaurantathand.server.access;



import co.unicauca.restaurantathand.commons.domain.Restaurant;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementación de IRestaurantRepository. Utilliza arreglos en memoria
 *
 * @author Mannuel Fernando Granoble
 *         Michel Andrea Gutierrez Vallejo
 *         Ximena Quijano Gutierrez
 *         Nathalia Ruiz Menses
 */
public class RestaurantRepositoryImplArrays  implements IRestaurantRepository{
    /**
     * Array de lista de Restaurantes
     */
    private static List<Restaurant> restaurants;
    
    public RestaurantRepositoryImplArrays(){
          if (restaurants == null){
            restaurants = new ArrayList();
        }
        
        if (restaurants.size() == 0){
            inicializar();
        }
    }
    
    public void inicializar(){
        restaurants.add(new Restaurant("100", "La abuela", "Popayan","3127242012", "abuelita@", "Popayan" , "mfgranoble" ));
        restaurants.add(new Restaurant("101","Deli", "Carrera 42 1-36", "812302", "Deli@", "Popayan" , "magutierre" ));
        restaurants.add(new Restaurant("102","La20", "Carrera 403 12-36", "3126472", "La20@","Popayan" , "nruiz" ));
    }
    /**
     * 
     *  Busca u restaurant en el arreglo
     *
     * @param prmNit Nit del restaurant
     * @return objeto restaurant
     */
    @Override
    public Restaurant findRestaurant(String prmNit) {
              for (Restaurant restaurant : restaurants) {
            if (restaurant.getAtrNitRest().equals(prmNit)) {
                return restaurant;
            }
        }
        return null;
    }

    @Override
    public String createRestaurant(Restaurant parRestauran) {
          restaurants.add(parRestauran);
        return parRestauran.getAtrNitRest();
    }

   
}
