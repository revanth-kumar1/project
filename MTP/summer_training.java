import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class summer_training extends HttpServlet
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
			int year=Integer.parseInt(req.getParameter("year"));
			String roll_no=req.getParameter("rollno");
			String nameoftheindustryvisited=req.getParameter("nameoftheindustryvisited1");
			String dateofvisit=req.getParameter("dateofvisit");
			String learningpoints=req.getParameter("learningpoints1");
			String actionplan=req.getParameter("actionplan1");
			String sql1 = "INSERT INTO Student_Summer_Training_Attended(Student_Code,Years,List_Of_Industries_Visited,Visited_Date,Learning_Points,Action_Plan,Created_On) VALUES('"+roll_no+"',"+year+",'"+nameoftheindustryvisited+"','"+dateofvisit+"','"+learningpoints+"','"+actionplan+"','"+sqlTime+"')";
			stmt.executeUpdate(sql1);
			String sql2 = "SELECT List_Of_Industries_Visited,convert(varchar,Visited_Date,105),Learning_Points,Action_Plan,Summer_Training_Attended_ID FROM Student_Summer_Training_Attended where Years="+year+" and Student_Code='"+roll_no+"' order by Summer_Training_Attended_ID desc";
			rs = stmt.executeQuery(sql2);

			out.print("<html><head><link href='vendor/bootstrap/css/bootstrap.min.css' rel='stylesheet'><link href='vendor/font-awesome/css/font-awesome.min.css' rel='stylesheet' type='text/css'></head><link href='vendor/datatables/dataTables.bootstrap4.css' rel='stylesheet'><link href='css/sb-admin.css' rel='stylesheet'><link rel='stylesheet' type='text/css' href='css/main.css'><link rel='stylesheet' type='text/css' href='css/util.css'>");

			out.print("<body><form action='summer_update' target='_self'><table class='table table-hover' id='data'><thead><div class='row'><tr class='table100-head theadrow'>");
					
			out.print("<div class='col-sm-2'><th>Name of the Industry Visited</th></div><div class='col-sm-3'><th>Visited Date</th></div><div class='col-sm-3'><th>Learning Points</th></div><div class='col-sm-3'><th>Action Plan</th></div><div class='col-sm-1'><th></th></div>");	
						
			out.print("</tr></div></thead><tbody><div class='row'>");	
			while(rs.next())
			{	
				out.print("<tr class='tbodyrow'><div class='col-sm-3'><td><textarea rows='5' cols='25' disabled>"+rs.getString(1)+"</textarea></td><div class='col-sm-3'><td><p>"+rs.getString(2)+"</p></td></div><div class='col-sm-3'><td><textarea rows='5' cols='35' disabled>"+rs.getString(3)+"</textarea></td></div><div class='col-sm-3'><td><textarea rows='5' cols='35' disabled>"+rs.getString(4)+"</textarea></td></div><div class='col-sm-1'><td><button type='submit' class='btn btn-primary' name='"+rs.getInt(5)+"'>Edit</button></td></div></tr>");			
			
			}
			out.print("</div></tbody></table><input type=hidden name='year' value='"+year+"'/><input type=hidden name='rollno' value='"+roll_no+"'/></form></body></html>");

			

		}

		else if(req.getParameter("update")!=null)
		{
			//values from hidden text
			int year=Integer.parseInt(req.getParameter("year"));
			String roll_no=req.getParameter("rollno");

			//updated values
			String nameoftheindustryvisited=req.getParameter("nameoftheindustryvisited1");
			String dateofvisit=req.getParameter("dateofvisit");
			String learningpoints=req.getParameter("learningpoints1");
			String actionplan=req.getParameter("actionplan1");
			String row_id=req.getParameter("update");//update button value was set as id in update class
			
	
			stmt.executeUpdate("UPDATE Student_Summer_Training_Attended SET List_Of_Industries_Visited = '"+nameoftheindustryvisited+"',Visited_Date='"+dateofvisit+"',Learning_Points = '"+learningpoints+"',Action_Plan = '"+actionplan+"' WHERE Summer_Training_Attended_ID = '"+row_id+"' ;");

		
			String sql2 = "SELECT List_Of_Industries_Visited,convert(varchar,Visited_Date,105),Learning_Points,Action_Plan,Summer_Training_Attended_ID FROM Student_Summer_Training_Attended where Years="+year+" and Student_Code='"+roll_no+"' order by Summer_Training_Attended_ID desc";
			rs = stmt.executeQuery(sql2);
			

				out.print("<html><head><link href='vendor/bootstrap/css/bootstrap.min.css' rel='stylesheet'><link href='vendor/font-awesome/css/font-awesome.min.css' rel='stylesheet' type='text/css'></head><link href='vendor/datatables/dataTables.bootstrap4.css' rel='stylesheet'><link href='css/sb-admin.css' rel='stylesheet'><link rel='stylesheet' type='text/css' href='css/main.css'><link rel='stylesheet' type='text/css' href='css/util.css'>");

				out.print("<body><form action='summer_update' target='_self'><form action='outstation_update' target='_self'><table class='table table-hover' id='data'><thead><div class='row'><tr class='table100-head theadrow'>");
					
				out.print("<div class='col-sm-2'><th>Name of the Industry Visited</th></div><div class='col-sm-3'><th>Visited Date</th></div><div class='col-sm-3'><th>Learning Points</th></div><div class='col-sm-3'><th>Action Plan</th></div><div class='col-sm-1'><th></th></div>");	
						
				out.print("</tr></div></thead><tbody><div class='row'>");	
				while(rs.next())
				{	
					out.print("<tr class='tbodyrow'><div class='col-sm-2'><td><textarea rows='5' cols='25'  disabled>"+rs.getString(1)+"</textarea></td><div class='col-sm-3'><td><p>"+rs.getString(2)+"</p></td></div><div class='col-sm-3'><td><textarea rows='5' cols='35'  disabled>"+rs.getString(3)+"</textarea></td></div><div class='col-sm-3'><td><textarea rows='5' cols='35' disabled>"+rs.getString(4)+"</textarea></td></div><div class='col-sm-1'><td><button type='submit' class='btn btn-primary' name='"+rs.getInt(5)+"'>Edit</button></td></div></tr>");			
			
				}
				out.print("</div></tbody></table><input type=hidden name='year' value='"+year+"'/><input type=hidden name='rollno' value='"+roll_no+"'/></form></body></html>");

			
		}
		
		else if(req.getParameter("search")!=null)
		{
			//html form values section
			int year=Integer.parseInt(req.getParameter("year"));
			String roll_no=req.getParameter("rollno");
			String sql2 = "SELECT List_Of_Industries_Visited,convert(varchar,Visited_Date,105),Learning_Points,Action_Plan,Summer_Training_Attended_ID FROM Student_Summer_Training_Attended where Years="+year+"order by Summer_Training_Attended_ID desc";
			rs = stmt.executeQuery(sql2);
			if(rs.isBeforeFirst())
			{

				out.print("<html><head><link href='vendor/bootstrap/css/bootstrap.min.css' rel='stylesheet'><link href='vendor/font-awesome/css/font-awesome.min.css' rel='stylesheet' type='text/css'></head><link href='vendor/datatables/dataTables.bootstrap4.css' rel='stylesheet'><link href='css/sb-admin.css' rel='stylesheet'><link rel='stylesheet' type='text/css' href='css/main.css'><link rel='stylesheet' type='text/css' href='css/util.css'>");

				out.print("<body><form action='summer_update' target='_self'><table class='table table-hover' id='data'><thead><div class='row'><tr class='table100-head theadrow'>");
					
				out.print("<div class='col-sm-2'><th>Name of the Industry Visited</th></div><div class='col-sm-3'><th>Visited Date</th></div><div class='col-sm-3'><th>Learning Points</th></div><div class='col-sm-3'><th>Action Plan</th></div><div class='col-sm-1'><th></th></div>");	
						
				out.print("</tr></div></thead><tbody><div class='row'>");	
				while(rs.next())
				{	
					out.print("<tr class='tbodyrow'><div class='col-sm-3'><td><textarea rows='5' cols='25' disabled>"+rs.getString(1)+"</textarea></td><div class='col-sm-3'><td><p>"+rs.getString(2)+"</p></td></div><div class='col-sm-3'><td><textarea rows='5' cols='35' disabled>"+rs.getString(3)+"</textarea></td></div><div class='col-sm-3'><td><textarea rows='5' cols='35' disabled>"+rs.getString(4)+"</textarea></td></div><div class='col-sm-1'><td><button type='submit' class='btn btn-primary' name='"+rs.getInt(5)+"'>Edit</button></td></div></tr>");			
			
				}
				out.print("</div></tbody></table><input type=hidden name='year' value='"+year+"'/><input type=hidden name='rollno' value='"+roll_no+"'/></form></body></html>");

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