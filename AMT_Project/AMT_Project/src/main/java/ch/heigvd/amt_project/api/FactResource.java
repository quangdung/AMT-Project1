/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.heigvd.amt_project.api;

import ch.heigvd.amt_project.dto.FactDTO;
import ch.heigvd.amt_project.model.Fact;
import ch.heigvd.amt_project.services.fact.FactManagerLocal;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.*;
import javax.ws.rs.*;

/**
 *
 * @author
 */
@Path("facts")
@Stateless
public class FactResource {
    @EJB
    FactManagerLocal factsManager;

//    @Context
//    private UriInfo context;

    public FactResource() {
    }

    @GET
    @Produces("application/json")
    public List<FactDTO> getAllFacts()
    {
        List<Fact> facts = factsManager.read();
        List<FactDTO> result = new ArrayList<>();
        
        for(Fact fact : facts) {
            result.add(toDTO(fact));
        }
        
        return result;
    }
    
    @Path("/{id}")
    @GET
    @Produces("application/json")
    public FactDTO getFactDetails(@PathParam("id") long id)
    {
        Fact fact = factsManager.read(id);
        return toDTO(fact);
    }
    
    @POST
    @Consumes("application/json")
    public long createFact(FactDTO dto)
    {
        Fact newFact = new Fact();
  
        long idFact = factsManager.create(toFact(dto, newFact));

        return idFact;
    }
    
    @Path("/{id}")
    @PUT
    @Consumes("application/json")
    public void updateFact(@PathParam("id") long id, FactDTO dto)
    {
        Fact existing = factsManager.read(id);
        factsManager.update(toFact(dto, existing));
    }

    @Path("/{id}")
    @DELETE
    public void deleteFact(@PathParam("id") long id)
    {
        factsManager.read(id);
        factsManager.delete(factsManager.read(id));
    }    
    
    private Fact toFact(FactDTO factDto, Fact fact)
    {
        fact.setId(factDto.getId());
        fact.setName(factDto.getName());
        fact.setDescription(factDto.getDescription());
        fact.setType(factDto.getType());
        fact.setOrganizationId(factDto.getOrganizationId());
        fact.setPublicFact(factDto.isPublicFact());
        fact.setNbObs(factDto.getNbObs());
        fact.setSensorId(factDto.getSensorId());
        
        return fact;
    }
    
    private FactDTO toDTO(Fact fact) {
        FactDTO dto = new FactDTO();
        dto.setId(fact.getId());
        dto.setName(fact.getName());
        dto.setDescription(fact.getDescription());
        dto.setType(fact.getType());
        dto.setOrganizationId(fact.getOrganizationId());
        dto.setPublicFact(fact.isPublicFact());
        dto.setNbObs(fact.getNbObs());
        dto.setSensorId(fact.getSensorId());
        
        return dto;
    }
}
