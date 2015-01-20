package ch.heigvd.amt_project.services.organization;

import ch.heigvd.amt_project.model.Organization;
import java.util.*;
import javax.ejb.Singleton;
import javax.persistence.*;

/**
 *
 * @author
 */
@Singleton
public class OrganizationDAO implements OrganizationDAOLocal {

    @PersistenceContext
    public EntityManager em;
    
    public OrganizationDAO() {}

    @Override
    public long create(Organization org) {
        em.persist(org);
        em.flush();
        
        return org.getId();
    }

    @Override
    public List<Organization> read() {
        return em.createNamedQuery("findAllOrganizations")
                .getResultList();
    }

    @Override
    public Organization read(long id) {
        return (Organization) em.createNamedQuery("findOrganizationById")
                .setParameter("id", id)
                .getSingleResult();
    }

    @Override
    public void update(Organization org) {
        em.merge(org);
    }

    @Override
    public void delete(Organization org) {
        em.remove(org);
    }
}