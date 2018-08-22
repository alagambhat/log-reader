package com.log.reader;

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

import com.log.reader.service.FileReader;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = {
		"job.autorun.enabled=false" })
public class IntegrationTest {

	@LocalServerPort
	int port;

	@Autowired
	private FileReader fileReader;

	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	public void testHttpOk() throws Exception {
		String url = "http://localhost:" + port + "/read/all";
		ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
		assertEquals(response.getStatusCode(), HttpStatus.OK);
	}

	/**
	 * Verifies that the expected data (with threshold > 4) is stored in DB and is
	 * read using the REST end point.
	 */
	@Test
	public void testDataStoredinDb() throws Exception {
		fileReader.read("src/test/resources/sample.log");
		String url = "http://localhost:" + port + "/read/all";
		final String expectedEntry1 = "\"id\":\"scsmbstgra\",\"eventDuration\":5,\"type\":\"APPLICATION_LOG\",\"host\":\"12345\",\"alert\":true";
		final String expectedEntry2 = "\"id\":\"scsmbstgrc\",\"eventDuration\":8,\"type\":null,\"host\":null,\"alert\":true";
		assertThat(this.restTemplate.getForObject(url, String.class)).contains(expectedEntry1, expectedEntry2);
	}

}
