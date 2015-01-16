/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.heigvd.amt_project.api;

import ch.heigvd.amt_project.dto.ObservationDTO;
import ch.heigvd.amt_project.model.Fact;
import ch.heigvd.amt_project.model.FactTiedToDate;
import ch.heigvd.amt_project.model.FactTiedToSensor;
import ch.heigvd.amt_project.model.FactType;
import ch.heigvd.amt_project.model.Observation;
import ch.heigvd.amt_project.model.Sensor;
import ch.heigvd.amt_project.services.fact.FactTiedToDateManagerLocal;
import ch.heigvd.amt_project.services.fact.FactTiedToSensorManagerLocal;
import ch.heigvd.amt_project.services.observation.ObservationManagerLocal;
import ch.heigvd.amt_project.services.sensor.SensorManagerLocal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.*;
import javax.ws.rs.*;
import java.sql.Date;
import static java.sql.Types.NULL;
import javax.ws.rs.core.*;

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
    FactTiedToSensorManagerLocal factsTiedToSensorManager;

    @EJB
    FactTiedToDateManagerLocal factsTiedToDateManager;

    @EJB
    SensorManagerLocal sensorsManager;

    @Context
    private UriInfo context;

    public ObservationResource() {
    }

    @POST
    @Consumes("application/json")
    public ObservationDTO createObservation(ObservationDTO dto) {
        Observation newObservation = new Observation();

        long idObservation = obsManager.create(toObservation(dto, newObservation));

        // update or create fact tied to sensor
        Sensor sensor = sensorsManager.read(dto.getSensorId());

        List<FactTiedToSensor> facts = factsTiedToSensorManager.readAllTiedToSensor();

        long factId = 0L;

        for (FactTiedToSensor f : facts) {
            if (f.getSensor().getId() == sensor.getId()) {
                factId = f.getId();
                f.setNbObs(f.getNbObs() + 1);
            }
        }

        if (factId == 0L) {
            FactTiedToSensor newFact = new FactTiedToSensor(
                    sensor.getOrganization(),
                    true,
                    sensor,
                    1);

            factsTiedToSensorManager.create(newFact);
        }

        // update or create fact tied to date
        java.sql.Date date = dto.getCreationDate();

        List<FactTiedToDate> factsDate = factsTiedToDateManager.readAllTiedToDate();

        factId = 0L;

        for (FactTiedToDate f : factsDate) {
            if (f.getSensor().getId() == sensor.getId()) {
                if (f.getDate().toString().equals(date.toString())) {
                    if (dto.getObsValue() > f.getMaxVal()) {
                        f.setMaxVal(dto.getObsValue());
                    }
                    
                    if (dto.getObsValue() < f.getMinVal()) {
                        f.setMinVal(dto.getObsValue());
                    }

                    f.setSumVal(f.getSumVal() + dto.getObsValue());
                    f.setNbVal(f.getNbVal() + 1);
                    f.setAvVal((float) (f.getSumVal() / f.getNbVal()));

                    factId = f.getId();
                }
            }
        }

        if (factId == 0L) {
            FactTiedToDate newFactDate = new FactTiedToDate(sensor.getOrganization(),
                                                            true, sensor, NULL, date, 1, dto.getObsValue(),
                                                            dto.getObsValue(), dto.getObsValue(), dto.getObsValue());

            factsTiedToDateManager.create(newFactDate);
        }

        return toDTO(obsManager.read(idObservation));
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
        obs.setName(obsDto.getName());
        obs.setObsValue(obsDto.getObsValue());
        obs.setCreationDate(obsDto.getCreationDate());
        obs.setSensor(sensorsManager.read(obsDto.getSensorId()));

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
