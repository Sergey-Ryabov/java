package com.startup.registrationcrash.dao.jpa;

import com.startup.registrationcrash.dao.RuleDAO;
import com.startup.registrationcrash.model.Rule;
import javax.ejb.Stateless;

/**
 *
 * @author Сергей
 */
@Stateless
public class RuleDAOJpa extends GenericDAOJpa<Rule, Long>
        implements RuleDAO {

}
