package com.simpolor.app.security;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.web.authentication.rememberme.PersistentRememberMeToken;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

public class CustomInMemoryTokenRepository implements PersistentTokenRepository{

	private final Map<String, PersistentRememberMeToken> seriesTokens = new HashMap<>();
	
	@Override
	public void createNewToken(PersistentRememberMeToken token) {
		
		PersistentRememberMeToken current = seriesTokens.get(token.getSeries());
		if (current != null) {
			throw new DataIntegrityViolationException("Series Id '" + token.getSeries()
					+ "' already exists!");
		}
		seriesTokens.put(token.getSeries(), token);
	}

	@Override
	public void updateToken(String series, String tokenValue, Date lastUsed) {
		PersistentRememberMeToken token = getTokenForSeries(series);

		PersistentRememberMeToken newToken = new PersistentRememberMeToken(
				token.getUsername(), series, tokenValue, new Date());

		// Store it, overwriting the existing one.
		seriesTokens.put(series, newToken);
	}

	@Override
	public PersistentRememberMeToken getTokenForSeries(String seriesId) {
		return seriesTokens.get(seriesId);
	}

	@Override
	public void removeUserTokens(String username) {
		Iterator<String> series = seriesTokens.keySet().iterator();

		while (series.hasNext()) {
			String seriesId = series.next();

			PersistentRememberMeToken token = seriesTokens.get(seriesId);

			if (username.equals(token.getUsername())) {
				series.remove();
			}
		}
	}

}
