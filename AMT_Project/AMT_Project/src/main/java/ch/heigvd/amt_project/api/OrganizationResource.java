/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.heigvd.amt_project.api;

import static ch.heigvd.amt_project.api.UserResource.toDTO;
import ch.heigvd.amt_project.dto.OrganizationDTO;
import ch.heigvd.amt_project.model.Organization;
import ch.heigvd.amt_project.services.organization.OrganizationManagerLocal;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.*;
import javax.ws.rs.*;

import ch.heigvd.amt_project.services.user.UserManagerLocal;
import ch.heigvd.amt_project.dto.UserDTO;
import ch.heigvd.amt_project.model.User;

/**
 *
 * @author
 */
@Path("organizations")
@Stateless
public class OrganizationResource {

    @EJB
    OrganizationManagerLocal orgsManager;

    @EJB
    UserManagerLocal usersManager;

    public OrganizationResource() {
    }

    @GET
    @Produces("application/json")
    public List<OrganizationDTO> getAllOrganizations() {
        List<Organization> orgs = orgsManager.read();
        List<OrganizationDTO> result = new ArrayList<>();

        for (Organization org : orgs) {
            result.add(toDTO(org));
        }

        return result;
    }

    @Path("/{id}")
    @GET
    @Produces("application/json")
    public OrganizationDTO getOrganizationDetails(@PathParam("id") long id) {
        Organization org = orgsManager.read(id);
        return toDTO(org);
    }

    @Path("/{id}/contact")
    @GET
    @Produces("application/json")
    public UserDTO getOrganizationContact(@PathParam("id") long id) {
        User user = usersManager.readContactByOrgId(id);

        return UserResource.toDTO(user);
    }

    @Path("/{id}/users")
    @GET
    @Produces("application/json")
    public List<UserDTO> getOrganizationUsers(@PathParam("id") long id) {
        List<UserDTO> result = new ArrayList<>();

        List<User> usersByOrgId = usersManager.readUserByOrgId(id);

        for (User user : usersByOrgId) {
            result.add(UserResource.toDTO(user));
        }

        return result;
    }

    @POST
    @Consumes("application/json")
    public OrganizationDTO createOrganization(OrganizationDTO dto) {
        Organization newOrganization = new Organization();

        long idOrganization = orgsManager.create(toOrganization(dto, newOrganization));

        return toDTO(orgsManager.read(idOrganization));
    }

    @Path("/{id}")
    @PUT
    @Consumes("application/json")
    public void updateOrganization(@PathParam("id") long id, OrganizationDTO dto) {
        Organization existing = orgsManager.read(id);
        orgsManager.update(toOrganization(dto, existing));
    }

    @Path("/{id}")
    @DELETE
    public void deleteOrganization(@PathParam("id") long id) {
        orgsManager.read(id);
        orgsManager.delete(orgsManager.read(id));
    }

    private Organization toOrganization(OrganizationDTO orgDto, Organization org) {
        org.setId(orgDto.getId());
        org.setName(orgDto.getName());

        return org;
    }

    private OrganizationDTO toDTO(Organization org) {
        OrganizationDTO dto = new OrganizationDTO();

        dto.setId(org.getId());
        dto.setName(org.getName());

        return dto;
    }
}
