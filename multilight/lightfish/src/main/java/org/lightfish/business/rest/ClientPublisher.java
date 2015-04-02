/*
 *
 */
package org.lightfish.business.rest;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.ClientRequestContext;
import javax.ws.rs.client.ClientRequestFilter;
import javax.ws.rs.core.MultivaluedMap;
import javax.xml.bind.DatatypeConverter;

import org.lightfish.business.authenticator.GlassfishAuthenticator;

/**
 *
 * @author adam-bien.com
 */
public class ClientPublisher {

	private static class Authenticator implements ClientRequestFilter {

		private final GlassfishAuthenticator gfAuthenticator;

		public Authenticator(GlassfishAuthenticator gfAuthenticator) {
			this.gfAuthenticator = gfAuthenticator;
		}

		@Override
		public void filter(ClientRequestContext requestContext) throws IOException {
			if (this.gfAuthenticator.hasCredentials()) {
				MultivaluedMap<String, Object> headers = requestContext.getHeaders();
				final String basicAuthentication = getBasicAuthentication();
				headers.add("Authorization", basicAuthentication);
			}
		}

		private String getBasicAuthentication() {
			String token = this.gfAuthenticator.getUsername() + ":" + this.gfAuthenticator.getPassword();
			try {
				return "BASIC " + DatatypeConverter.printBase64Binary(token.getBytes("UTF-8"));
			} catch (UnsupportedEncodingException ex) {
				throw new IllegalStateException("Cannot encode with UTF-8", ex);
			}
		}
	}
	@Inject
	private GlassfishAuthenticator gfAuthenticator;

	@Produces
	public Client create() {
		return ClientBuilder.newClient().register(new Authenticator(gfAuthenticator));
	}
}
