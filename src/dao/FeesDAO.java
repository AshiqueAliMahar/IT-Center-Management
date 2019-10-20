package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import beans.FeesBean;
import errorlogs.Logs;
import interfacesDAO.FeesDAOI;
import jdbc.DBConnect;

public class FeesDAO implements FeesDAOI {
	
	static String sql = "";
	//static Connection dbConnection = DBConnect.getConnection();
	PreparedStatement ps = null;
	@Override
	public List<FeesBean> getFees() {
		sql="SELECT `fees`.`id`,\n" + 
				"    `fees`.`rollno`,\n" + 
				"    `fees`.`pay_date`,\n" + 
				"    `fees`.`amount`\n" + 
				"FROM `it_center`.`fees`";
		List<FeesBean> list=new LinkedList<>();
		try (Connection dbConnection=DBConnect.getConnection();){
			ResultSet rs = dbConnection.createStatement().executeQuery(sql);
			while (rs.next()) {
				FeesBean feesBean=new FeesBean(rs.getLong(1), rs.getString(2), rs.getTimestamp(3), rs.getLong(4));
				list.add(feesBean);
			}
		} catch (SQLException e) {
			Logs.insertException(e);
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public FeesBean getFees(FeesBean feesBean) {
		sql="SELECT `fees`.`id`,\n" + 
				"    `fees`.`rollno`,\n" + 
				"    `fees`.`pay_date`,\n" + 
				"    `fees`.`amount`\n" + 
				"FROM `it_center`.`fees` where `fees`.`id`=?";
		try(Connection dbConnection=DBConnect.getConnection();) {
			PreparedStatement ps = dbConnection.prepareStatement(sql);
			ps.setLong(1, feesBean.getId());
			ResultSet rs =ps.executeQuery();
			feesBean=rs.next()?new FeesBean(rs.getLong(1), rs.getString(2), rs.getTimestamp(3), rs.getLong(4)):null;
		} catch (SQLException e) {
			Logs.insertException(e);
			e.printStackTrace();
		}
		
		return feesBean;
	}
	public static long getTotalFees(String rollNo) {
		sql="SELECT sum(`fees`.`amount`) FROM `it_center`.`fees` where rollno=?";
		long fees=0;
		try(Connection dbConnection=DBConnect.getConnection();) {
			PreparedStatement ps = dbConnection.prepareStatement(sql);
			ps.setString(1, rollNo);
			ResultSet rs =ps.executeQuery();
			fees=rs.next()?rs.getLong(1):0;
		} catch (SQLException e) {
			Logs.insertException(e);
			e.printStackTrace();
		}
		
		return fees;
	}

	@Override
	public boolean updateFees(FeesBean feesBean) {
		sql="UPDATE `it_center`.`fees`\n" + 
				"SET" 
				+ "`id` =?,"
				+ "`rollno` =?,"
				+ "pay_date` =?," 
				+ "`amount` = ?" + 
				"WHERE `id` = ?";
		boolean isUpdate=false;
		try(Connection dbConnection=DBConnect.getConnection();) {
			PreparedStatement ps = dbConnection.prepareStatement(sql);
			ps.setLong(1, feesBean.getId());
			ps.setString(2, feesBean.getRollNo());
			ps.setTimestamp(3, feesBean.getPayDate());
			ps.setLong(4, feesBean.getAmount());
			ps.setLong(5, feesBean.getId());
			isUpdate=!ps.execute();
		} catch (SQLException e) {
			e.printStackTrace();
			Logs.insertException(e);
		}
		return isUpdate;
	}

	@Override
	public boolean deleteFees(FeesBean feesBean) {
		sql="DELETE FROM `it_center`.`fees` WHERE id=?";
		boolean isDeleted=false;
		try(Connection dbConnection=DBConnect.getConnection();) {
			PreparedStatement ps = dbConnection.prepareStatement(sql);
			ps.setLong(1, feesBean.getId());
			isDeleted=!ps.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Logs.insertException(e);
		}
		
		return isDeleted;
	}

	@Override
	public boolean addFees(FeesBean feesBean) {
		sql="INSERT INTO `it_center`.`fees` \n" + 
				"(`rollno`,`pay_date`,`amount`) VALUES (?,?,?)";
		boolean isAdded=false;
		try(Connection dbConnection=DBConnect.getConnection();) {
			PreparedStatement ps = dbConnection.prepareStatement(sql);
			ps.setString(1, feesBean.getRollNo());
			ps.setTimestamp(2,feesBean.getPayDate());
			ps.setLong(3, feesBean.getAmount());
			isAdded=!ps.execute();
		} catch (SQLException e) {
			Logs.insertException(e);
			e.printStackTrace();
		}
		return isAdded;
	}

	@Override
	public List<FeesBean> getSumFees() {
		sql="select sum(amount),rollNo from fees group by rollno";
		List<FeesBean> lFeesBeans=new LinkedList<>();
		try (Connection dbConnection=DBConnect.getConnection();){
			ResultSet rs = dbConnection.createStatement().executeQuery(sql);
			while (rs.next()) {
				FeesBean feesBean=new FeesBean();
				feesBean.setAmount(rs.getLong(1));
				feesBean.setRollNo(rs.getString(2));
				lFeesBeans.add(feesBean);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return lFeesBeans;
	}

	@Override
	public List<FeesBean> getFees(String rollNo) {
		sql="SELECT `fees`.`id`,`fees`.`rollno`,`fees`.`pay_date`,`fees`.`amount` FROM `it_center`.`fees` where `fees`.`rollno`=?";
		List<FeesBean> list=new LinkedList<>();
		try {
			PreparedStatement ps = DBConnect.getConnection().prepareStatement(sql);
			ps.setString(1, rollNo);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				FeesBean feesBean=new FeesBean(rs.getLong(1), rs.getString(2), rs.getTimestamp(3), rs.getLong(4));
				list.add(feesBean);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return list;
	}

	@Override
	public long getTotalNoOfFees() {
		sql="select count(id) from fees";
		long totalNoOfFees=0;
		try (Connection connection= DBConnect.getConnection()){
			ResultSet rs = connection.createStatement().executeQuery(sql);
			totalNoOfFees=rs.next()?rs.getLong(1):0;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return totalNoOfFees;
	}
	public static long getTotalNoOfSumFees() {
		sql="select count(distinct rollno) from fees ";
		long totalNoOfFees=0;
		try (Connection connection= DBConnect.getConnection()){
			ResultSet rs = connection.createStatement().executeQuery(sql);
			totalNoOfFees=rs.next()?rs.getLong(1):0;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return totalNoOfFees;
	} 

	@Override	
	public List<FeesBean> getSumFees(int start, int length) {
		sql="SELECT `fees`.`id`,`fees`.`rollno`,`fees`.`pay_date`,sum(`fees`.`amount`) FROM `it_center`.`fees` group by rollno limit ?,? ";
		List<FeesBean> lFeesBeans=new LinkedList<>();
		
		try(Connection connection=DBConnect.getConnection();) {
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setInt(1, start);
			ps.setInt(2, length);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				FeesBean feesBean=new FeesBean(rs.getLong(1), rs.getString(2), rs.getTimestamp(3), rs.getLong(4));
				lFeesBeans.add(feesBean);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return lFeesBeans;
	}

}
