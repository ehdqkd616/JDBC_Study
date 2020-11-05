package com.kh.controller;

import java.util.ArrayList;

import com.kh.model.dao.EmployeeDAO;
import com.kh.model.vo.Employee;
import com.kh.view.Menu;

public class EmployeeController {
	// View에서 받은 데이터를 가공하여 dao로 전달
	// dao에서 전달받은 데이터 값에 따라 View를 결정하여 데이터 전송
	private EmployeeDAO empDAO = new EmployeeDAO();
	private Menu menu = new Menu();
	
	// 전체 사원 정보 조회
	public void selectAll() {
		ArrayList<Employee> empList = empDAO.selectAll();
		
		if(!empList.isEmpty()) { // 조회 결과가 있을 경우
			menu.selectAll(empList);
		} else { // 조회 결과가 없을 경우
			menu.displayError("조회 결과가 없습니다.");
		}
	}

	public void selectEmployee() {
		int empNo = menu.selectEmpNo(); // 사번 입력하는 뷰 호출
		Employee emp = empDAO.selectEmployee(empNo);
		if(emp != null) { // 사원이 존재하면
			menu.selectEmployee(emp);
		}else { // 사원이 존재하지 않으면
			menu.displayError("해당 사번의 검색 결과가 없습니다.");
		}
	}

	public void insertEmployee() {
		Employee emp = menu.insertEmployee();
		int result = empDAO.insertEmployee(emp);
		if(result > 0) {
			menu.displaySuccess(result + "개의 행이 추가되었습니다.");
		}else {
			menu.displayError("데이터 삽입 과정 중 오류 발생");
		}
	}

	public void updateEmployee() {
		int empNo = menu.selectEmpNo();
		Employee emp = menu.updateEmployee();
		emp.setEmpNo(empNo);
		int result = empDAO.updateEmployee(emp);
		if(result > 0) {
			menu.displaySuccess(result + "개의 행이 변경되었습니다.");
		}else {
			menu.displayError("데이터 수정 과정 중 오류 발생");
		}
	}

	public void deleteEmployee() {
		int empNo = menu.selectEmpNo();
		char yn = menu.deleteEmployee();
		int result = 0;
		if(yn == 'y') {
			result = empDAO.deleteEmployee(empNo);
			if(result > 0) {
				menu.displaySuccess(result + "개의 행이 삭제되었습니다.");
			}else {
				menu.displayError("데이터 삭제 도중 오류가 발생하였습니다.");
			}
		}else if(yn == 'n') {
			menu.displaySuccess("사원 정보 삭제 취소");
		}else{
			menu.displayError("잘못 입력하셨습니다.");
		}
	}

}
