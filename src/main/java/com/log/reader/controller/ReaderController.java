package com.log.reader.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.log.reader.db.model.Event;
import com.log.reader.repository.LogEventRepository;

@RestController
@RequestMapping("/read")
public class ReaderController {

	private static final Logger logger = LoggerFactory.getLogger(ReaderController.class);
	
	@Autowired
	LogEventRepository logEventRepository;

	@GetMapping("/")
	public String getResourceDetails() {
		return this.getClass().getSimpleName();
	}

	@GetMapping("/fetchall")
	public List<Event> fetchAll() {
		final List<Event> result = logEventRepository.findAll();
		logger.debug("Fetch all returned {} results", result.size());
		return result;
	}

}
