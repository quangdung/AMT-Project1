/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.heigvd.amt_project.api;

import ch.heigvd.amt_project.dto.SensorDTO;
import ch.heigvd.amt_project.model.Sensor;
import ch.heigvd.amt_project.services.sensor.SensorManagerLocal;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;

/**
 *
 * @author
 */
@Path("sensors")
@Stateless
public class SensorResource {

    @EJB
    SensorManagerLocal sensorsManager;

    @Context
    private UriInfo context;

    public SensorResource() {
    }

    @GET
    @Produces("application/json")
    public List<SensorDTO> getAllSensors(@DefaultValue("0") @QueryParam("organizationId") long organizationId,
                                         @DefaultValue("null") @QueryParam("type") String type) {
        
        List<Sensor> sensors = new ArrayList<>();
        
//        if(organizationId != 0 || !type.equals("null")) {
//            sensors = sensorsManager.findSensorByParameters(organizationId, type);
//        } else {
            sensors = sensorsManager.findAllSensors();
//        }
        
        List<SensorDTO> result = new ArrayList<>();
        
        for(Sensor sensor : sensors) {
            result.add(toDTO(sensor));
        }
        
        return result;
    }
    
    @Path("/{id}")
    @GET
    @Produces("application/json")
    public SensorDTO getSensorDetails(@PathParam("id") long id) {
        Sensor sensor = sensorsManager.findSensorById(id);
        return toDTO(sensor);
    }

    private SensorDTO toDTO(Sensor sensor) {
        SensorDTO dto = new SensorDTO();
        dto.setId(sensor.getId());
        dto.setDescription(sensor.getDescription());
        dto.setType(sensor.getType());
        dto.setOrganizationId(sensor.getOrganizationId());
        return dto;
    }
}
