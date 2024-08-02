package practice;

import java.sql.*;

public class Practice {
	
	
	/*
	 * * JDBC용 객체
	 * - Connection				: DB의 연결 정보를 담고있는 객체
	 * - [Prepared]Statement	: 연결된 DB에 sql문을 전달하여 실행하고
	 * 							  그 결과를 받아주는 객체 **
	 * - ResultSet				: DQL(SELECT)문 실행 후 조회 결과를 담고 있는 객체
	 * 
	 * * JDBC 과정 (* 순서 중요 *)
	 * 	1) jdbc driver 등록 : 해당 DBMS(오라클)가 제공하는 클래스 등록
	 *  2) Connection 생성 : 연결하고자 하는 DB정보를 입력해서 해당 DB와 연결하면서 생성
	 *  		- DB정보 : 접속 주소(url), 사용자이름(username), 사용자비밀번호(password)
	 *  3) Statement 생성 : Connection 객체를 이용하여 생성
	 *  				sql문을 실행하고 결과를 받아주는 역할
	 *  4) sql문을 DB에 전달하여 실행 (Statement 객체 사용)
	 *  5) 실행 결과를 받기
	 *    - SELECT 문 실행 : ResultSet 객체로 받음 (조회된 데이터들이 담겨져 있음)
	 *  6) 결과 처리
	 *    - ResultSet에 담겨져있는 데이터들을 하나하나 꺼내서 vo 객체에 옮겨 담기
	 *    - 트랜잭션 처리 (실행을 성공했으면 commit, 실패했으면 rollback)
	 *  7) 사용 후 JDBC용 객체들을 반납 (close) --> 생성 역순으로!
	 * 
	 */
	
	public static void main(String[] args) {
//		insertTest();
		selectTest();
	}
	
	public static void selectTest() {
		
		String URL = "jdbc:oracle:thin:@localhost:1521:xe";
		String USER_NAME = "C##JDBC";
		String PASSWORD = "JDBC";
		
		Connection conn = null;
		Statement stmt = null;
		ResultSet rset = null;
		
		String sql = "SELECT * FROM TEST";
		
		try {
		// 1) 드라이버 생성
		Class.forName("oracle.jdbc.driver.OracleDriver");
		
		// 2) 코넥션 생성(자동ㅋ커밋끄기 ㅋ)
		conn = DriverManager.getConnection(URL, USER_NAME, PASSWORD);
		conn.setAutoCommit(false);
		// 3) 스테잇먼트 객체 생성
		stmt = conn.createStatement();
		
		// 4) 스테잇먼트로 익스큣 - 트, rset에 저장
		rset= stmt.executeQuery(sql);
		
		// 5) 저장된 rset객체에서 데이터 뽑뽑
		while(rset.next()) {
			System.out.println(rset.getInt("TNO")+", "+rset.getString("TNAME")+", "+rset.getString("TDATE"));
		}
		// 6) 다했으면 커밋..해주지 않아도 되지 왜냐면 dql이니까
		
		// 7) close는 해야지
		
		
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
	}
	
	
	public static void insertTest() {
		
		String URL = "jdbc:oracle:thin:@localhost:1521:xe";
		String USER_NAME = "C##JDBC";
		String PASSWORD = "JDBC";
		
		Connection conn = null;
		Statement stmt = null;
		String sql = "INSERT INTO TEST VALUES(3, '허완', SYSDATE)";
		int result = 0;
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			System.out.println("드라이버 등록 완료");
			conn = DriverManager.getConnection(URL, USER_NAME, PASSWORD);
			conn.setAutoCommit(false);
			System.out.println("오라클 db 접속 성공");
			stmt = conn.createStatement();
			result = stmt.executeUpdate(sql);
			
			if(result>0) {
				// 0보다 크면 실행이 잘 되어서 1이상이 반환되었다는 뜻이니까.. 커밋?
				conn.commit();
				System.out.println("추가성공! 커밋이 완료됨");
			} else {
				// 그렇지 않으면 실패한거니까ㅏ... 롤백
				conn.rollback();
				System.out.println("추가실패! 롤백");
			} 
			
		
		} catch(ClassNotFoundException e) {
			e.printStackTrace();
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				stmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}



