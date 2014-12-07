/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.heigvd.amt_project.api;

import ch.heigvd.amt_project.dto.OrganizationDTO;
import ch.heigvd.amt_project.model.Organization;
import ch.heigvd.amt_project.services.organization.OrganizationManagerLocal;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.*;
import javax.ws.rs.*;

/**
 *
 * @author
 */
@Path("organizations")
@Stateless
public class OrganizationResource {
    @EJB
    OrganizationManagerLocal orgsManager;

    public OrganizationResource() {
    }

    @GET
    @Produces("application/json")
    public List<OrganizationDTO> getAllOrganizations()
    {
        List<Organization> orgs = orgsManager.read();
        List<OrganizationDTO> result = new ArrayList<>();
        
        for(Organization org : orgs) {
            result.add(toDTO(org));
        }
        
        return result;
    }
    
    @Path("/{id}")
    @GET
    @Produces("application/json")
    public OrganizationDTO getOrganizationDetails(@PathParam("id") long id)
    {
        Organization org = orgsManager.read(id);
        return toDTO(org);
    }
    
    @POST
    @Consumes("application/json")
    public long createOrganization(OrganizationDTO dto)
    {
        Organization newOrganization = new Organization();
  
        long idOrganization = orgsManager.create(toOrganization(dto, newOrganization));
        
        return idOrganization;
    }
    
    @Path("/{id}")
    @PUT
    @Consumes("application/json")
    public void updateOrganization(@PathParam("id") long id, OrganizationDTO dto)
    {
        Organization existing = orgsManager.read(id);
        orgsManager.update(toOrganization(dto, existing));
    }

    @Path("/{id}")
    @DELETE
    public void deleteOrganization(@PathParam("id") long id)
    {
        orgsManager.read(id);
        orgsManager.delete(orgsManager.read(id));
    }    
    
    private Organization toOrganization(OrganizationDTO orgDto, Organization org)
    {
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
