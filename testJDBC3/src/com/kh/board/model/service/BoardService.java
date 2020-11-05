package com.kh.board.model.service;

import java.sql.Connection;
import java.util.ArrayList;
import static com.kh.common.JDBCTemplate.*;

import com.kh.board.model.dao.BoardDAO;
import com.kh.board.model.vo.Board;

public class BoardService {

	public ArrayList<Board> selectAll() {
		Connection conn = getConnection();
		BoardDAO bDAO = new BoardDAO();
		ArrayList<Board> bList = bDAO.selectAll(conn);
		return bList;
	}

	public Board seleteOne(int bNo) {
		Connection conn = getConnection();
		BoardDAO bDAO = new BoardDAO();
		Board board = bDAO.selectOne(conn, bNo);
		return board;
	}

	public int insertBoard(Board board) {
		Connection conn = getConnection();
		BoardDAO bDAO = new BoardDAO();
		int result = bDAO.insertBoard(conn, board);
		if(result > 0) {
			commit(conn);
		} else {
			rollback(conn);
		}
		return result;
	}

	public int updateBoard(int bNo, int num, String str) {
		Connection conn = getConnection();
		BoardDAO bDAO = new BoardDAO();
		int result = bDAO.updateBoard(conn, bNo, num, str);
		if (result > 0) {
			commit(conn);
		} else {
			rollback(conn);
		}
		return result;
	}

	public int deleteBoard(int bNo) {
		Connection conn = getConnection();
		BoardDAO bDAO = new BoardDAO();
		int result = bDAO.deleteBoard(conn, bNo);
		if (result > 0) {
			commit(conn);
		} else {
			rollback(conn);
		}
		return result;
	}

}
