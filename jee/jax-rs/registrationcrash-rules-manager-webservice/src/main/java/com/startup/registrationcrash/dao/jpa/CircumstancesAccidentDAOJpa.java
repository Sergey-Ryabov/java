package com.startup.registrationcrash.dao.jpa;

import com.startup.registrationcrash.dao.CircumstancesAccidentDAO;
import com.startup.registrationcrash.model.CircumstancesAccident;
import java.io.Serializable;
import javax.ejb.Stateless;

/**
 *
 * @author Сергей
 */
@Stateless
public class CircumstancesAccidentDAOJpa
        extends GenericDAOJpa<CircumstancesAccident, Long>
        implements CircumstancesAccidentDAO {

}
