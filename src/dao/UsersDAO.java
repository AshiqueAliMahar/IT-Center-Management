package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import beans.UsersBean;
import errorlogs.Logs;
import interfacesDAO.UsersDAOI;
import jdbc.DBConnect;

public class UsersDAO  implements UsersDAOI{

	String sql = "";
	//Connection dbConnection = DBConnect.getConnection();
	PreparedStatement ps = null;
	@Override
	public UsersBean getUser(UsersBean usersBean) {
		sql="SELECT `users`.`email`,\n" + 
				"    `users`.`name`,\n" + 
				"    `users`.`password`,\n" + 
				"    `users`.`cnic`,\n" + 
				"    `users`.`pic`\n" + 
				"FROM `it_center`.`users` " + 
				" where `users`.`email`=? && password=?";
		
		try (Connection dbConnection=DBConnect.getConnection();){
			ps=dbConnection.prepareStatement(sql);
			ps.setString(1, usersBean.getEmail());
			ps.setString(2, usersBean.getPassword());
			ResultSet rs = ps.executeQuery();
			usersBean=rs.next()?new UsersBean(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getBytes(5)):null;
		} catch (SQLException e) {
			Logs.insertException(e);
			e.printStackTrace();
		}
		return usersBean;
	}

	@Override
	public boolean addUser(UsersBean usersBean) {
		
		return false;
	}

	@Override
	public boolean updateUser(UsersBean usersBean) {
		sql="UPDATE `it_center`.`users`\n" + 
				"SET\n" + 
				"`email` = ?," + 
				"`name` = ?,\n" + 
				"`cnic` = ?,\n" + 
				"`pic` = ?" + 
				"WHERE `email` = ?";
		boolean isUpdated=false;
		try(Connection dbConnection=DBConnect.getConnection();) {
			ps=dbConnection.prepareStatement(sql);
			ps.setString(1, usersBean.getEmail());
			ps.setString(2, usersBean.getName());
			ps.setString(3, usersBean.getCnic());
			ps.setBytes(4, usersBean.getPic());
			ps.setString(5, usersBean.getEmail());
			isUpdated=!ps.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return isUpdated;
	}

}
