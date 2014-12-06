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
public class OrganizationManager implements OrganizationManagerLocal {

    @PersistenceContext
    public EntityManager em;
    
    public OrganizationManager() {}

    @Override
    public long create(Organization org) {
        em.persist(org);
        em.flush();
        
        return org.getId();
    }

    @Override
    public List<Organization> read() {
        return em.createNamedQuery("findAll")
                .getResultList();
    }

    @Override
    public Organization read(long id) {
        return (Organization) em.createNamedQuery("findById")
                .setParameter("id", id)
                .getSingleResult();
    }

    @Override
    public List<Organization> readOrgByName(String name) {
         return em.createNamedQuery("findByName")
                .setParameter("name", name)
                .getResultList();
    }

    @Override
    public Organization update(Organization org) {
        Organization o = em.merge(org);
        em.flush();
        
        return o;
    }

    @Override
    public void delete(Organization org) {
        em.remove(org);
        em.flush();
    }

}