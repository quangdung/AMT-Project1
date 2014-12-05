///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package ch.heigvd.amt_project.model;
//
//import java.io.Serializable;
//import javax.persistence.Entity;
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
//import javax.persistence.Id;
//import javax.persistence.NamedQueries;
//import javax.persistence.NamedQuery;
//import javax.persistence.Table;
//
///**
// *
// * @author
// */
//
////@NamedQueries({
////    @NamedQuery(
////        name="findById",
////        query="SELECT o FROM Organization o WHERE o.id = :id"
////    ),
////    @NamedQuery(
////        name="findBySensorId",
////        query="SELECT o FROM Organization o WHERE o.sensorId = :sensorId"
////    ),
////    @NamedQuery(
////        name="findByFactId",
////        query="SELECT o FROM Organization o WHERE o.factId = :factId"
////    ),
////    @NamedQuery(
////        name="findAll",
////        query="SELECT o FROM Organization o"
////    )
////})
//
//@Entity
//@Table(name="organizations")
//public class Organization implements Serializable {
//    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
//    private long id;
//
//    private String name;
//    private String description;
//    private long sensorId;
//    private long factId;
//    
//    
//    public Organization() { 
//    }
//
//    public Organization(long id, String name, String description, long sensorId, long factId) {
//        this.id = id;
//        this.name = name;
//        this.description = description;
//        this.sensorId = sensorId;
//        this.factId = factId;
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
//    public long getSensorId() {
//        return sensorId;
//    }
//
//    public void setSensorId(long sensorId) {
//        this.sensorId = sensorId;
//    }
//
//    public long getFactId() {
//        return factId;
//    }
//
//    public void setFactId(long factId) {
//        this.factId = factId;
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
//    public String getDescription() {
//        return description;
//    }
//
//    public void setDescription(String description) {
//        this.description = description;
//    }
//    
//    @Override
//    public String toString() {
//        return "";
//    }
//}
