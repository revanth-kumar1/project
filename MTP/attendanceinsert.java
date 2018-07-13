import java.io.*;
import java.util.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class attendanceinsert extends HttpServlet
{
  public void service(HttpServletRequest req,HttpServletResponse res) throws ServletException,IOException
  {

	//global object declaration section
    Connection conn = null;
	Statement stmt = null;
	PreparedStatement pst=null;
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
		

		if(req.getParameter("save_attendance")!=null)
		{
			//variable declaration
			int subject_count=Integer.parseInt(req.getParameter("subject_count"));
			int year=Integer.parseInt(req.getParameter("year"));
			int semester=Integer.parseInt(req.getParameter("semester"));
			String average=req.getParameter("average");
			String month_value=req.getParameter("month");
			int index,loop_count=0;


			
			ResultSet month_check = stmt.executeQuery("select count(Months) from Student_Attendance where Years="+year+" and Semester="+semester+" and Months='"+month_value+"' and Student_Code='15071A1245' ");
			month_check.next();
			if(month_check.getInt(1)==0)
			{

	//insertion logic
			Enumeration paramNames=req.getParameterNames();

			
			pst = conn.prepareStatement("insert into Student_Attendance(Student_Code,Years,Semester,Subject_1,Subject_2,Subject_3,Subject_4,Subject_5,Subject_6,Subject_7,Subject_8,Subject_9,Subject_10,Subject_11,Months,Attendance_1,Attendance_2,Attendance_3,Attendance_4,Attendance_5,Attendance_6,Attendance_7,Attendance_8,Attendance_9,Attendance_10,Attendance_11,Subject_Seminar,Averages) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");

			rs=stmt.executeQuery("select subcode from subjectsample where year="+year+"and semester="+semester);
			pst.setString(1,"15071A1245");
			pst.setInt(2,year);
			pst.setInt(3,semester);

	

			//subject name insertion
			index=3;
			while(rs.next())
			{
				index++;
				pst.setString(index,rs.getString(1));
					
			}
			while(index!=14)
			{
				index++;
				pst.setString(index," ");
			}

			//attendance insertion
			//index=14 attendance starts @ 15
			while(loop_count<=subject_count)
			{
				
				String paramName = (String)paramNames.nextElement();
				String[] paramValues = req.getParameterValues(paramName);

				// Read single valued data
				if (paramValues.length == 1)
				{
					String paramValue = paramValues[0];
					index++;
					pst.setString(index,paramValue);
					
				} 
				loop_count++;
			}
			while(index!=27)
			{
				index++;
				pst.setString(index," ");

			
			}
			pst.setString(28,average);
			pst.executeUpdate();
			}
			else
			{
				out.print("Month already inserted.Unable to process");
		
			}

			
	//display logic

			//displayng table head ---  section 
	
			ResultSet subject=stmt.executeQuery("select subcode from subjectsample where year='"+year+"' and semester='"+semester+"';");//reading subjects for table head
			out.print("<html><head><link href='vendor/bootstrap/css/bootstrap.min.css' rel='stylesheet'><link href='vendor/font-awesome/css/font-awesome.min.css' rel='stylesheet' type='text/css'></head><link href='vendor/datatables/dataTables.bootstrap4.css' rel='stylesheet'><link href='css/sb-admin.css' rel='stylesheet'><link rel='stylesheet' type='text/css' href='css/main.css'><link rel='stylesheet' type='text/css' href='css/util.css'>");

			out.print("<body><form action='attendanceinsert' name='attendance' target='display_frame1'>");
			out.print("<table class='table table-hover' id='data'><thead><div class='row'><tr class='table100-head theadrow'><div class='col-sm-1'><th>Month</th></div>");
								
			while(subject.next())
			{	
				out.print("<div class='col-sm-1'><th>"+subject.getString(1)+"</th></div>");
			
			}
			
			out.print("<div class='col-sm-1'><th>Average</th></div></tr></div></thead>");
			
			//displaying table body --- section

			//declaring statement objects
			Statement month_stm=conn.createStatement();
			Statement attendance_stm=conn.createStatement();
			Statement average_stm=conn.createStatement();


			ResultSet month = month_stm.executeQuery("SELECT Months from Student_Attendance where Years="+year+" and Semester="+semester+" and Student_Code='15071A1245'" );//reading month table			
			out.print("<tbody><div class='row'>");	

			int count;
			String m;
			while(month.next())//displaying body
			{
				count=0;
				m=month.getString(1);//month value
				out.print("<tr class='tbodyrow'><div class='col-sm-1'><td>"+m+"</td></div>");//displaying month	

				while(count<subject_count)//displaying attendance
				{
					count++;
					ResultSet attendance = attendance_stm.executeQuery("SELECT Attendance_"+count+" from Student_Attendance where Years="+year+" and Months='"+m+"' and Semester="+semester+" and Student_Code='15071A1245'");//reading attendance according to month
					attendance.next();
					out.print("<div class='col-sm-1'><td>"+attendance.getString(1)+"</td></div>");//displaying attendance
				
				}

				ResultSet avg = average_stm.executeQuery("SELECT Averages from Student_Attendance where Years="+year+" and Semester="+semester+" and Months='"+m+"' and Student_Code='15071A1245'");//reading average
				avg.next();
				out.print("<div class='col-sm-1'><td>"+avg.getString(1)+"</td></div></tr>");//displaying average
			
			}
			out.print("</div></tbody></table></body></html>");
			month_stm.close();
			attendance_stm.close();
			average_stm.close();

		
		}
		
		
		
		stmt.close();
		pst.close();
		conn.close();
    }
	catch(Exception e)
	{
		
		out.print(e);
	
	}
	
	
  }
 
}