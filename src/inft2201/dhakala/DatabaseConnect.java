/**
 * 
 * @author Ashesh Dhakal
 * @version 1.0
 * @since 1.0
 * date: Feb 16, 2024
 */
package inft2201.dhakala;

import java.sql.*;

/**
 * DatabaseConnect - Manages database connectivity for the INFT2201 course.
 * This class handles opening and closing connections to the PostgreSQL database.
 * 
 * @author Ashesh Dhakal
 * @version 3.0 (02/16/2024)
 * @since 1.0
 */
public class DatabaseConnect
{
	
    /**
     * JDBC URL for the PostgreSQL database.
     */
    static String url = "jdbc:postgresql://127.0.0.1:5432/inft2201_db";

    /**
     * Connection object to manage the connection to the database.
     */
    static Connection aConnection;

    /**
     * Database user name.
     */
    static String user = "inft2201_admin";

    /**
     * Database user password.
     */
    static String password = "inft2201_password"; 

    /**
     * Establishes the database connection.
     * 
     * @return Connection to the inft2201_db database.
     */
    public static Connection initialize()
    {
        try
        {   
            Class.forName("org.postgresql.Driver");

            aConnection = DriverManager.getConnection(url, user, password);
        }
        catch (ClassNotFoundException e)  
        {
            System.out.println("PostgreSQL JDBC Driver not found: " + e.getMessage());
        }
        catch (SQLException e)  // Handles any other database connection error
        {
            System.out.println("Database connection failed: " + e.getMessage());
        }
        return aConnection;
    }

    
    /**
     * Closes the database connection.
     */
    public static void terminate()
	{
		try
 		{
    		aConnection.close();
		}
		catch (SQLException e)
			{ System.out.println(e);	}
	}
    
}
