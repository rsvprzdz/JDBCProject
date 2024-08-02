package com.kh.controller;

import java.util.ArrayList;

import com.kh.model.dao.MemberDao;
import com.kh.model.service.MemberService;
import com.kh.model.vo.Member;
import com.kh.view.MemberMenu;

// Controller : View를 통해서 사용자가 요청한 기능에 대해 처리하는 역할
// 				요청 받은 메소드에서 전달된 데이터를 가공처리한 후 DAO로 전달하여 뭔가를 호출하게 될것임
//				DAO로부터 반환받은 결과에 따라 성공인지 실패인지 판단한 후 응답

public class MemberController {

	/**
	 * 회원 추가 요청을 처리할 메소드
	 * @param userId 회원아이디 
	 */
	public void insertMember(String userId, String userPwd, String name, String gender) {
		// view로부터 전달받은 값을들 dao에게 바로 전달하지 않고
		// 어딘가(Member)에 담아서 전달
		
		// * 기본생성자 생성 후 setter 메소드 하나하나 저장 or
		// * 매개변수 생성자를 사용하여 객체 생성
		Member m = new Member(userId, userPwd, name, gender);
		
		int result = new MemberService().insertMember(m);
		
		if (result > 0) {
			// DAO 의 결과 : 성공했을 경우 성공화면
			new MemberMenu().displaySuccess("회원 추가 성공!");
		} else {
			// DAO 의 결과 : 실패했을 경우 실패화면
			new MemberMenu().displayFailed("회원 추가 실패!");
		}
	}
	
	/**
	 * 전체 회원 목록 조회
	 */
	public void selectList() {
		ArrayList<Member> list = new MemberService().selectList();
		
		// 조회된 결과에 따라 사용자에게 결과 표시
		if (list.isEmpty()) {
			// 조회 결과가 없다면 (=> 리스트가 비어있다면)
			new MemberMenu().displayNoData("전체 조회 결과가 없음!");
		} else {
			new MemberMenu().displayMemberList(list);
		}
	}

	/**
	 * 전달받은 id로 SELECT
	 * @param id
	 */
	public void searchById(String userId) {
		Member m = new MemberService().selectByUserId(userId);
		
		if(m==null) {
			// 검색 결과가 없는 경우
			new MemberMenu().displayNoData(userId+"에 해당하는 결과가 없음!");
			
		} else {
			// 검색 결과가 있는 경우
			new MemberMenu().displayMember(m);
		}
	}

	public void modifyById(String userId, String selectedInfo, String afterInfo) {
		int result = new MemberService().modifyById(userId, selectedInfo, afterInfo);
		
		if(result>0) {
			new MemberMenu().displaySuccess("요청하신 정보가 수정되었습니다.");
		} else {
			new MemberMenu().displayFailed("수정실패..");
		}
		
	}

	public boolean modifySearchById(String userId) {
		Member m = new MemberService().selectByUserId(userId);
		
		if(m==null) {
			// 검색 결과가 없는 경우
			new MemberMenu().displayNoData(userId+"에 해당하는 결과가 없음!");
			return false;
		} else {
			// 검색 결과가 있는 경우
			new MemberMenu().displayMember(m);
		} return true;
	}
	
	public void deleteById(String userId) {
		int result = new MemberService().deleteByUserId(userId);
		
		if(result>0) {
			//회원 탈퇴 성공
			new MemberMenu().displaySuccess("회원 탈퇴 성공");
			
		} else {
			// 검색 결과가 있는 경우
			new MemberMenu().displayFailed("회원 탈퇴 실패");
		}
	}

	public void modifyById2(String id, String userPw, String userName, String address, String phone, String hobby) {
		
		Member m = new Member();
		m.setUserId(id);
		m.setUserPw(userPw);
		m.setUserName(userName);
		m.setAddress(address);
		m.setPhone(phone);
		m.setHobby(hobby);
		
		int result = new MemberService().modifyById2(m);
		
		if(result>0) {
			new MemberMenu().displaySuccess("요청하신 정보가 수정되었습니다.");
		} else {
			new MemberMenu().displayFailed("수정실패..");
		}
	}

	/**
	 * 사용자가 입력한 이름을 키워드로 하여 회원 정보 검색
	 * @param keyword 이름에 대한 키워드 값
	 */
	public void searchByName(String keyword) {
		ArrayList<Member> list = new MemberService().selectByUserName(keyword);
		
		if (list.isEmpty()) {
			new MemberMenu().displayNoData("해당 이름에 대한 회원 정보가 없습니다.");
		} else {
			new MemberMenu().displayMemberList(list);
		}
	}
}
