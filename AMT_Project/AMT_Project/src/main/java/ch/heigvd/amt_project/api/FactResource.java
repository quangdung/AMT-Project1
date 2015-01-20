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
import ch.heigvd.amt_project.model.FactType;
import ch.heigvd.amt_project.services.fact.FactDAOLocal;
import ch.heigvd.amt_project.services.fact.FactTiedToDateDAOLocal;
import ch.heigvd.amt_project.services.fact.FactTiedToSensorDAOLocal;
import ch.heigvd.amt_project.services.organization.OrganizationDAOLocal;
import ch.heigvd.amt_project.services.sensor.SensorDAOLocal;
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
    FactDAOLocal factsDAO;

    @EJB
    FactTiedToSensorDAOLocal factsBySensorDAO;

    @EJB
    FactTiedToDateDAOLocal factsBySensorByDateDAO;

    @EJB
    OrganizationDAOLocal orgsDAO;

    @EJB
    SensorDAOLocal sensorsDAO;

    @Context
    private UriInfo context;

    public FactResource() {
    }

    @GET
    @Produces("application/json")
    public List<FactDTO> getAllFacts(
            @QueryParam("sensorId") long sensorId,
            @QueryParam("orgId") long orgId) {

        MultivaluedMap<String, String> mapAllParam = context.getQueryParameters();

        List<Fact> facts = null;

        List<FactDTO> result = new ArrayList<>();

        if (mapAllParam.isEmpty()) {
            facts = factsDAO.read();
        }
        else if (mapAllParam.containsKey("orgId") && !mapAllParam.containsKey("sensorId")) {
            facts = factsDAO.readByOrgId(orgId);
        }
        else if (mapAllParam.containsKey("sensorId") && !mapAllParam.containsKey("orgId")) {
            List<FactTiedToSensor> factsSensor = factsBySensorDAO.readBySensorId(sensorId);

            for (FactTiedToSensor f : factsSensor) {
                if (f.getType().equals(FactType.FACT_TIED_TO_SENSOR)) {
                    result.add(toBySensorDTO(f));
                }
                else {
                    result.add(toBySensorByDateDTO((FactTiedToDate) f));
                }
            }

            return result;
        }

        for (Fact fact : facts) {
            switch (fact.getType()) {
                case FactType.FACT_TIED_TO_SENSOR:
                    result.add(toBySensorDTO((FactTiedToSensor) fact));
                    break;
                case FactType.FACT_TIED_TO_SENSOR_BY_DATE:
                    result.add(toBySensorByDateDTO((FactTiedToDate) fact));
                    break;
                default:
                    result.add(toDTO(fact));
                    break;
            }
        }

        return result;
    }

    @Path("/bySensor")
    @GET
    @Produces("application/json")
    public List<FactTiedToSensorDTO> getAllFactsBySensor() {
        List<FactTiedToSensorDTO> result = new ArrayList<>();
        List<FactTiedToSensor> factsBySensor = factsBySensorDAO.readAllTiedToSensor();

        for (FactTiedToSensor f : factsBySensor) {
            result.add(toBySensorDTO(f));
        }

        return result;
    }

    @Path("/bySensorByDate")
    @GET
    @Produces("application/json")
    public List<FactTiedToDateDTO> getAllFactsBySensorByDateDTOs() {
        List<FactTiedToDateDTO> result = new ArrayList<>();
        List<FactTiedToDate> factsBySensorByDate = factsBySensorByDateDAO.readAllTiedToDate();

        for (FactTiedToDate f : factsBySensorByDate) {
            result.add(toBySensorByDateDTO(f));
        }

        return result;
    }

    @Path("/{id}")
    @GET
    @Produces("application/json")
    public FactDTO getFactDetails(@PathParam("id") long id) {
        Fact fact = factsDAO.read(id);
        return toDTO(fact);
    }

    private Fact toFact(FactDTO factDto, Fact fact) {
        fact.setId(factDto.getId());
        fact.setOrganization(orgsDAO.read(factDto.getOrgId()));
        fact.setVisible(factDto.isVisible());

        fact.setType(factDto.getType());

        return fact;
    }

    
    private FactDTO toDTO(Fact fact) {
        FactDTO dto = new FactDTO();

        dto.setId(fact.getId());
        dto.setOrgId(fact.getOrganization().getId());
        dto.setType(fact.getType());
        dto.setVisible(fact.isVisible());

        return dto;
    }

    private FactTiedToSensor toFactTiedToSensor(FactTiedToSensorDTO factDto, FactTiedToSensor fact) {
        fact.setId(factDto.getId());
        fact.setOrganization(orgsDAO.read(factDto.getOrgId()));
        fact.setType(factDto.getType());
        fact.setVisible(factDto.isVisible());
        fact.setSensor(sensorsDAO.read(factDto.getSensorId()));
        fact.setNbObs(factDto.getTotNbObs());

        return fact;
    }

    private FactTiedToSensorDTO toBySensorDTO(FactTiedToSensor fact) {
        FactTiedToSensorDTO dto = new FactTiedToSensorDTO();

        dto.setId(fact.getId());
        dto.setOrgId(fact.getOrganization().getId());
        dto.setType(FactType.FACT_TIED_TO_SENSOR);
        dto.setVisible(fact.isVisible());
        dto.setSensorId(fact.getSensor().getId());
        dto.setTotNbObs(fact.getNbObs());

        return dto;
    }

    private FactTiedToDate toFactTiedToDate(FactTiedToDateDTO factDto, FactTiedToDate fact) {
        fact.setId(factDto.getId());
        fact.setOrganization(orgsDAO.read(factDto.getOrgId()));
        fact.setType(FactType.FACT_TIED_TO_SENSOR_BY_DATE);
        fact.setVisible(factDto.isVisible());
        fact.setSensor(sensorsDAO.read(factDto.getSensorId()));
        fact.setNbObs(factDto.getTotNbObs());
        fact.setDate(factDto.getDate());
        fact.setAvVal(factDto.getAvVal());
        fact.setMaxVal(factDto.getMaxVal());
        fact.setMinVal(factDto.getMinVal());
        fact.setNbVal(factDto.getNbVal());
        fact.setSumVal(factDto.getSumVal());

        return fact;
    }

    private FactTiedToDateDTO toBySensorByDateDTO(FactTiedToDate fact) {
        FactTiedToDateDTO dto = new FactTiedToDateDTO();
        dto.setId(fact.getId());
        dto.setOrgId(fact.getOrganization().getId());
        dto.setType(FactType.FACT_TIED_TO_SENSOR_BY_DATE);
        dto.setVisible(fact.isVisible());
        dto.setSensorId(fact.getSensor().getId());
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
