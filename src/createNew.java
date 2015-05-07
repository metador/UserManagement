

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
 * Servlet implementation class createNew
 */
@WebServlet("/createNew")
public class createNew extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Connection connection;
    String database=" ";
    private static String User = null;
    private static String Pass = null;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public createNew() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		 PrintWriter out = response.getWriter();
			// Incorporate mySQL driver
			try {
				Class.forName("com.mysql.jdbc.Driver").newInstance();
			

			 // Connect to MySQL as root
			connection = DriverManager.getConnection("jdbc:mysql://","testuser", "testpass");

			 String message = request.getParameter("message");
				// Create and execute an SQL statement to get all the database names
				out.println("<HEAD><TITLE>Create New User</TITLE></HEAD>");
				

				 out.println("<div id='login'><div style='float:left;width:35%'>"
				 		+ " "
						 + "<br>"
				 		+ "<div style='float:right;width:55%'><H2 ALIGN=\"CENTER\">Login</H2><FORM =\"/UserManagement/\" METHOD=\"POST\">"
				 		+ "<center> Username: <INPUT id='login_field' TYPE='TEXT' NAME=\"Username\"><BR><Br> Password: <INPUT id='login_field' TYPE=\"PASSWORD\" NAME=\"password\"></center><BR><BR><BR>"
				 		+ " <CENTER><button class='login_btn' type='submit' style='font-size:20px;width:60%;'>"
					+ "<img src='http://goo.gl/wwTkAq?gdriveurl' height='24' width='24'>Confirm</button></center></div></div></div>");

				 if(message !=null)
					 out.println("<br><div><center><span style=\"color:red;fonts-size:50px;font-weight:bold;\">" + message + "</span></center></div>");
				
				    // Output stream to STDOUT
				  
					Statement statement = connection.createStatement();
					User =request.getParameter("Username");
					Pass =request.getParameter("password");
					if(User !=null && Pass !=null)
					{
				   String query = "create  user'"+ User +"'@'localhost' identified by'"+ Pass +"';";
					int rs = statement.executeUpdate(query);
						
				          // Iterate through each row of rs
				      //    if(message !=null)
						 if (rs==0)
						 {
							
						    
							 response.sendRedirect("/UserManagement/addUser.html?user="+User);   
						   	
						    
						  //  if (Page.equals("/UserManagement/index.html") || Page.equals("/UserManagement/Confirm") || Page.equals("/UserManagement/index.html?message=Username%20or%20password%20incorrect.%20Please%20try%20Again!"))
						  //  	Page = "/UserManagement/Main";
					//	    out.print("this is temp value=trddyt"+(String)session.getAttribute("Page"));
						    
						  //  response.sendRedirect("/index");
						    
						    
							 
						 }else{
						
							 String mess="User already exsists. Please try Again!";
				        response.sendRedirect("/UserManagement/createNew?message="+mess);  
				          }
				         
				         
					} 
	
			} catch (SQLException e) {
				String mess="User already exsists. Please try Again!";
				response.sendRedirect("/UserManagement/createNew?message="+mess); 
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request,response);
	}

}
