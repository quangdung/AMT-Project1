package ch.heigvd.amt_project.api;

import ch.heigvd.amt_project.dto.SensorDTO;
import ch.heigvd.amt_project.model.Sensor;
import ch.heigvd.amt_project.services.organization.OrganizationDAOLocal;
import ch.heigvd.amt_project.services.sensor.SensorDAOLocal;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.*;
import javax.ws.rs.core.*;

@Path("sensors")
@Stateless
public class SensorResource {

    @EJB
    SensorDAOLocal sensorsDAO;
    
    @EJB
    OrganizationDAOLocal orgsDAO;

    @Context
    private UriInfo context;

    public SensorResource() {
    }

    @GET
    @Produces("application/json")
    public List<SensorDTO> getAllSensors(@QueryParam("orgId") long orgId) {
        MultivaluedMap<String, String> mapAllParam = context.getQueryParameters();
        
        List<Sensor> sensors = sensorsDAO.read();
        List<SensorDTO> result = new ArrayList<>();

        if (mapAllParam.isEmpty()) {
            for (Sensor sensor : sensors) {
                result.add(toDTO(sensor));
            }
        }
        else if (mapAllParam.containsKey("orgId")) {
            List<Sensor> sensorsByOrgId = sensorsDAO.readByOrgId(orgId);
            
           for (Sensor sensor : sensorsByOrgId) {
               result.add(toDTO(sensor));
           } 
        }

        return result;
    }

    @Path("/{id}")
    @GET
    @Produces("application/json")
    public SensorDTO getSensorDetails(@PathParam("id") long id) {
        Sensor sensor = sensorsDAO.read(id);
        return toDTO(sensor);
    }

    @POST
    @Consumes("application/json")
    public SensorDTO createSensor(SensorDTO dto) {
        Sensor newSensor = new Sensor();

        long idSensor = sensorsDAO.create(toSensor(dto, newSensor));
        
        return toDTO(sensorsDAO.read(idSensor));
    }

    @Path("/{id}")
    @PUT
    @Consumes("application/json")
    public void updateSensor(@PathParam("id") long id, SensorDTO dto) {
        Sensor existing = sensorsDAO.read(id);
        sensorsDAO.update(toSensor(dto, existing));
    }

    @Path("/{id}")
    @DELETE
    public void deleteSensor(@PathParam("id") long id) {
        sensorsDAO.read(id);
        sensorsDAO.delete(sensorsDAO.read(id));
        
//        Sensor toDelete = sensorsDAO.read(id);
//        if (toDelete != null) {
//            sensorsDAO.delete(toDelete);
//        }
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
        if (sensorDto.getOrgId() != 0L) {
            sensor.setOrganization(orgsDAO.read(sensorDto.getOrgId()));
        }
        sensor.setVisible(sensorDto.isVisible());

        return sensor;
    }

    private Sensor toSensor(SensorDTO sensorDto, Sensor sensor) {
        sensor.setName(sensorDto.getName());
        sensor.setDescription(sensorDto.getDescription());
        sensor.setType(sensorDto.getType());
        sensor.setOrganization(orgsDAO.read(sensorDto.getOrgId()));
        sensor.setVisible(sensorDto.isVisible());

        return sensor;
    }

    private SensorDTO toDTO(Sensor sensor) {
        SensorDTO dto = new SensorDTO();
        dto.setId(sensor.getId());
        dto.setName(sensor.getName());
        dto.setDescription(sensor.getDescription());
        dto.setType(sensor.getType());
        dto.setOrgId(sensor.getOrganization().getId());
        dto.setVisible(sensor.isVisible());
        return dto;
    }
}
