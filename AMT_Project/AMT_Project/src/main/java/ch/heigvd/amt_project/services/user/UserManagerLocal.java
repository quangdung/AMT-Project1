/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.heigvd.amt_project.services.user;

import ch.heigvd.amt_project.model.User;
import java.util.List;

/**
 *
 * @author
 */
public interface UserManagerLocal {
    
    public User findUserById(long id);
    
    public List<User> findUserByParameters(long organizationId, boolean isMainContact);
    
    public List<User> findAllUsers();
    
    public long addUser(User user);
    
    public void updateUser(User user);
    
    public void deleteUser(long id);
    
}
