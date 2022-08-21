/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author student
 */
public class Lanes implements Serializable
{
    public int idLane;
    public String nameLane;
    public boolean isLaneOccupied;
    
    public Lanes(int id, String name, boolean isOccupied){
        this.idLane = id;
        this.nameLane = name;
        this.isLaneOccupied = isOccupied;
    }
    
    public Lanes(ResultSet rs) throws SQLException{
        this.idLane = rs.getInt(1);
        this.nameLane = rs.getString(2);
        this.isLaneOccupied = rs.getBoolean(3);
    }
    
}
