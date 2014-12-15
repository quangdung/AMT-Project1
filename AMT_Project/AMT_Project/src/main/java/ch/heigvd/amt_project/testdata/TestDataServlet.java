/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.heigvd.amt_project.testdata;

import ch.heigvd.amt_project.model.Observation;
import ch.heigvd.amt_project.model.Organization;
import ch.heigvd.amt_project.model.Sensor;
import ch.heigvd.amt_project.model.User;
import ch.heigvd.amt_project.services.observation.ObservationManagerLocal;
import ch.heigvd.amt_project.services.organization.OrganizationManagerLocal;
import ch.heigvd.amt_project.services.sensor.SensorManagerLocal;
import ch.heigvd.amt_project.services.user.UserManagerLocal;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author drezaroth
 */
@WebServlet(name = "TestDataServlet", urlPatterns = {"/TestDataServlet"})
public class TestDataServlet extends HttpServlet {
//    @EJB
//    OrganizationManagerLocal orgMan;
//    
//    @EJB
//    SensorManagerLocal senMan;
//    
//    @EJB
//    ObservationManagerLocal obsMan;
//    
//    @EJB
//    UserManagerLocal usrMan;
    
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        URL url = new URL(request.getRequestURL().toString());
        
        String baseUrl = url.getProtocol() + "://" + url.getAuthority() + "/AMT_Project/api";
        String orgURL = "/organizations";
        String senURL = "/sensors";
        String obsURL = "/observations";
        String usrURL = "/users";
        
//        Organization org = new Organization();
//        org.setName("organization 1");
//        
//        orgMan.create(org);
//        
//        Sensor sen = new Sensor();
//        sen.setName("sensor 1");
//        sen.setDescription("description sensor 1");
//        sen.setOrganization(org);
//        sen.setType("type sensor 1");
//        sen.setVisible(true);
//        
//        senMan.create(sen);
//        
//        Observation obs = new Observation();
//        java.util.Date today = new java.util.Date();
//        java.sql.Date dateToday = new java.sql.Date(today.getTime());
//        obs.setCreationDate(dateToday);
//        obs.setName("observation 1");
//        obs.setSensor(sen);
//        obs.setObsValue(666);
//        
//        obsMan.create(obs);
        
        List<Organization> orgStored = new ArrayList<>();
        List<Sensor> senStored = new ArrayList<>();
        List<User> usrStored = new ArrayList<>();
        
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {

            // create Organizations
            try {

                URL organizationUrl = new URL(baseUrl + orgURL);

    //            response.setContentType("text/html;charset=UTF-8");
    //            try (PrintWriter out = response.getWriter()) {

                    String input;

                    int orgId;

                    for (int i = 1; i <= 10; ++i)
                    {
                        HttpURLConnection con = (HttpURLConnection) organizationUrl.openConnection();
                        con.setDoOutput(true);
                        con.setRequestMethod("POST");
                        con.setRequestProperty("Content-Type", "application/json");
                        OutputStream outputStream = con.getOutputStream();

                        input = "{\"name\":\"testGeneratedOrg" + i + "\"}";
                        outputStream.write(input.getBytes());
                        outputStream.flush();

                        if (con.getResponseCode() != 200) {
                                throw new RuntimeException("Failed : HTTP error code : "
                                        + con.getResponseCode());
                        }

                        BufferedReader buffRep = new BufferedReader(new InputStreamReader(
                                (con.getInputStream())));

                        orgId = Integer.parseInt(buffRep.readLine());

                        orgStored.add(new Organization(orgId, "testGeneratedOrg" + i));

                        con.disconnect();
                    }

                    for (Organization o : orgStored)
                    {
                        out.println("Organisation " + o.getName() + " stored with id : " + o.getId());
                        out.println("<br />");                    
                    }
    //            }
            }
            catch (MalformedURLException e) {
                throw new RuntimeException("Error : Malformed url");
            }
            catch (IOException e) {
                throw new RuntimeException("Error : IOException");
            }

            // create Sensors
            try {

                URL sensorUrl = new URL(baseUrl + senURL);

    //            response.setContentType("text/html;charset=UTF-8");
    //            try (PrintWriter out = response.getWriter()) {

                    String input;

                    int senId;

                    for (Organization o : orgStored)
                    {
                        for (int i = 1; i <= 10; ++i)
                        {
                            HttpURLConnection con = (HttpURLConnection) sensorUrl.openConnection();
                            con.setDoOutput(true);
                            con.setRequestMethod("POST");
                            con.setRequestProperty("Content-Type", "application/json");
                            OutputStream outputStream = con.getOutputStream();

                            input = "{\"name\":\"testGeneratedSen" + i + "org" + o.getId() + "\","
                                    + "\"description\":\"Sensor nb " + i + " associated with the organization " + o.getName() + "\","
                                    + "\"organization\":{\"id\":" + o.getId() + ",\"name\":\"" + o.getName() + "\"},"
                                    + "\"type\":\"sensorType\",\"visible\":true}";

                            outputStream.write(input.getBytes());
                            outputStream.flush();

                            if (con.getResponseCode() != 200) {
                                    throw new RuntimeException("Failed : HTTP error code : "
                                            + con.getResponseCode());
                            }

                            BufferedReader buffRep = new BufferedReader(new InputStreamReader(
                                    (con.getInputStream())));

                            senId = Integer.parseInt(buffRep.readLine());

                            senStored.add(new Sensor(senId, ("testGeneratedSen" + i + "org" + o.getId()),
                                    ("Sensor nb " + i + " associated with the organization " + o.getName()),
                                    "sensorType", o, true));

                            con.disconnect();
                        }

                        for (Sensor s : senStored)
                        {
                            out.println(s.getDescription() + " stored with id : " + s.getId());
                            out.println("<br />");                    
                        }
                    }
    //            }
            }
            catch (MalformedURLException e) {
                throw new RuntimeException("Error : Malformed url");
            }
            catch (IOException e) {
                throw new RuntimeException("Error : IOException");
            }
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
