package com.hedging.odd.remote;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import com.hedging.odd.vo.MatchVo;

import lombok.Cleanup;
import lombok.extern.slf4j.Slf4j;

/**
 * 赔率远程接口
 * 
 * @author tianwenlong
 *
 */
@Slf4j
@Component
public class OddRemoteService {

	@Value("${url.matchList}")
	private String matchListUrl;

	@Value("${url.odd}")
	private String oddsUrl;

	/**
	 * 获取赛事列表
	 * 
	 * @param gameType 项目类型
	 * @return
	 */
	public List<MatchVo> fetchMatchList(String gameType) {
		List<MatchVo> result = Lists.newArrayList();
		try {
			@Cleanup
			CloseableHttpClient httpClient = null;
			httpClient = HttpClients.createDefault();
			HttpGet httpPost = new HttpGet(matchListUrl);

			HttpResponse response = httpClient.execute(httpPost);

			int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode == HttpStatus.SC_OK) {
				String getResult = EntityUtils.toString(response.getEntity(), "UTF-8");
				log.info("请求数据返回结果： {}", getResult);

				ObjectMapper mapper = new ObjectMapper();
				JsonNode rootNode = mapper.readTree(getResult);
				JsonNode dataNode = rootNode.path("result");
				if (!StringUtils.isNotEmpty(gameType)) {
					JavaType javaType = mapper.getTypeFactory().constructParametricType(ArrayList.class, MatchVo.class);
					result = mapper.readValue(dataNode.toString(), javaType);
				} else {
					for (JsonNode dataJsonNode : dataNode) {
						if (gameType.equals(dataJsonNode.get("game_name").asText())) {
							MatchVo vo = mapper.readValue(dataJsonNode.toString(), MatchVo.class);
							result.add(vo);
						}

					}
				}
			}

		} catch (Exception e) {
			log.error("获取赛事列表发生错误", e);
		}
		return result;
	}

}
