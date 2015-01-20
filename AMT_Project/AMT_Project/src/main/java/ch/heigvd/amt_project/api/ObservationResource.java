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
import ch.heigvd.amt_project.services.fact.FactDAOLocal;
import ch.heigvd.amt_project.services.fact.FactTiedToDateDAOLocal;
import ch.heigvd.amt_project.services.fact.FactTiedToSensorDAOLocal;
import ch.heigvd.amt_project.services.observation.ObservationDAOLocal;
import ch.heigvd.amt_project.services.sensor.SensorDAOLocal;
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
    ObservationDAOLocal obsDAO;

    @EJB
    FactDAOLocal factsDAO;

    @EJB
    FactTiedToSensorDAOLocal factsTiedToSensorDAO;

    @EJB
    FactTiedToDateDAOLocal factsTiedToDateDAO;

    @EJB
    SensorDAOLocal sensorsDAO;

    @Context
    private UriInfo context;

    public ObservationResource() {
    }

    @POST
    @Consumes("application/json")
    public ObservationDTO createObservation(ObservationDTO dto) {
        Observation newObservation = new Observation();

        long idObservation = obsDAO.create(toObservation(dto, newObservation));

        // update or create fact tied to sensor
        Sensor sensor = sensorsDAO.read(dto.getSensorId());

        long factId;

        List<FactTiedToSensor> factsTiedToSensor = factsTiedToSensorDAO.readBySensorId(sensor.getId());
        factId = (factsTiedToSensor.size() > 0
                  ? factsTiedToSensor.get(0).getId() : 0L);

        if (factId == 0L) {
            FactTiedToSensor newFact = new FactTiedToSensor(
                    sensor.getOrganization(),
                    true,
                    sensor,
                    1);

            factsTiedToSensorDAO.create(newFact);
        }
        else {
            FactTiedToSensor f = (FactTiedToSensor) factsDAO.read(factId);
            f.setNbObs(f.getNbObs() + 1);
        }

        // update or create fact tied to date
        java.sql.Date date = dto.getCreationDate();

        List<FactTiedToDate> factsTiedToDate = factsTiedToDateDAO.readBySensorId(sensor.getId());
        factId = (factsTiedToDate.size() > 0
                  ? factsTiedToDate.get(0).getId() : 0L);

        if (factId == 0L) {
            FactTiedToDate newFactDate = new FactTiedToDate(sensor.getOrganization(),
                                                            true, sensor, NULL, date, 1, dto.getObsValue(),
                                                            dto.getObsValue(), dto.getObsValue(), dto.getObsValue());

            factsTiedToDateDAO.create(newFactDate);
        }
        else {
            FactTiedToDate f = (FactTiedToDate) factsDAO.read(factId);

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
            }
        }

        return toDTO(obsDAO.read(idObservation));
    }

    @Path("/{id}")
    @PUT
    @Consumes("application/json")
    public void updateObservation(@PathParam("id") long id, ObservationDTO dto) {
        Observation existing = obsDAO.read(id);
        obsDAO.update(toObservation(dto, existing));
    }

    @Path("/{id}")
    @DELETE
    public void deleteObservation(@PathParam("id") long id) {
        obsDAO.read(id);
        obsDAO.delete(obsDAO.read(id));
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
