/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.heigvd.amt_project.api;

import java.util.Set;
import javax.ws.rs.core.Application;

/**
 *
 * @author
 */
@javax.ws.rs.ApplicationPath("api")
public class ApplicationConfig extends Application {
    
    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new java.util.HashSet<>();
        addRestResourceClasses(resources);
        return resources;
    }

    private void addRestResourceClasses(Set<Class<?>> resources) {
        resources.add(ch.heigvd.amt_project.api.FactResource.class);
        resources.add(ch.heigvd.amt_project.api.ObservationResource.class);
        resources.add(ch.heigvd.amt_project.api.OrganizationResource.class);
        resources.add(ch.heigvd.amt_project.api.SensorResource.class);
        resources.add(ch.heigvd.amt_project.api.UserResource.class);
    }
}