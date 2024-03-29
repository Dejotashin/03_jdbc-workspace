package com.kh.model.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import static com.kh.common.JDBCTemplate.*;
import com.kh.model.vo.Member;

// DAO(Data Access Object) : DB에 직접적으로 접근해서 사용자의 요청에 맞는 sql문 실행 후 결과받기(JDBC)
//							 결과를 Controller로 다시 리턴
public class MemberDao {
	public int insertMember(Connection conn, Member m) {

		// insert문 => 처리된 행수 => 트랜젝션 처리
		int result = 0;
		// conn은 이미 서비스에서 생성돼있음!
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String sql = "INSERT INTO MEMBER VALUES(SEQ_USERNO.NEXTVAL, ?, ?, ?, ?, ?, ?, ?, ?, ?, SYSDATE)";

		try {
			pstmt = conn.prepareStatement(sql); // 미완성된 sql문

			pstmt.setString(1, m.getUserId());
			pstmt.setString(2, m.getUserPwd());
			pstmt.setString(3, m.getUserName());
			pstmt.setString(4, m.getGender());
			pstmt.setInt(5, m.getAge());
			pstmt.setString(6, m.getEmail());
			pstmt.setString(7, m.getPhone());
			pstmt.setString(8, m.getAddress());
			pstmt.setString(9, m.getHobby());

			result = pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
			// conn은 아직 반납하면 안됨!! (트랜젝션 처리 이따가 서비스에서 해야하기 때문에!)
		}
		return result;

	}

	public ArrayList<Member> selectList(Connection conn) {
		// select문(여러행) = ResultSet => ArrayList

		PreparedStatement pstmt = null;
		ResultSet rset = null;
		ArrayList<Member> list = new ArrayList<Member>();
		String sql = "SELECT * FROM MEMBER ORDER BY USERNAME";

		try {

			pstmt = conn.prepareStatement(sql);
			rset = pstmt.executeQuery();

			while (rset.next()) {
				list.add(new Member(rset.getInt("userno"), rset.getString("userid"), rset.getString("userpwd"),
						rset.getString("username"), rset.getString("gender"), rset.getInt("age"),
						rset.getString("email"), rset.getString("phone"), rset.getString("address"),
						rset.getString("hobby"), rset.getDate("enrolldate")));

			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		}
		return list;
	}

	public Member selectByUserId(Connection conn, String userId) {
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		Member m = null;
		String sql = "SELECT * FROM MEMBER WHERE USERID = ?";

		try {

			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, userId);
			rset = pstmt.executeQuery();

			if (rset.next()) { // 한행이라도 조회됐을 때
				// 조회됐다면 해당 조회 된 컬럼값들을 뽑아서 한 Member 객체의 각필드에 담기
				m = new Member(rset.getInt("userno"), rset.getString("userid"), rset.getString("userpwd"),
						rset.getString("username"), rset.getString("gender"), rset.getInt("age"),
						rset.getString("email"), rset.getString("phone"), rset.getString("address"),
						rset.getString("hobby"), rset.getDate("enrolldate"));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);

		}
		return m;

	}

	public ArrayList<Member> selectByUserName(Connection conn, String keyword) {
		ArrayList<Member> list = new ArrayList<Member>();
		PreparedStatement pstmt = null;
		ResultSet rset = null;

		String sql = "SELECT * FROM MEMBER WHERE USERNAME LIKE ?";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, "%" + keyword + "%");
			rset = pstmt.executeQuery();

			while (rset.next()) {
				list.add(new Member(rset.getInt("userno"), rset.getString("userid"), rset.getString("userpwd"),
						rset.getString("username"), rset.getString("gender"), rset.getInt("age"),
						rset.getString("email"), rset.getString("phone"), rset.getString("address"),
						rset.getString("hobby"), rset.getDate("enrolldate")));

			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		}

		return list;

	}
	public int updateMember(Connection conn, Member m) {
		int result = 0;
		PreparedStatement pstmt = null;
		String sql = "UPDATE MEMBER SET USERPWD = ?, EMAIL = ?, PHONE = ?, ADDRESS = ? WHERE USERID = ?";
		
		try {
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, m.getUserPwd());
			pstmt.setString(2, m.getEmail());
			pstmt.setString(3, m.getPhone());
			pstmt.setString(4, m.getAddress());
			pstmt.setString(5, m.getUserId());
			
			result = pstmt.executeUpdate();
		
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(pstmt);
		}
		return result;
		
		
		
	}
	
	public int deleteMember(Connection conn, String userId) {
		// dml문 => 처리된 행수 => 트랜젝션 처리
		
		int result = 0;
		PreparedStatement pstmt = null;
		
		String sql = "DELETE FROM MEMBER WHERE USERID = ?";
		
		try {
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, userId);
			
			result = pstmt.executeUpdate();
			
			
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(pstmt);
		}
		return result;
		
	}
	
	
	
	
	
	
}