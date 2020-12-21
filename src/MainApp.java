import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Vector;

import config.DBConnection;
import dao.DeptDao;
import model.Dept;

public class MainApp {

	
	public static void main(String[] args) {
//		추가(9);
//		삭제(9);
//		수정(10);
//		Dept dept = 찾기(10);
		DeptDao dd = new DeptDao();
		List<Dept> depts = dd.전체찾기();
		
	}
}
