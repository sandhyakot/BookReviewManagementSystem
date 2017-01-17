package bms.common;

import java.sql.*;
import javax.sql.*;
import java.util.Date;
import java.util.*;
import java.io.*;

public class BMSDBManager
{	
	Connection con = null;
	
	public BMSDBManager()
	{
		
	}
	
	public void openConnection()
	{
			try
			{			
				String driverName="com.mysql.jdbc.Driver";
				System.out.println("Loading Driver ......."+driverName);
				Class.forName(driverName);
				System.out.println("Driver Loaded inside DBManager getConnection Method"+driverName);			
			}catch(ClassNotFoundException cnfe)
			{
				System.out.println("Class Not Found Exception Has Occured");
			}
			try
			{
				String url="jdbc:mysql://localhost:3306/cybage";			
				con=DriverManager.getConnection(url,"root","root");
				System.out.println("Conection Established inside OpenConnection"+url);
			}catch(SQLException sqle)
			{
				System.out.println("SQL Exception Has Occured"+sqle);
			}
		}
	
	
		public Connection getConnection()
		{
			try
			{
				String driverName="com.mysql.jdbc.Driver";
				Class.forName(driverName);
			}catch(ClassNotFoundException cnfe)
			{
				//System.out.println("Class Not Found Exception Has Occured");
			}
			try
			{
				String url="jdbc:mysql://localhost:3306/cybage";
				Connection con1=DriverManager.getConnection(url,"root","root");
				return con1;
			}catch(SQLException sqle)
			{
				//System.out.println("SQL Exception Has Occured");
				return null;
			}
	
		}
	/******************************************************************************/
			// Method Which Closes the Connection
	/******************************************************************************/
		public void closeConnection()
		{
			try
			{
				if(con!=null)
				{
					con.close();
					con = null;
				}
				
			}catch(SQLException sql)
			{
				//System.out.println("Error While Closing Connection");
			}
		}
		
	public int getPKID(String strTblName,String strFieldName)
	{
		if(con==null)
			openConnection();
		System.out.println("Inside getPKID");
		int id=0;
		try
		{
			String sql="select max(" + strFieldName + ") from "+strTblName;
			System.out.println("Inside getPKID"+strTblName);
			ResultSet tmpRs=getResultSet(sql);
			if(tmpRs.next())
			{
				id=tmpRs.getInt(1);
				id=id+1;
				System.out.println("Inside IF MAX ID ="+id);
			}
			else
			{
				id=1;
				System.out.println("Inside ELSE MAX ID ="+id);
			}
		}catch(Exception e)
		{
			System.out.println("Exception Ocuured inside getPKID Function"+e);
		}

		return id;
	}
	/******************************************************************************/
			// Method Which is Used For Queries like Insert, Update, Delete
	/******************************************************************************/
		public boolean executeQuery(String SQ)
		{
			//System.out.println("Inside ExecuteQuery"+SQ);
			int rowsAffected=0;
			try
			{
				if(con==null)
					openConnection();
			//	System.out.println("Connection Est Inside ExecuteQuery"+con);
				Statement stat=con.createStatement();
				rowsAffected=stat.executeUpdate(SQ);
			//	System.out.println("Row Affected Inside ExecuteQuery"+SQ+".... ROW AFFCTED.........."+rowsAffected);
			}catch(SQLException sqle)
			{
				System.out.println("Exeption executeQuery ....."+sqle);
			}
	
	
				if(rowsAffected>=1)
					return true;
				else
					return false;
		}

	
/******************************************************************************/
		// Method Which is Used to Get Result Set   For Select Query
/******************************************************************************/
	public ResultSet getResultSet(String SQ)
	{
		ResultSet rs = null;
		try
		{
			if(con==null)
				openConnection();
			System.out.println("Test1...con...."+con);
			Statement stat=con.createStatement();
			System.out.println("Test2...."+SQ);
			rs=stat.executeQuery(SQ);
		}catch(SQLException sqle)
		{
			System.out.println("SQL Exception Inside getResultSet"+sqle);
		}

		return rs;
	}

}