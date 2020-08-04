package com.springbook.board;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springbook.board.common.Const;

@Service
public class BoardService {
	
	@Autowired
	private BoardMapper mapper;
	
	public int insBoard(BoardVO param) {
		return mapper.insBoard(param);
	}
	public List<BoardVO> selBoardList(int page) {
		int sidx = (page -1) * Const.ROW_COUNT;
		//return mapper.selBoardList();
		
		BoardVO param = new BoardVO();
		param.setSidx(sidx);
		param.setCount(Const.ROW_COUNT);
		
		return mapper.selBoardList(param);
	}
	
	public BoardVO selBoard(BoardVO param){
		return mapper.selBoard(param);
	}
	
	public int delBoard(int param) {
		return mapper.delBoard(param);
	}
	
	public int updBoard(BoardVO param) {
		return mapper.updBoard(param);
	}
	

}
