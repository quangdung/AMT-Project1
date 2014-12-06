package ch.heigvd.amt_project.services.fact;

import ch.heigvd.amt_project.model.Fact;
import java.util.*;
import javax.ejb.Singleton;
import javax.persistence.*;

/**
 *
 * @author
 */
@Singleton
public class FactManager implements FactManagerLocal {
    @PersistenceContext
    public EntityManager em;
    
    public FactManager() {
    }

    @Override
    public long create(Fact fact) {
        em.persist(fact);
        em.flush();
        
        return fact.getId();
    }
    
    @Override
    public List<Fact> read() {
        return em.createNamedQuery("findAllFacts")
                .getResultList();
    }
    
    @Override
    public Fact read(long factId) {
        return (Fact) em.createNamedQuery("findFactById")
                .setParameter("id", factId)
                .getSingleResult();
    }
    
    @Override
    public List<Fact> readByName(String factName) {
        return em.createNamedQuery("findFactsByName")
                .setParameter("name", factName)
                .getResultList();
    }

    @Override
    public List<Fact> readByType(String type) {
        return em.createNamedQuery("findFactsByType")
                .setParameter("type", type)
                .getResultList();
    }

    @Override
    public List<Fact> readByOrgId(long orgId) {
        return em.createNamedQuery("findFactsByOrganizationId")
                .setParameter("organizationId", orgId)
                .getResultList();
    }

    @Override
    public List<Fact> readPublicFact() {
        return em.createNamedQuery("findFactsByPublicFact")
                .getResultList();
    }

    @Override
    public Fact update(Fact fact) {
        Fact s = em.merge(fact);
        em.flush();
        
        return s;
    }

    @Override
    public void delete(Fact fact) {
        em.remove(fact);
        em.flush();
    }
}
