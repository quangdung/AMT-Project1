/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.heigvd.amt_project.api;

import ch.heigvd.amt_project.dto.FactDTO;
import ch.heigvd.amt_project.dto.FactTiedToDateDTO;
import ch.heigvd.amt_project.dto.FactTiedToSensorDTO;
import ch.heigvd.amt_project.model.Fact;
import ch.heigvd.amt_project.model.FactTiedToDate;
import ch.heigvd.amt_project.model.FactTiedToSensor;
import ch.heigvd.amt_project.services.fact.FactManagerLocal;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.*;
import javax.ws.rs.*;
import javax.ws.rs.core.*;

/**
 *
 * @author
 */
@Path("facts")
@Stateless
public class FactResource {

    @EJB
    FactManagerLocal factsManager;

    @Context
    private UriInfo context;

    public FactResource() {
    }

    @GET
    @Produces("application/json")
    public List<FactDTO> getAllFacts(
            @QueryParam("type") String type,
            @QueryParam("sensorId") long sensorId,
            @QueryParam("orgId") long orgId) {

        MultivaluedMap<String, String> mapAllParam = context.getQueryParameters();

        List<Fact> facts = factsManager.read();
        List<FactDTO> result = new ArrayList<>();

        if (mapAllParam.isEmpty()) {
            for (Fact fact : facts) {
                switch (fact.getType()) {
                    case "sensor":
                        result.add(toSensorDTO((FactTiedToSensor)fact));
                        break;
                    case "date":
                        result.add(toDateDTO((FactTiedToDate)fact));
                        break;
                    default:
                        result.add(toDTO(fact));
                        break;
                }
                
            }
        }
        else if (mapAllParam.containsKey("orgId")) {
            List<Fact> factsByOrgId = factsManager.readByOrgId(orgId);

            for (Fact fact : factsByOrgId) {
                System.out.println(fact.getId() + " - " + fact.getOrganization().getId());
                result.add(toDTO(fact));
            }
        }

        return result;
    }

    @Path("/{id}")
    @GET
    @Produces("application/json")
    public FactDTO getFactDetails(@PathParam("id") long id) {
        Fact fact = factsManager.read(id);
        return toDTO(fact);
    }

    @Path("/{id}")
    @PUT
    @Consumes("application/json")
    public void updateFact(@PathParam("id") long id, FactDTO dto) {
        Fact existing = factsManager.read(id);
        factsManager.update(toFact(dto, existing));
    }

    @Path("/{id}")
    @DELETE
    public void deleteFact(@PathParam("id") long id) {
        factsManager.delete(id);
    }

    private Fact toFact(FactDTO factDto, Fact fact) {
        fact.setId(factDto.getId());
        fact.setOrganization(factDto.getOrganization());
        fact.setVisible(factDto.isVisible());

        return fact;
    }

    private FactDTO toDTO(Fact fact) {
        FactDTO dto = new FactDTO();
        dto.setId(fact.getId());
        dto.setOrganizationId(fact.getOrganization());
        dto.setType(fact.getType());
        dto.setVisible(fact.isVisible());

        return dto;
    }

    private FactTiedToSensorDTO toSensorDTO(FactTiedToSensor fact) {
        FactTiedToSensorDTO dto = new FactTiedToSensorDTO();
        dto.setId(fact.getId());
        dto.setOrganizationId(fact.getOrganization());
        dto.setType(fact.getType());
        dto.setVisible(fact.isVisible());
        dto.setSensor(fact.getSensor());
        dto.setTotNbObs(fact.getNbObs());

        return dto;
    }

    private FactTiedToDateDTO toDateDTO(FactTiedToDate fact) {
        FactTiedToDateDTO dto = new FactTiedToDateDTO();
        dto.setId(fact.getId());
        dto.setOrganizationId(fact.getOrganization());
        dto.setType(fact.getType());
        dto.setVisible(fact.isVisible());
        dto.setSensor(fact.getSensor());
        dto.setTotNbObs(fact.getNbObs());
        dto.setDate(fact.getDate());
        dto.setAvVal(fact.getAvVal());
        dto.setMaxVal(fact.getMaxVal());
        dto.setMinVal(fact.getMinVal());
        dto.setNbVal(fact.getNbVal());
        dto.setSumVal(fact.getSumVal());

        return dto;
    }

}
