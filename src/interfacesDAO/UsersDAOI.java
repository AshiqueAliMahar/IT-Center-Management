package interfacesDAO;

import beans.UsersBean;

public interface UsersDAOI {
	UsersBean getUser(UsersBean usersBean);
	boolean addUser(UsersBean usersBean);
	boolean updateUser(UsersBean usersBean);
	
}
