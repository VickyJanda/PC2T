package projekt;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Connect {
	
	private Connection conn; 
	public boolean connect() { 
	       conn= null; 
	       try {
	    	     //Class.forName("org.sqlite.JDBC");
	              conn = DriverManager.getConnection("jdbc:sqlite:App.db");                       
	       } 
	      catch (SQLException e) { 
	            System.out.println(e.getMessage());
		    return false;
	      } 
	      return true;
	}
	public boolean createTable()
	{
	     if (conn==null)
	           return false;
	    String sql = "CREATE TABLE IF NOT EXISTS books (" + "id integer PRIMARY KEY," + "nazev varchar(255) NOT NULL," + "autor varchar(255) NOT NULL,"+ "rok int, " + "dostupnost varchar(255) NOT NULL, " + "typ varchar(255) NOT NULL" + ");";
	    try{
	            Statement stmt = conn.createStatement(); 
	            stmt.execute(sql);
	            return true;
	    } 
	    catch (SQLException e) {
	    System.out.println(e.getMessage());
	    }
	    return false;
	}
	
	public void insertRecord(String nazev, String autor,int rok, String dostupnost,String typ) {
        String sql = "INSERT INTO books(nazev,autor,rok,dostupnost,typ) VALUES(?,?,?,?,?)";
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql); 
            pstmt.setString(1, nazev);
            pstmt.setString(2, autor);
            pstmt.setInt(3, rok);
            pstmt.setString(4, dostupnost);
            pstmt.setString(5, typ);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
	
	public void selectAll(){
        String sql = "SELECT id,nazev,autor,rok,dostupnost,typ FROM books";
        try {
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql);
             while (rs.next()) {
                	System.out.println(rs.getInt("id") +  "\t" + 
                			rs.getString("nazev") +  "\t" +  
                			rs.getString("autor") + "\t" + 
                			rs.getInt("rok") + "\t" + 
                			rs.getString("dostupnost") + "\t" + 
                			rs.getString("typ"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
	}
	
	public ResultSet selectAllresult(){
        String sql = "SELECT id,nazev,autor,rok,dostupnost,typ FROM books";
        try {
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql);
             return rs;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
		return null;
      
	}
	
	public void deleteTable() {
        String sql = "DROP TABLE books";

        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
          
            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

	public void disconnect() { 
		if (conn != null) {
		       try {     conn.close();  } 
	               catch (SQLException ex) { System.out.println(ex.getMessage()); }
		}
	}


}


