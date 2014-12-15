package ch.heigvd.amt_project.api;

import ch.heigvd.amt_project.dto.SensorDTO;
import ch.heigvd.amt_project.model.Sensor;
import ch.heigvd.amt_project.services.sensor.SensorManagerLocal;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;

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
    public List<SensorDTO> getAllSensors(
            @QueryParam("orgId") long orgId) {
        List<Sensor> sensors = sensorsManager.read();
        List<SensorDTO> result = new ArrayList<>();

        if (orgId > 0L) {
            List<Sensor> sensorsByOrgId = sensorsManager.readByOrgId(orgId);
            
           for (Sensor sensor : sensorsByOrgId) {
               result.add(toDTO(sensor));
           } 
        }
        else {
            for (Sensor sensor : sensors) {
                result.add(toDTO(sensor));
            }
        }

        return result;
    }

    @Path("/{id}")
    @GET
    @Produces("application/json")
    public SensorDTO getSensorDetails(@PathParam("id") long id) {
        Sensor sensor = sensorsManager.read(id);
        return toDTO(sensor);
    }

    @POST
    @Consumes("application/json")
    public long createSensor(SensorDTO dto) {
        Sensor newSensor = new Sensor();

        long idSensor = sensorsManager.create(toSensor(dto, newSensor));
        return idSensor;
    }

    @Path("/{id}")
    @PUT
    @Consumes("application/json")
    public Sensor updateSensor(@PathParam("id") long id, SensorDTO dto) {
        Sensor s = new Sensor(sensorsManager.read(id));
        s.setId(id);
        sensorsManager.update(toSensorUpdate(dto, s));

        return s;
    }

    @Path("/{id}")
    @DELETE
    public void deleteSensor(@PathParam("id") long id) {
        Sensor toDelete = sensorsManager.read(id);

        if (toDelete != null) {
            sensorsManager.delete(toDelete);
        }
    }

    private Sensor toSensorUpdate(SensorDTO sensorDto, Sensor sensor) {
        if (sensorDto.getName() != null) {
            sensor.setName(sensorDto.getName());
        }
        if (sensorDto.getDescription() != null) {
            sensor.setDescription(sensorDto.getDescription());
        }
        if (sensorDto.getType() != null) {
            sensor.setType(sensorDto.getType());
        }
        if (sensorDto.getOrganization() != null) {
            sensor.setOrganization(sensorDto.getOrganization());
        }
        sensor.setVisible(sensorDto.isVisible());

        return sensor;
    }

    private Sensor toSensor(SensorDTO sensorDto, Sensor sensor) {
        sensor.setName(sensorDto.getName());
        sensor.setDescription(sensorDto.getDescription());
        sensor.setType(sensorDto.getType());
        sensor.setOrganization(sensorDto.getOrganization());
        sensor.setVisible(sensorDto.isVisible());

        return sensor;
    }

    private SensorDTO toDTO(Sensor sensor) {
        SensorDTO dto = new SensorDTO();
        dto.setId(sensor.getId());
        dto.setName(sensor.getName());
        dto.setDescription(sensor.getDescription());
        dto.setType(sensor.getType());
        dto.setOrganization(sensor.getOrganization());
        dto.setVisible(sensor.isVisible());
        return dto;
    }
}
