/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SessionBeans;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author student
 */
public class FlightBean implements Serializable {
    
    private final ArrayList<String> flightList;
    
    public FlightBean() {
        flightList = new ArrayList<>();
    }
    
    public void setFlightList(ResultSet res) throws SQLException {
        
        while(res.next())
        {
            for(int i = 1 ; i <= res.getMetaData().getColumnCount() ; i++)
                flightList.add(res.getString(i));
        }
    }
    
    public ArrayList<String> getFlightList() {
        return flightList;
    }
    
    public void setCartFlightList(ResultSet res, int seat) throws SQLException
    {
        while(res.next())
        {
            for(int i = 1 ; i <= res.getMetaData().getColumnCount() ; i++)
            {
                if((i%7) != 0)
                    flightList.add(res.getString(i));
                else
                    flightList.add(Integer.toString(seat));
            }
        }
    }
    
}
