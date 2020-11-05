package com.kh.board.controller;

import java.util.ArrayList;

import com.kh.board.model.service.BoardService;
import com.kh.board.model.vo.Board;
import com.kh.view.View;

public class BoardController {

	private BoardService bs = new BoardService();
	private View view = new View();
	public void selectAll() {
		ArrayList<Board> bList = bs.selectAll();
		view.selectAll(bList);
	}
	public void selectOne() {
		int bNo = view.inputBNo();
		Board board = bs.seleteOne(bNo);
		if(board == null) {
			view.displayError("찾으시는 게시글이 존재하지 않습니다.");
		}else {
			view.selectOne(board);
		}
	}
	public void insertBoard() {
		Board board = view.insertBoard();
		int result = bs.insertBoard(board);
		if(result > 0) {
			view.displaySuccess("글이 작성 되었습니다.");
		} else {
			view.displayError("작성 도중 오류가 발생하였습니다.");
		}
	}
	public void updateBoard() {
		int bNo = view.inputBNo();
		int num = view.updateMenu();
		int result = 0;
		if(num == 0) {
			return;
		} else if(num == 1){
			String title = view.updateTitle();
			result = bs.updateBoard(bNo, num, title);
		} else if(num == 2) {
			String content = view.updateContent();
			result = bs.updateBoard(bNo, num, content);
		}
		if(result > 0) {
			view.displaySuccess("글 수정이 완료되었습니다.");
		}else {
			view.displayError("수정 도중 오류가 발생되었습니다.");
		}
	}
	public void deleteBoard() {
		while(true) {
			int bNo = view.inputBNo();
			char yn = view.deleteBoard();
			if(yn == 'Y') {
				int result = bs.deleteBoard(bNo);
				if(result > 0) {
					view.displaySuccess("글 삭제가 완료되었습니다.");
				}else {
					view.displayError("글 삭제 도중 오류가 발생했습니다. 맞는 번호인지 확인하여 주시기 바랍니다.");
				}
				break;
			} else if(yn == 'N') {
				view.displayError("글 삭제를 취소합니다.");
				break;
			} else {
				view.displayError(" y 혹은 n 을 입력해 주세요.");
			}
		}
	}

}
