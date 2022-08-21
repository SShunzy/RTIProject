<%-- 
    Document   : badFlights
    Created on : 12-nov.-2021, 22:34:40
    Author     : student
--%>

<%@page import="java.util.ArrayList"%>
<%@page import="SessionBeans.FlightBean"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<% FlightBean FB = (FlightBean)request.getSession(true).getAttribute("flights");
ArrayList<String> flightList = null;
int size = 0;
if(FB != null)
{
    flightList = FB.getFlightList();
    size = flightList.size();
} %>

<!--jsp:useBean id="flights" class="SessionBeans.FlightBean" scope="session"-->

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body text="#dddddd" bgcolor="#353535">
        
        <h4 align="center" style="color: red">Please select a flight and enter a valid number of seats to buy</h4>
        
        <form method="POST" action="ControlServlet">
            <div style=" background-color: gray; justify-content: space-around">
                <h1 align="right">
                    <input type="submit" name="action" value="Go to cart"/>
                    <input type="submit" name="action" value="Log out"/>
                    <input type="button" disabled="true" style="background-color: gray; border: gray"/>
                </h1>
                <h1>‎</h1>
                
            </div>
            <div style="display:flex; flex-direction: row; align-items: baseline; justify-content: space-around">
                <table border="1" id="table">
                    <thead>
                        <tr>
                            <th>Sélection</th>
                            <th>Destination</th>
                            <th>Départ</th>
                            <th>Arrivée</th>
                            <th>Places restantes</th>
                            <th>Prix</th>
                        </tr>
                    </thead>
                    <tbody>
                        <%
                            for(int i = 0 ; i < size ; i += 9)
                            {
                        %>
                            <tr>
                                <td><input type="radio" name="flightSelection" value="<%= i %>"/></td>
                                <input type="hidden" name="<%= i %>" value="<%= flightList.get(i) %>"/>
                                <td> <%= flightList.get(i+1) %> </td>
                                <td> <%= flightList.get(i+2) %> </td>
                                <td> <%= flightList.get(i+3) %> </td>
                                <td> <%= flightList.get(i+6) %> </td>
                                <td> <%= flightList.get(i+8) %> </td>
                            </tr>
                        <%
                            }
                        %>
                    </tbody>
                </table>

                <div>
                    <h1>
                        <input align="center" type="number" placeholder="Number of seats" name="placeNumber" />
                    </h1>
                    <h1>
                        <input type="submit" name="action" value="Add to cart" size=10>
                    </h1>
                </div>

            </div>
        </form>
    </body>
</html>
