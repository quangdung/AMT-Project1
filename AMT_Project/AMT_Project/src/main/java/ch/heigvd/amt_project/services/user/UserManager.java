/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.heigvd.amt_project.services.user;

import ch.heigvd.amt_project.model.User;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import javax.ejb.Singleton;

/**
 *
 * @author
 */
@Singleton
public class UserManager implements UserManagerLocal {

    private Map<Long, User> users = new HashMap<>();

    public UserManager()
    {
        // ici on peut mettre des users en dur
    }
    
    @Override
    public List<User> findAllUsers()
    {
        return new ArrayList(users.values());
    }

    @Override
    public User findUserById(long id)
    {
        User user = users.get(id);
        return user;
    }
    
    @Override
    public List<User> findUserByFirstName(String firstName)
    {
        List<User> uList = new ArrayList<>();        
        Iterator it = users.entrySet().iterator();
        
        while (it.hasNext())
        {
            User currentU = (User) it.next();
            
            if(currentU.getFirstName().equals(firstName))
            {
                uList.add(currentU);
            }
        }
        
        return uList;
    }
    
    @Override
    public List<User> findUserByLastName(String LastName)
    {
        List<User> uList = new ArrayList<>();        
        Iterator it = users.entrySet().iterator();
        
        while (it.hasNext())
        {
            User currentU = (User) it.next();
            
            if(currentU.getLastName().equals(LastName))
            {
                uList.add(currentU);
            }
        }
        
        return uList;
    }

    @Override
    public List<User> findUserByEmail(String email)
    {
        List<User> uList = new ArrayList<>();        
        Iterator it = users.entrySet().iterator();
        
        while (it.hasNext())
        {
            User currentU = (User) it.next();
            
            if(currentU.getEmail().equals(email))
            {
                uList.add(currentU);
            }
        }
        
        return uList;
    }

    @Override
    public List<User> findUserByOrganizationId(long orgaId)
    {
        List<User> uList = new ArrayList<>();        
        Iterator it = users.entrySet().iterator();
        
        while (it.hasNext())
        {
            User currentU = (User) it.next();
            
            if(currentU.getOrganizationId() == orgaId)
            {
                uList.add(currentU);
            }
        }
        
        return uList;
    }

    @Override
    public List<User> findContactByOrganizationId(long orgaId)
    {
        List<User> uList = new ArrayList<>();        
        Iterator it = users.entrySet().iterator();
        
        while (it.hasNext())
        {
            User currentU = (User) it.next();
            
            if(currentU.isIsMainContact() && currentU.getOrganizationId() == orgaId)
            {
                uList.add(currentU);
            }
        }
        
        return uList;
    }
    
    @Override
    public long addUser(User user)
    {
        user.setId(users.size()+1);
        users.put(user.getId(), user);
        
        return user.getId();
    }

    @Override
    public void updateUser(User user)
    {
        users.put(user.getId(), user);
    }

    @Override
    public void deleteUser(long id)
    {
        users.remove(id);
    }
}
