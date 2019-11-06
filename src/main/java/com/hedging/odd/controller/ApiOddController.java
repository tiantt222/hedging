package com.hedging.odd.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hedging.odd.remote.OddRemoteService;
import com.hedging.odd.vo.MatchVo;

/**
 * 赔率接口
 * 
 * @author tianwenlong
 *
 */
@Controller
@RequestMapping("/api/odd")
public class ApiOddController {

	@Resource
	private OddRemoteService oddRemoteService;

	@RequestMapping(method = RequestMethod.POST, value = "/fetchMatchList")
	@ResponseBody
	public List<MatchVo> fetchMatchList(String gameType) {

		return oddRemoteService.fetchMatchList(gameType);
	}

}
