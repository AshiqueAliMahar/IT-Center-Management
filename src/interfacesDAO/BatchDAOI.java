package interfacesDAO;

import java.util.List;

import beans.BatchBean;

public interface BatchDAOI {
	boolean addBatch(BatchBean batchBean);
	boolean deleteBatch(BatchBean batchBean);
	boolean updateBatch(BatchBean batchBean);
	List<BatchBean> getBatches();
	BatchBean getBatch(BatchBean batchBean);
	List<BatchBean> getBatches(int start,int length);
	public int totalRecords(); 
}
