package interfacesDAO;

import java.util.List;

import beans.CourseBean;

public interface CourseDAOI {
	List<CourseBean> getCourse();
	CourseBean getCourse(CourseBean courseBean);
	boolean updateCourse(CourseBean courseBean);
	boolean addCourse(CourseBean courseBean);
	boolean deleteCourse(CourseBean courseBean);
}
