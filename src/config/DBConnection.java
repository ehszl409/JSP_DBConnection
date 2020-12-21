package config;

import java.sql.Connection;
import java.sql.DriverManager;

import oracle.jdbc.driver.OracleDriver;

public class DBConnection {
	
	// 굳이 new를 띄우지 않아도 되기에 static
	// 목적 : Connection을 return 하는 것.
	public static Connection getInstance() {
		// Connetion 객체를 가지고 올것이다.
		Connection conn = null;
		// 주소를 저장하는 변수 
		// 주소 앞에 적어줘야 하는 고정 값(jdbc:oracle:thin:@)이 있다
		// thin : 순수하게 자바 패키지만으로 바로 DB와 연결. 순수하게 자바함수로 연결하는 것.
		// oci : 운영체제의 네이티브 함수를 사용한는 것. 윈도우나 리눅스가 가지고 있는 함수로 연결하는 것.
		// 연결은 한 번만 하기에 이 코드는 프로젝트 기간동안 한 번만 적으면 된다.
		// conn에 연결하기 위한 준비 사항들.
		String url = "jdbc:oracle:thin:@localhost:1521:xe";
		String username = "SCOTT";
		String password = "TIGER";
		
		try {
			// ( )을 new해서 띄워주는게 forname
			// OracleDriver 클래스를 메모리에 로드
			// 문자열을 호출하는 곳에 실수할 수 있기때문에 try이가 동반된다.
			// (중요하다.)
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			// 메모리에 OracleDriver를 띄우를 코드
			// 오류 : username등이 틀렸을 가능성이 있다
			// reflection : 메모리에 떠있는 어떤 클래스를 타입으로 찾아내는 기법.
			// 				그리고 어떤 피드가 있는지 분석할 수 있다.
			//				그래서 그때 그때마다 때려지는 타입이 다르다.
			//				(크게 중요하지 않아서 넘어가면 된다.)
			conn = DriverManager.getConnection(url,username,password);
			System.out.println("DB연결 성공");
			return conn;
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("DB연결 실패");
		// catch를 못해주기 때문에 사용하는 코드.
		return null;
	}
}
