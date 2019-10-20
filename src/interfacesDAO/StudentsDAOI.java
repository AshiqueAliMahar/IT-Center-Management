package interfacesDAO;

import java.util.List;

import beans.StudentBean;

public interface StudentsDAOI {
	boolean addStudent(StudentBean studentBean);
	boolean deleteStudent(StudentBean studentBean);
	boolean updateStudent(StudentBean studentBean);
	List<StudentBean> getStudents();
	StudentBean getStudent(StudentBean studentBean);
	
}
