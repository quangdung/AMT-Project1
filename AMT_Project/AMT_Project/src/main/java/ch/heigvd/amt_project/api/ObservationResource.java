/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.heigvd.amt_project.api;

import ch.heigvd.amt_project.dto.ObservationDTO;
import ch.heigvd.amt_project.model.Observation;
import ch.heigvd.amt_project.services.fact.FactDAOLocal;
import ch.heigvd.amt_project.services.fact.FactTiedToDateDAOLocal;
import ch.heigvd.amt_project.services.fact.FactTiedToSensorDAOLocal;
import ch.heigvd.amt_project.services.observation.ObservationDAOLocal;
import ch.heigvd.amt_project.services.observation.ObservationFlowProcessor;
import ch.heigvd.amt_project.services.observation.ObservationFlowProcessorLocal;
import ch.heigvd.amt_project.services.sensor.SensorDAOLocal;
import java.util.logging.Logger;
import javax.ejb.*;
import javax.ws.rs.*;
import javax.ws.rs.core.*;

/**
 *
 * @author
 */
@Path("observations")
@Stateless
public class ObservationResource {

    @EJB
    ObservationDAOLocal obsDAO;

    @EJB
    FactDAOLocal factsDAO;

    @EJB
    FactTiedToSensorDAOLocal factsTiedToSensorDAO;

    @EJB
    FactTiedToDateDAOLocal factsTiedToDateDAO;

    @EJB
    SensorDAOLocal sensorsDAO;

    @EJB
    ObservationFlowProcessorLocal obsFlowProcessor;

    @Context
    private UriInfo context;
    
    private static final Logger LOG = Logger.getLogger(ObservationFlowProcessor.class.getName());

    public ObservationResource() {
    }

    @POST
    @Consumes("application/json")
    public ObservationDTO createObservation(ObservationDTO dto) {
        Observation newObservation = new Observation();

        long idObservation = obsDAO.create(toObservation(dto, newObservation));

        try {
            obsFlowProcessor.UpdateFactBySensor(dto);

            obsFlowProcessor.UpdateFactByDate(dto);
        }
        catch(Exception e) {
            LOG.info("\nProblem adding observation\n");
            throw e;
        }

        return toDTO(obsDAO.read(idObservation));
    }

    @Path("/{id}")
    @PUT
    @Consumes("application/json")
    public Response updateObservation(@PathParam("id") long id, ObservationDTO dto) {
        Observation existing = obsDAO.read(id);
        obsDAO.update(toObservation(dto, existing));
        return Response.status(Response.Status.OK).entity("PUT").build();
    }

    @Path("/{id}")
    @DELETE
    public Response deleteObservation(@PathParam("id") long id) {
        obsDAO.read(id);
        obsDAO.delete(obsDAO.read(id));
        return Response.status(Response.Status.OK).entity("DELETED").build();
    }

    private Observation toObservation(ObservationDTO obsDto, Observation obs) {
        obs.setName(obsDto.getName());
        obs.setObsValue(obsDto.getObsValue());
        obs.setCreationDate(obsDto.getCreationDate());
        obs.setSensor(sensorsDAO.read(obsDto.getSensorId()));

        return obs;
    }

    private ObservationDTO toDTO(Observation obs) {
        ObservationDTO dto = new ObservationDTO();
        dto.setId(obs.getId());
        dto.setName(obs.getName());
        dto.setObsValue(obs.getObsValue());
        dto.setCreationDate(obs.getCreationDate());
        dto.setSensorId(obs.getSensor().getId());

        return dto;
    }

}
