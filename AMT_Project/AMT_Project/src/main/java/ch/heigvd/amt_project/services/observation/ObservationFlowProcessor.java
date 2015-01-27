/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.heigvd.amt_project.services.observation;

import ch.heigvd.amt_project.dto.ObservationDTO;
import ch.heigvd.amt_project.model.FactTiedToDate;
import ch.heigvd.amt_project.model.FactTiedToSensor;
import ch.heigvd.amt_project.model.Sensor;
import ch.heigvd.amt_project.services.fact.FactDAOLocal;
import ch.heigvd.amt_project.services.fact.FactTiedToDateDAOLocal;
import ch.heigvd.amt_project.services.fact.FactTiedToSensorDAOLocal;
import ch.heigvd.amt_project.services.sensor.SensorDAOLocal;
import static java.sql.Types.NULL;
import java.util.List;
import javax.ejb.Singleton;
import java.util.logging.*;
import javax.ejb.EJB;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

/**
 *
 */
@Singleton
public class ObservationFlowProcessor implements ObservationFlowProcessorLocal {

    @EJB
    SensorDAOLocal sensorsDAO;

    @EJB
    FactDAOLocal factsDAO;

    @EJB
    FactTiedToSensorDAOLocal factsTiedToSensorDAO;

    @EJB
    FactTiedToDateDAOLocal factsTiedToDateDAO;

    private static final Logger LOG = Logger.getLogger(ObservationFlowProcessor.class.getName());

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void UpdateFactBySensor(ObservationDTO observationDto) {
        Sensor sensor = sensorsDAO.read(observationDto.getSensorId());

        long factId;

        List<FactTiedToSensor> factsTiedToSensor = null;

        int attempt = 3;

        while (attempt > 0 && factsTiedToSensor == null) {
            try {
                attempt--;
                factsTiedToSensor = factsTiedToSensorDAO.readBySensorId(sensor.getId());
            }
            catch (Exception e) {
                LOG.info("Problem update fact bySensor...retry..." 
                        + attempt + "\n" + e.getMessage() + "\n");
            }
        }

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
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void UpdateFactByDate(ObservationDTO observationDto) {
        Sensor sensor = sensorsDAO.read(observationDto.getSensorId());

        long factId;

        java.util.Date date = observationDto.getCreationDate();

        List<FactTiedToDate> factsTiedToDate = null;

        int attempt = 3;

        while (attempt > 0 && factsTiedToDate == null) {
            try {
                attempt--;
                factsTiedToDate
                        = factsTiedToDateDAO.readBySensorIdByDate(sensor.getId(),
                                                                  observationDto.getCreationDate());
            }
            catch (Exception e) {
                LOG.info("Problem update fact byDate...retry..."
                        + attempt + "\n" + e.getMessage() + "\n");
            }
        }

        factId = (factsTiedToDate.size() > 0
                  ? factsTiedToDate.get(0).getId() : 0L);

        if (factId == 0L) {
            FactTiedToDate newFactDate
                    = new FactTiedToDate(sensor.getOrganization(),
                                         true,
                                         sensor,
                                         NULL,
                                         date,
                                         1,
                                         observationDto.getObsValue(),
                                         observationDto.getObsValue(),
                                         observationDto.getObsValue(),
                                         observationDto.getObsValue());

            factsTiedToDateDAO.create(newFactDate);
        }
        else {
            FactTiedToDate f = (FactTiedToDate) factsDAO.read(factId);

            if (observationDto.getObsValue() > f.getMaxVal()) {
                f.setMaxVal(observationDto.getObsValue());
            }

            if (observationDto.getObsValue() < f.getMinVal()) {
                f.setMinVal(observationDto.getObsValue());
            }

            f.setSumVal(f.getSumVal() + observationDto.getObsValue());
            f.setNbVal(f.getNbVal() + 1);
            f.setAvVal((float) (f.getSumVal() / f.getNbVal()));
        }
    }

}
