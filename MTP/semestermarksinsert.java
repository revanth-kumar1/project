import java.io.*;
import java.util.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class semestermarksinsert extends HttpServlet
{
  public void service(HttpServletRequest req,HttpServletResponse res) throws ServletException,IOException
  {

	//global object declaration section
    Connection conn = null;
	Statement stmt = null;
	Statement st=null;
	ResultSet subject_name=null;
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
		

		if(req.getParameter("save_semester_marks")!=null)
		{
			//variable declaration
			int subject_count=Integer.parseInt(req.getParameter("subject_count"));
			int year=Integer.parseInt(req.getParameter("year"));
			int semester=Integer.parseInt(req.getParameter("semester"));
			String attempt_value=req.getParameter("attempt");

			
			ResultSet attempt_check = stmt.executeQuery("select count(attempt) from Student_Semester_Marks where Years="+year+" and Semester="+semester+" and attempt='"+attempt_value+"' and Student_Code='15071A1245' ");
			attempt_check.next();
			if(attempt_check.getInt(1)==0)
			{

	//insertion logic
				Enumeration paramNames=req.getParameterNames();
				//subject names
				subject_name=stmt.executeQuery("select subcode from subjectsample where year="+year+"and semester="+semester);
				String marks_paramValue;
				String[] paramValues;
				String paramName;
				for(int i=0;i<=subject_count;i++)
					{
					paramName = (String)paramNames.nextElement();
					paramValues = req.getParameterValues(paramName);
					// Read single valued data
					if (paramValues.length == 1 && i!=0)
						{
						st=conn.createStatement();
						subject_name.next();
						marks_paramValue = paramValues[0];
						st.executeUpdate("insert into Student_Semester_Marks(Student_Code,Years,Semester,Subject,attempt,Marks,Created_On) values('15071A1245',"+year+","+semester+",'"+subject_name.getString(1)+"','"+attempt_value+"',"+marks_paramValue+",'"+sqlTime+"')");					
						} 
					}
					out.print("<h3>Marks Inserted</h3>");
			}
			else
			{
				out.print("<h3>"+attempt_value+" Attempt already inserted.Unable to process</h3>");
			}
		st.close();
		stmt.close();
		conn.close();
		}
	}
	catch(Exception e)
	{
		
		out.print(e);
	
	}
	
	
  }
 
}