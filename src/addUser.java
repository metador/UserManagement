
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
@WebServlet("/addUser.html")
public class addUser extends HttpServlet {
	private static final long serialVersionUID = 1L;
	headerFooter base = new headerFooter();

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public addUser() {
		// super();

		// TODO Auto-generated constructor stub
	}

	ResultSet resultDB = null;
	Statement myDBStm = null;
	String dbName;
	String tblName = null;
	String rtName = null;
	String ftName = null;
	Connection connection;
	String database = " ";
	private static String User = null;
	private static String Pass = null;
	String statement = null;
	String colName = null;
	String select = null;
	String insert = null;

	String delete = null;
	String alter = null;
	String execute = null;
	String grant = null;
	String all = null;
	String user = "melwin";

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		PrintWriter out = response.getWriter();
		// Incorporate mySQL driver
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();

			// Connect to MySQL as root
			connection = DriverManager.getConnection("jdbc:mysql://",
					"testuser", "testpass");

			String message = request.getParameter("message");
			// Create and execute an SQL statement to get all the database names
			user = request.getParameter("user");
			out.println(base.header());
			out.println("<HEAD><TITLE>Add / Remove privileges for " + user
					+ "</TITLE></HEAD>");
			out.println(base.banner());

			out.println("<br>"
					+ "<H2 ALIGN=\"CENTER\">Add/Remove privileges for '" + user + "'</H2>"
					+ "<FORM ='/UserManagement/' METHOD=\"GET\">");

			if (message != null)
				out.println("<br><div><center><span style=\"color:Black;fonts-size:50px;font-weight:bold;\">"
						+ message + "</span></center>=");

			out.println("<input type=\"hidden\" name=\"user\" value=" + user + ">");
			// Output stream to STDOUT

			out.println("<br><br><div class='error_head' style='height:40px;border-radius: 8px 8px 8px 8px;'><span>Privileges: </span>"
			+ "<button class='cart_btn' type='submit'>"
			+ "<img src='http://goo.gl/wwTkAq?gdriveurl' height='24' width='24'>Next</button></div><br>");
			out.println("<span style='padding-left:20%;'>Grant or Revoke Privileges: </span><br>"
					+ "<table  cellpadding='10' id='cart_res'><tr style='background-color:#00CCFF;' align='left'>");
			grant = request.getParameter("prev");
			if (grant == null) {
				out.println(" <td> <input type=\"radio\" name=\"prev\" value=\"grant\" checked>Grant</td>");

				out.println(" <td> <input type=\"radio\" name=\"prev\" value=\"revoke\" >Revoke</td>");
			} else {
				if (grant.contains("revoke")) {
					out.println(" <td> <input type=\"radio\" name=\"prev\" value=\"grant\">Grant</td>");
					out.println(" <td> <input type=\"radio\" name=\"prev\" value=\"revoke\" checked>Revoke</td>");
				} else {
					out.println(" <td> <input type=\"radio\" name=\"prev\" value=\"grant\" checked>Grant</td>");
					out.println(" <td> <input type=\"radio\" name=\"prev\" value=\"revoke\" >Revoke</td>");
				}
			}
			out.print("</tr></table>");
			
			out.println("<span style='padding-left:20%;'>Select Aspect of Privileges: </span><br>"
					+ "<table  cellpadding='10' id='cart_res'><tr style='background-color:#00CCFF;' align='left'>");
			all = request.getParameter("all");
			if (all == null)
				out.println(" <td> <input type=\"checkbox\" name=\"all\" value=\"*\">All</td>");
			else
				out.println(" <td> <input type=\"checkbox\" name=\"all\" value=\"*\" checked>All</td>");
			select = request.getParameter("select");

			if (select == null)
				out.println(" <td> <input type=\"checkbox\" name=\"select\" value=\"select\">Select</td>");
			else
				out.println(" <td> <input type=\"checkbox\" name=\"select\" value=\"select\" checked>Select</td>");
			insert = request.getParameter("insert");
			if (insert == null)
				out.println(" <td> <input type=\"checkbox\" name=\"insert\" value=\"insert\">Insert</td>");
			else
				out.println(" <td> <input type=\"checkbox\" name=\"insert\" value=\"insert\" checked>Insert</td>");
			alter = request.getParameter("alter");
			if (alter == null)
				out.println(" <td> <input type=\"checkbox\" name=\"alter\" value=\"alter\">Alter</td>");
			else
				out.println(" <td> <input type=\"checkbox\" name=\"alter\" value=\"alter\" checked>Alter</td>");
			delete = request.getParameter("delete");
			if (delete == null)
				out.println(" <td> <input type=\"checkbox\" name=\"delete\" value=\"delete\">Delete</td>");
			else
				out.println(" <td> <input type=\"checkbox\" name=\"delete\" value=\"delete\" checked>Delete</td>");
			execute = request.getParameter("execute");
			if (execute == null)
				out.println(" <td> <input type=\"checkbox\" name=\"execute\" value=\"execute\">Execute</td>");
			else {
				dbName = request.getParameter("dbName");
				out.println(" <td> <input type=\"checkbox\" name=\"execute\" value=\"execute\" checked>Execute</td>");
				if (dbName != null) {

				}
			}
			out.print("</tr></table>");

			// / DB names

			Statement statement = connection.createStatement();
			User = request.getParameter("Username");
			Pass = request.getParameter("password");
			myDBStm = connection.createStatement();
			String mode = request.getParameter("mode");
			resultDB = myDBStm.executeQuery("show databases");
			// Perform the query
			out.println("<span style='padding-left:20%;'>Choose the Database: </span><br>"
					+ "<table  cellpadding='10' id='cart_res'><tr style='background-color:#00CCFF;' align='left'>");
			int count = 0;
			String temp = null;
			temp = request.getParameter("dbname");
			out.println(" <td><input type=\"radio\" name=\"dbname\" value=\"*\" >All</td>");
			while (resultDB.next()) {

				dbName = resultDB.getString(1);

				// only get information inside the "moviedb" database

				// first, we need to switch to this database;

				if ((dbName.compareTo("information_schema")) != 0
						&& (dbName.compareTo("performance_schema") != 0)
						&& (dbName.compareTo("mysql") != 0))

				{
					if (temp != null && temp.compareTo(dbName) == 0)
						out.println(" <td> <input type=\"radio\" name=\"dbname\" value="
								+ dbName + " checked>" + dbName + "</td>");
					else
						out.println(" <td> <input type=\"radio\" name=\"dbname\" value="
								+ dbName + " >" + dbName + "</td>");

				}

			}
			out.println("</tr></table>");
			int count_tb = 0;

			// Table Names
			execute = request.getParameter("execute");
			if (execute == null) {

				do {
					count--;
					Statement mySWStm = connection.createStatement();
					dbName = request.getParameter("dbname");

					if (dbName != null) {
						out.println("<span style='padding-left:20%;'>Select Database: </span><br>"
								+ "<table  cellpadding='10' id='cart_res'><tr style='background-color:#00CCFF;' align='left'>");
						if (dbName.contains("*"))
							privilegesOnDB(request, response);
						out.println("<input type=\"hidden\" name=\"dbname\" value="
								+ dbName + ">");
						mySWStm.execute("use " + dbName);
						// Create and execute an SQL statement to get all the
						// table names in moviedb
						Statement myTBStm = connection.createStatement();
						ResultSet resultTB = myTBStm
								.executeQuery("show tables");
						ResultSet ColData;
						Statement myColStm;
						count_tb = 0;
						temp = null;
						temp = request.getParameter("tblname");
						out.println("<td><input type=\"radio\" name=\"tblname\" value=\"*\" >All</td>");

						while (resultTB.next()) {
							tblName = resultTB.getString(1);

							if (temp != null && temp.compareTo(tblName) == 0)
								out.println(" <td> <input type=\"radio\" name=\"tblname\" value="
										+ tblName
										+ " checked>"
										+ tblName
										+ "</td>");
							else
								out.println(" <td> <input type=\"radio\" name=\"tblname\" value="
										+ tblName + " >" + tblName + "</td>");

						}

						out.println("</tr></table>");
						// break;
					}
				} while (count >= 0);

				// Column names

				database = request.getParameter("dbname");
				if (database != null) {

					Statement mySWStm = connection.createStatement();
					tblName = request.getParameter("tblname");
					if (tblName != null) {
						out.println("<span style='padding-left:20%;'>Columns for Selected Database: </span><br>"
								+ "<table  cellpadding='10' id='cart_res'><tr style='background-color:#00CCFF;' align='left'>");
						if (tblName.contains("*"))
							privilegesOnDB(request, response, database);
						mySWStm.execute("use " + database);
						// Create and execute an SQL statement to get all the
						// table names in moviedb
						Statement myTBStm = connection.createStatement();
						ResultSet resultTB = myTBStm
								.executeQuery("show tables");
						tblName = request.getParameter("tblname");
						myTBStm = connection.createStatement();
						ResultSet ColData = myTBStm.executeQuery("describe "
								+ tblName);
						Statement myColStm;
						int count_col = 0;
						out.println("<td><input type=\"radio\" name=\"colname\" value=\"*\">All</td>");

						while (ColData.next()) {
							count_col++;
							colName = ColData.getString(1);

							out.println("<td><input type=\"radio\" name=\"colname\" value="
									+ colName + ">" + colName + "</td>");

							// out.println(
							// "</td></tr> <tr><td> <br></td></tr> ");

						}
						out.println(" </tr></table>");
					}
				}
				
				colName = request.getParameter("colname");
				if (colName != null) {
					if (colName.contains("*")) {
						privilegesOnDB(request, response, dbName, tblName);
					} else
						privilegesOnDB(request, response, dbName, tblName,
								colName);
				}
			} else {

				do {
					count--;
					Statement mySWStm = connection.createStatement();
					dbName = request.getParameter("dbname");

					if (dbName != null) {

						out.println("<input type=\"hidden\" name=\"dbname\" value="
								+ dbName + ">");
						mySWStm.execute("use " + dbName);
						// Create and execute an SQL statement to get all the
						// table names in moviedb
						Statement myTBStm = connection.createStatement();
						ResultSet resultRT = myTBStm
								.executeQuery("show procedure status");
						ResultSet ColData;
						Statement myColStm;
						out.println("<span style='padding-left:20%;'>Procedures: </span><br>"
								+ "<table  cellpadding='10' id='cart_res'><tr style='background-color:#00CCFF;' align='left'>");
						count_tb = 0;
						temp = null;
						temp = request.getParameter("proc");

						out.println(" <td><input type=\"radio\" name=\"proc\" value=\"*\" >All</td>");

						while (resultRT.next()) {
							rtName = resultRT.getString(2);

							if (temp != null && temp.compareTo(rtName) == 0)
								out.println(" <td> <input type=\"radio\" name=\"proc\" value="
										+ rtName
										+ " checked>"
										+ rtName
										+ "</td>");
							else
								out.println(" <td> <input type=\"radio\" name=\"proc\" value="
										+ rtName + " >" + rtName + "</td>");

						}

						out.println(" </tr></table>");
						
						Statement myfunct = connection.createStatement();
						ResultSet resultFt = myfunct
								.executeQuery("show function status");
						count_tb = 0;
						out.println("<span style='padding-left:20%;'>Functions: </span><br>"
								+ "<table  cellpadding='10' id='cart_res'><tr style='background-color:#00CCFF;' align='left'>");
						temp = null;
						temp = request.getParameter("proc");

						out.println(" <td><input type=\"radio\" name=\"func\" value=\"*\" >All</td>");

						while (resultFt.next()) {
							ftName = resultFt.getString(2);

							if (temp != null && temp.compareTo(rtName) == 0)
								out.println(" <td> <input type=\"radio\" name=\"func\" value="
										+ ftName
										+ " checked>"
										+ ftName
										+ "</td>");
							else
								out.println(" <td> <input type=\"radio\" name=\"func\" value="
										+ ftName + " >" + ftName + "</td>");

						}

						out.println("</tr></table>");
						// break;
					}
				} while (count >= 0);

				String proc = request.getParameter("proc");
				if (proc != null) {
					privilegesOnProc(request, response, proc);
				}

				String func = request.getParameter("func");
				if (func != null) {
					privilegesOnFunc(request, response, func);
				}

			}

		} catch (SQLException e) {
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

	void privilegesOnDB(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		String query = " ";
		grant = request.getParameter("prev");
		query += query + grant;
		String to_f = null;
		if (grant.contains("grant"))
			to_f = " to ";
		else
			to_f = " from ";
		if (all != null) {
			query += " all privileges  ";
		} else {
			if (select != null)
				query += " , " + select;
			if (alter != null)
				query += " , " + alter;
			if (delete != null)
				query += " , " + delete;
			if (insert != null)
				query += " , " + insert;
			query = query.replaceFirst(",", " ");
		}
		query = query + " on  *.*  " + to_f + " '" + user + "'@'localhost'";

		try {
			Statement mySWStm = connection.createStatement();
			int rs = mySWStm.executeUpdate(query);
			if (rs == 0) {
				response.sendRedirect("/UserManagement/addUser.html?user="
						+ user + "&message=sussfully updated all privileges");
			}
		} catch (SQLException | IOException e) {
			response.sendRedirect("/UserManagement/addUser.html?user=" + user
					+ "&message=" + query + e);

			e.printStackTrace();
		}

	}

	void privilegesOnDB(HttpServletRequest request,
			HttpServletResponse response, String dbName) throws IOException {
		String query = " ";
		grant = request.getParameter("prev");
		query += query + grant;
		String to_f = null;
		if (grant.contains("grant"))
			to_f = " to ";
		else
			to_f = " from ";
		if (all != null) {
			query += " all privileges  ";
		} else {
			if (select != null)
				query += " , " + select;
			if (alter != null)
				query += " , " + alter;
			if (delete != null)
				query += " , " + delete;
			if (insert != null)
				query += " , " + insert;
			query = query.replaceFirst(",", " ");
		}
		query = query + " on " + dbName + ".* " + to_f + " '" + user
				+ "'@'localhost'";

		try {
			Statement mySWStm = connection.createStatement();
			int rs = mySWStm.executeUpdate(query);
			if (rs == 0) {
				response.sendRedirect("/UserManagement/addUser.html?user="
						+ user + "&message=sussfully updated all privileges");
			}
		} catch (SQLException | IOException e) {
			response.sendRedirect("/UserManagement/addUser.html?user=" + user
					+ "&message=" + query + e);

			e.printStackTrace();
		}

	}

	void privilegesOnDB(HttpServletRequest request,
			HttpServletResponse response, String dbName, String tblName)
			throws IOException {
		String query = " ";
		grant = request.getParameter("prev");
		query += query + grant;
		String to_f = null;
		if (grant.contains("grant"))
			to_f = " to ";
		else
			to_f = " from ";
		if (all != null) {
			query += " all privileges  ";
		} else {
			if (select != null)
				query += " , " + select;
			if (alter != null)
				query += " , " + alter;
			if (delete != null)
				query += " , " + delete;
			if (insert != null)
				query += " , " + insert;
			query = query.replaceFirst(",", " ");
		}
		query = query + " on " + dbName + "." + tblName + " " + to_f + " '"
				+ user + "'@'localhost'";

		try {
			Statement mySWStm = connection.createStatement();
			int rs = mySWStm.executeUpdate(query);
			if (rs == 0) {
				response.sendRedirect("/UserManagement/addUser.html?user="
						+ user + "&message=sussfully updated all privileges");
			}
		} catch (SQLException | IOException e) {
			response.sendRedirect("/UserManagement/addUser.html?user=" + user
					+ "&message=" + query + e);

			e.printStackTrace();
		}

	}

	void privilegesOnDB(HttpServletRequest request,
			HttpServletResponse response, String dbName, String tblName,
			String colName) throws IOException {
		String query = " ";
		grant = request.getParameter("prev");
		query += query + grant;
		String to_f = null;
		if (grant.contains("grant"))
			to_f = " to ";
		else
			to_f = " from ";
		if (all != null) {
			query += " all privileges  ";
		} else {
			if (select != null)
				query += " , " + select + "(" + colName + ")";
			if (alter != null)
				query += " , " + alter + "(" + colName + ")";
			if (delete != null)
				query += " , " + delete + "(" + colName + ")";
			;
			if (insert != null)
				query += " , " + insert + "(" + colName + ")";
			;
			query = query.replaceFirst(",", " ");
		}
		query = query + " on " + dbName + "." + tblName + " " + to_f + "'"
				+ user + "'@'localhost'";

		try {
			Statement mySWStm = connection.createStatement();
			int rs = mySWStm.executeUpdate(query);
			if (rs == 0) {
				response.sendRedirect("/UserManagement/addUser.html?user="
						+ user + "&message=sussfully updated all privileges");
			}
		} catch (SQLException | IOException e) {
			response.sendRedirect("/UserManagement/addUser.html?user=" + user
					+ "&message=" + query + e);

			e.printStackTrace();
		}

	}

	void privilegesOnProc(HttpServletRequest request,
			HttpServletResponse response, String proc) throws IOException {
		String query = " ";
		grant = request.getParameter("prev");
		String to_f = null;
		if (grant.contains("grant"))
			to_f = " to ";
		else
			to_f = " from ";

		query = grant + " execute on procedure " + proc + " " + to_f + " '"
				+ user + "'@'localhost'";

		try {
			Statement mySWStm = connection.createStatement();
			int rs = mySWStm.executeUpdate(query);
			if (rs == 0) {
				String func = request.getParameter("func");
				if (func != null)
					privilegesOnFunc(request, response, func);
				else
					response.sendRedirect("/UserManagement/addUser.html?user="
							+ user
							+ "&message=sussfully updated all privileges");
			}
		} catch (SQLException | IOException e) {
			response.sendRedirect("/UserManagement/addUser.html?user=" + user
					+ "&message=" + query + e);

			e.printStackTrace();
		}
	}

	void privilegesOnFunc(HttpServletRequest request,
			HttpServletResponse response, String func) throws IOException {
		String query = " ";
		grant = request.getParameter("prev");
		String to_f = null;
		if (grant.contains("grant"))
			to_f = " to ";
		else
			to_f = " from ";
		query = grant + " execute on function sakila." + func + " " + to_f
				+ "'" + user + "'@'localhost'";

		try {
			Statement mySWStm = connection.createStatement();
			int rs = mySWStm.executeUpdate(query);
			if (rs == 0) {
				response.sendRedirect("/UserManagement/addUser.html?user="
						+ user + "&message=sussfully updated all privileges");
			}
		} catch (SQLException | IOException e) {
			response.sendRedirect("/UserManagement/addUser.html?user=" + user
					+ "&message=" + query + e);

			e.printStackTrace();
		}

	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		// doPost(request,response);
	}

}
