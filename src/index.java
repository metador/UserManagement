

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class index
 */
@WebServlet("/index.html")
public class index extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public index() {
       // super();
        
        // TODO Auto-generated constructor stub
    }
    ResultSet resultDB=null;
    Statement myDBStm=null;
    String dbName;
    Connection connection;
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
    @Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		 PrintWriter out = response.getWriter();
			// Incorporate mySQL driver
			try {
				Class.forName("com.mysql.jdbc.Driver").newInstance();
			

			 // Connect to MySQL as root
			connection = DriverManager.getConnection("jdbc:mysql://","root", "decodder");
			
			// Create and execute an SQL statement to get all the database names
			 myDBStm = connection.createStatement();
			 resultDB = myDBStm.executeQuery("show databases");
			
	out.print("<table><tr><td>+++++++++database+++++++++</td><td>\n**Table Name:** </td></tr>");
			while (resultDB.next())
			{
				dbName = resultDB.getString(1);
				
				out.println(" <tr><td>"+dbName+"</td>");
				//only get information inside the "moviedb" database
				
					//first, we need to switch to this database;
				//if((dbName.compareTo("information_schema")) !=0  &&   (dbName.compareTo("performance_schema")!=0) )
				
				{
					Statement mySWStm = connection.createStatement();
					mySWStm.execute("use "+dbName);
					//Create and execute an SQL statement to get all the table names in moviedb
					Statement myTBStm = connection.createStatement();
					ResultSet resultTB = myTBStm.executeQuery("show tables");
					//myStm.close();			
					String tblName;
					ResultSet ColData;
					Statement myColStm;
					out.println("<td>" );
					int count =0;
					while (resultTB.next() && count < 25)
					{
						count++;
						tblName = resultTB.getString(1);
						
						out.println(  " "+tblName+"" );
					
									
					}
					out.println(  "</td></tr> <tr><td> <br></td></tr> ");
				}
				
			}
			out.println(  "</table> ");
			myDBStm = connection.createStatement();
			ResultSet rs_user = myDBStm.executeQuery("select * from mysql.user");
			out.print("<table><tr><td>Users  </td><td>  Select</td><td>  insert </td><td>  Delete </td><td>  Grant </td><td><tr>  ");
			while(rs_user.next()){
				String user = rs_user.getString(2);
				String select = rs_user.getString("Select_priv");
				String insert = rs_user.getString("Insert_priv");
				String Delete = rs_user.getString("Delete_priv");
				String grant = rs_user.getString("Grant_priv");
				out.print(" <tr><td><a href=/UserManagement/addUser.html?user="+user+"> "+user +"<a> </td><td> "+select+" </td><td> "+ insert +"</td><td> "+ Delete +"</td><td> "+ grant+" </td><td> <tr> ");
				
			}
			out.print("</table>");
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    }	
		

	


	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	*/
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub  
		doPost(request,response);
	} 

}
