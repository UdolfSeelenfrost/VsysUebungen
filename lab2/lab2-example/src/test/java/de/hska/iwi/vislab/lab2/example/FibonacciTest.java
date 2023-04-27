package de.hska.iwi.vislab.lab2.example;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

import org.glassfish.grizzly.http.server.HttpServer;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import javax.ws.rs.client.Entity;

import static org.junit.Assert.assertEquals;

public class FibonacciTest {

	private HttpServer server;
	private WebTarget target;

	@Before
	public void setUp() throws Exception {
		// start the server
		server = Main.startServer();
		// create the client
		Client c = ClientBuilder.newClient();

		// uncomment the following line if you want to enable
		// support for JSON in the client (you also have to uncomment
		// dependency on jersey-media-json module in pom.xml and
		// Main.startServer())
		// --
		// c.configuration().enable(new
		// org.glassfish.jersey.media.json.JsonJaxbFeature());

		target = c.target(Main.BASE_URI);
	}

	@After
	public void tearDown() throws Exception {
		server.shutdown();
	}

	/**
	 * Test to repeat the fibonacci request 8 times and check the result (should be 8th Fib number)
	 */
	@Test
	public void testIncrementFibonacci() {

		String responseMsg = "";
		for(int i=0; i<8; i++)
		{
			responseMsg = target.path("fib/next").request().accept(MediaType.TEXT_PLAIN).post(null, String.class);
		}
		assertEquals("13", responseMsg);
	}

	/**
	 * Request Fibonacci 8 Times, reset the index, request the Fibonacci again
	 */
	@Test
	public void testResetFibonacci() {
		String responseMsg = "";
		responseMsg = target.path("fib/reset").request().accept(MediaType.TEXT_PLAIN).put(Entity.text(""), String.class);

		for(int i=0; i<8; i++)
		{
			responseMsg = target.path("fib/next").request().accept(MediaType.TEXT_PLAIN).post(null, String.class);
		}
		assertEquals("13", responseMsg);

		responseMsg = target.path("fib/reset").request().accept(MediaType.TEXT_PLAIN).put(Entity.text(""), String.class);
		assertEquals("Index has been reset!", responseMsg);

		responseMsg = target.path("fib/next").request().accept(MediaType.TEXT_PLAIN).post(null, String.class);
		assertEquals("0", responseMsg);
	}


}
