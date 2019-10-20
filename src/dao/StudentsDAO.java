package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import beans.StudentBean;
import errorlogs.Logs;
import interfacesDAO.StudentsDAOI;
import jdbc.DBConnect;

public class StudentsDAO implements StudentsDAOI {

	static String sql = "";
	static PreparedStatement ps = null;
	@Override
	public boolean addStudent(StudentBean studentBean) {
		sql="INSERT INTO `it_center`.`students`\n" + 
				"(`roll_no`,\n" + 
				"`name`,\n" + 
				"`dob`,\n" + 
				"`admsn_date`,\n" + 
				"`batch_name`,\n" + 
				"`user_email`)\n" + 
				"VALUES\n" + 
				"(?,?,?,?,?,?);\n";
		boolean isAdded=false;
		try (Connection dbConnection=DBConnect.getConnection();){
			ps=dbConnection.prepareStatement(sql);
			ps.setString(1, studentBean.getRollNo());
			ps.setString(2, studentBean.getName());
			ps.setDate(3, studentBean.getDob());
			ps.setTimestamp(4, studentBean.getAdmsnDate());
			ps.setString(5, studentBean.getBatchName());
			ps.setString(6, studentBean.getUserEmail());
			isAdded=!ps.execute();
		} catch (SQLException e) {
			Logs.insertException(e);
			e.printStackTrace();
		}
		return isAdded;
	}

	@Override
	public boolean deleteStudent(StudentBean studentBean) {
		sql="DELETE FROM `it_center`.`students` WHERE roll_no=?";
		boolean isDel=false;
		try (Connection dbConnection=DBConnect.getConnection();){
			ps=dbConnection.prepareStatement(sql);
			ps.setString(1, studentBean.getRollNo());
			isDel=!ps.execute();
		} catch (SQLException e) {
			Logs.insertException(e);
			e.printStackTrace();
		}
		return isDel;
	}

	@Override
	public boolean updateStudent(StudentBean studentBean) {
		sql="UPDATE `it_center`.`students`\n" + 
				"SET\n" + 
				"`roll_no` = ?,\n" + 
				"`name` = ?,\n" + 
				"`dob` = ?,\n" + 
				"`batch_name` = ?,\n" + 
				"`user_email` = ?\n" + 
				"WHERE `roll_no` =? ";
		boolean isUpdated=false;
		try (Connection dbConnection=DBConnect.getConnection();){
			ps=dbConnection.prepareStatement(sql);
			ps.setString(1, studentBean.getRollNo());
			ps.setString(2, studentBean.getName());
			ps.setDate(3, studentBean.getDob());
			ps.setString(4, studentBean.getBatchName());
			ps.setString(5, studentBean.getUserEmail());
			ps.setString(6, studentBean.getRollNo());
			isUpdated=!ps.execute();
		} catch (SQLException e) {
			Logs.insertException(e);
			e.printStackTrace();
		}
		return isUpdated;
	}

	@Override
	public List<StudentBean> getStudents() {
		sql="SELECT `students`.`roll_no`,\n" + 
				"    `students`.`name`,\n" + 
				"    `students`.`dob`,\n" + 
				"    `students`.`admsn_date`,\n" + 
				"    `students`.`batch_name`,\n" + 
				"    `students`.`user_email`\n" + 
				"FROM `it_center`.`students`";
		List<StudentBean> list=new LinkedList<>();
		try(Connection dbConnection=DBConnect.getConnection();) {
			ResultSet rs=dbConnection.prepareStatement(sql).executeQuery();
			while (rs.next()) {
				list.add(new StudentBean(rs.getString(1), rs.getString(2), rs.getDate(3), rs.getTimestamp(4), rs.getString(5), rs.getString(6)));
			}
		} catch (SQLException e) {
			Logs.insertException(e);
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public StudentBean getStudent(StudentBean studentBean) {
		sql="SELECT `students`.`roll_no`,\n" + 
				"    `students`.`name`,\n" + 
				"    `students`.`dob`,\n" + 
				"    `students`.`admsn_date`,\n" + 
				"    `students`.`batch_name`,\n" + 
				"    `students`.`user_email`\n" + 
				"FROM `it_center`.`students`"
				+ "where `students`.`roll_no`=?";
		
		try(Connection dbConnection=DBConnect.getConnection();) {
			ps=dbConnection.prepareStatement(sql);
			ps.setString(1, studentBean.getRollNo());
			ResultSet rs=ps.executeQuery();
			studentBean=rs.next()?new StudentBean(rs.getString(1), rs.getString(2), rs.getDate(3), rs.getTimestamp(4), rs.getString(5), rs.getString(6)):null;
		} catch (SQLException e) {
			Logs.insertException(e);
			e.printStackTrace();
		}
		return studentBean;
	}
	public static List<StudentBean> getStudent(int start,int noOfRecords) {
		sql="SELECT `students`.`roll_no`,\n" + 
				"    `students`.`name`,\n" + 
				"    `students`.`dob`,\n" + 
				"    `students`.`admsn_date`,\n" + 
				"    `students`.`batch_name`,\n" + 
				"    `students`.`user_email`\n" + 
				"FROM `it_center`.`students`"
				+ " Limit ?,? ";
		List<StudentBean> list=new LinkedList<>();
		try(Connection dbConnection=DBConnect.getConnection();) {
			ps=dbConnection.prepareStatement(sql);
			ps.setInt(1, start);
			ps.setInt(2, noOfRecords);
			ResultSet rs=ps.executeQuery();
			while (rs.next()) {
				list.add(new StudentBean(rs.getString(1), rs.getString(2), rs.getDate(3), rs.getTimestamp(4), rs.getString(5), rs.getString(6)));
			}
		} catch (SQLException e) {
			Logs.insertException(e);
			e.printStackTrace();
		}
		return list;
	}
	public static long getNoStudents() {
		sql="select count(roll_no) from students";
		long noOfStudents=0;
		try(Connection dbConnection=DBConnect.getConnection();) {
			ResultSet rs = dbConnection.createStatement().executeQuery(sql);
			noOfStudents=rs.next()?rs.getLong(1):0;
		} catch (SQLException e) {
			Logs.insertException(e);
			e.printStackTrace();
		}
		return noOfStudents;
	}
}
