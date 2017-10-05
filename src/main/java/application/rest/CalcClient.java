package application.rest;

import javax.annotation.PostConstruct;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.json.JsonObject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

import org.eclipse.microprofile.config.inject.ConfigProperty;

@Dependent
public class CalcClient {

	@Inject
	@ConfigProperty(name = "CALC_SERVICE_SERVICE_HOST", defaultValue = "localhost")
	private String calcHost;

	@Inject
	@ConfigProperty(name = "CALC_SERVICE_SERVICE_PORT_HTTP", defaultValue = "9080")
	private String calcPort;

	private String calcServiceUrl;

	@PostConstruct
	private void init() {
		calcServiceUrl = "http://" + calcHost + ":" + calcPort + "/calc/rest/";
		System.out.println(this.getClass().getSimpleName() + ": calcServiceUrl=" + calcServiceUrl);
	}

	public long multiply(long p1, long p2) {
		System.out.println(this.getClass().getSimpleName() + ": p1=" + p1 + ", p2=" + p2);
		Client client = ClientBuilder.newClient();
		WebTarget wt = client.target(calcServiceUrl);
		JsonObject res = wt.path(Long.toString(p1)).path(Long.toString(p2)).request("application/json")
				.get(JsonObject.class);
		long product = res.getJsonNumber("product").longValue();
		System.out.println(this.getClass().getSimpleName() + ": p1=" + p1 + ", p2=" + p2 + ", product=" + product);
		return product;
	}
}