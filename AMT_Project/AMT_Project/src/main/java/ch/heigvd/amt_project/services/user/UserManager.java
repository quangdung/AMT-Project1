package ch.heigvd.amt_project.services.user;

import ch.heigvd.amt_project.model.User;
import java.util.*;
import javax.ejb.Singleton;
import javax.persistence.*;

/**
 *
 * @author
 */
@Singleton
public class UserManager implements UserManagerLocal {

    @PersistenceContext
    public EntityManager em;
    
    public UserManager() {}

    @Override
    public long create(User user) {
        em.persist(user);
        em.flush();
        
        return user.getId();
    }

    @Override
    public List<User> read() {
        return em.createNamedQuery("findAll")
                .getResultList();
    }

    @Override
    public User read(long id) {
        return (User) em.createNamedQuery("findById")
                .setParameter("id", id)
                .getSingleResult();
    }

    @Override
    public List<User> readUserByFirstName(String firstName) {
         return em.createNamedQuery("findByFisrtName")
                .setParameter("firstName", firstName)
                .getResultList();
    }

    @Override
    public List<User> readUserByLastName(String lastName) {
         return em.createNamedQuery("findByLastName")
                .setParameter("lastName", lastName)
                .getResultList();
    }

    @Override
    public List<User> readUserByEmail(String email) {
        return em.createNamedQuery("findByEmail")
                .setParameter("email", email)
                .getResultList();
    }

    @Override
    public List<User> readUserByOrgId(long orgId) {
        return em.createNamedQuery("findByOrgId")
                .setParameter("orgId", orgId)
                .getResultList();
    }

    @Override
    public List<User> readContactByOrgId(long orgId) {
        return em.createNamedQuery("findContactByOrgId")
                .setParameter("orgId", orgId)
                .getResultList();
    }

    @Override
    public User update(User user) {
        User u = em.merge(user);
        em.flush();
        
        return u;
    }

    @Override
    public void delete(User user) {
        em.remove(user);
        em.flush();
    }

}
//
//    public UserManager()
//    {
//    }
//    
//    @Override
//    public List<User> findAllUsers()
//    {
//        return new ArrayList(users.values());
//    }
//
//    @Override
//    public User findUserById(long id)
//    {
//        User user = users.get(id);
//        return user;
//    }
//    
//    @Override
//    public List<User> findUserByFirstName(String firstName)
//    {
//        List<User> uList = new ArrayList<>();        
//        Iterator it = users.entrySet().iterator();
//        
//        while (it.hasNext())
//        {
//            User currentU = (User) it.next();
//            
//            if(currentU.getFirstName().equals(firstName))
//            {
//                uList.add(currentU);
//            }
//        }
//        
//        return uList;
//    }
//    
//    @Override
//    public List<User> findUserByLastName(String LastName)
//    {
//        List<User> uList = new ArrayList<>();        
//        Iterator it = users.entrySet().iterator();
//        
//        while (it.hasNext())
//        {
//            User currentU = (User) it.next();
//            
//            if(currentU.getLastName().equals(LastName))
//            {
//                uList.add(currentU);
//            }
//        }
//        
//        return uList;
//    }
//
//    @Override
//    public List<User> findUserByEmail(String email)
//    {
//        List<User> uList = new ArrayList<>();        
//        Iterator it = users.entrySet().iterator();
//        
//        while (it.hasNext())
//        {
//            User currentU = (User) it.next();
//            
//            if(currentU.getEmail().equals(email))
//            {
//                uList.add(currentU);
//            }
//        }
//        
//        return uList;
//    }
//
//    @Override
//    public List<User> findUserByOrganizationId(long orgaId)
//    {
//        List<User> uList = new ArrayList<>();        
//        Iterator it = users.entrySet().iterator();
//        
//        while (it.hasNext())
//        {
//            User currentU = (User) it.next();
//            
//            if(currentU.getOrganizationId() == orgaId)
//            {
//                uList.add(currentU);
//            }
//        }
//        
//        return uList;
//    }
//
//    @Override
//    public List<User> findContactByOrganizationId(long orgaId)
//    {
//        List<User> uList = new ArrayList<>();        
//        Iterator it = users.entrySet().iterator();
//        
//        while (it.hasNext())
//        {
//            User currentU = (User) it.next();
//            
//            if(currentU.isIsMainContact() && currentU.getOrganizationId() == orgaId)
//            {
//                uList.add(currentU);
//            }
//        }
//        
//        return uList;
//    }
//    
//    @Override
//    public long addUser(User user)
//    {
//        user.setId(users.size()+1);
//        users.put(user.getId(), user);
//        
//        return user.getId();
//    }
//
//    @Override
//    public void updateUser(User user)
//    {
//        users.put(user.getId(), user);
//    }
//
//    @Override
//    public void deleteUser(long id)
//    {
//        users.remove(id);
//    }
//}
