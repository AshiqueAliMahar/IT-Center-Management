package interfacesDAO;

import java.util.List;

import beans.FeesBean;

public interface FeesDAOI {
	List<FeesBean> getFees();
	FeesBean getFees(FeesBean feesBean);
	boolean updateFees(FeesBean feesBean);
	boolean deleteFees(FeesBean feesBean);
	boolean addFees(FeesBean feesBean);
	List<FeesBean> getSumFees();
	List<FeesBean> getSumFees(int start ,int length);
	List<FeesBean> getFees(String rollNo);
	
	long getTotalNoOfFees();
}
