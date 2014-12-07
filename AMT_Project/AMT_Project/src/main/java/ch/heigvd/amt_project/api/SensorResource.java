
package ch.heigvd.amt_project.api;

import ch.heigvd.amt_project.dto.SensorDTO;
import ch.heigvd.amt_project.model.Sensor;
import ch.heigvd.amt_project.services.sensor.SensorManagerLocal;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
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
    public List<SensorDTO> getAllSensors()
    {
        List<Sensor> sensors = sensorsManager.read();
        List<SensorDTO> result = new ArrayList<>();
        
        for(Sensor sensor : sensors) {
            result.add(toDTO(sensor));
        }
        
        return result;
    }
    
    @Path("/{id}")
    @GET
    @Produces("application/json")
    public SensorDTO getSensorDetails(@PathParam("id") long id)
    {
        Sensor sensor = sensorsManager.read(id);
        return toDTO(sensor);
    }
    
    @POST
    @Consumes("application/json")
//    @Produces("application/json")
    public long createSensor(SensorDTO dto)
    {
        Sensor newSensor = new Sensor();
  
        long idSensor = sensorsManager.create(toSensor(dto, newSensor));
//        Sensor sensor = sensorsManager.create(toSensor(dto, newSensor));
        
//        return sensor;
        return idSensor;
    }
    
    @Path("/{id}")
    @PUT
    @Consumes("application/json")
    public Sensor updateSensor(@PathParam("id") long id, SensorDTO dto)
    {
        Sensor s = new Sensor(sensorsManager.read(id));
        s.setId(id);
        sensorsManager.update(toSensorUpdate(dto, s));
        
        return s;
    }

    @Path("/{id}")
    @DELETE
    public void deleteSensor(@PathParam("id") long id)
    {
        sensorsManager.read(id);
        sensorsManager.delete(sensorsManager.read(id));
    }
    
    private Sensor toSensorUpdate(SensorDTO sensorDto, Sensor sensor)
    {
        if (sensorDto.getName() != null)
        {
            sensor.setName(sensorDto.getName());
        }
        if (sensorDto.getDescription() != null)
        {
            sensor.setDescription(sensorDto.getDescription());
        }
        if (sensorDto.getType() != null)
        {
            sensor.setType(sensorDto.getType());
        }
        if (sensorDto.getOrganizationId() != 0)
        {
            sensor.setOrganizationId(sensorDto.getOrganizationId());
        }
        sensor.setPublicSensor(sensorDto.isPublicSensor());
        
        return sensor;
    }
    
    private Sensor toSensor(SensorDTO sensorDto, Sensor sensor)
    {
        sensor.setName(sensorDto.getName());
        sensor.setDescription(sensorDto.getDescription());
        sensor.setType(sensorDto.getType());
        sensor.setOrganizationId(sensorDto.getOrganizationId());
        sensor.setPublicSensor(sensorDto.isPublicSensor());
        
        return sensor;
    }
    
    private SensorDTO toDTO(Sensor sensor) {
        SensorDTO dto = new SensorDTO();
        dto.setId(sensor.getId());
        dto.setName(sensor.getName());
        dto.setDescription(sensor.getDescription());
        dto.setType(sensor.getType());
        dto.setOrganizationId(sensor.getOrganizationId());
        dto.setPublicSensor(sensor.isPublicSensor());
        return dto;
    }
}
