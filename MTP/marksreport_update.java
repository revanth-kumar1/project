import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class marksreport_update extends HttpServlet
{
  public void service(HttpServletRequest req,HttpServletResponse res) throws ServletException,IOException
  {

	//global object declaration section
    Connection conn = null;
	Statement stmt = null;
	Statement marks_statement = null;
	ResultSet rs=null;
	ResultSet subject_code = null;
	PrintWriter out = res.getWriter();
	res.setContentType("text/html");

	//variables for internal code functioning
	String[] exam_types = new String[6];
	exam_types[0] = "MID-1" ;
	exam_types[1] = "Assignment-1";
	exam_types[2] = "MID-2";
	exam_types[3] = "Assignment-2";
	exam_types[4] = "Average";
	exam_types[5] = "Semester";

	int i,j,subject_count=0;
	
	
	//html form values section
	int year=Integer.parseInt(req.getParameter("year"));
	int semester=Integer.parseInt(req.getParameter("semester"));
	String roll_no=req.getParameter("rollno");
	
	//created on, date calculation
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

		if(req.getParameter("update")!=null)//if block only for update button
		{
			//updating data logic
			subject_code=stmt.executeQuery("select subcode from subjectsample where year="+year+" and semester="+semester);
			while(subject_code.next())
			{	

				//updated values
				String date_of_interaction=req.getParameter("dateofinteraction");
				String remarks=req.getParameter("remarks");
				String row_id=req.getParameter("update");//update button value was set as id in update class
			
	
			stmt.executeUpdate("UPDATE Student_Mentor_Mentee_Meetings SET Date_Of_Interaction = '"+date_of_interaction+"',Remarks_Advice_Guidance='"+remarks+"' WHERE Mentor_Mentee_Meeting_ID = '"+row_id+"' ;");
				out.print("<div class='col-sm-1'><th>"+subject_code.getString(1)+"</th></div>");
				subject_count++;//to get subject count which is used to print blank marks in table
		
			}
























	//to display the updated table I used marksreport class logic which is called when proceed button is clicked

			//displaying table head with subjects loading dynamically----start
			subject_code=stmt.executeQuery("select subcode from subjectsample where year="+year+" and semester="+semester);
			out.print("<html><head><link href='vendor/bootstrap/css/bootstrap.min.css' rel='stylesheet'><link href='vendor/font-awesome/css/font-awesome.min.css' rel='stylesheet' type='text/css'></head><link href='vendor/datatables/dataTables.bootstrap4.css' rel='stylesheet'><link href='css/sb-admin.css' rel='stylesheet'><link rel='stylesheet' type='text/css' href='css/main.css'><link rel='stylesheet' type='text/css' href='css/util.css'>");
			out.print("<body><form action='marksreportedit' target='_self'>");
			out.print("<table class='table table-hover' id='data'><thead><div class='row'><tr class='table100-head theadrow'><div class='col-sm-3'><th>Exam Type</th></div>");
			while(subject_code.next())
			{	
				out.print("<div class='col-sm-1'><th>"+subject_code.getString(1)+"</th></div>");
				subject_count++;//to get subject count which is used to print blank marks in table
		
			}
			out.print("<div class='col-sm-1'><th></th></div>");
			out.print("</tr></div></thead><tbody>");	
			//-----subjects diplaying end




			//displayng marks for each exam type ---- start
			marks_statement=conn.createStatement();
			int button = 0;//if button = 1 provide edit button
			for(i=0;i<4;i++)
			{	
				out.print("<div class='row'><tr class='tbodyrow'><div class='col-sm-1'><td>"+exam_types[i]+"</td></div>");
				ResultSet marks=marks_statement.executeQuery("select Marks from Internal_Marks where Years="+year+" and Semester="+semester+" and Types='"+exam_types[i]+"' and Student_Code='"+roll_no+"' ;");
				while(marks.next())
				{
					out.print("<div class='col-sm-1'><td>"+marks.getString(1)+"</td></div>");
					button = 1;
				}
				if(button == 1)
				{
					out.print("<div class='col-sm-1'><td><button type='submit' class='btn btn-primary' name='"+exam_types[i]+"'>Edit</button></td></div></tr></div>");
					button = 0;
				}
				else
				{
					// this block is to replace empty values
					for(j=0;j<subject_count+1;j++)//in subject_count+1 '+1' is to replace edit button
					{
						out.print("<div class='col-sm-1'><td></td></div>");
					}
					out.print("</tr></div>");
				
				}			
			}
			//displaying marks for each exam type ---- end





			//average mid marks calculating and displaying  ---- start
			out.print("<div class='row'><tr class='tbodyrow'><div class='col-sm-1'><td>"+exam_types[4]+"</td></div>");
			out.print("</tr></div>");
			//average mid marks diplaying ---- end





			//semsester marks diplaying ---- start
			button = 0;//if button = 1 provide edit button
			out.print("<div class='row'><tr class='tbodyrow'><div class='col-sm-1'><td>"+exam_types[5]+"</td></div>");
			ResultSet sem_marks=marks_statement.executeQuery("select Marks  from Student_Semester_Marks where Years="+year+" and Semester="+semester+" and Student_Code='"+roll_no+"' ;");
			while(sem_marks.next())
			{
				out.print("<div class='col-sm-1'><td>"+sem_marks.getString(1)+"</td></div>");
				button = 1;
			}
			if(button == 1)
			{
				out.print("<div class='col-sm-1'><td><button type='submit' class='btn btn-primary' name='"+exam_types[5]+"'>Edit</button></td></div></tr></div>");
				button = 0;
			}
			else
			{
				for(j=0;j<subject_count+1;j++)//in subject_count+1 '+1' is to replace edit button
				{
					out.print("<div class='col-sm-1'><td></td></div>");
				}
				out.print("</tr></div>");
			}
				
			//semester marks displaying ---- end


            //html closing tags
			out.print("</tbody></table>");
			out.print("<input type=hidden name='year' value='"+year+"'/><input type=hidden name='semester' value='"+semester+"'/><input type=hidden name='rollno' value='"+roll_no+"'/>");//hidden values for next page
			out.print("</form></body></html>");

		}
		
		stmt.close();
		marks_statement.close();
		conn.close();
    }
    catch(Exception e)
	{
		out.print(e);
	
	}
	 
  }
 
}