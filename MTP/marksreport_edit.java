import java.io.*;
import java.util.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class marksreport_edit extends HttpServlet
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
	
	//variables for internal code
	String[] exam_types = new String[6];
	exam_types[0] = "MID-1" ;
	exam_types[1] = "Assignment-1";
	exam_types[2] = "MID-2";
	exam_types[3] = "Assignment-2";
	exam_types[4] = "Average";
	exam_types[5] = "Semester";

	int i,j,subject_count=0,button=0;


	//html form values section
	int year=Integer.parseInt(req.getParameter("year"));
	int semester=Integer.parseInt(req.getParameter("semester"));
	String roll_no=req.getParameter("rollno");
	String date_of_interaction=req.getParameter("dateofinteraction");
	String remarks=req.getParameter("remarks");

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
		marks_statement = conn.createStatement();



		
		//-----subjects displaying start
		subject_code=stmt.executeQuery("select subcode from subjectsample where year="+year+" and semester="+semester);
		out.print("<html><head><link href='vendor/bootstrap/css/bootstrap.min.css' rel='stylesheet'><link href='vendor/font-awesome/css/font-awesome.min.css' rel='stylesheet' type='text/css'></head><link href='vendor/datatables/dataTables.bootstrap4.css' rel='stylesheet'><link href='css/sb-admin.css' rel='stylesheet'><link rel='stylesheet' type='text/css' href='css/main.css'><link rel='stylesheet' type='text/css' href='css/util.css'>");
		out.print("<body><form action='marksreportupdate' target='_self'>");
		out.print("<table class='table table-hover' id='data'><thead><div class='row'><tr class='table100-head theadrow'><div class='col-sm-3'><th>Exam Type</th></div>");
		while(subject_code.next())
		{	
			out.print("<div class='col-sm-1'><th>"+subject_code.getString(1)+"</th></div>");
			subject_count++;//to get subject count which is used to print blank marks in table
		
		}
		out.print("<div class='col-sm-1'><th></th></div>");
		out.print("</tr></div></thead><tbody>");	
		//-----subjects displaying end




		//displaying body 
		subject_code=stmt.executeQuery("select subcode from subjectsample where year="+year+" and semester="+semester);//reading subject codes to name the textareas
		for(i=0;i<4;i++)
		{
			//this block deals only internal marks update not semester marks
			if(req.getParameter(exam_types[i])!=null)
			{
				out.print("<div class='row'><tr class='tbodyrow'><div class='col-sm-1'><td>"+exam_types[i]+"</td></div>");
				ResultSet marks=marks_statement.executeQuery("select Marks from Internal_Marks where Years="+year+" and Semester="+semester+" and Types='"+exam_types[i]+"' and Student_Code='"+roll_no+"' ;");
				while(marks.next())
				{
					subject_code.next();
					out.print("<div class='col-sm-1'><td><textarea row='2' col='2' name='"+subject_code.getString(1)+"'>"+marks.getString(1)+"</textarea></td></div>");
				}
				out.print("<div class='col-sm-1'><td><button type='submit' class='btn btn-primary' name='update'>Update</button></td></div></tr></div>");
			
			}
			else
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
					// this block is to replace empty values when there are no marks in the database
					for(j=0;j<subject_count+1;j++)//in subject_count+1 '+1' is to replace edit button
					{
						out.print("<div class='col-sm-1'><td></td></div>");
					}
					out.print("</tr></div>");
				
				}
			
			}//after clicking edit, display screen logic for internal marks is completed
	



			//after clicking edit, display screen logic for semester exams ---- starts
			subject_code=stmt.executeQuery("select subcode from subjectsample where year="+year+" and semester="+semester);//reading subject codes to name the textareas
			
			if(req.getParameter(exam_types[5])!=null)
			{
				out.print("<div class='row'><tr class='tbodyrow'><div class='col-sm-1'><td>"+exam_types[5]+"</td></div>");
				ResultSet marks=marks_statement.executeQuery("select Marks from Student_Semester_Marks where Years="+year+" and Semester="+semester+" and Types='"+exam_types[5]+"' and Student_Code='"+roll_no+"' ;");
				while(marks.next())
				{
					subject_code.next();
					out.print("<div class='col-sm-1'><td><textarea row='2' col='2' name='"+subject_code.getString(1)+"'>"+marks.getString(1)+"</textarea></td></div>");
				}
				out.print("<div class='col-sm-1'><td><button type='submit' class='btn btn-primary' name='update'>Update</button></td></div></tr></div>");
			
			}
			else
			{
				out.print("<div class='row'><tr class='tbodyrow'><div class='col-sm-1'><td>"+exam_types[5]+"</td></div>");
				ResultSet marks=marks_statement.executeQuery("select Marks from Internal_Marks where Years="+year+" and Semester="+semester+" and Types='"+exam_types[5]+"' and Student_Code='"+roll_no+"' ;");
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
					// this block is to replace empty values when there are no marks in the database
					for(j=0;j<subject_count+1;j++)//in subject_count+1 '+1' is to replace edit button
					{
						out.print("<div class='col-sm-1'><td></td></div>");
					}
					out.print("</tr></div>");
				
				}
			}//after clicking edit, display screen logic for semsester marks is completed




			//html closing tags
			out.print("</tbody></table>");
			out.print("<input type=hidden name='year' value='"+year+"'/><input type=hidden name='semester' value='"+semester+"'/><input type=hidden name='rollno' value='"+roll_no+"'/>");//hidden values for next page
			out.print("</form></body></html>");
		
		}

		marks_statement.close();
		stmt.close();
		conn.close();
    }
    catch(Exception e)
	{
		out.print(e);
	
	}
	 
  }
 
}