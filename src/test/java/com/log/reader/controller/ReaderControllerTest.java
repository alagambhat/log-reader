package com.log.reader.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.log.reader.db.model.Event;
import com.log.reader.repository.LogEventRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = {
		"job.autorun.enabled=false" })
public class ReaderControllerTest {

	@LocalServerPort
	int port;

	@Autowired
	private LogEventRepository logEventRepository;

	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	public void testHttpOk() throws Exception {
		String url = "http://localhost:" + port + "/read/all";
		ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
		assertEquals(response.getStatusCode(), HttpStatus.OK);
	}

	/**
	 * Verifies that the data is stored in DB and is read using the REST end point.
	 */
	@Test
	public void testDataStoredinDb() throws Exception {
		save();
		String url = "http://localhost:" + port + "/read/all";
		ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
		assertTrue(response.getBody().contains(
				"\"id\":\"12345\",\"eventDuration\":8,\"type\":\"dummyType\",\"host\":\"dummyHost\",\"alert\":true}"));
	}

	private void save() {
		Event event = new Event();
		event.setId("12345");
		event.setAlert(Boolean.TRUE);
		event.setHost("dummyHost");
		event.setType("dummyType");
		event.setEventDuration(8L);
		logEventRepository.save(event);
	}

}
