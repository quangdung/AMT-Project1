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
    public List<Fact> readByOrgId(long orgId) {
        return em.createNamedQuery("findFactsByOrganizationId")
                .setParameter("orgId", orgId)
                .getResultList();
    }

    @Override
    public List<Fact> readByVisibility(boolean visible) {
        return em.createNamedQuery("findFactsByVisibility")
                .setParameter("visible", visible)
                .getResultList();
    }

    @Override
    public Fact update(Fact fact) {
        Fact s = em.merge(fact);
        em.flush();
        
        return s;
    }

    @Override
    public void delete(long factId) {
        Fact f = read(factId);
        if (f != null)
        {
            em.remove(f);
            em.flush();
        }
    }
}
