import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class swotanalysis extends HttpServlet
{
  public void service(HttpServletRequest req,HttpServletResponse res) throws ServletException,IOException
  {

	//global object declaration section
    Connection conn = null;
	Statement stmt = null;
	ResultSet rs=null;
	PrintWriter out = res.getWriter();
	res.setContentType("text/html");
	
	//html form values section
	int year=Integer.parseInt(req.getParameter("year"));
	String roll_no=req.getParameter("rollno");
	
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

		if(req.getParameter("proceed")!=null)
		{
			ResultSet check = stmt.executeQuery("select count(Year) from Student_SWOT_Analysis where Year="+year+" and Student_Code='"+roll_no+"' ");
			check.next();
			if(check.getInt(1)==0)
			{

			out.print("<html><head><link href='vendor/bootstrap/css/bootstrap.min.css' rel='stylesheet'><link href='vendor/font-awesome/css/font-awesome.min.css' rel='stylesheet' type='text/css'></head><link href='vendor/datatables/dataTables.bootstrap4.css' rel='stylesheet'><link href='css/sb-admin.css' rel='stylesheet'><link rel='stylesheet' type='text/css' href='css/main.css'><link rel='stylesheet' type='text/css' href='css/util.css'>");

			out.print("<body><form action='swotinsert' name='swot' target='_self'>");
								
			out.print("<div class='container-fluid'><table class='table table-striped'><tbody><tr><div class='row'><td><div><label for='comment'><strong>Ambition:</strong></label><textarea class='form-control' rows='3' name='ambition' id='ambition'></textarea></div></td>");
			out.print("<td><div><label for='comment'><strong>Goals:</strong></label><textarea class='form-control' rows='3' name='goals' id='goals'></textarea></div></td></div></tr><tr>");
			out.print("<div class='row'><td><div><label for='comment'><strong>Strengths & Enhancement:</strong></label><textarea class='form-control' rows='3' name='strengths_enhancement' id='strengths_enhancement'></textarea></div></td><td><div><label for='comment'><strong>Weaknesses & Corrections:</strong></label><textarea class='form-control' rows='3' name='weakness_corrections' id='weakness_corrections'></textarea></div></td></div></tr>");
			out.print("<tr><div class='row'><td><div><label for='comment'><strong>Opportunities:</strong></label><textarea class='form-control' rows='3' name='opportunities' id='opportunities'></textarea></div></td><td><div><label for='comment'><strong>Training Requirements:</strong></label><textarea class='form-control' rows='3' name='trainingrequirements' id='trainingrequirements'></textarea></div></td></div></tr>");
			out.print("</tbody></table><br><center><button type='submit' name='save' class='btn btn-primary'>Save</button>&nbsp;&nbsp;&nbsp;<button type='reset' class='btn btn-info'>Reset</button></center></div><input type='hidden' name='year' value='"+year+"'/><input type='hidden' name='rollno' value='"+roll_no+"'/>");		
			out.print("</form></body></html>");
			}
			else
			{
				ResultSet data = stmt.executeQuery("select Ambition,Goals,Strengths_Enhancement,Weakness_Corrections,Oppurtunities,Training_Requirements from Student_SWOT_Analysis where Year="+year+" and Student_Code='"+roll_no+"'");
				data.next();
				out.print("<html><head><link href='vendor/bootstrap/css/bootstrap.min.css' rel='stylesheet'><link href='vendor/font-awesome/css/font-awesome.min.css' rel='stylesheet' type='text/css'></head><link href='vendor/datatables/dataTables.bootstrap4.css' rel='stylesheet'><link href='css/sb-admin.css' rel='stylesheet'><link rel='stylesheet' type='text/css' href='css/main.css'><link rel='stylesheet' type='text/css' href='css/util.css'>");
				out.print("<body><form action='swotinsert' name='swot' target='_self'>");
				out.print("<div class='container-fluid'><table class='table'><tbody><tr><div class='row'><td><div><label for='comment'><strong>Ambition:</strong></label><textarea class='form-control' rows='3' name='ambition' id='ambition' disabled>"+data.getString(1)+"</textarea></div></td>");
				out.print("<td><div><label for='comment'><strong>Goals:</strong></label><textarea class='form-control' rows='3' name='goals' id='goals' disabled>"+data.getString(2)+"</textarea></div></td></div></tr><tr>");
				out.print("<div class='row'><td><div><label for='comment'><strong>Strengths & Enhancement:</strong></label><textarea class='form-control' rows='3' name='strengths_enhancement' id='strengths_enhancement' disabled>"+data.getString(3)+"</textarea></div></td><td><div><label for='comment'><strong>Weaknesses & Corrections:</strong></label><textarea class='form-control' rows='3' name='weakness_corrections' id='weakness_corrections' disabled>"+data.getString(4)+"</textarea></div></td></div></tr>");
				out.print("<tr><div class='row'><td><div><label for='comment'><strong>Opportunities:</strong></label><textarea class='form-control' rows='3' name='opportunities' id='opportunities' disabled>"+data.getString(5)+"</textarea></div></td><td><div><label for='comment'><strong>Training Requirements:</strong></label><textarea class='form-control' rows='3' name='trainingrequirements' id='trainingrequirements' disabled>"+data.getString(6)+"</textarea></div></td></div></tr>");
				out.print("</tbody></table><br><center><button type='submit' name='edit' class='btn btn-primary'>Edit</button></center><input type='hidden' name='year' value='"+year+"'/><input type='hidden' name='rollno' value='"+roll_no+"'/>");		
				out.print("</form></body></html>");
				
			}
			
		}
		

		stmt.close();
		conn.close();
    }
    catch(Exception e)
	  {
		out.print(e);
	
	}
	
  }
 
}