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
////
////@NamedQueries({
////     @NamedQuery(
////        name="findById",
////        query="SELECT f FROM Fact f WHERE f.id = :id"
////    ),
////    @NamedQuery(
////        name="findByName",
////        query="SELECT f FROM Fact f WHERE f.name = :name"
////    ),
////    @NamedQuery(
////        name="findByType",
////        query="SELECT f FROM Fact f WHERE f.type = :type"
////    ),
////    @NamedQuery(
////        name="findByIsPublic",
////        query="SELECT f FROM Fact f WHERE f.isPublic = :isPublic"
////    ),
////    @NamedQuery(
////        name="findByOrganizationId",
////        query="SELECT f FROM Fact f WHERE f.organizationId = :organizationId"
////    ),
////    @NamedQuery(
////        name="findAll",
////        query="SELECT o FROM Organization o"
////    )
////})
//
//@Entity
//@Table(name="facts")
//public class Fact implements Serializable {
//    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
//    private long id;
//
//    private String name;
//    private String type;
//    private String description;
//    private boolean isPublic;
//    private long organizationId;
//    
//    public Fact() { 
//    }
//
//    public Fact(long id, String name, String type, String description, boolean isPublic, long organizationId) {
//        this.id = id;
//        this.name = name;
//        this.type = type;
//        this.description = description;
//        this.isPublic = isPublic;
//        this.organizationId = organizationId;
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
//    public boolean isIsPublic() {
//        return isPublic;
//    }
//
//    public void setIsPublic(boolean isPublic) {
//        this.isPublic = isPublic;
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
//    public String getType() {
//        return type;
//    }
//
//    public void setType(String type) {
//        this.type = type;
//    }
//
//    public long getOrganizationId() {
//        return organizationId;
//    }
//
//    public void setOrganizationId(long organizationId) {
//        this.organizationId = organizationId;
//    }
//    
//    @Override
//    public String toString() {
//        return "Sensor #" + id + ", " + description + ": " + type;
//    }
//}
