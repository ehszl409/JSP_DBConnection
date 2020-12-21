package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.Vector;

import config.DBConnection;
import model.Dept;

// 모듈화
// Data Access Object (데이터에 접근하게 해주는 객체)
// A(F11) - DAO - DB 
// Connection 객체는 new가 한 번만 해주면 된다.
// B - DAO2 - DB
// DAO는 프로그램당 하나씩 들고 있어서 static으로 선언해도 괜찮다.
// A,B,C - SERVER - DAO - DB
// DAO는 하나이고 static으로 선언하게 되면 메모리상에 떠있는 상태이기 때문에
// 사람들이 다중 접속이 가능하게 된다. DB는 순차적으로 데이터를 처리해야 값이 꼬이지 않기
// 때문에 static 속성은 적합하지 않다 그래서 Connection이 한 번만 new되는 것처럼
// DAO속 함수들도 한 번만 new되서 사용되어야 한다.

/* 비동기적 (일의 순서가 없다.)
 * 중요!!! DB는 비동기 처리가 안된다.
 * 만약 A가 B한테 돈을 빌리고 싶은데 당장 B가 돈이 없어서 보증서만 써줬다.
 * 그래서 A가 일단 보증서로 일처리를 하고 나중에 B가 돈을 구해 A한테 주고 
 * A는 돈을 받고 일을 마무리 하는 것이 비동기적 일처리 하는 것이다.
 * */

// 결론: DB는 new를 통해 다중 접속은 가능 하지만 비동기처리가 안되서
//		데이터 베이스 처리가 안된다.		
public class DeptDao {

	// 모듈화
	public void 추가(int id) {
		// " "안에 ';'넣으면 절때 안된다. 넣는 순간 오류.
		// prepareStatement, VALUES(?) = 준비가 되어 있지 않는 상태
		String sql = "INSERT INTO test1(id) VALUES(?)";
		Connection conn = DBConnection.getInstance();
		// 인터페이스가 적용되어 있는 함수. (따로 인터페이스(프로토콜)을 만들지 않아도 된다.)
		try {
			// conn으로 버퍼를 만들어서 통신해주는 코드.
			PreparedStatement pstmt = conn.prepareStatement(sql);
			// 인젝션 공격을 다 막아주는 코드.
			pstmt.setInt(1, id);

			// 요청에 응답하는 방법.
			// pstmt.executeUpdate(); => 변경된 rowcount를 리턴한다
			// 만약 2개를 변경했는데 값이 1이 나온다면 변경실패 이다.
			// -값은 오류시에만 리턴된다. (하나도 변경하지 못했다.)
			int result = pstmt.executeUpdate();
			System.out.println("result: " + result);
		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	// 모듈화
	public void 삭제(int id) {
		String sql = "DELETE FROM test1 WHERE id = ?";
		Connection conn = DBConnection.getInstance();
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, id);
			int result = pstmt.executeUpdate();
			System.out.println("result: " + result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 모듈화
	public void 수정(int id) {
		// " "안에 ';'넣으면 절때 안된다. 넣는 순간 오류.
		// prepareStatement, VALUES(?) = 준비가 되어 있지 않는 상태
		String sql = "UPDATE test1 SET id = ?";
		Connection conn = DBConnection.getInstance();
		// 인터페이스가 적용되어 있는 함수. (따로 인터페이스(프로토콜)을 만들지 않아도 된다.)
		try {
			// conn으로 버퍼를 만들어서 통신해주는 코드.
			PreparedStatement pstmt = conn.prepareStatement(sql);
			// 인젝션 공격을 다 막아주는 코드.
			pstmt.setInt(1, id);

			// 요청에 응답하는 방법.
			// pstmt.executeUpdate(); => 변경된 rowcount를 리턴한다
			// 만약 2개를 변경했는데 값이 1이 나온다면 변경실패 이다.
			// -값은 오류시에만 리턴된다. (하나도 변경하지 못했다.)
			int result = pstmt.executeUpdate();
			System.out.println("result: " + result);
		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	// 모듈화
	public Dept 찾기(int deptno) {
		// " "안에 ';'넣으면 절때 안된다. 넣는 순간 오류.
		// prepareStatement, VALUES(?) = 준비가 되어 있지 않는 상태
		// deptno는 PK이기 때문에 무조껀 while
		String sql = "SELECT deptno, dname, loc FROM dept WHERE deptno = ?";
		Connection conn = DBConnection.getInstance();
		// 인터페이스가 적용되어 있는 함수. (따로 인터페이스(프로토콜)을 만들지 않아도 된다.)
		try {
			// conn으로 버퍼를 만들어서 통신해주는 코드.
			PreparedStatement pstmt = conn.prepareStatement(sql);
			// 인젝션 공격을 다 막아주는 코드.
			pstmt.setInt(1, deptno);

			// SELECT방법.
			// rs = 결과 집합.
			// 커서를 한 칸 씩 내려서 찾는 방식(중요!!)
			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) { // 커서가 한 칸 내려갔다.
				// builder 사용
				Dept dept = Dept.builder().deptno(rs.getInt("deptno")).dname(rs.getString("dname"))
						.loc(rs.getString("loc")).build();
				System.out.println(dept);
				return dept;

//					// 데이터를 꺼낸다.
//					// 실수할 수 있기에 변수에 옮겨서 사용한다.
//					int deptno2 = rs.getInt("deptno");
//					String dname = rs.getString("dname");
//					String loc = rs.getString("loc");
//					System.out.println(deptno2);
//					System.out.println(dname);
//					System.out.println(loc);
			}

		} catch (Exception e) {

			e.printStackTrace();
		}
		return null;
	}

	// 모듈화
	public List<Dept> 전체찾기() { // java.util이 가지고 있는 List

		String sql = "SELECT deptno, dname, loc FROM dept";
		Connection conn = DBConnection.getInstance();

		try {

			PreparedStatement pstmt = conn.prepareStatement(sql);
			// 인젝션 공격을 다 막아주는 코드.
			// pstmt.setInt(1, deptno);

			// SELECT방법.
			// rs = 결과 집합.
			// 커서를 한 칸 씩 내려서 찾는 방식(중요!!)
			ResultSet rs = pstmt.executeQuery();

			int count = 0;
			Vector<Dept> vc = new Vector<>();
			while (rs.next()) {
				Dept dept = Dept.builder().deptno(rs.getInt("deptno")).dname(rs.getString("dname"))
						.loc(rs.getString("loc")).build();
				vc.add(dept);
//						count++;
			}
			System.out.println(vc);
			return vc;

		} catch (Exception e) {

			e.printStackTrace();
		}
		return null;
	}

}
