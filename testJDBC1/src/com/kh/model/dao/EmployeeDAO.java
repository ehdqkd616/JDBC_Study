package com.kh.model.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.kh.model.vo.Employee;

public class EmployeeDAO {
	
	public ArrayList<Employee> selectAll() {
		Connection conn = null; // 연결 정보를 담은 객체
		Statement stmt = null; // Connection객체를 통해 DB에 sql문을 전달하여 실행시키고 결과 값을 반환 받는 역할
		ResultSet rset = null; // SELECT문을 사용한 질의 성공 시 반환되는 객체
		ArrayList<Employee> empList = null;
		try {
			// 해당 DB에 대한 라이브러리(JDB드라이버) 등록 작업
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "SCOTT", "SCOTT");
			
			// jdbc:oracle:thin
			//		JDBC드라이버가 thin타입
			
			// @localhost
			//		오라클이 설치된 서버의 ip가 자신의 컴퓨터임 == @127.0.0.1
			
			// 1521
			//		오라클 listener 포트번호
			
			// xe
			//		접속할 오라클 명
			System.out.println(conn); // 실패시 null 반환
			String query = "SELECT * FROM EMP";
			stmt = conn.createStatement();
			rset = stmt.executeQuery(query);
			
			empList = new ArrayList<Employee>(); // 조회결과를 넣을 리스트
			Employee emp = null; // SELECT문 결과 중 1개의 행을 담아두는 vo선언
			
			while(rset.next()) {
				int empNo = rset.getInt("EMPNO");
				String empName = rset.getString("ENAME");
				String job = rset.getString("JOB");
				int mgr = rset.getInt("MGR");
				Date hireDate = rset.getDate("HIREDATE");
				int sal = rset.getInt("SAL");
				int comm = rset.getInt("COMM");
				int deptNo = rset.getInt("DEPTNO");
				emp = new Employee(empNo, empName, job, mgr, hireDate, sal, comm, deptNo);
				empList.add(emp);
			}
			
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				rset.close();
				stmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return empList;
	}

	public Employee selectEmployee(int empNo) {
		Connection conn = null;
//		Statement stmt = null;
		ResultSet rset = null;
		Employee emp = null;
		PreparedStatement pstmt = null;
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "SCOTT", "SCOTT");
			// String query = "SELECT * FROM EMP WHERE EMPNO = " + empNo;
			String query = "SELECT * FROM EMP WHERE EMPNO = ?"; // 자동적으로 쿼리문에 ''이 들어감
			// Statement를 사용할땐 문자열을 넣고싶으면 ''표시 해줘야함
			
			// stmt = conn.createStatement();
			pstmt = conn.prepareStatement(query);
			
			// rset = stmt.executeQuery(query);
			pstmt.setInt(1, empNo);
			rset = pstmt.executeQuery();
			
			if(rset.next()) {
				String empName = rset.getString("ename");
				String job = rset.getString("job");
				int mgr = rset.getInt("mgr");
				Date hireDate = rset.getDate("hiredate");
				int sal = rset.getInt("sal");
				int comm = rset.getInt("comm");
				int deptNo = rset.getInt("deptno");
				emp = new Employee(empNo, empName, job, mgr, hireDate, sal, comm, deptNo);
			}
			
			
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				rset.close();
				pstmt.close();
//				stmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return emp;
		
	}

	public int insertEmployee(Employee emp) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		int result = 0;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","SCOTT","SCOTT");
			String query = "INSERT INTO EMP VALUES(?,?,?,?,SYSDATE,?,?,?)";
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, emp.getEmpNo());
			pstmt.setString(2, emp.getEmpName());
			pstmt.setString(3, emp.getJob());
			pstmt.setInt(4, emp.getMgr());
			//pstmt.setDate(5, new Date(System.currentTimeMillis())); 그냥 query에 SYSDATE넣자
			pstmt.setInt(5, emp.getSal());
			pstmt.setInt(6, emp.getComm());
			pstmt.setInt(7, emp.getDeptNo());
			result = pstmt.executeUpdate();
			
			if(result > 0) {
				conn.commit(); // sql COMMIT;
			}else {
				conn.rollback(); // sql ROLLBACK;
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				pstmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	public int updateEmployee(Employee emp) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		int result = 0;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","SCOTT","SCOTT");
			
			String query = "UPDATE EMP SET JOB = ?, SAL = ?, COMM = ? WHERE EMPNO = ?";
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, emp.getJob());
			pstmt.setInt(2, emp.getSal());
			pstmt.setInt(3, emp.getComm());
			pstmt.setInt(4, emp.getEmpNo());
			result = pstmt.executeUpdate();
			if(result > 0) {
				conn.commit();
			}else {
				conn.rollback();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				pstmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return result;
	}

	public int deleteEmployee(int empNo) {
		Connection conn = null;
		Statement stmt = null;
		int result = 0;
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","SCOTT","SCOTT");
			stmt = conn.createStatement();
			String query = "DELETE FROM EMP WHERE EMPNO = " + empNo;
			result = stmt.executeUpdate(query);
			if(result > 0) {
				conn.commit();
			}else {
				conn.rollback();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				stmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
	
		}
		
		return result;
	}

}
