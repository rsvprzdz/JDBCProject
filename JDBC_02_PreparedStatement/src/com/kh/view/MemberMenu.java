package com.kh.view;

import java.util.ArrayList;
import java.util.Scanner;

import com.kh.controller.MemberController;
import com.kh.model.vo.Member;

// View : 사용자가 보게 될 시각적인 요소(화면) 출력 및 입력
public class MemberMenu {
	// Scanner 객체 생성
	private Scanner sc = new Scanner(System.in);
	// MemberController 객체 생성
	private MemberController mc = new MemberController(); 
	
	/**
	 * 사용자가 보게 될 첫 화면 : 메인메뉴(화면)
	 */
	public void mainMenu() {
		while(true) {
		
			System.out.println("======== 회원 관리 프로그램 ========");
			System.out.println("1. 회원 추가");		// C (Create)
			System.out.println("2. 전체 회원 조회");	// R (Read)
			System.out.println("3. 회원 아이디로 검색");	// R (Read)
			System.out.println("4. 회원 정보 수정");	// U (Update)
			System.out.println("5. 회원 탈퇴");		// D (Delete)
			System.out.println("6. 회원 이름으로 키워드검색");
			System.out.println("9. 프로그램 종료");
		
			System.out.print(">> 메뉴 번호: ");
			int menu = sc.nextInt();
			sc.nextLine();
			
			switch(menu){
				case 1:
					addMenu();
					break;
				case 2:
					mc.selectList();
					break;
				case 3:
					searchById();
					break;
				case 4:
					modifyById2();
					break;
				case 5:
					deleteById();
					break;
				case 6:
					searchByName();
					break;
				case 9:
					System.out.println("프로그램 종료합니다...");
					break;
					
			}
		}
		
	}
	
	/**
	 * 회원 추가를 위한 메뉴 : 회원 정보 입력받아서 추가 요청
	 */
	
	public void addMenu() {
		System.out.print("아이디 : ");
		String userId = sc.nextLine();
		
		System.out.print("비밀번호 : ");
		String userPw = sc.nextLine();
		
		System.out.print("이름 : ");
		String name = sc.nextLine();
		
		System.out.print("성별 (M.F) : ");
		char gender = sc.nextLine().toUpperCase().charAt(0);
		
		// 회원 추가 요청 --> Controller에게 요청
		mc.insertMember(userId, userPw, name, gender);
		
	}
	
	/**
	 * 요청 처리 후 성공했을 경우 사용자에게 표시할 화면 
	 * @param message 성공메세지
	 */
	public void displaySuccess(String message) {
		System.out.println("서비스 요청 성공 : " + message);
	}
	
	/**
	 * 요청 처리 후 실패했을 경우 사용자에게 표시할 화면
	 * @param message 실패메세지
	 */
	public void displayFailed(String message) {
		System.out.println("서비스 요청 실패 : " + message);
	}

	public void displayNoData(String message) {
		System.out.println(message);
	}

	/**
	 * 조회 결과가 여러 행일 때 사용자에게 표시할 화면
	 * @param list 조회된 회원 정보가 담겨있는 리스트
	 */
	public void displayMemberList(ArrayList<Member> list) {
		
		System.out.println("--- 조회 결과 ---");
		
		for(int i=0; i<list.size(); i++) {
			System.out.println(list.get(i));
		}
		
	}
	
	/**
	 * 입력받은 아이디로 SELECT
	 */
	public void searchById() {
		System.out.println("조회할 아이디 : ");
		String id = sc.nextLine();
		
		mc.searchById(id);
	}
	
	public void displayMember(Member m) {
		
		System.out.println("--- 조회 결과 ---");
		
		System.out.println(m);
		
	}
	
	public void modifyById() {
		String selectedInfo = null;
		
		System.out.print("수정할 아이디 : ");
		String id = sc.nextLine();
		if(!mc.modifySearchById(id)) {
			System.out.println("메뉴로 돌아갑니다.");
			return;
		}
		
		System.out.println("==== 수정 가능한 정보 메뉴 ====");
		System.out.println("1. 비밀번호");
		System.out.println("2. 이름");
		System.out.println("3. 성별");
		System.out.println("4. 나이");
		System.out.println("5. 이메일");
		System.out.println("6. 주소");
		System.out.println("7. 전화번호");
		System.out.println("8. 취미");
		System.out.println("수정할 정보 선택 : ");
		int menu = sc.nextInt();
		sc.nextLine();
		System.out.println("수정할 내용 입력 : ");
		String afterInfo = sc.nextLine();
		
		switch(menu) {
		case 1: selectedInfo = "USERPW";
			break;
		case 2: selectedInfo = "USERNAME";
			break;
		case 3: selectedInfo = "GENDER";
			break;
		case 4: selectedInfo = "AGE";
			break;
		case 5: selectedInfo = "EMAIL";
			break;
		case 6: selectedInfo = "ADDRESS";
			break;
		case 7: selectedInfo = "PHONE";
			break;
		case 8: selectedInfo = "HOBBY";
			break;
		}
		
		mc.modifyById (id, selectedInfo, afterInfo);
	}
	
	
	public void modifyById2() {
		String selectedInfo = null;
		
		System.out.print("수정할 아이디 : ");
		String id = sc.nextLine();
		if(!mc.modifySearchById(id)) {
			System.out.println("메뉴로 돌아갑니다.");
			return;
		}
		System.out.println("===== 수정할 내용 ======");
		System.out.print("비밀번호 : ");
		String userPw = sc.nextLine();
		System.out.print("이름 : ");
		String userName = sc.nextLine();
		System.out.print("주소 : ");
		String address = sc.nextLine();
		System.out.print("전화번호 : ");
		String phone = sc.nextLine();
		System.out.print("취미 : ");
		String hobby = sc.nextLine();
		
		mc.modifyById2 (id, userPw, userName, address, phone, hobby);
	}
		
	public void deleteById() {
		System.out.println("탈퇴할 아이디 : ");
		String id = sc.nextLine();
		
		mc.deleteById(id);
	}
	
	public void searchByName() {
		System.out.println("--- 회원 이름으로 키워드 검색 ---");
		System.out.print("검색할 회원명 입력 : ");
		String name = sc.nextLine();
		
		mc.searchByName(name);
	};
}

                              
