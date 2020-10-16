package co.unicauca.restaurantathand.server.domain.services;

import co.unicauca.restaurantathand.commons.domain.Person;
import co.unicauca.restaurantathand.commons.infra.JsonError;
import co.unicauca.restaurantathand.server.access.IPersonRepository;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Mannuel Fernando Granoble
 *         Michel Andrea Gutierrez Vallejo
 *         Ximena Quijano Gutierrez
 *         Nathalia Ruiz Menses
 */
public class PersonService {

    /**
     * repositorio de Usuarios, Objeto de tipo IUserRepository.
     */
    IPersonRepository repo;

    /**
     * Contructor por defecto.
     */
    public PersonService() {
    }

    /**
     * Constructor parametrizado. Hace inyeccion de dependencias
     *
     * @param repo repositorio de tipo ICustomerRepository
     */
    public PersonService(IPersonRepository repo) {
        this.repo = repo;
    }

    /**
     * Metodo encargado de buscar un Restaurante.
     *
     * @param parUserName
     * @return objeto tipo Restaurante
     */
    public Person findUser(String parUserName) {
        return repo.findUser(parUserName);
    }

    /**
     * Metodo encargado de crear un nuevo Usuario (User).
     *
     * @param parUserName objeto de tipo User.
     * @return llamado a funcion createUser() de la interfaz IUserRepository.
     */
    public String CreateUser(Person parUserName) {
        List<JsonError> errors = new ArrayList<>();
        if (parUserName.getAtrUserName().isEmpty() || parUserName.getAtrAddress().isEmpty() || parUserName.getAtrCity().isEmpty()
                || parUserName.getAtrIdentification().isEmpty() || parUserName.getAtrLastName().isEmpty()
                || parUserName.getAtrName().isEmpty() || parUserName.getAtrPassword().isEmpty()
                || parUserName.getAtrPhone().isEmpty() || parUserName.getAtrType().isEmpty()) {
            errors.add(new JsonError("400", "BAD_REQUEST", "LA INFORMACION X ES OBLIGATORIA "));
        }
        if (!errors.isEmpty()) {
            Gson gson = new Gson();
            String errorJson = gson.toJson(errors);
            return errorJson;
        }
        return repo.createUser(parUserName);
    }
}
