/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.heigvd.amt_project.api;

import ch.heigvd.amt_project.dto.ObservationDTO;
import ch.heigvd.amt_project.model.Fact;
import ch.heigvd.amt_project.model.Observation;
import ch.heigvd.amt_project.model.Sensor;
import ch.heigvd.amt_project.services.fact.FactManagerLocal;
import ch.heigvd.amt_project.services.observation.ObservationManagerLocal;
import ch.heigvd.amt_project.services.sensor.SensorManagerLocal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.*;
import javax.ws.rs.*;

/**
 *
 * @author
 */
@Path("observations")
@Stateless
public class ObservationResource {

    @EJB
    ObservationManagerLocal obsManager;

    @EJB
    FactManagerLocal factsManager;

    @EJB
    SensorManagerLocal sensorsManager;

    public ObservationResource() {
    }
    
    DateFormat df = new SimpleDateFormat("yyyy-MM-d");

    @GET
    @Produces("application/json")
    public List<ObservationDTO> getAllObservations(
            @QueryParam("date") String dateString) {
        List<ObservationDTO> result = new ArrayList<>();

        if (dateString == null) {
            List<Observation> obss = obsManager.read();

            for (Observation obs : obss) {
                result.add(toDTO(obs));
            }
        }
        else if (dateString != null)
        {
            Date date = null;
            
            try {
                date = df.parse(dateString);
            }
            catch (ParseException ex) {
                Logger.getLogger(ObservationResource.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            List<Observation> obsDate = obsManager.readByCreationDate(date);
            
            for (Observation obs : obsDate) {
                if (df.format(obs.getCreationDate()).equals(df.format(date))) {
                    result.add(toDTO(obs));
                }
            }
        }

        return result;
    }

    @Path("/{id}")
    @GET
    @Produces("application/json")
    public ObservationDTO getObservationDetails(@PathParam("id") long id) {
        Observation obs = obsManager.read(id);
        return toDTO(obs);
    }


    @POST
    @Consumes("application/json")
    public long createObservation(ObservationDTO dto) {
        Observation newObservation = new Observation();

        long idObservation = obsManager.create(toObservation(dto, newObservation));

        long sensorId = dto.getSensorId();
        Sensor sensor = sensorsManager.read(sensorId);

        List<Fact> facts = factsManager.read();
//        Fact newFact = null;
        long factId = 0L;

        for (Fact f : facts) {
            if (f.getSensorId() == sensorId) {
                f.setNbObs(f.getNbObs() + 1);
                factId = f.getId();
            }
        }

        if (factId == 0L) {
            Fact newFact = new Fact("fact_" + sensor.getName(),
                                    sensor.getType(),
                                    sensor.getDescription(),
                                    sensor.getOrganizationId(),
                                    true, 1, sensorId);

            factId = factsManager.create(newFact);
        }

        return idObservation;
    }

    @Path("/{id}")
    @PUT
    @Consumes("application/json")
    public void updateObservation(@PathParam("id") long id, ObservationDTO dto) {
        Observation existing = obsManager.read(id);
        obsManager.update(toObservation(dto, existing));
    }

    @Path("/{id}")
    @DELETE
    public void deleteObservation(@PathParam("id") long id) {
        obsManager.read(id);
        obsManager.delete(obsManager.read(id));
    }

    private Observation toObservation(ObservationDTO obsDto, Observation obs) {
        obs.setId(obsDto.getId());
        obs.setName(obsDto.getName());
        obs.setObsValue(obsDto.getObsValue());
        obs.setCreationDate(obsDto.getCreationDate());
        obs.setSensorId(obsDto.getSensorId());

        return obs;
    }

    private ObservationDTO toDTO(Observation obs) {
        ObservationDTO dto = new ObservationDTO();
        dto.setId(obs.getId());
        dto.setName(obs.getName());
        dto.setObsValue(obs.getObsValue());
        dto.setCreationDate(obs.getCreationDate());
        dto.setSensorId(obs.getSensorId());

        return dto;
    }

}
