package com.springbook.board;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/board")
public class BoardController {
	
	@Autowired
	private BoardService service;
	
	@ResponseBody
	@RequestMapping(value = "/getListData", method = RequestMethod.GET)
	public Map<String, Object> getListData(@RequestParam int page,@RequestParam String searchText) {
		System.out.println("page : "+page);
		System.out.println("searchText : "+searchText);
		
		Map<String, Object> map = new HashMap();
		map.put("result", service.selBoardList(page,searchText));
		
		return map;
	}
	
	
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String boardList(Model model) {
			
		return "board/list";
	}
	
	@RequestMapping(value = "/write", method = RequestMethod.GET)
	public String boardWrite() {
		return "board/write";
	}
	
	@RequestMapping(value = "/write", method = RequestMethod.POST)// input 태그에 name과 param의 멤버필드와 키값이 메칭이 되면 알아서 저장된다. 
	public String boardWrite(BoardVO param) {
		
//		System.out.println("title : " + param.getTitle());
//		System.out.println("ctnt : " + param.getCtnt());
		int result = service.insBoard(param);
		return "redirect:/board/list"; // redirect->주소이동/ 이건  servlet에 묻지 않고 그냥 바로 이동 하는것이다.   
		//get 방식으로 redirect 됩니다. 
	}
	
	@RequestMapping(value = "/detail", method = RequestMethod.GET)
	public String boardDetail(BoardVO param,Model model) {//순서 바꿔도 된다. 
		//System.out.println("i_board = "+param.getI_board());
		
		model.addAttribute("data",service.selBoard(param));
		return "board/detail";
	}
	
	@RequestMapping(value = "/del", method = RequestMethod.GET)
	public String boardDel(@RequestParam int i_board,BoardVO param) {// @RequestParam int -> 쿼리 스트링의 데이터 타입을 int로 받아올수있는 방법 
														// @RequestParam("pk") -> pk라는 쿼리스트링을 찾아 i_board에 저장 한다. 
		//System.out.println("i_board = "+param.getI_board());
		
		//System.out.println("i_board = "+i_board);
		int result = service.delBoard(i_board);
		return "redirect:/board/list";
	}
	
	@RequestMapping(value = "/upd", method = RequestMethod.GET)
	public String boardUpd(BoardVO param, Model model) {
		//System.out.println("i_board ="+ param.getI_board());
		
		model.addAttribute("data",service.selBoard(param));
		return "board/write";
	}
	
	@RequestMapping(value = "/upd", method = RequestMethod.POST)
	public String boardUpd(BoardVO param) {
//		System.out.println("i_board = "+ param.getI_board());
//		System.out.println(" title = "+ param.getTitle());
//		System.out.println("Ctnt = "+ param.getCtnt());
		
		int result = service.updBoard(param);
		
		return "redirect:/board/detail?i_board="+param.getI_board();
	}
}
