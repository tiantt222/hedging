package com.hedging.user.vo;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author tianwenlong
 *
 */
@Accessors(chain = true)
@Data
public class AccessTokenRequest {

	private String grant_type;

	private String client_id;

	private String client_secret;

}
