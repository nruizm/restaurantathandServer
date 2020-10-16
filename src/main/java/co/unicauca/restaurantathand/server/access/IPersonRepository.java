package co.unicauca.restaurantathand.server.access;

import co.unicauca.restaurantathand.commons.domain.Person;

/**
 *
 * @author Mannuel Fernando Granoble
 *         Michel Andrea Gutierrez Vallejo
 *         Ximena Quijano Gutierrez
 *         Nathalia Ruiz MensesZ
 */
public interface IPersonRepository {

    /**
     * Metodo encargado de encontrar un usuariot
     *
     * @param id Id del restaurante
     * @return Objeto restaurant
     */
    public Person findUser(String id);

    /**
     * Metodo para crear usuarios,
     *
     * @param user Objeto de tipo User.
     * @return cadena de texto con el valor de getUserName.
     */
    public String createUser(Person user);
}
