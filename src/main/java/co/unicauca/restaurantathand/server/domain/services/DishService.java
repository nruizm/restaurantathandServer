package co.unicauca.restaurantathand.server.domain.services;

import co.unicauca.restaurantathand.commons.domain.Dish;
import co.unicauca.restaurantathand.commons.infra.JsonError;
import co.unicauca.restaurantathand.commons.infra.Utilities;
import co.unicauca.restaurantathand.server.access.IDishRepository;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.List;

/**
 * Servicio de platos. Da acceso a la lógica de negocio
 * @author Mannuel Fernando Granoble
 *         Michel Andrea Gutierrez Vallejo
 *         Ximena Quijano Gutierrez
 *         Nathalia Ruiz Menses
 */
public class DishService {
    
    /**
     * Repositorio de Platos
     */
    IDishRepository repo;
    /**
     * Constructor parametrizado. Hace inyeccion de dependencias
     *
     * @param repo repositorio de tipo IDishRepository
     */
    public DishService(IDishRepository repo) {
        this.repo = repo;
    }
    /**
     * Buscar un Plato
     *
     * @param prmId id 
     * @return objeto tipo Plato
     */
    public Dish findDish(String prmId) {
        return repo.findDish(prmId);
    }
    
    
    /**
     * Crea un nuevo Plato. Aplica validaciones de negocio
     *
     * @param prmDish Plato
     * @return devuelve la Id del Plato creado
     */
    public String createDish(Dish prmDish) {
        List<JsonError> errors = new ArrayList<>();
  
        // Validaciones y reglas de negocio
        if (prmDish.getAtrIdDish().isEmpty() || prmDish.getAtrNameDish().isEmpty()
                || prmDish.getAtrDescriptionDish().isEmpty() || prmDish.getAtrTypeDish().isEmpty()) {
           errors.add(new JsonError("400", "BAD_REQUEST","id, nombre, descripcion tipo son obligatorios. "));
        }
        
        if(!Utilities.isNumeric(prmDish.getAtrPriceDish()+"")){
            errors.add(new JsonError("400", "BAD_REQUEST","Teléfono móvil debe contener sólo dígitos "));
            
        }
        // Que no esté repetido
        
        Dish dishSearched = this.findDish(prmDish.getAtrIdDish());
        if (dishSearched != null){
            errors.add(new JsonError("400", "BAD_REQUEST","La cédula ya existe. "));
        }
        
       if (!errors.isEmpty()) {
            Gson gson = new Gson();
            String errorsJson = gson.toJson(errors);
            return errorsJson;
        }             
        return repo.createDish(prmDish);
    }
}
