package ch.heigvd.amt_project.api;

import ch.heigvd.amt_project.services.fact.FactManagerLocal;
import ch.heigvd.amt_project.services.observation.ObservationManagerLocal;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

@Stateless
@Path("operations")
public class AdminOperationResource {
 
    @EJB
    FactManagerLocal factManager;
    
    @EJB
    ObservationManagerLocal obsManager;
    
    public AdminOperationResource() {}
    
//    @Path("/reset")
//    @POST
//    @Consumes("application/json")
//    public void postResetOperation(Object operation) {
//        factManager.deleteAll();
//    }
    
    @Path("/resetFact")
    @DELETE
    public void postResetFactOperation() {
        factManager.deleteAll();
    }
    
    @Path("/resetObservation")
    @DELETE
    public void postResetObservationOperation() {
        obsManager.deleteAll();
    }
}
