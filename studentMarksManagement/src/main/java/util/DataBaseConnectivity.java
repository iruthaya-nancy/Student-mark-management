package util;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.lang.ClassNotFoundException;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import customException.DbConnectionException;


public class DataBaseConnectivity {
   

	public static Connection getdbConnectivity() throws DbConnectionException{
		Logger logger = LogManager.getLogger(DataBaseConnectivity.class);
		Connection connection = null;
		try {
			FileInputStream ifs = new FileInputStream("C:\\Users\\IruthayaNancy\\Documents\\new\\Connection.prop");
			Properties p = new Properties();
			p.load(ifs);
			String DriverName = (String) p.get("Dname");
			String url = (String) p.get("Url");
			String UserName = (String) p.get("User");
			String PassWord = (String) p.get("Password");
			Class.forName(DriverName);
			connection = DriverManager.getConnection(url, UserName, PassWord);
			}
		
		catch(IOException | ClassNotFoundException exception) {
			logger.error(exception.getMessage(),exception);
			throw new DbConnectionException(exception);
		}
		
		catch(Exception exception) {
			exception.printStackTrace();
		}
		return connection;
		
	}
	
	
	
	
	
	}
