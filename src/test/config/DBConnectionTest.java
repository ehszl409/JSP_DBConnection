package test.config;

import static org.junit.Assert.assertNotNull;

import java.sql.Connection;

import org.junit.Test;

import config.DBConnection;

public class DBConnectionTest {
	
	// 만드는 이유 : 메인을 만들어서 테스트 하면 안되기 때문.
	// 단위가 큰 프로젝트를 하면 왜 사용하는지 알게 된다.
	// 로그 대신 사용하면 가시적으로 보기도 편하다.
	@Test
	public void 데이터베이스연결_테스트() {
		//DBConnection.getInstance();
		// conn 값이 DB가 연결되었는지 저장된 변수
		Connection conn = DBConnection.getInstance();
		// conn값이 null인지 체크하는 함수.
		assertNotNull(conn);
	}
	// MAIN으로 테스트하면 안된다 시간이 오래 걸림.
}
