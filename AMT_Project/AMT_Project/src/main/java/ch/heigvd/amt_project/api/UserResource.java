/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.heigvd.amt_project.api;

import ch.heigvd.amt_project.dto.UserDTO;
import ch.heigvd.amt_project.model.User;
import ch.heigvd.amt_project.services.user.UserManagerLocal;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.*;
import javax.ws.rs.*;
import javax.ws.rs.core.*;

/**
 *
 * @author
 */
@Path("users")
@Stateless
public class UserResource {
    @EJB
    UserManagerLocal usersManager;
    
    @Context
    private UriInfo context;

    public UserResource() {
    }

    @GET
    @Produces("application/json")
    public List<UserDTO> getAllUsers(@QueryParam("orgId") long orgId)
    {
        MultivaluedMap<String, String> mapAllParam = context.getQueryParameters();
        
        List<User> users = usersManager.read();
        List<UserDTO> result = new ArrayList<>();
        
        if (mapAllParam.isEmpty()) 
        {
            for(User user : users) {
                result.add(toDTO(user));
            }
        }
        else if (mapAllParam.containsKey("orgId")) {
            List<User> usersByOrgId = usersManager.readUserByOrgId(orgId);
            
           for (User sensor : usersByOrgId) {
               result.add(toDTO(sensor));
           } 
        }
        
        return result;
    }
    
    @Path("/{id}")
    @GET
    @Produces("application/json")
    public UserDTO getUserDetails(@PathParam("id") long id)
    {
        User user = usersManager.read(id);
        return toDTO(user);
    }
    
    @POST
    @Consumes("application/json")
    public long createUser(UserDTO dto)
    {
        User newUser = new User();
  
        long idUser = usersManager.create(toUser(dto, newUser));
        
        return idUser;
    }
    
    @Path("/{id}")
    @PUT
    @Consumes("application/json")
    public void updateUser(@PathParam("id") long id, UserDTO dto)
    {
        User existing = usersManager.read(id);
        usersManager.update(toUser(dto, existing));
    }

    @Path("/{id}")
    @DELETE
    public void deleteUser(@PathParam("id") long id)
    {
        usersManager.read(id);
        usersManager.delete(usersManager.read(id));
    }    
    
    private User toUser(UserDTO userDto, User user)
    {
        user.setId(userDto.getId());
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        user.setOrganization(userDto.getOrganization());
        user.setMainContact(userDto.isMainContact());
        
        return user;
    }
    
    private UserDTO toDTO(User user) {
        UserDTO dto = new UserDTO();
        
        dto.setId(user.getId());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setEmail(user.getEmail());
        dto.setPassword(user.getPassword());
        dto.setOrganization(user.getOrganization());
        dto.setMainContact(user.isMainContact());
        
        return dto;
    }
}
