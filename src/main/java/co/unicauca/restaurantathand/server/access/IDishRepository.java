package co.unicauca.restaurantathand.server.access;

import co.unicauca.restaurantathand.commons.domain.Dish;

/**
 *@author Mannuel Fernando Granoble
 *         Michel Andrea Gutierrez Vallejo
 *         Ximena Quijano Gutierrez
 *         Nathalia Ruiz Menses
 */
public interface IDishRepository {
     
    /** Busca un Plato por su Id
     * @param prmDish Id del Plato
     * @return  objeto de tipo Plato
     */
    public String createDish(Dish prmDish);

    public Dish findDish(String prmIdDish);
}
