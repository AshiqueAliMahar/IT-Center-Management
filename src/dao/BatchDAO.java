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

import beans.BatchBean;
import errorlogs.Logs;
import interfacesDAO.BatchDAOI;
import jdbc.DBConnect;

public class BatchDAO implements BatchDAOI {
	String sql="";
	PreparedStatement ps=null;
	FileOutputStream fOutputStream=null;
	
	public BatchDAO() {
		try {
			fOutputStream=new FileOutputStream("logs.txt", true);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	@Override
	public boolean addBatch(BatchBean batchBean) {
		sql="INSERT INTO `it_center`.`batch`\n" + 
				"(`batch_name`,\n" + 
				"`course_name`,\n" + 
				"`start_time`,\n" + 
				"`end_time`,\n" + 
				"`start_date`,\n" + 
				"`end_date`)\n" + 
				"VALUES (?,?,?,?,?,?);";
		boolean isAdded=false;
		try(Connection dbConnection=DBConnect.getConnection();){
			ps = dbConnection.prepareStatement(sql);
			ps.setString(1, batchBean.getBatchName());
			ps.setString(2,batchBean.getCourseName());
			ps.setTime(3,batchBean.getStartTime());
			ps.setTime(4, batchBean.getEndTime());
			ps.setDate(5,batchBean.getStartDate());
			ps.setDate(6, batchBean.getEndDate());
			isAdded=!ps.execute();
		} catch (Exception e) {
				e.printStackTrace();
				try {
					fOutputStream.write(e.getMessage().getBytes());
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			
		}
		return isAdded;
	}

	@Override
	public boolean deleteBatch(BatchBean batchBean) {
		sql="DELETE FROM `it_center`.`batch` WHERE batch_name=?";
		boolean isDeleted=false;
		try (Connection dbConnection=DBConnect.getConnection();){
			ps = dbConnection.prepareStatement(sql);
			ps.setString(1, batchBean.getBatchName());
			isDeleted=!ps.execute();
		} catch (SQLException e) {
			e.printStackTrace();
			Logs.insertException(e);
		}
		return isDeleted;
	}

	@Override
	public boolean updateBatch(BatchBean batchBean) {
		sql="UPDATE `it_center`.`batch`\n" + 
				"SET"+ 
				"`course_name` = ?," + 
				"`start_time` = ?," + 
				"`end_time` = ?," + 
				"`start_date` = ?," + 
				"`end_date` = ?" + 
				"WHERE `batch_name` = ?";
		boolean isUpdated=false;
		try (Connection dbConnection=DBConnect.getConnection();){
			PreparedStatement ps = dbConnection.prepareStatement(sql);
			ps.setString(1, batchBean.getCourseName());
			ps.setTime(2, batchBean.getStartTime());
			ps.setTime(3, batchBean.getEndTime());
			ps.setDate(4, batchBean.getStartDate());
			ps.setDate(5, batchBean.getEndDate());
			ps.setString(6, batchBean.getBatchName());
			isUpdated=!ps.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			try {
				fOutputStream.write(e.getMessage().getBytes());
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
		return isUpdated;
	}

	@Override
	public List<BatchBean> getBatches() {
		List<BatchBean> listBatches=new LinkedList<>();
		sql="SELECT `batch`.`batch_name`,\n" + 
				"    `batch`.`course_name`,\n" + 
				"    `batch`.`start_time`,\n" + 
				"    `batch`.`end_time`,\n" + 
				"    `batch`.`start_date`,\n" + 
				"    `batch`.`end_date`\n" + 
				"FROM `it_center`.`batch` ";
		try(Connection dbConnection=DBConnect.getConnection();) {
			ResultSet rs = dbConnection.createStatement().executeQuery(sql);
			while (rs.next()) {
				BatchBean batchBean=new BatchBean(rs.getString(1), rs.getString(2), rs.getTime(3), rs.getTime(4), rs.getDate(5), rs.getDate(6));
				listBatches.add(batchBean);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				fOutputStream.write(e.getMessage().getBytes());
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		return listBatches;
	}

	@Override
	public BatchBean getBatch(BatchBean batchBean) {
		
		sql="SELECT `batch`.`batch_name`,\n" + 
				"    `batch`.`course_name`,\n" + 
				"    `batch`.`start_time`,\n" + 
				"    `batch`.`end_time`,\n" + 
				"    `batch`.`start_date`,\n" + 
				"    `batch`.`end_date`\n" + 
				"FROM `it_center`.`batch`"
				+ "WHERE `batch`.`batch_name`=? ";
		try (Connection dbConnection=DBConnect.getConnection();){
			PreparedStatement ps = dbConnection.prepareStatement(sql);
			ps.setString(1, batchBean.getBatchName());
			ResultSet rs = ps.executeQuery();
			batchBean=null;
			while (rs.next()) {
				batchBean=new BatchBean(rs.getString(1), rs.getString(2), rs.getTime(3), rs.getTime(4), rs.getDate(5), rs.getDate(6));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				fOutputStream.write(e.getMessage().getBytes());
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		return batchBean;
	}
	@Override
	public List<BatchBean> getBatches(int start, int length) {
		List<BatchBean> lBatchBeans=new LinkedList<>();
		sql="SELECT `batch`.`batch_name`,\n" + 
				"    `batch`.`course_name`,\n" + 
				"    `batch`.`start_time`,\n" + 
				"    `batch`.`end_time`,\n" + 
				"    `batch`.`start_date`,\n" + 
				"    `batch`.`end_date`\n" + 
				"FROM `it_center`.`batch` limit ?,? ";
		try (Connection dbConnection=DBConnect.getConnection();){
			PreparedStatement ps = dbConnection.prepareStatement(sql);
			ps.setInt(1, start);
			ps.setInt(2, length);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				BatchBean batchBean=new BatchBean(rs.getString(1), rs.getString(2), rs.getTime(3), rs.getTime(4), rs.getDate(5), rs.getDate(6));
				lBatchBeans.add(batchBean);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			Logs.insertException(e);
		}
		return lBatchBeans;
	}
	@Override
	public int totalRecords() {
		int totalRecords=0;
		sql="select count(batch_name) from batch ";
		try(Connection dbConnection=DBConnect.getConnection();) {
			ResultSet rs = dbConnection.createStatement().executeQuery(sql);
			totalRecords=rs.next()?rs.getInt(1):0;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return totalRecords;
	}
}
