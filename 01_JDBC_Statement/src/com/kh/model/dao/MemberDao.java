package com.kh.model.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.kh.model.vo.Member;

// DAO(Data Access Object) : DB에 직접적으로 접근해서 사용자의 요청에 맞는 sql문 실행 후 결과받기(JDBC)
//							 결과를 Controller로 다시 리턴
public class MemberDao {
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
	
	/**
	 * 사용자가 입력한 정보들을 추가시켜주는 메소드
	 * @param m		: 사용자가 입력한 값들이 주섬주섬 담겨있는 Member 객체
	 * @return		: insert문 수행 후 처리된 행수
	 */
	public int insertMember(Member m) {
			// insert문 => 처리된 행수(int) => 트랜젝션 처리
			
			// 필요한 변수들 셋팅
			int result = 0; // 처리된 결과(처리된 행수)를 받아줄 변수
			Connection conn = null; // 연결된 DB의 연결정보를 담는 객체
			Statement stmt = null; // "완성된 sql(실제 값이 다 채워지 상태로)문" 전달해서 곧바로 실행 후 결과 받는 객체
	
		// 실행할 sql문 (완성된 형태로 만들어두기!!)
		// INSERT INTO MEMBER VALUES(SEQ_USERNO.NEXTVAL, 'XXX', 'XXX', 'XXX', 'X', XX, 'XXXXXXX', 'XXXXXX', 'XXXX', 'XXX', SYSDATE);
		
		String sql = "INSERT INTO MEMBER VALUES(SEQ_USERNO.NEXTVAL,"
										+ "'" + m.getUserId() + "', " 
										+ "'" + m.getUserPwd() + "', "
										+ "'" + m.getUserName() + "', "
										+ "'" + m.getGender() + "', "
										 	  + m.getAge()  + "," 
										+ "'" + m.getEmail() + "', "
										+ "'" + m.getPhone() + "', "
										+ "'" + m.getAddress() + "', "
										+ "'" + m.getHobby() + "', SYSDATE)";
	
			
			try {// 1) jdbc driver 등록
				Class.forName("oracle.jdbc.driver.OracleDriver"); // ojdbc6.jar 추가!
				
				// 2) Connection 객체 생성 == db연결
				conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "JDBC", "JDBC");
				
				
				// 3) Statement 객체 생성
				stmt = conn.createStatement();
				
				// 4, 5) sql문 전달하면서 실행 후 결과 받기
				result = stmt.executeUpdate(sql);
				
				// 6) 트랜젝션 처리
				if(result > 0 ) {// 성공시 커밋
					conn.commit();
				}else {// 실패시 롤백
					conn.rollback();
				}
				
			} catch (ClassNotFoundException e) {
				
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally {
				// 7) 다 쓴 JDBC용 객체 반납!
				try {
					stmt.close();
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
				return result;


	}
	
	public  ArrayList<Member> selectList() {
		// select문(여러행 조회) => ResultSet 객체 => ArrayList에 차곡차곡 담기
		
		// 필요한 변수들 셋팅
		ArrayList<Member> list = new ArrayList<Member>(); // [] 텅비어있음
		
		Connection conn = null;
		Statement stmt = null;
		ResultSet rset = null; // select문 실행시 조회된 결과값들이 최초로 담기는 객체
		
		String sql = "SELECT * FROM MEMBER";
		
		
		try {
			// 1) jdbc driver 등록
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			// 2) Connection 객체 생성
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "JDBC", "JDBC");
			
			// 3) Statement 객체 생성
			stmt = conn.createStatement();
			
			// 4, 5) sql 실행 및 결과받기
			rset = stmt.executeQuery(sql);
			
			// 6) ResultSet으로 부터 데이터 하나씩 뽑아서 vo객체에 주섬주섬 담고 + list에 vo 객체 추가
			
			while(rset.next()) {
				
				// 현재 rset의 커서가 가리키고 있는 한 행의 데이터들을 싹 다 뽑아서 Member 객체 주섬주섬 담기
				Member m = new Member();
				
				m.setUserNO(rset.getInt("USERNO"));
				m.setUserId(rset.getString("USERID"));
				m.setUserPwd(rset.getString("USERPWD"));
				m.setUserName(rset.getString("USERNAME"));
				m.setGender(rset.getString("GENDER"));
				m.setAge(rset.getInt("AGE"));
				m.setEmail(rset.getString("EMAIL"));
				m.setPhone(rset.getString("PHONE"));
				m.setAddress(rset.getString("ADDRESS"));
				m.setHobby(rset.getString("HOBBY"));
				m.setEnrollDate(rset.getDate("ENROLLDATE"));
				// 현재 참조하고 있는 행에 대한 모든 컬럼데이터들을 한 Member 객체에 담기
				
				list.add(m); // 리스트에 해당 회원 객체 차곡차곡 담기
				
			}
			
			// 반복문이 다 끝난 시점에
			// 만약에 조회된 데이터가 있었다면 list에 뭐라도 담겨 있을 것!
			// 만약에 조회된 데이터가 없었다면 list가 텅 빈 상태일꺼임!!
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			try {
				// 7) 다 쓴 자원 반납
				rset.close();
				stmt.close();
				conn.close();
					
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return list; // 텅빈 리스트 | 뭐라도 담겨있는 리스트
	}
	
	public  ArrayList<Member> inputMemberId(String id) {
		// select문(여러행 조회) => ResultSet 객체 => ArrayList에 차곡차곡 담기
		
		// 필요한 변수들 셋팅
		ArrayList<Member> list = new ArrayList<Member>(); // [] 텅비어있음
		
		Connection conn = null;
		Statement stmt = null;
		ResultSet rset = null; // select문 실행시 조회된 결과값들이 최초로 담기는 객체
		String sql = "SELECT * FROM MEMBER WHERE USERID ='" + id + "'";
		// 1) jdbc driver 등록
		try {
			// 1) jdbc driver 등록
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			// 2) Connection 객체 생성
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "JDBC", "JDBC");
			
			// 3) Statement 객체 생성
			stmt = conn.createStatement();
			
			// 4, 5) sql 실행 및 결과받기
			rset = stmt.executeQuery(sql);
			
			// 6) ResultSet으로 부터 데이터 하나씩 뽑아서 vo객체에 주섬주섬 담고 + list에 vo 객체 추가
			
			rset.next();
				
				// 현재 rset의 커서가 가리키고 있는 한 행의 데이터들을 싹 다 뽑아서 Member 객체 주섬주섬 담기
				Member m = new Member();
				
				m.setUserNO(rset.getInt("USERNO"));
				m.setUserId(rset.getString("USERID"));
				m.setUserPwd(rset.getString("USERPWD"));
				m.setUserName(rset.getString("USERNAME"));
				m.setGender(rset.getString("GENDER"));
				m.setAge(rset.getInt("AGE"));
				m.setEmail(rset.getString("EMAIL"));
				m.setPhone(rset.getString("PHONE"));
				m.setAddress(rset.getString("ADDRESS"));
				m.setHobby(rset.getString("HOBBY"));
				m.setEnrollDate(rset.getDate("ENROLLDATE"));
				// 현재 참조하고 있는 행에 대한 모든 컬럼데이터들을 한 Member 객체에 담기
				
				list.add(m); // 리스트에 해당 회원 객체 차곡차곡 담기
				
			
			
			// 반복문이 다 끝난 시점에
			// 만약에 조회된 데이터가 있었다면 list에 뭐라도 담겨 있을 것!
			// 만약에 조회된 데이터가 없었다면 list가 텅 빈 상태일꺼임!!
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			try {
				// 7) 다 쓴 자원 반납
				rset.close();
				stmt.close();
				conn.close();
					
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return list; // 텅빈 리스트 | 뭐라도 담겨있는 리스트
	}
			
			
	}
