<%-- 
    Document   : badPayment
    Created on : 27-nov.-2021, 15:22:31
    Author     : student
--%>

<%@page import="SessionBeans.FlightBean"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<% FlightBean FB = (FlightBean)request.getSession(true).getAttribute("cartList");
ArrayList<String> flightList = null;
int size = 0, total = 0;
if(FB != null)
{
    flightList = FB.getFlightList();
    size = flightList.size();
} %>

<html lang="en">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body text="#dddddd" bgcolor="#353535">
        <h4 align="center" style="color: red">Please fill all fields</h4>
        <form method="POST" action="ControlServlet">
            <div style=" background-color: gray; justify-content: space-around">
                <h1 align="right">
                    <input type="submit" name="action" value="Go to flight list"/>
                    <input type="submit" name="action" value="Go to cart"/>
                    <input type="submit" name="action" value="Log out"/>
                    <input type="button" disabled="true" style="background-color: gray; border: gray"/>
                </h1>
                <h1>‎</h1>
                
            </div>
            <div>
                <%
                    for(int i = 0 ; i < size ; i += 9)
                    {
                        total += Integer.parseInt(flightList.get(i+8));
                %>
                
                <div style="display:flex; flex-direction: row; align-items: baseline; justify-content: space-around; background-color: darkslategray">
                    <input type="hidden" name="<%= i %>" value="<%= flightList.get(i) %>"/>
                    <h4> Destination : <%= flightList.get(i+1) %> </h4>
                    <h4> Départ : <%= flightList.get(i+2) %> </h4>
                    <h4> Arrivée : <%= flightList.get(i+3) %> </h4>
                </div>
                
                <%
                        for(int j = 0 ; j < Integer.parseInt(flightList.get(i+6)) ; j++)
                        {
                %>
                
                <h4 align="center">
                    <h4> Place <%= j+1 %> : </h4>
                    <B>Last name : </B><INPUT type="text" name="last name+<%= i %>+<%= j %>" size=30>
                    <B>First name : </B><INPUT type="text" name="first name+<%= i %>+<%= j %>" size=30>
                    <B>Birth date : </B><INPUT type="date" name="birth date+<%= i %>+<%= j %>" size=30>
                </h4>
                <h4>‎</h4>
                
                <%
                        }
                    }
                %>
                
                <B>E-mail address : </B><INPUT type="text" name="emailAddress" size=30>
                
                <h1> Prix total : <%= total %> </h1>

                <div style="display:flex; flex-direction: row; align-items: baseline; justify-content: space-around">
                    <h1>
                        <input type="submit" name="action" value="Cancel" size=10>
                    </h1>
                    <h1>
                        <input type="submit" name="action" value="Proceed to payment" size=10>
                    </h1>
                </div>

            </div>
        </form>
    </body>
</html>
