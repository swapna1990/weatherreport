package com.broadcast.weatherreport.controllers;

import java.io.IOException;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import com.broadcast.weatherreport.constant.CommonConstants;
import com.broadcast.weatherreport.models.Weather;
import com.broadcast.weatherreport.service.WeatherReportServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
public class WeatherController {

	private Logger logger = LoggerFactory.getLogger(WeatherController.class);

	@Autowired
	private WeatherReportServiceImpl weatherReportService;

	@RequestMapping(value = "tomorrow/weather", method = RequestMethod.GET)
	public ResponseEntity<Object> getWeather(@RequestParam Integer zipCode) throws IOException {
		logger.info("API invoked : tomorrow/weather");
		logger.info("request :" + zipCode);
		if (!validateZipCode(zipCode)) {
			logger.error("invalid zipcode: " + zipCode);
			return ResponseEntity.ok("Please pick Zipcode from this list: " + CommonConstants.zipCodeCity);
		}

		Weather weather = weatherReportService.getWeatherReport(zipCode);

		return ResponseEntity.ok(weather);

	}

	@RequestMapping(value = "today/coolestHour", method = RequestMethod.GET)
	public ResponseEntity<Object> getCoolestHour(@RequestParam Integer zipCode) throws IOException {

		logger.info("API invoked : today/coolestHour");
		logger.info("request :" + zipCode);
		if (!validateZipCode(zipCode)) {
			logger.error("invalid zipcode: " + zipCode);
			return ResponseEntity.ok("Please pick Zipcode from this list: " + CommonConstants.zipCodeCity);
		}

		Weather weather = weatherReportService.getWeatherReport(zipCode);

		return ResponseEntity.ok(weather);

	}

	private boolean validateZipCode(Integer zipCode) throws IOException {

		ObjectMapper mapper = new ObjectMapper();
		HashMap<String, String> zipcodeCitiesMap = mapper.readValue(CommonConstants.zipCodeCity, HashMap.class);
		if (!zipcodeCitiesMap.containsKey(zipCode.toString())) {
			return false;
		}
		return true;
	}

}