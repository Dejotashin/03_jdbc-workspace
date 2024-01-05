package com.kh.controller;

import java.util.ArrayList;

import com.kh.model.dao.MemberDao;
import com.kh.model.vo.Member;
import com.kh.view.MemberMenu;

// Controller : view를 통해서 사용자가 요청한 기능에 대해서 처리하는 담당
// 				해당 메소드로 전달된 데이터 [가공처리한 후] Dao로 전달하면서 호출
//				Dao로 부터 반환받은 결과에 따라 성공인지 실패인지 판단 후 응답화면 결정

public class MemberController {
	
	/**
	 * 사용자의 회원 추가 요청을 처리해주는 메소드
	 * @param userId ~ hobby : 사용자가 입력했던 정보들이 담겨있는 매개변수
	 */
	public void insertMember(String userId, String userPwd, String userName, String gender, String age, String email, String phone, String address, String hobby) {
			
		// 받은값들을 데이터를 직접적으로 처리해주는 DAO로 넘기기!!
		// => 어딘가에 주섬주섬 담아서 전달!
		// 어딘가??? => Member 객체
		
		// 방법1) 기본생성자로 생성한 후 각 필드에 setter 메소드 통해서 일일히 담는 방법 => 이건 매개변수가 몇개 없을 때..
		// 방법2) 아싸리 매개변수 생성자를 통해서 생성과 동시에 담는 방법
		
		Member m = new Member(userId, userPwd, userName, gender, Integer.parseInt(age), email, phone, address, hobby);
		// 여기서 나이를 반드시 int형으로 바꿔야함!
		// String => int로 변경하는 방법? parseInt();
		
		
		
		int result = new MemberDao().insertMember(m);
		
		// 팝업창 뜨는 것 처럼 사용자에게 문구보여주기
		if(result > 0) {// 성공
			new MemberMenu().displaySuccess("성공적으로 회원 추가 되었습니다.");
		}else {// 실패
			new MemberMenu().displayFail("회원 추가 실패했습니다...");
		}
		
		
		}
	/**
	 * 사용자의 회원 전체 조회 요청을 처리해주는 메소드
	 */
	public void selectList() {
		ArrayList<Member> list = new MemberDao().selectList();
		
		// 조회결과가 있는지 없는지 판단 한 후 사용자가 보게될 응답화면 지정
		if(list.isEmpty()) { // 텅 비어있을 경우 == 조회된 데이터 없었을 경우
			new MemberMenu().displayNoData("전체 조회결과가 없습니다");
		}else { // 뭐라도 조회된 데이터가 있을 경우
			new MemberMenu().displayMemberList(list);
		}
		
	}
	public void inputMemberId(String id) {
		ArrayList<Member> list = new MemberDao().inputMemberId(id);
		if(list.isEmpty()) {
			new MemberMenu().displayNoData("조회결과가 없습니다");
		}else {
			new MemberMenu().displayMemberList(list);
		}
			
		
	}
	
	
	
}
