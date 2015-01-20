package ch.heigvd.amt_project.model;

import ch.heigvd.amt_project.model.Organization;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2015-01-20T18:09:55")
@StaticMetamodel(Sensor.class)
public class Sensor_ { 

    public static volatile SingularAttribute<Sensor, Boolean> visible;
    public static volatile SingularAttribute<Sensor, Organization> organization;
    public static volatile SingularAttribute<Sensor, String> name;
    public static volatile SingularAttribute<Sensor, String> description;
    public static volatile SingularAttribute<Sensor, Long> id;
    public static volatile SingularAttribute<Sensor, String> type;

}