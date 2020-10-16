package co.unicauca.restaurantathand.server.domain.services;

import co.unicauca.restaurantathand.commons.domain.Menu;
import co.unicauca.restaurantathand.commons.infra.JsonError;
import co.unicauca.restaurantathand.commons.infra.Utilities;
import co.unicauca.restaurantathand.server.access.IMenuRepository;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.List;

/**
 *Servicio de Menu. Da acceso a la lógica de negocio
 * @author Mannuel Fernando Granoble
 *         Michel Andrea Gutierrez Vallejo
 *         Ximena Quijano Gutierrez
 *         Nathalia Ruiz Menses
 */
public class MenuService {
    /**
     * Repositorio de Menu
     */
    IMenuRepository repo;
    /**
     * Constructor parametrizado. Hace inyeccion de dependencias
     *
     * @param repo repositorio de tipo IDishRepository
     */
    public MenuService(IMenuRepository repo) {
        this.repo = repo;
    }
    /**
     * Buscar un Menu
     *
     * @param prmId id Menu
     * @return objeto tipo Menu
     */
    public Menu findDish(String prmId) {
        return repo.findMenu(prmId);
    }
    
    
    /**
     * Crea un nuevo Menu. Aplica validaciones de negocio
     *
     * @param prmMenu Menu
     * @return devuelve la Id del Menu creado
     */
    public String createDish(Menu prmMenu) {
        List<JsonError> errors = new ArrayList<>();
  
        // Validaciones y reglas de negocio
        if (prmMenu.getAtrNomMenu().isEmpty() || prmMenu.getAtrIdMenu().isEmpty() || prmMenu.getAtrTypeMenu().isEmpty()) {
           errors.add(new JsonError("400", "BAD_REQUEST","id, nombre, tipo son obligatorios. "));
        }
        
        if(!Utilities.isNumeric(prmMenu.getAtrIdMenu()+"")){
            errors.add(new JsonError("400", "BAD_REQUEST","Id s debe contener sólo dígitos "));
            
        }
        // Que no esté repetido
        
        Menu dishSearched = this.findDish(prmMenu.getAtrIdMenu());
        if (dishSearched != null){
            errors.add(new JsonError("400", "BAD_REQUEST","El id ya existe. "));
        }
        
       if (!errors.isEmpty()) {
            Gson gson = new Gson();
            String errorsJson = gson.toJson(errors);
            return errorsJson;
        }             
        return repo.createMenu(prmMenu);
    }
}
