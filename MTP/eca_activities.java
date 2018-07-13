import java.io.*;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class eca_activities extends HttpServlet
{
  public void service(HttpServletRequest req,HttpServletResponse res) throws ServletException,IOException
  {

	//global object declaration section
    Connection conn = null;
	Statement stmt = null;
	Statement stmt2 = null;
	ResultSet rs = null;
	ResultSet row_id = null;
	PrintWriter out = res.getWriter();
	res.setContentType("text/html");
	
	//html form values section
	int year=Integer.parseInt(req.getParameter("year"));
	String roll_no=req.getParameter("rollno");
	String eca=req.getParameter("eca1");
	String interests=req.getParameter("interests1");

	//calculating current date section
	long millis=System.currentTimeMillis();  
    java.sql.Date date=new java.sql.Date(millis);


	//jdbc logic section
	try
    {
		Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		String dbURL = "jdbc:sqlserver://10.45.29.254;DatabaseName=VNR_Students_Record";
        String user = "sa";
        String pass = "vnrvjiet1@3";
        conn = DriverManager.getConnection(dbURL, user, pass);
		stmt = conn.createStatement();
		stmt2 = conn.createStatement();
		
		//html common part html head complete part and body starting part
		out.print("<html><head><link href='vendor/bootstrap/css/bootstrap.min.css' rel='stylesheet'><link href='vendor/font-awesome/css/font-awesome.min.css' rel='stylesheet' type='text/css'></head><link href='vendor/datatables/dataTables.bootstrap4.css' rel='stylesheet'><link href='css/sb-admin.css' rel='stylesheet'><link rel='stylesheet' type='text/css' href='css/main.css'><link rel='stylesheet' type='text/css' href='css/util.css'>");
		out.print("<body>");
		out.print("<form action='ecaactivities' target='_self'>");

		if(req.getParameter("save")!=null)
		{
			String activity=req.getParameter("eca1");
			ResultSet activity_check = stmt.executeQuery("SELECT count(ECA_Activity) FROM ECA_Activities where ECA_Activity='"+activity+"' and ECA_Year="+year+" and ECA_Rollno='"+roll_no+"'");
			activity_check.next();
			
			if(activity_check.getInt(1)==0)//checking activity to allow only one activity for one time  for each student
			{
				stmt.executeUpdate("INSERT INTO ECA_Activities(ECA_Rollno,ECA_Year,ECA_Activity,ECA_Interest,Created_On) VALUES('"+roll_no+"',"+year+",'"+eca+"','"+interests+"','"+date+"')");
			}
			else
			{
				
				out.print("<h6>Info related to "+activity+" is already inserted.So please update the content.</h6>");
			}
		
		}
		
		
		
		
		if(req.getParameter("edit")!=null)
		{
			rs = stmt.executeQuery("SELECT ECA_Activity,ECA_Interest FROM ECA_Activities where ECA_Year="+year+" and ECA_Rollno='"+roll_no+"'order by ECA_ID desc");
			row_id = stmt2.executeQuery("SELECT ECA_ID FROM ECA_Activities where ECA_Year="+year+" and ECA_Rollno='"+roll_no+"'order by ECA_ID desc");
			
			out.print("<table class='table table-condensed' id='data'><thead><div class='row'><tr class='table100-head theadrow'>");	
			out.print("<div class='col-sm-4'><th>ECA Activity</th></div><div class='col-sm-7'><th>Interests and Participation</th></div><div class='col-sm-1'><th></th></div>");				
			out.print("</tr></div></thead>");//table head close
			
			//table body logic starts 
			out.print("<tbody><div class='row'>");	
			
			while(rs.next())
			{	
				row_id.next();
				if(Integer.parseInt(req.getParameter("edit"))==row_id.getInt(1))
				{
					out.print("<tr class='tbodyrow'><div class='col-sm-4'><td><input type=text name='eca1' value='"+rs.getString(1)+"'></td></div><div class='col-sm-7'><td><textarea rows='4' cols='100' name='interests1'>"+rs.getString(2)+"</textarea></td></div><div class='col-sm-1'><td><button type='submit' class='btn btn-primary' name='update' value="+row_id.getInt(1)+">Update</button></td></div></tr>");
				}
				else		
					out.print("<tr class='tbodyrow'><div class='col-sm-4'><td>"+rs.getString(1)+"</td></div><div class='col-sm-7'><td><form><textarea rows='4' cols='100' disabled>"+rs.getString(2)+"</textarea></form></td></div><div class='col-sm-1'><td><button type='submit' class='btn btn-primary' name='edit' disabled>Edit</button></td></div></tr>");
				
			}
			
			out.print("</div></tbody></table>");
			
		}
		
		if(req.getParameter("update")!=null)
		{
				stmt.executeUpdate("UPDATE ECA_Activities SET ECA_Activity='"+eca+"', ECA_Interest='"+interests+"' WHERE ECA_ID="+Integer.parseInt(req.getParameter("update")) );
		}
		

		if(req.getParameter("search")!=null || req.getParameter("save")!=null || req.getParameter("update")!=null)
		{
			rs = stmt.executeQuery("SELECT ECA_Activity,ECA_Interest FROM ECA_Activities where ECA_Year="+year+" and ECA_Rollno='"+roll_no+"'order by ECA_ID desc");
			
			if(rs.isBeforeFirst())
			{
				row_id = stmt2.executeQuery("SELECT ECA_ID from ECA_Activities where ECA_Year="+year+" and ECA_Rollno='"+roll_no+"'order by ECA_ID desc");
				
				out.print("<table class='table table-condensed' id='data'><thead><div class='row'><tr class='table100-head theadrow'>");
				out.print("<div class='col-sm-4'><th>ECA Activity</th></div><div class='col-sm-7'><th>Interests and Participation</th></div><div class='col-sm-1'><th></th></div>");	
				out.print("</tr></div></thead><tbody><div class='row'>");	
				
				while(rs.next())
				{	
					row_id.next();
					out.print("<tr class='tbodyrow'><div class='col-sm-4'><td><p>"+rs.getString(1)+"</p></td></div><div class='col-sm-7'><td><form><textarea rows='4' cols='100' disabled>"+rs.getString(2)+"</textarea></form></td></div><div class='col-sm-1'><td><button type='submit' class='btn btn-primary' name='edit' value="+row_id.getInt(1)+">Edit</button></td></div></tr>");
							
				}
				out.print("</div></tbody></table>");
			}
			else
			{
				out.print("<p>Sorry!no records to display. Add records....</p>");
			
			}

		}
		out.print("<input type='hidden' name='year' value='"+year+"'/><input type='hidden' name='rollno' value='"+roll_no+"'/>");//hidden values
		out.print("</form>");
		out.print("</body></html>");
		
		
		stmt.close();
		stmt2.close();
		conn.close();
    }
	catch(NullPointerException npe)
	{
	}
    catch(Exception e)
	{
		out.print(e);
	}
	 
  }
 
}