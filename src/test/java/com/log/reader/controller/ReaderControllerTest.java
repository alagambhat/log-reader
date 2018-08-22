package com.log.reader.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.log.reader.db.model.Event;
import com.log.reader.log.model.LogEvent;
import com.log.reader.service.DbService;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = {
		"job.autorun.enabled=false" })
public class ReaderControllerTest {

	@LocalServerPort
	int port;

	@Autowired
	private DbService dbService;

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
		assertThat(this.restTemplate.getForObject(url, String.class)).contains(
				"\"id\":\"12345\",\"eventDuration\":8,\"type\":\"dummyType\",\"host\":\"dummyHost\",\"alert\":true}");
	}

	private void save() {
		LogEvent entry = new LogEvent("12345", "FINISHED", 7866542314L, "dummyType", "dummyHost");
		dbService.save(entry, 8L);
	}

}
