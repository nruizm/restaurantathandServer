package co.unicauca.restaurantathand.server.access;

import co.unicauca.restaurantathand.commons.domain.Menu;

/**
 * @author Mannuel Fernando Granoble
 *         Michel Andrea Gutierrez Vallejo
 *         Ximena Quijano Gutierrez
 *         Nathalia Ruiz Menses
 */

public interface IMenuRepository {
    /** Busca un Menu por su Id
     * @param prmMenu Id del Menu
     * @return  objeto de tipo Menu
     */
    public String createMenu(Menu prmMenu);

    public Menu findMenu(String prmIdMenu);
}
