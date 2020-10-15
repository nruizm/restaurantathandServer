/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.unicauca.restaurantathand.server.domain.services;

import co.unicauca.restaurantathand.commons.domain.Restaurant;
import co.unicauca.restaurantathand.commons.infra.JsonError;
import co.unicauca.restaurantathand.commons.infra.Utilities;
import co.unicauca.restaurantathand.server.access.IRestaurantRepository;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.List;

/**
 * Servicio de restaurantes. Da acceso a la lógica de negocio
 * @author Mannuel Fernando Granoble
 *         Michel Andrea Gutierrez Vallejo
 *         Ximena Quijano Gutierrez
 *         Nathalia Ruiz Menses
 */
public class RestaurantService {
    
    /**
     * Repositorio de restaurant
     */
    IRestaurantRepository repo;

    /**
     * Constructor parametrizado. Hace inyeccion de dependencias
     *@param repo repositorio de tipo ICustomerRepository
     *
     */
    public RestaurantService(IRestaurantRepository repo) {
        this.repo = repo;
    }

    /**
     * Buscar un Restaurant
     *
     * @param prmNitRest Nit
     * @return objeto tipo Restaurant
     * 
     */
    public Restaurant findRestaurant(String prmNitRest) {
        return repo.findRestaurant(prmNitRest);
    }

    /**
     * Crea un nuevo restauramt .Aplica validaciones de negocio
     *
     * @param restaurant
     * @param prmRestaurant Restaurant
     * @return devuelve el nit del Restauran creado
     */
    public String createRestaurant(Restaurant prmRestaurant) {
        List<JsonError> errors = new ArrayList<>();
  
        // Validaciones y reglas de negocio
        if (prmRestaurant.getAtrNitRest().isEmpty() || prmRestaurant.getAtrNameRest().isEmpty()
                || prmRestaurant.getAtrAddressRest().isEmpty() || prmRestaurant.getAtrMobileRest().isEmpty()
                || prmRestaurant.getAtrEmailRest().isEmpty()) {
           errors.add(new JsonError("400", "BAD_REQUEST","nit, nombre, apellidos, email, telefono, address son obligatorios. "));
        }
        
        if (!prmRestaurant.getAtrEmailRest().contains("@")){
            errors.add(new JsonError("400", "BAD_REQUEST","Email debe tener una @. "));
        }
           
        if(!Utilities.isNumeric(prmRestaurant.getAtrMobileRest())){
            errors.add(new JsonError("400", "BAD_REQUEST","Teléfono móvil debe contener sólo dígitos "));
            
        }
        if(!Utilities.isNumeric(prmRestaurant.getAtrNitRest())){
            errors.add(new JsonError("400", "BAD_REQUEST","El Nit debe contener sólo dígitos "));
            
        }
        // Que no esté repetido
        
        Restaurant restaurantSearched = this.findRestaurant(prmRestaurant.getAtrNitRest());
        if (restaurantSearched != null){
            errors.add(new JsonError("400", "BAD_REQUEST","El Nit ya existe. "));
        }
        
       if (!errors.isEmpty()) {
            Gson gson = new Gson();
            String errorsJson = gson.toJson(errors);
            return errorsJson;
        }             
        return repo.createRestaurant(prmRestaurant);
    }


    
}
