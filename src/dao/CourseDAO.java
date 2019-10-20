package dao;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import beans.CourseBean;
import errorlogs.Logs;
import interfacesDAO.CourseDAOI;
import jdbc.DBConnect;

public class CourseDAO implements CourseDAOI {
	String sql = "";
	PreparedStatement ps = null;

	@Override
	public List<CourseBean> getCourse() {
		sql = "SELECT `course`.`name`,\n" + "    `course`.`fees`,\n" + "    `course`.`startDate`\n"
				+ "FROM `it_center`.`course`";
		List<CourseBean> list = new LinkedList<>();

		try(Connection dbConnection=DBConnect.getConnection();) {
			ResultSet rs = dbConnection.createStatement().executeQuery(sql);
			while (rs.next()) {
				CourseBean courseBean = new CourseBean(rs.getString(1), rs.getLong(2), rs.getTimestamp(3));
				list.add(courseBean);
			}
		} catch (Exception e) {
			e.printStackTrace();
			Logs.insertException(e);
		}
		return list;
	}

	@Override
	public CourseBean getCourse(CourseBean courseBean) {
		sql = "SELECT `course`.`name`,\n" + "    `course`.`fees`,\n" + "    `course`.`startDate`\n"
				+ "FROM `it_center`.`course` where name=?";
		try (Connection dbConnection=DBConnect.getConnection();){
			PreparedStatement ps = dbConnection.prepareStatement(sql);
			ps.setString(1, courseBean.getName());
			courseBean = null;
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				courseBean = new CourseBean(rs.getString(1), rs.getLong(2), rs.getTimestamp(3));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			Logs.insertException(e);
		}
		return courseBean;
	}

	@Override
	public boolean updateCourse(CourseBean courseBean) {
		sql = "UPDATE `it_center`.`course` SET `fees` =?,`startDate` =? WHERE `name` =? ;";
		boolean isUpdated = false;
		try(Connection dbConnection=DBConnect.getConnection();) {
			PreparedStatement ps = dbConnection.prepareStatement(sql);
			ps.setLong(1, courseBean.getFees());
			ps.setTimestamp(2, courseBean.getStartDate());
			ps.setString(3, courseBean.getName());
			isUpdated = !ps.execute();
		} catch (SQLException e) {
			e.printStackTrace();
			Logs.insertException(e);
		}
		return isUpdated;
	}

	@Override
	public boolean addCourse(CourseBean courseBean) {
		sql = "INSERT INTO `it_center`.`course`\n" + "(`name`,\n" + "`fees`,\n" + "`startDate`)\n" + "VALUES\n"
				+ "(?,?,?) ";
		boolean isAdded = false;
		try (Connection dbConnection=DBConnect.getConnection();){
			PreparedStatement ps = dbConnection.prepareStatement(sql);
			ps.setString(1, courseBean.getName());
			ps.setLong(2, courseBean.getFees());
			ps.setTimestamp(3, courseBean.getStartDate());
			isAdded = !ps.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Logs.insertException(e);
		}
		return isAdded;
	}

	@Override
	public boolean deleteCourse(CourseBean courseBean) {
		sql="DELETE FROM `it_center`.`course`\n" + 
				"WHERE name=?";
		boolean isDeleted=false;
		try(Connection dbConnection=DBConnect.getConnection();) {
			PreparedStatement ps = dbConnection.prepareStatement(sql);
			ps.setString(1, courseBean.getName());
			isDeleted=!ps.execute();
		} catch (SQLException e) {
			e.printStackTrace();
			Logs.insertException(e);
		}
		
		return isDeleted;
	}
	public List<CourseBean> getCourse(int noOfRecords,int page) {
		sql = "SELECT `course`.`name`,`course`.`fees`,`course`.`startDate`\n"
				+ "FROM `it_center`.`course` limit ?,? ";
		List<CourseBean> list = new LinkedList<>();

		try(Connection dbConnection=DBConnect.getConnection();) {
			PreparedStatement ps = dbConnection.prepareStatement(sql);
			ps.setInt(1, (page-1)*noOfRecords);
			ps.setInt(2, noOfRecords);
			ResultSet rs = ps.executeQuery();
					
			while (rs.next()) {
				CourseBean courseBean = new CourseBean(rs.getString(1), rs.getLong(2), rs.getTimestamp(3));
				list.add(courseBean);
			}
		} catch (Exception e) {
			e.printStackTrace();
			Logs.insertException(e);
		}
		return list;
	}
	public int getNoOfCourses() {
		int records=0;
		sql="select count(name) from course";
		try (Connection dbConnection=DBConnect.getConnection();){
			ResultSet rs = dbConnection.createStatement().executeQuery(sql);
			if (rs.next()) {
				records=rs.getInt(1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return records;
	}
}
