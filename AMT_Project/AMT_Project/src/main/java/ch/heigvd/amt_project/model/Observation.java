///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package ch.heigvd.amt_project.model;
//
//import java.io.Serializable;
//import java.util.Date;
//import javax.persistence.Entity;
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
//import javax.persistence.Id;
//import javax.persistence.NamedQueries;
//import javax.persistence.NamedQuery;
//import javax.persistence.Table;
//import javax.persistence.Temporal;
//import javax.persistence.TemporalType;
//
///**
// *
// * @author
// */
////@NamedQueries({
////    @NamedQuery(
////            name = "findById",
////            query = "SELECT o FROM Observation o WHERE o.id = :id"
////    ),
////    @NamedQuery(
////            name = "findByName",
////            query = "SELECT o FROM Observation o WHERE o.name = :name"
////    ),
////    @NamedQuery(
////            name = "findBySensorId",
////            query = "SELECT o FROM Observation o WHERE o.sensorId = :sensorId"
////    )
////})
//
//@Entity
//@Table(name = "observations")
//public class Observation implements Serializable {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
//    private long id;
//
//    private String name;
//    private int value;
//    
//    @Temporal(TemporalType.DATE)
//    private Date time;
//    private long sensorId;
//
//    public Observation() {
//    }
//
//    public Observation(long id, String name, int value, Date time, long sensorId) {
//        this.id = id;
//        this.name = name;
//        this.value = value;
//        this.time = time;
//        this.sensorId = sensorId;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//
//    public int getValue() {
//        return value;
//    }
//
//    public void setValue(int value) {
//        this.value = value;
//    }
//
//    public Date getTime() {
//        return time;
//    }
//
//    public void setTime(Date time) {
//        this.time = time;
//    }
//
//    public long getSensorId() {
//        return sensorId;
//    }
//
//    public void setSensorId(long sensorId) {
//        this.sensorId = sensorId;
//    }
//
//    public long getId() {
//        return id;
//    }
//
//    public void setId(long id) {
//        this.id = id;
//    }
//
//    @Override
//    public String toString() {
//        return "";
//    }
//}
