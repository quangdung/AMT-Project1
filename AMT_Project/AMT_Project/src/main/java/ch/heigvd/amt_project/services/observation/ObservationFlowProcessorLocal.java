/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.heigvd.amt_project.services.observation;

import ch.heigvd.amt_project.dto.ObservationDTO;
import javax.ejb.Local;

/**
 *
 */
@Local
public interface ObservationFlowProcessorLocal {

    void UpdateFactBySensor(ObservationDTO observationDto);

    void UpdateFactByDate(ObservationDTO observationDto);
    
}
