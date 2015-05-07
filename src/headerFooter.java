import javax.servlet.http.HttpSession;


public class headerFooter {
	
	public String header()
	{
		String head = "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">" + 
				"<html xmlns=\"http://www.w3.org/1999/xhtml\">" + 
				"<head>" + 
				"<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />" + 
				"<link href='http://fonts.googleapis.com/css?family=Roboto+Condensed' rel='stylesheet' type='text/css'>" + 
				"<link href='http://fonts.googleapis.com/css?family=Roboto' rel='stylesheet' type='text/css'>"
				+ "<link href='http://fonts.googleapis.com/css?family=Pacifico' rel='stylesheet' type='text/css'>"
				+ "<link rel=\"stylesheet\" href=\"design.css\" type=\"text/css\" /></head>";
		return head;
	}
	
	public String banner()
	{
		String banner = "<body id='set_font' bgcolor=\"#EFFFFF\"><div id=\"banner\">" + 
				"<div><center><span style=\"font-size:47px;padding:0;\">User Management</span></center>"; 
				banner += "</div></div><br><br>" + 
				"";
		return banner;
	}
	
	public String footer()
	{
		String footer = "<div id='footline'>" + 
				"<center><span>Copyright © 2009 by Mohammed & Melwin James. All rights reserved (R).</span></center></div>" + 
				"</div>" + 
				"</body>" + 
				"</html>" + 
				"";
		return footer;
	}

}
