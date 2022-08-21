/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ServletsUtiles;

import java.sql.*;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import DataBase_Beans.*;
import SessionBeans.FlightBean;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletConfig;
import javax.servlet.http.HttpSession;

/**
 *
 * @author student
 */
public class ControlServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param config
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    
    FlightBean FB;
    VolsBean VB;
    BDBean UserBean;
    BDBean UserBean2;
    boolean exists;
    ResultSet selection;
    int selectionId;
    int placeNumber;
    String selectionList[];
    
    @Override
    public void init(ServletConfig config)
            throws ServletException {
        
        FB = null;
        UserBean = new BDBean();
        UserBean2 = new BDBean();
        
        try {
            VB = new VolsBean();
            
            UserBean.setDriver("com.mysql.cj.jdbc.Driver");
            UserBean.setConnection("jdbc:mysql://localhost:3306/BD_AIRPORT", "root", "PassRoot11_");
            UserBean2.setDriver("com.mysql.cj.jdbc.Driver");
            UserBean2.setConnection("jdbc:mysql://localhost:3306/BD_AIRPORT", "root", "PassRoot11_");
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(ControlServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        super.init();
    }
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        /* String newVisit = DateFormat.getDateTimeInstance(DateFormat.FULL,DateFormat.FULL,Locale.FRANCE).format(new java.util.Date());
        newVisit = newVisit.replace(' ', '.'); // + "\n" for Internet Explorer
        Cookie lastVisitCookie = new Cookie ("lastVisit", newVisit);
        response.addCookie(lastVisitCookie);

        String lastVisit = null;
        Cookie[] tabCookies = request.getCookies();
        if(tabCookies != null)
        {
            for (Cookie tabCookie : tabCookies)
            {
                if (tabCookie.getName().equals("lastVisit"))
                    lastVisit = tabCookie.getValue();
            }
        } */
        
        HttpSession session = request.getSession(true);
        String action = request.getParameter("action");

        switch(action)
        {
            case "Sign in" :
                exists = true;
                UserBean.setTable("Utilisateur");
                
                if(request.getParameter("username").equals("") || request.getParameter("password").equals(""))
                {
                    response.sendRedirect("badLogin.html");
                    break;
                }
                
                try {
                    exists = UserExists(UserBean.Select(false), request.getParameter("username"), request.getParameter("password"), false);
                    
                    if(exists == false)
                    {
                        UserBean.setValues("('" + request.getParameter("username") + "', '" + request.getParameter("password") + "')");
                        
                        UserBean.Insert();
                    }
                    else
                    {
                        response.sendRedirect("badLogin.html");
                        break;
                    }
                    
                } catch (SQLException ex) {
                    Logger.getLogger(ControlServlet.class.getName()).log(Level.SEVERE, null, ex);
                }
                
            case "Log in" :
                exists = false;
                UserBean.setTable("Utilisateur");
                UserBean.setCondition("");
                
                try {
                    exists = UserExists(UserBean.Select(false), request.getParameter("username"), request.getParameter("password"), true);
                } catch (SQLException ex) {
                    Logger.getLogger(ControlServlet.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                if(exists == true)
                {
                    FB = new FlightBean();
                    try {
                        FB.setFlightList(VB.Select());
                    } catch (SQLException ex) {
                        Logger.getLogger(ControlServlet.class.getName()).log(Level.SEVERE, null, ex);
                    }

                    if(FB.getFlightList().isEmpty() == false)
                        session.setAttribute("flights", FB);
                    else
                        session.setAttribute("flights", null);
                    
                    session.setAttribute("logon.isDone", request.getParameter("username"));
                    
                    response.sendRedirect("flights.jsp");
                }
                else
                    response.sendRedirect("badLogin.html");
                
                break;
                
            case "Go to flight list" :
                
                FB = new FlightBean();
                try {
                    FB.setFlightList(VB.Select());
                } catch (SQLException ex) {
                    Logger.getLogger(ControlServlet.class.getName()).log(Level.SEVERE, null, ex);
                }

                if(FB.getFlightList().isEmpty() == false)
                    session.setAttribute("flights", FB);
                else
                    session.setAttribute("flights", null);

                response.sendRedirect("flights.jsp");
                
                break;
                
            case "Add to cart" :
                
                if(request.getParameter("flightSelection") == null || request.getParameter("placeNumber").equals(""))
                {
                    response.sendRedirect("badFlights.jsp");
                    break;
                }
                
                selection = null;
                selectionId = Integer.parseInt(request.getParameter(request.getParameter("flightSelection")));
                UserBean.setTable("Cart");
                
                try {
                    selection = VB.Select(false, selectionId, "", null, null, null, 0, -1);
                    selection.next();
                    placeNumber = selection.getInt("NbPlace") - Integer.parseInt(request.getParameter("placeNumber"));
                    
                    if(placeNumber >= 0)
                    {
                        VB.Update(selectionId, "", null, null, null, 0, -1, 0, "", null, null, null, 0, placeNumber);
                        
                        UserBean.setCondition("username = '" + session.getAttribute("logon.isDone") + "' and idVols = " + selectionId);
                        selection = UserBean.Select(false);
                        if(selection.next())
                        {
                            UserBean.setValues("Quantité = " + (selection.getInt("Quantité")+Integer.parseInt(request.getParameter("placeNumber"))));
                            UserBean.Update();
                        }
                        else
                        {
                            UserBean.setValues("('" + session.getAttribute("logon.isDone") + "', '" + selectionId + "', '" + request.getParameter("placeNumber") + "', '" + 0 + "')");
                            UserBean.Insert();
                        }
                    }
                    else
                    {
                        response.sendRedirect("badFlights.jsp");
                        break;
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(ControlServlet.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                try {
                    FB = new FlightBean();
                    FB.setFlightList(VB.Select());
                } catch (SQLException ex) {
                    Logger.getLogger(ControlServlet.class.getName()).log(Level.SEVERE, null, ex);
                }

                if(FB.getFlightList().isEmpty() == false)
                    session.setAttribute("flights", FB);
                else
                    session.setAttribute("flights", null);
                
                response.sendRedirect("flights.jsp");
            
                break;
                
            case "Delete" :
                
                if(request.getParameter("flightSelection") == null)
                {
                    response.sendRedirect("badCart.jsp");
                    break;
                }
                
                selection = null;
                UserBean.setTable("Cart");
                selectionList = request.getParameterValues("flightSelection");
                
                for (String selectionList1 : selectionList)
                {
                    selectionId = Integer.parseInt(request.getParameter(selectionList1));
                    try {
                        selection = VB.Select(false, selectionId, "", null, null, null, 0, -1);
                        selection.next();

                        UserBean.setCondition("username = '" + session.getAttribute("logon.isDone") + "' and idVols = " + selectionId);

                        ResultSet cartSelection = UserBean.Select(false);
                        cartSelection.next();
                        placeNumber = selection.getInt("NbPlace") + cartSelection.getInt("Quantité");

                        VB.Update(selectionId, "", null, null, null, 0, -1, 0, "", null, null, null, 0, placeNumber);

                        UserBean.Delete();
                    } catch (SQLException ex) {
                        Logger.getLogger(ControlServlet.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                
            case "Cancel" :
                
            case "Go to cart" :
                
                selection = null;
                UserBean.setTable("Cart");
                UserBean.setCondition("username = '" + session.getAttribute("logon.isDone") + "'");
                
                try {
                    FB = new FlightBean();
                    selection = UserBean.Select(false);
                    
                    while(selection.next())
                    {
                        FB.setCartFlightList(VB.Select(false, selection.getInt("idVols"), "", null, null, null, 0, -1), selection.getInt("Quantité"));
                    }
                    
                } catch (SQLException ex) {
                    Logger.getLogger(ControlServlet.class.getName()).log(Level.SEVERE, null, ex);
                }

                if(FB.getFlightList().isEmpty() == false)
                    session.setAttribute("cartList", FB);
                else
                    session.setAttribute("cartList", null);
                
                response.sendRedirect("Cart.jsp");
                    
                break;
                
            case "Go to payment page" :
                
                if(request.getParameter("flightSelection") == null)
                {
                    response.sendRedirect("badCart.jsp");
                    break;
                }
                
                selection = null;
                selectionList = request.getParameterValues("flightSelection");
                
                String idList = "";
                for(int i = 0 ; i < selectionList.length ; i++)
                {
                    idList += request.getParameter(selectionList[i]);
                    
                    if(i+1 < selectionList.length)
                        idList += ", ";
                }
                
                UserBean.setTable("Cart");
                UserBean.setCondition("username = '" + session.getAttribute("logon.isDone") + "' and idVols in (" + idList + ")");
                
                try {
                    FB = new FlightBean();
                    selection = UserBean.Select(false);
                    
                    while(selection.next())
                    {
                        FB.setCartFlightList(VB.Select(false, selection.getInt("idVols"), "", null, null, null, 0, -1), selection.getInt("Quantité"));
                    }
                    
                } catch (SQLException ex) {
                    Logger.getLogger(ControlServlet.class.getName()).log(Level.SEVERE, null, ex);
                }

                if(FB.getFlightList().isEmpty() == false)
                    session.setAttribute("cartList", FB);
                else
                    session.setAttribute("cartList", null);
                
                response.sendRedirect("Payment.jsp");
                    
                break;
                
            case "Proceed to payment" :
                FB = (FlightBean)request.getSession(true).getAttribute("cartList");
                ArrayList<String> flightList = new ArrayList<>();
                int size = 0;
                if(FB != null)
                {
                    flightList = FB.getFlightList();
                    size = flightList.size();
                }
                
                UserBean.setTable("Passagers");
                UserBean.setColumns("idVols, Nom, Prénom, DateDeNaissance");
                UserBean.setCondition("");
                UserBean2.setTable("Cart");
                
                int empty = 0;
                
                for(int i = 0 ; i < size ; i += 9)
                {
                    for(int j = 0 ; j < Integer.parseInt(flightList.get(i+6)) ; j++)
                    {
                        if(request.getParameter("last name+"+i+"+"+j).equals("") || request.getParameter("last name+"+i+"+"+j).equals("") || request.getParameter("birth date+"+i+"+"+j).equals(""))
                        {
                            empty = 1;
                            break;
                        }
                    }
                    if(empty == 1)
                        break;
                }
                
                if(empty==1 || request.getParameter("emailAddress").isEmpty())
                {
                    response.sendRedirect("badPayment.jsp");
                    break;
                }
                
                for(int i = 0 ; i < size ; i += 9)
                {
                    for(int j = 0 ; j < Integer.parseInt(flightList.get(i+6)) ; j++)
                    {
                        UserBean.setValues("(" + flightList.get(i) + ", '" + request.getParameter("last name+"+i+"+"+j) + "', '" + request.getParameter("first name+"+i+"+"+j) + "', '" + request.getParameter("birth date+"+i+"+"+j) + "')");
                        UserBean2.setCondition("username = '" + session.getAttribute("logon.isDone") + "' and idVols = " + flightList.get(i));
                        try {
                            UserBean.Insert();
                            UserBean2.Delete();
                        } catch (SQLException ex) {
                            Logger.getLogger(ControlServlet.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
                
//ENVOYER L'EMAIL ICI EN RÉCUPÉRANT L'EMAIL AVEC request.getParameter("emailAddress");
                
                selection = null;
                UserBean.setTable("Cart");
                UserBean.setColumns("");
                UserBean.setCondition("username = '" + session.getAttribute("logon.isDone") + "'");
                
                try {
                    FB = new FlightBean();
                    selection = UserBean.Select(false);
                    
                    while(selection.next())
                    {
                        FB.setCartFlightList(VB.Select(false, selection.getInt("idVols"), "", null, null, null, 0, -1), selection.getInt("Quantité"));
                    }
                    
                } catch (SQLException ex) {
                    Logger.getLogger(ControlServlet.class.getName()).log(Level.SEVERE, null, ex);
                }

                if(FB.getFlightList().isEmpty() == false)
                    session.setAttribute("cartList", FB);
                else
                    session.setAttribute("cartList", null);
                
                response.sendRedirect("Cart.jsp");
                    
                break;
                
            case "Log out" :
                response.sendRedirect("login.html");
                session.setAttribute("logon.isDone", null);
                session.setAttribute("login.target", null);
                break;
        }
        
    }
    
    private boolean UserExists(ResultSet UserTable, String username, String password, boolean isLogIn) throws SQLException
    {
        while(UserTable.next())
        {
            if(UserTable.getString("username").equals(username))
            {
                if(isLogIn == false)
                    return true;
                else return UserTable.getString("Password").equals(password);
            }
        }
        return false;
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
        
        HttpSession session = request.getSession(true);
        Object existe = session.getAttribute("logon.isDone");
        if(existe == null)
        {
            session.setAttribute("login.target", request.getRequestURL().toString());
            response.sendRedirect(request.getScheme() + "://" + request.getServerName()+ ":" + request.getServerPort() + "/Applic_Tickets");
            return;
        }
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
        
        HttpSession session = request.getSession(true);
        if(session.getAttribute("logon.isDone") == null)
            session.setAttribute("logon.isDone", request.getParameter("username"));
        
        String target = (String)session.getAttribute("login.target");
        if(target == null)
            processRequest(request, response);
        else
            response.sendRedirect(target);
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
