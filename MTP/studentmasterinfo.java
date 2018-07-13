import java.io.*;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class studentmasterinfo extends HttpServlet
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
			//student detais
			String s_name=req.getParameter("sname");
			String roll_no=req.getParameter("regd");
			String gender=req.getParameter("sex");
			int year=Integer.parseInt(req.getParameter("year"));
			String date_of_birth=req.getParameter("DOB");
			String mail_id=req.getParameter("mail");

			//eamcet details
			int rank=Integer.parseInt(req.getParameter("rank"));
			String dept=req.getParameter("dept");
			String rcpt=req.getParameter("rcpt");
			String date_of_join=req.getParameter("dat");
			int amount=Integer.parseInt(req.getParameter("amt"));
			String seat_type=req.getParameter("seattype");
			String exempt=req.getParameter("exempt");
			String lateral=req.getParameter("lstud");
			String region=req.getParameter("region");
			String category=req.getParameter("category");
			String scholarship=req.getParameter("scholarship");
			String medium=req.getParameter("med");
			String mother_tongue=req.getParameter("m_tongue");
			String nation=req.getParameter("nation");
			String religion=req.getParameter("religion");
			String caste=req.getParameter("cas");
			String locality=req.getParameter("locality");

			//tenth details
			String tenth_marks=req.getParameter("tmar");
			String tenth_grade=req.getParameter("tgrd");
			String tenth_percentage=req.getParameter("tpct");
			//inter details
			String inter_marks=req.getParameter("imar");
			String inter_grade=req.getParameter("igrd");
			String inter_percentage=req.getParameter("ipct");
			
			//personal details
			String blood_group=req.getParameter("blood");
			String medical_problem=req.getParameter("prblm");
			String identification_mark1=req.getParameter("mole1");
			String identification_mark2=req.getParameter("mole2");
			String phy_handicapped=req.getParameter("phand");
			//parent details
			String father_name=req.getParameter("fnam");
			String father_phn_no=req.getParameter("fmob");
			String father_profession=req.getParameter("fprfn");
			int income=Integer.parseInt(req.getParameter("incm"));
			String mother_name=req.getParameter("mnam");
			String mother_mobile_no=req.getParameter("mmob");
			String phone_no=req.getParameter("phn");
			String mother_profession=req.getParameter("mprfn");
			//Address
			String parent_address=req.getParameter("adrs");
			String parent_mailid=req.getParameter("pmal");
			String guardian_name=req.getParameter("gnam");
			String guardian_address=req.getParameter("gadrs");
			String guardian_mailid=req.getParameter("gmal");
			String guardian_phn_no=req.getParameter("gphn");
			String guardian_mob_no=req.getParameter("gmob");
			//Family Background
			String community=req.getParameter("community");
			String father_education=req.getParameter("father_education");
			String mother_education=req.getParameter("mother_education");
			String parent_science=req.getParameter("science");
			String father_employment=req.getParameter("father_employment");
			String mother_employment=req.getParameter("mother_employment");			
			//Activities
			String activity=req.getParameter("activity");
			String date_of_activity=req.getParameter("dateofactivity");
			String description=req.getParameter("description");
			String grade=req.getParameter("grade");
			String remarks=req.getParameter("remarks");
			
			String sql1 = "INSERT INTO Student_Master_Information(Student_Name,Student_code,Student_Gender,Academic_Year,Date_Of_Birth,Student_Mail_Id,EAMCET_ECET,Branch,Fee_Deposit_Receipt_No,Receipt_Date,Receipt_Amount,Seat_Type,Is_Lateral_Student,Fees_Exempted,Region,Allotment_Category,Receipt_Of_Scholarship,Medium,Mother_Tongue,Nationality,Religion_Optional,Caste_Optional,Local_NonLocal,X_Class_Total_Marks,X_Class_Grade_Obtained,X_Class_Division_Perc,Inter_Total_Marks,Inter_Grade_Obtained,Inter_Division_Perc,Blood_Group,Any_Medical_Problem,Physically_Handicapped,Identification_Marks_1,Identification_Marks_2,Fathers_Name,Fathers_Mobile,Fathers_Profession,Family_Annual_Income,Mothers_Name,Mothers_Mobile,Phone_No,Mothers_Profession,Parents_Address,Parents_Mail_Id,Local_Guardian_Name,Local_Guardian_Address,Local_Guardian_Mail_Id,Local_Guardian_Phone_No,Local_Guardian_Mobile,Students_Home_Community,Fathers_Highest_Education,Mothers_Highest_Education,Parents_Trained_Science,Fathers_Employment,Mothers_Employment,Created_On) ";
			String sql2 = "VALUES('"+s_name+"','"+roll_no+"','"+gender+"',"+year+",'"+date_of_birth+"','"+mail_id+"',"+rank+",'"+dept+"','"+rcpt+"','"+date_of_join+"',"+amount+",'"+seat_type+"','"+lateral+"','"+exempt+"','"+region+"','"+category+"','"+scholarship+"','"+medium+"','"+mother_tongue+"','"+nation+"','"+religion+"','"+caste+"','"+locality+"'";
			String sql3 = ",'"+tenth_marks+"','"+tenth_grade+"','"+tenth_percentage+"','"+inter_marks+"','"+inter_grade+"','"+inter_percentage+"','"+blood_group+"','"+medical_problem+"','"+phy_handicapped+"','"+identification_mark1+"','"+identification_mark2+"','"+father_name+"','"+father_phn_no+"','"+father_profession+"',"+income+",'"+mother_name+"','"+mother_mobile_no+"','"+phone_no+"','"+mother_profession+"',";
			String sql4 = "'"+parent_address+"','"+parent_mailid+"','"+guardian_name+"','"+guardian_address+"','"+guardian_mailid+"','"+guardian_phn_no+"','"+guardian_mob_no+"','"+community+"','"+father_education+"','"+mother_education+"','"+parent_science+"','"+father_employment+"','"+mother_employment+"','"+sqlTime+"')";
			int i=stmt.executeUpdate(sql1+sql2+sql3+sql4);

			String sql5 = "Insert into Student_Activities_Prizes(Activity,Student_Code,Date_Of_Activity,Description_Of_Activity,Grade_Activity,Remarks,Created_On) Values ('"+activity+"','"+roll_no+"','"+date_of_activity+"','"+description+"','"+grade+"','"+remarks+"','"+sqlTime+"')";
			int j=stmt.executeUpdate(sql5);

			out.print("inserted records"+i+j);
		/*
			
		
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
*/
			

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