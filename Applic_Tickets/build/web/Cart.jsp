<%-- 
    Document   : Cart
    Created on : 27-nov.-2021, 15:22:31
    Author     : student
--%>

<%@page import="SessionBeans.FlightBean"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<% FlightBean FB = (FlightBean)request.getSession(true).getAttribute("cartList");
ArrayList<String> flightList = null;
int size = 0;
if(FB != null)
{
    flightList = FB.getFlightList();
    size = flightList.size();
} %>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body text="#dddddd" bgcolor="#353535">
        <form method="POST" action="ControlServlet">
            <div style=" background-color: gray; justify-content: space-around">
                <h1 align="right">
                    <input type="submit" name="action" value="Go to flight list"/>
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
                            <th>Places</th>
                            <th>Prix par place</th>
                        </tr>
                    </thead>
                    <tbody>
                        <%
                            for(int i = 0 ; i < size ; i += 9)
                            {
                        %>
                            <tr>
                                <td><input type="checkbox" name="flightSelection" value="<%= i %>"/></td>
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
                        <input type="submit" name="action" value="Go to payment page" size=10>
                    </h1>
                    <h1>
                        <input type="submit" name="action" value="Delete" size=10>
                    </h1>
                </div>

            </div>
        </form>
    </body>
</html>
