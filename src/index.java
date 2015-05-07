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
	headerFooter base = new headerFooter();

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public index() {
		// super();

		// TODO Auto-generated constructor stub
	}

	ResultSet resultDB = null;
	Statement myDBStm = null;
	String dbName;
	Connection connection;

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		PrintWriter out = response.getWriter();
		// Incorporate mySQL driver
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();

			// Connect to MySQL as root
			connection = DriverManager.getConnection("jdbc:mysql://",
					"testuser", "testpass");

			// Create and execute an SQL statement to get all the database names
			myDBStm = connection.createStatement();
			resultDB = myDBStm.executeQuery("show databases");
			out.println(base.header());
			out.println(base.banner());

			out.println("<br><br><div class='error_head'><span>Databases: </span></div>");
			out.println("<table  cellpadding='10' id='cart_res'><tr style='background-color:#00CCFF;' align='left'>"
					+ "<th>Schema Name</th>" + "<th>Columns</th></tr>");
			while (resultDB.next()) {
				dbName = resultDB.getString(1);
				out.println("<tr style='color:brown;'><td>" + dbName + "</td>");
				{
					Statement mySWStm = connection.createStatement();
					mySWStm.execute("use " + dbName);
					// Create and execute an SQL statement to get all the table
					// names in moviedb
					Statement myTBStm = connection.createStatement();
					ResultSet resultTB = myTBStm.executeQuery("show tables");
					// myStm.close();
					String tblName;
					ResultSet ColData;
					Statement myColStm;
					int count = 0;
					String tnames = "";
					while (resultTB.next() && count < 25) {
						count++;
						tblName = resultTB.getString(1);
						tnames += tblName;
						tnames += ", ";
					}
					tnames = tnames.substring(0, tnames.length() - 2);
					out.println("<td>" + tnames + "</td></tr>");
				}
			}
			out.println("</table><br>");

			myDBStm = connection.createStatement();
			ResultSet rs_user = myDBStm
					.executeQuery("select * from mysql.user");
			out.println("<br><br><div id='nostar' class='error_head'><span>Users & Privileges: </span>"
					+ "<a class='cart_btn' href='/UserManagement/createNew'>"
					+ "<img src='http://goo.gl/wwTkAq?gdriveurl' height='24' width='24'>Create New User</a></div>");
			out.println("<table  cellpadding='10' id='cart_res'><tr style='background-color:#00CCFF;' align='left'>"
					+ "<th>Users</th>" 
					+ "<th>Select</th>" 
					+ "<th>Insert</th>" 
					+ "<th>Delete</th>" 
					+ "<th>Grant</th></tr>");
			while (rs_user.next()) {
				String user = rs_user.getString(2);
				String select = rs_user.getString("Select_priv");
				String insert = rs_user.getString("Insert_priv");
				String Delete = rs_user.getString("Delete_priv");
				String grant = rs_user.getString("Grant_priv");
				out.println("<tr style='color:brown;'><td><a href='/UserManagement/addUser.html?user="+ user + "'>" + user + "</a></td>");
				out.println("<td>" + select + "</td>");
				out.println("<td>" + insert + "</td>");
				out.println("<td>" + Delete + "</td>");
				out.println("<td>" + grant + "</td></tr>");
			}
			out.println("</table><br>");
			out.println(base.footer());

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
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}

}
