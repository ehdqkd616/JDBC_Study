package com.kh.model.dao;

import static com.kh.common.JDBCTemplate.close;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Properties;

import com.kh.model.vo.Member;

public class MemberDAO {
	
	private Properties prop = null;
	
	public MemberDAO() {
	
		try {
			prop = new Properties();
			prop.load(new FileReader("query.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public int insertMember(Connection conn, Member member) {
		/*
			testJDBC1 프로젝트에서 DAO가 했던 업무
				1) JDBC 드라이버 등록
				2) DB 연결(Connection 객체 생성)
				3) SQL 실행
				4) 트랜젝션 처리
				5) 자원 반납(close())
				
			실제로 DAO가 처리해야 하는 업무 : 3번(SQL문을 DB로 전달하여 실행하고 반환 값 받아오기)
		 	-- > 1, 2, 4, 5번 업무 분담 : com.kh.common.JDBCTemplate
		 
		 */
		
		String query = prop.getProperty("insertMember");
		PreparedStatement pstmt = null;
		int result = 0;
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, member.getMemberId());
			pstmt.setString(2, member.getMemberPwd());
			pstmt.setString(3, member.getMemberName());
			pstmt.setString(4, member.getGender()+"");
			pstmt.setString(5, member.getEmail());
			pstmt.setString(6, member.getPhone());
			pstmt.setString(7, member.getAddress());
			pstmt.setInt(8, member.getAge());
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
		}
		
		return result;
	}

	public ArrayList<Member> selectAll(Connection conn) {
		Statement stmt = null;
		ResultSet rset = null;
		ArrayList<Member> mList = null;
		
		String query = prop.getProperty("selectAll");
		try {
			stmt = conn.createStatement();
			rset = stmt.executeQuery(query);
			
			mList = new ArrayList<Member>();
			
			Member member = null;
			while(rset.next()) {
				String memberId = rset.getString("member_id");
				String memberPwd = rset.getString("member_pwd");
				String memberName = rset.getString("member_name");
				char gender = rset.getString("gender").charAt(0);
				String email = rset.getString("email");
				String phone = rset.getString("phone");
				String address = rset.getString("address");
				int age = rset.getInt("age");
				Date enrollDate = rset.getDate("enroll_date");
				// 테이블의 컬럼명이 아닌 sql문 실행후 나오는 컬럼명 지정할것(별칭 지었으면 별칭으로)
				member = new Member(memberId, memberPwd, memberName, gender, email, phone, address, age, enrollDate);
				
				mList.add(member);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(stmt);
		}
		
		return mList;
	}

	// 한글자 라도 포함되어있으면 가져오기
	public ArrayList<Member> selectMemberId(Connection conn, String id) {
//		Statement stmt = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		ArrayList<Member> mList = null;
		
		String query = prop.getProperty("selectMemberId");
//		selectMemberId=SELECT * FROM MEMBER WHERE MEMBER_ID LIKE
//		query += "'%" + id + "%'";
		
		try {
//			stmt = conn.createStatement();
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, "%" + id + "%");
			
//			rset = stmt.executeQuery(query);
			rset = pstmt.executeQuery();
			
			mList = new ArrayList<Member>();
			
			while(rset.next()) {
				String memberId = rset.getString("member_id");
				String memberPwd = rset.getString("member_pwd");
				String memberName = rset.getString("member_name");
				char gender = rset.getString("gender").charAt(0);
				String email = rset.getString("email");
				String phone = rset.getString("phone");
				String address = rset.getString("address");
				int age = rset.getInt("age");
				Date enrollDate = rset.getDate("enroll_date");
				
				Member member = new Member(memberId, memberPwd, memberName, gender, email, phone, address, age, enrollDate);
				
				mList.add(member);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rset);
//			close(stmt);
			close(pstmt);
		}
		
		return mList;
		
	}

	public ArrayList<Member> selectGender(Connection conn, char gender) {
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		ArrayList<Member> mList = null;
		
		String query = prop.getProperty("selectGender");
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, gender+"");
			rset = pstmt.executeQuery();
			mList = new ArrayList<Member>();
			while(rset.next()) {
				String memberId = rset.getString("member_id");
				String memberPwd = rset.getString("member_pwd");
				String memberName = rset.getString("member_name");
				char gen = rset.getString("gender").charAt(0);
				String email = rset.getString("email");
				String phone = rset.getString("phone");
				int age = rset.getInt("age");
				String address = rset.getString("address");
				Date enrollDate = rset.getDate("enroll_date");
				
				Member member = new Member(memberId, memberPwd, memberName, gen, email, phone, address, age, enrollDate);
				
				mList.add(member);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		}
		return mList;
	}

	public int checkMember(Connection conn, String memberId) {
		// select ~~~ from member where member_id = ?
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		int check = 0;
		
		String query = prop.getProperty("checkMember");
		
		// SELECT COUNT(*) FROM MEMBER WHERE MEMBER_ID = ?
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, memberId);
			
			rset = pstmt.executeQuery();
			// 1 or 0
			
			if(rset.next()) {
				check = rset.getInt(1);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		}
		
		return check;
	}

	public int updateMember(Connection conn, String memberId, int sel, String input) {
		PreparedStatement pstmt = null;
		int result = 0;
		
		String query = prop.getProperty("updateMember" + sel);
		// 비밀번호 : updateMember1
		// 이메일	 : updateMember2
		// 전화번호 : updateMember3
		// 주소	 : updateMember4
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, input);
			pstmt.setString(2, memberId);
			
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
		}
		
		return result;
	}

	public int deleteMember(Connection conn, String memberId) {
		PreparedStatement pstmt = null;
		int result = 0;
		
		String query = prop.getProperty("deleteMember");
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, memberId);
			
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
		}
		return result;
	}

}
