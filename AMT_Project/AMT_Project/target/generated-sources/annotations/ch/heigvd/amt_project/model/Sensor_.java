package ch.heigvd.amt_project.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2014-12-05T20:39:38")
@StaticMetamodel(Sensor.class)
public class Sensor_ { 

    public static volatile SingularAttribute<Sensor, Long> id;
    public static volatile SingularAttribute<Sensor, String> description;
    public static volatile SingularAttribute<Sensor, String> name;
    public static volatile SingularAttribute<Sensor, Long> organizationId;
    public static volatile SingularAttribute<Sensor, Boolean> isPublic;
    public static volatile SingularAttribute<Sensor, String> type;

}