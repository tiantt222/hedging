package com.hedging.odd.vo;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

/**
 * 比赛vo
 * 
 * @author tianwenlong
 *
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class MatchVo {

	private int id;

	private String game_name;

	private String match_name;

	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	private Date start_time;

	private String round;

	private List<TeamVo> team;

}
