/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.heigvd.amt_project.api;

import ch.heigvd.amt_project.dto.UserDTO;
import ch.heigvd.amt_project.model.User;
import ch.heigvd.amt_project.services.organization.OrganizationDAOLocal;
import ch.heigvd.amt_project.services.user.UseDAOLocal;
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
    UseDAOLocal usersDAO;
    
    @EJB
    OrganizationDAOLocal orgsDAO;

    @Context
    private UriInfo context;

    public UserResource() {
    }

    @GET
    @Produces("application/json")
    public List<UserDTO> getAllUsers(@QueryParam("orgId") long orgId) {
        MultivaluedMap<String, String> mapAllParam = context.getQueryParameters();

        List<User> users = usersDAO.read();
        List<UserDTO> result = new ArrayList<>();

        if (mapAllParam.isEmpty()) {
            for (User user : users) {
                result.add(toDTO(user));
            }
        }
        else if (mapAllParam.containsKey("orgId")) {
            List<User> usersByOrgId = usersDAO.readUserByOrgId(orgId);

            for (User user : usersByOrgId) {
                result.add(toDTO(user));
            }
        }

        return result;
    }

    @Path("/{id}")
    @GET
    @Produces("application/json")
    public UserDTO getUserDetails(@PathParam("id") long id) {

        User user = usersDAO.read(id);

        return toDTO(user);
    }

    @POST
    @Consumes("application/json")
    public UserDTO createUser(UserDTO dto) {
        User newUser = new User();

        long idUser = usersDAO.create(toUser(dto, newUser));

        return toDTO(usersDAO.read(idUser));
    }

    @Path("/{id}")
    @PUT
    @Consumes("application/json")
    public Response updateUser(@PathParam("id") long id, UserDTO dto) {
        User existing = usersDAO.read(id);
        usersDAO.update(toUser(dto, existing));
        return Response.status(Response.Status.OK).entity("PUT").build();
    }

    @Path("/{id}")
    @DELETE
    public Response deleteUser(@PathParam("id") long id) {
        usersDAO.read(id);
        usersDAO.delete(usersDAO.read(id));
        return Response.status(Response.Status.OK).entity("DELETED").build();
    }

    private User toUser(UserDTO userDto, User user) {
        user.setId(userDto.getId());
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        user.setOrganization(orgsDAO.read(userDto.getOrgId()));
        user.setMainContact(userDto.isMainContact());

        return user;
    }

    public static UserDTO toDTO(User user) {
        UserDTO dto = new UserDTO();

        dto.setId(user.getId());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setEmail(user.getEmail());
        dto.setOrgId(user.getOrganization().getId());
        dto.setMainContact(user.isMainContact());

        return dto;
    }
}
