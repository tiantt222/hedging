package com.hedging.odd.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

/**
 * 战队vo
 * 
 * @author tianwenlong
 *
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class TeamVo {

	private int team_id;

	private String team_logo;

	private String team_name;

}
