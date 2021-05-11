package com.broadcast.weatherreport.service;

import com.broadcast.weatherreport.constant.CommonConstants;
import com.broadcast.weatherreport.models.Weather;
import com.broadcast.weatherreport.models.WeatherUrl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.util.HashMap;

@Service
public class WeatherReportServiceImpl {

	@Autowired
	RestTemplate restTemp;

	@Autowired
	private WeatherUrl weatherData;

	public Weather getWeatherReport(Integer zipCode) throws IOException {
		String zipCodeCity = CommonConstants.zipCodeCity;
		ObjectMapper mapper = new ObjectMapper();
		HashMap<String, String> zipcodeCitiesMap = mapper.readValue(zipCodeCity, HashMap.class);

		UriComponents uriComponents = UriComponentsBuilder.newInstance().scheme("http").host(weatherData.getUrl())
				.path("").query("q={keyword}&appid={appid}")
				.buildAndExpand(zipcodeCitiesMap.get(zipCode.toString()), weatherData.getApiKey());

		String uri = uriComponents.toUriString();
		ResponseEntity<String> resp = restTemp.exchange(uri, HttpMethod.GET, null, String.class);
		Weather weather = mapper.readValue(resp.getBody(), Weather.class);
		return weather;
	}
}
