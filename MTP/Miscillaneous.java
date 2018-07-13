import java.io.*;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class miscillaneous extends HttpServlet
{
  public void service(HttpServletRequest req,HttpServletResponse res) throws ServletException,IOException
  {

	//global object declaration section
    Connection conn = null;
	Statement stmt = null;
	ResultSet rs=null;
	PrintWriter out = res.getWriter();
	res.setContentType("text/html");
	
	

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

		if(req.getParameter("save")!=null)
		{
			//html form values section
			String college_code=req.getParameter("collegecode");
			String course_code=req.getParameter("course");
			String staff_dept_id_code=req.getParameter("staffcode");
			
			String sql1 = "INSERT INTO Miscillaneous(College_code,Course_code,Staff_code,Created_On) VALUES('"+college_code+"',"+course_code+","+staff_dept_id_code+",'"+sqlTime+"')";
			
			stmt.executeUpdate(sql1);

		
			String sql2 = "SELECT  FROM Miscillaneous order by Mentor_Mentee_Meeting_ID desc";
			rs = stmt.executeQuery(sql2);

			out.print("<html><head><link href='vendor/bootstrap/css/bootstrap.min.css' rel='stylesheet'><link href='vendor/font-awesome/css/font-awesome.min.css' rel='stylesheet' type='text/css'></head><link href='vendor/datatables/dataTables.bootstrap4.css' rel='stylesheet'><link href='css/sb-admin.css' rel='stylesheet'><link rel='stylesheet' type='text/css' href='css/main.css'><link rel='stylesheet' type='text/css' href='css/util.css'>");

			out.print("<body><form action='update' target='_self'><table class='table table-hover' id='data'><thead><div class='row'><tr class='table100-head theadrow'>");
					
			out.print("<div class='col-sm-3'><th>Date of Interaction</th></div><div class='col-sm-7'><th>Remarks / Advice / Guidance</th></div><div class='col-sm-2'><th></th></div>");	
						
			out.print("</tr></div></thead><tbody><div class='row'>");	
			while(rs.next())
			{	
				out.print("<tr class='tbodyrow'><div class='col-sm-3'><td>"+rs.getString(1)+"</td></div><div class='col-sm-7'><td><textarea rows='4' cols='80' disabled>"+rs.getString(2)+"</textarea></td></div><div class='col-sm-2'><td><button type='submit' class='btn btn-primary' name='"+rs.getInt(3)+"'>Edit</button></td></div></tr>");			
			
			}
			out.print("</div></tbody></table><input type=hidden name='year' value='"+year+"'/><input type=hidden name='semester' value='"+semester+"'/><input type=hidden name='rollno' value='"+roll_no+"'/></form></body></html>");

			

		}
		else if(req.getParameter("update")!=null)
		{
			//values from hidden text
			int year=Integer.parseInt(req.getParameter("year"));
			int semester=Integer.parseInt(req.getParameter("semester"));
			String roll_no=req.getParameter("rollno");

			//updated values
			String date_of_interaction=req.getParameter("dateofinteraction");
			String remarks=req.getParameter("remarks");
			String row_id=req.getParameter("update");//update button value was set as id in update class
			
	
			stmt.executeUpdate("UPDATE Student_Mentor_Mentee_Meetings SET Date_Of_Interaction = '"+date_of_interaction+"',Remarks_Advice_Guidance='"+remarks+"' WHERE Mentor_Mentee_Meeting_ID = '"+row_id+"' ;");

		
			String sql2 = "SELECT convert(varchar,Date_Of_Interaction,105),Remarks_Advice_Guidance,Mentor_Mentee_Meeting_ID FROM Student_Mentor_Mentee_Meetings where Years="+year+" and Semester="+semester+"order by Mentor_Mentee_Meeting_ID desc";
			rs = stmt.executeQuery(sql2);

			out.print("<html><head><link href='vendor/bootstrap/css/bootstrap.min.css' rel='stylesheet'><link href='vendor/font-awesome/css/font-awesome.min.css' rel='stylesheet' type='text/css'></head><link href='vendor/datatables/dataTables.bootstrap4.css' rel='stylesheet'><link href='css/sb-admin.css' rel='stylesheet'><link rel='stylesheet' type='text/css' href='css/main.css'><link rel='stylesheet' type='text/css' href='css/util.css'>");

			out.print("<body><form action='update' target='_self'><table class='table table-hover' id='data'><thead><div class='row'><tr class='table100-head theadrow'>");
					
			out.print("<div class='col-sm-3'><th>Date of Interaction</th></div><div class='col-sm-7'><th>Remarks / Advice / Guidance</th></div><div class='col-sm-2'><th></th></div>");	
						
			out.print("</tr></div></thead><tbody><div class='row'>");	
			while(rs.next())
			{	
				out.print("<tr class='tbodyrow'><div class='col-sm-3'><td>"+rs.getString(1)+"</td></div><div class='col-sm-7'><td><textarea rows='4' cols='80' disabled>"+rs.getString(2)+"</textarea></td></div><div class='col-sm-2'><td><button type='submit' class='btn btn-primary' name='"+rs.getInt(3)+"'>Edit</button></td></div></tr>");			
			
			}
			out.print("</div></tbody></table><input type=hidden name='year' value='"+year+"'/><input type=hidden name='semester' value='"+semester+"'/><input type=hidden name='rollno' value='"+roll_no+"'/></form></body></html>");

			
		}

		else if(req.getParameter("search")!=null)
		{
			//HTML form values
			int year=Integer.parseInt(req.getParameter("year"));
			int semester=Integer.parseInt(req.getParameter("semester"));
			String roll_no=req.getParameter("rollno");

			String sql2 = "SELECT convert(varchar,Date_Of_Interaction,105),Remarks_Advice_Guidance,Mentor_Mentee_Meeting_ID FROM Student_Mentor_Mentee_Meetings where Years="+year+" and Semester="+semester+" and Student_Code='"+roll_no+"'order by Mentor_Mentee_Meeting_ID desc";
			rs = stmt.executeQuery(sql2);
			if(rs.isBeforeFirst())
			{

				out.print("<html><head><link href='vendor/bootstrap/css/bootstrap.min.css' rel='stylesheet'><link href='vendor/font-awesome/css/font-awesome.min.css' rel='stylesheet' type='text/css'></head><link href='vendor/datatables/dataTables.bootstrap4.css' rel='stylesheet'><link href='css/sb-admin.css' rel='stylesheet'><link rel='stylesheet' type='text/css' href='css/main.css'><link rel='stylesheet' type='text/css' href='css/util.css'>");

				out.print("<body><form action='update' target='_self'><table class='table table-hover' id='data'><thead><div class='row'><tr class='table100-head theadrow'>");
				
				out.print("<div class='col-sm-3'><th>Date of Interaction</th></div><div class='col-sm-7'><th>Remarks / Advice / Guidance</th></div><div class='col-sm-2'><th></th></div>");	
						
				out.print("</tr></div></thead><tbody><div class='row'>");	
				while(rs.next())
				{	
					out.print("<tr class='tbodyrow'><div class='col-sm-3'><td>"+rs.getString(1)+"</td></div><div class='col-sm-7'><td><textarea rows='4' cols='80' disabled>"+rs.getString(2)+"</textarea></td></div><div class='col-sm-2'><td><button type='submit' class='btn btn-primary' name='"+rs.getInt(3)+"'>Edit</button></td></div></tr>");			
							
				}
				
				out.print("</div></tbody></table><input type=hidden name='year' value='"+year+"'/><input type=hidden name='semester' value='"+semester+"'/><input type=hidden name='rollno' value='"+roll_no+"'/></form></body></html>");
			}
			else
			{
				out.print("<p>Sorry!no records to display. Please add records....</p>");
			
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