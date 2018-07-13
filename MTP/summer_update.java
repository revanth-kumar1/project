import java.io.*;
import java.util.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class summer_update extends HttpServlet
{
  public void service(HttpServletRequest req,HttpServletResponse res) throws ServletException,IOException
  {

	//global object declaration section
    Connection conn = null;
	Statement stmt = null;
	Statement stmt_date = null;
	ResultSet rs=null;
	ResultSet rs_date=null;
	PrintWriter out = res.getWriter();
	res.setContentType("text/html");
	
	//html form values section
	int year=Integer.parseInt(req.getParameter("year"));
	String roll_no=req.getParameter("rollno");
	String nameoftheindustryvisited=req.getParameter("nameoftheindustryvisited1");
	String dateofvisit=req.getParameter("dateofvisit");
	String learningpoints=req.getParameter("learningpoints1");
	String actionplan=req.getParameter("actionplan1");


	//date calculation
	java.util.Date date=new java.util.Date();
	java.sql.Timestamp sqlTime=new java.sql.Timestamp(date.getTime());
	

	//jdbc logic section
	try
    {
		Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		String dbURL = "jdbc:sqlserver://10.45.29.254;DatabaseName=VNR_Students_Record";
        String user = "sa";
        String pass = "vnrvjiet1@3";
        conn = DriverManager.getConnection(dbURL, user, pass);
		stmt = conn.createStatement();
		stmt_date = conn.createStatement();


		String sql2 = "SELECT List_Of_Industries_Visited,convert(varchar,Visited_Date,105),Learning_Points,Action_Plan,Summer_Training_Attended_ID FROM Student_Summer_Training_Attended where Years="+year+" and Student_Code='"+roll_no+"' order by Summer_Training_Attended_ID desc";
		rs = stmt.executeQuery(sql2);
		//to get database date format
		rs_date = stmt_date.executeQuery("SELECT Visited_Date FROM Student_Summer_Training_Attended where Years="+year+" and Student_Code='"+roll_no+"' order by Summer_Training_Attended_ID desc");

		//table head code
		out.print("<html><head><link href='vendor/bootstrap/css/bootstrap.min.css' rel='stylesheet'><link href='vendor/font-awesome/css/font-awesome.min.css' rel='stylesheet' type='text/css'></head><link href='vendor/datatables/dataTables.bootstrap4.css' rel='stylesheet'><link href='css/sb-admin.css' rel='stylesheet'><link rel='stylesheet' type='text/css' href='css/main.css'><link rel='stylesheet' type='text/css' href='css/util.css'>");
		out.print("<link href='css/bootstrap-date-edit.css' rel='stylesheet'><link href='css/bootstrap-datetimepicker.min.css' rel='stylesheet'></head>");//datepicker css links
		out.print("<body><form action='summer_update_save' target='_self'><table class='table' id='data'><thead><div class='row'><tr class='table100-head theadrow'>");		
		out.print("<div class='col-sm-2'><th>Name of the Industry Visited</th></div><div class='col-sm-3'><th>Visited Date</th></div><div class='col-sm-3'><th>Learning Points</th></div><div class='col-sm-3'><th>Action Plan</th></div><div class='col-sm-1'><th></th></div>");	
		out.print("</tr></div></thead><tbody><div class='row'>");	
		//table body code

		while(rs.next())
		{
			rs_date.next();

			if(req.getParameter(String.valueOf(rs.getInt(5)))!=null)
			{
				out.print("<tr class='tbodyrow'><div class='col-sm-2'><td><textarea rows='5' cols='25' name='nameoftheindustryvisited1'>"+rs.getString(1)+"</textarea></td></div><div class='col-sm-3'><td><div class='controls input-append date form_date' data-date='' data-date-format='dd-mm-yyyy' data-link-field='id_date' data-link-format='yyyy-mm-dd'><p><input class=' form-control' type='text' value='"+rs.getString(2)+"' Style='background-color: white;' placeholder='dd-mm-yyyy' id='id_dateofvisit'readonly><span class='add-on'><i class='fa fa-calendar'></i></span></p></div><input type='hidden' value='"+rs_date.getString(1)+"' name='dateofvisit' id='id_date' value=''/></td></div>");
				out.print("<div class='col-sm-3'><td><textarea rows='5' cols='35' name='learningpoints1'>"+rs.getString(3)+"</textarea></td></div><div class='col-sm-3'><td><textarea rows='5' cols='35' name='actionplan1'>"+rs.getString(4)+"</textarea></td></div></tr>");			
				out.print("<tr class='tbodyrow'><div class='col-sm-12'><td><center><button type='submit' class='btn btn-primary' name='update' value='"+rs.getString(5)+"'>Update</button></center></td></div></tr>");			

			}
			else
			{
				out.print("<tr class='tbodyrow'><div class='col-sm-2'><td><textarea rows='5' cols='25'  disabled>"+rs.getString(1)+"</textarea></td></div><div class='col-sm-3'><td><p>"+rs.getString(2)+"</p></td></div><div class='col-sm-3'><td><textarea rows='5' cols='35'  disabled>"+rs.getString(3)+"</textarea></td></div><div class='col-sm-3'><td><textarea rows='5' cols='35' disabled>"+rs.getString(4)+"</textarea></td></div><div class='col-sm-1'><td><button disabled type='submit' class='btn btn-primary' name='"+rs.getInt(5)+"'>Edit</button></td></div></tr>");			
			
			}
			
       
		}
			out.print("</div></tbody></table><input type=hidden name='year' value='"+year+"'/><input type=hidden name='rollno' value='"+roll_no+"'/></form>");
			out.print("<script type='text/javascript' src='js/jquery-1.8.3.min.js' charset='UTF-8'></script><script type='text/javascript' src='js/bootstrap-datetimepicker.js' charset='UTF-8'></script>");//date picker script links
			out.print("<script>$('.form_date').datetimepicker({weekStart: 1,todayBtn:  1,autoclose: 1,todayHighlight: 1,startView: 2,minView: 2,forceParse: 0});</script></body></html>");//date pcker js

		stmt_date.close();
		stmt.close();
		conn.close();
    }
    catch(Exception e)
	  {
		out.print(e);
	
	}
	 
  }
 
}