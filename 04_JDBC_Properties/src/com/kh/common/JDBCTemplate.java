package com.kh.common;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

// 공통 템플릿 (매번 반복적으로 작성될 코드를 메소드로 정의해둘꺼임)
public class JDBCTemplate {

	// 모든 메소드 싹 다 static 메소드!!
	// 이건 실행되자마자 메모리 영역에 다 올라감!
	// 싱글톤 패턴 : 메모리 영역에 단 한 번만 올려두고, 매번 재사용 개념(Math 클래스 같은거..)a
	
	/**
	 * 1. Coneection 객체 생성 (DB와 접속) 한 후 해당 Connection 객체 반환해주는 메소드bpbpbpbpbnbp
	 * @return
	 */
	public static Connection getConnection() {
		
		/*
		 * 기존의 방식 : jdbc driver 구문, 접속할 db의 url, 접속할 계정명/비번 들을 자바소스코드 내에 명시적을 작성함 => 정적코딩방식
		 * 
		 * > 문제점 : DBMS가 변경되었을 경우, 접속할 DB의 URL또는 계정명/비번이 변경될 경우 => 자바 소스코드를 수정해야됨!
		 * 			=> 수정된 내용을 반영시키고자 한다면 프로그램 재기동 해야됨!! (프로그램이 비정상적으로 종료됐다가 다시 구동)
		 * 			=> 유지보수에 불편하다!!
		 * > 해결방식 : DB관련된 정보들을 별도로 관리하는 외부파일 만들어서 관리!
		 * 			 외부파일로부터 읽어들여서 반영 시키면 됨! => 동적코딩방식
		 * 
		 */
						
		Connection conn = null;
		Properties prop = new Properties();
		
		
		try {
			
			prop.load(new FileInputStream("resources/driver.properties"));
			
			
			
			Class.forName(prop.getProperty("driver"));
			conn = DriverManager.getConnection(prop.getProperty("url"), 
											   prop.getProperty("username"), 
											   prop.getProperty("password"));

			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return conn;
	}
	/** 2. Commit 처리해주는 메소드 (Coneection 객체 전달받아서)
	 * @param conn
	 */
	public static void Commit(Connection conn) {
		try {
			if(conn != null && !conn.isClosed()) {
				conn.commit();
			
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
			
	}
	
	
	/** 3. Rollback 처리해주는 메소드 (Connection 객체 전달받아서)
	 * @param conn
	 */
	public static void Rollback(Connection conn) {
		try {
			if(conn != null && !conn.isClosed()) {
				conn.rollback();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	
	}
	
	// JDBC용 객체들 전달받아서 반납처리해주는 메소드
	
	/**
	 * 4. Statement 관련 객체 전달받아서 반납시켜주는 메소드
	 * @param stmt
	 */
	public static void close(Statement stmt) { // 얘까 부모라서 PreparedStatement 받을 수 있음! 다형성!!
		try {
			if(stmt != null && !stmt.isClosed()) {
				stmt.close();
			}
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void close(Connection conn) {
		try {
			if(conn != null && !conn.isClosed()) {
				conn.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/** 6. ResultSet 객체 전달 받아서 반납시켜주는 메소드
	 * @param rset
	 */
	public static void close(ResultSet rset) {
		try {
			if(rset != null && !rset.isClosed()) {
				rset.close();
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	
	
}
