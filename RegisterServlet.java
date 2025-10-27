package com.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
@WebServlet("/register")
public class RegisterServlet extends HttpServlet{
	private static final String INSERT_QUERY="INSERT INTO user(Name,Email,DOB,Gender,Qualification,Hobbies)VALUES(?,?,?,?,?,?)";
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		//get printWriter
		PrintWriter pw=res.getWriter();
		//set res type
		res.setContentType("text/html");
		//read the form data
		String Name=req.getParameter("Name");
		String Email=req.getParameter("Email");
		String DOB=req.getParameter("DOB");
		//Gender
		String Gender=req.getParameter("Gender");
		//qlfy
		String Qualification= req.getParameter("Qualification");
		//hobbies
		String[] Hobbies=req.getParameterValues("Hobbies");
		List al=Arrays.asList(Hobbies);
		//print data
		 try {
			 Class.forName("com.mysql.cj.jdbc.Driver");
			
		} catch (ClassNotFoundException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		 
		 //create the connection
		 try (Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/student_db","root","Kishore@19");
				 PreparedStatement ps=con.prepareStatement(INSERT_QUERY);){
			//set the values
			 ps.setString(1, Name);
			 ps.setString(2, Email);
			 ps.setString(3, DOB);
			 ps.setString(4, Gender);
			 ps.setString(5, Qualification);
			 ps.setString(6, String.join(", ", Hobbies)); 
			 //execute the query
			 int count=ps.executeUpdate();
			 if(count==0) {
				 pw.println("Record not Stored into database");
			 }else {
				 pw.println("Record Stored into Database");
			 }

			 
		} catch (SQLException se) {
			pw.println(se.getMessage());
			se.printStackTrace();
			// TODO: handle exception
		}catch(Exception e) {
			pw.println(e.getMessage());
			e.printStackTrace();
		}
		//close the stream
		pw.close();
		
		
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doGet(req,res);
	}
}