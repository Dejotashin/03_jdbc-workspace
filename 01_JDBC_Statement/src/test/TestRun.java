package test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class TestRun {

	public static void main(String[] args) {

		/*
		 * * JDBC용 객체 - Connection : DB의 연결정보를 담고있는 객체 - [Prepared]Statement : 연결된 DB에
		 * SQL문 전달해서 실행 그 결과를 받아내는 객체 ***** - ResultSet : SELECT문 실행 후 조회된 결과물들이 담겨있는 객체
		 * 
		 * 
		 * * JDBC 과정 (순서중요!)
		 *  1) jdbc driver 등록 : 해당 DBMS(오라클)가 제공하는 클래스 등록 
		 *  2) Connection 생성 : 연결하고자 하는 DB정보를 입력해서 해당 DB와 연결하면서 생성
		 * 
		 *  3) Statement 생성 : Connection 객체를 이용해서 생성 (sql문 실행 및 결과 받는 객체)
		 * 
		 *  4) sql문 전달하면서 실행 : Statement 객체를 이용해서 sql문 실행 
		 * 	5) 결과받기 > SELECT문 실행 => ResultSet 객체 (조회된 데이터들이 담겨있음) > DML문 실행 => int (처리된 행 수)
		 * => 6_2
		 * 
		 * 6_1) ResultSet에 담겨있는 데이터들을 하나씩 하나씩 뽑아서 vo객체에 주섬주섬 옮겨담기 [+ 여러행 조회시에는
		 * ArrayList에 차곡차곡 담기] 6_2) 트랜젝션 처리(성공적으로 수행했으면 commit, 실패했으면 rollback)
		 * 
		 * 7) 다 사용한 JDBC용 객체들 반드시 자원 반납!! 안하면 디비 락걸림! (close) => 생성된 역순으로!
		 * 
		 *
		 */

		// 1. 각자 pc(localhost)에 JDBC계정에 연결 한 후 TEST 테이블에 INSERT 해보기!
		// insert문 => 처리된 행수(int) => 트랜젝션 처리

		// 필요한 변수들 먼저 셋팅
		int result = 0; // 결과(처리된 행수)를 받아줄 변수
		Connection conn = null; // DB의 연결정보를 보관할 객체
		Statement stmt = null; // sql문을 전달해서 실행 후 결과 받는 객체

		// ResultSet은 select문에서만 필요한거! 지금은 패스~

		// 앞으로 실행할 sql문 작성("완성형태"로 만들어두기)
		// 중요!!!! ("쿼리문" 안에 세미콜론 없어야됨!! 주요에러나는 부분이 여기임)
		String sql = "INSERT INTO TEST VALUES(1, '차은우', SYSDATE)";

		try {
			// 1) jdbc driver 등록
			// forName : class 파일 찾아서 등록한다는 의미!
			Class.forName("oracle.jdbc.driver.OracleDriver");
			System.out.println("jdbc driver 등록 성공!");
			
			// OracleDriver 이건 자바에서 제공하는 클래스가 아님...
			// 패키지부터 보면 oracle로 시작함
			
			// 그럼 어캐써야하나 => ojdbc6.jar 파일을 등록해줘야함
			
			// 2. Connection 객체 생성 : DB에 연결(url, 계정명, 비번)
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "JDBC", "JDBC");
			 
			// 3. Statement 생성 : Connection 객체를 이용해서 생성 (sql문 실행 및 결과 받는 객체)
			stmt = conn.createStatement();
			
			// 4, 5) sql문 전달하면서 실행 후 결과 받기
			result = stmt.executeUpdate(sql);
			// 내가 실행한 sql문이 DML문(I,U,D)일 경우 => stmt.executeUpdate("dml문"); : int
			// 내가 실행할 sql문이 SELECT문 일 경우 => stmt.executeQuery("select문") : ResultSet
			
			//6) 트랜젝션 처리
			if(result > 0) { // 성공했을 경우 commit
				conn.commit();
			}else { // 실패했을 경우 rollback
				conn.rollback();
			}
			
				
			
		} catch (ClassNotFoundException e) {
			System.out.println("OracleDriver 클래스 등록 실패");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			// 7) 다 쓴 JDBC용 객체 자원 반납 (생성된 역순으로)
		try {
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 		
		
		
		
		
		
		}

	}

}
