package ind.gopinnath.trenzz;

import java.util.Base64;
import java.util.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import ind.gopinnath.trenzz.bean.AuthToken;
import ind.gopinnath.trenzz.bean.Trends;
import io.quarkus.scheduler.Scheduled;

@ApplicationScoped
public class TaskScheduler {

	private static final Logger LOGGER = Logger.getLogger(TaskScheduler.class.getName()); 
	
    @ConfigProperty(name = "twitter.consumerKey")
    private String consumerKey;
	
    @ConfigProperty(name = "twitter.secret")
    private String secret;
    
    @ConfigProperty(name = "twitter.woeid")
    private String woeid;

    @Inject
    @RestClient
    private TwitterService service;
    
    @Scheduled(every = "60000s")
    public void queryAndCaptureTrend() {
    	AuthToken token = service.authenticate("client_credentials",generateBasicAuthValue());
    	Trends trends = service.fetchTrends(woeid,getBearerHeaderValue(token))[0];
    	LOGGER.info("Trend Info " + trends);
    	
    	// Invalidate call is failing with 403 error and not working with both Basic and Bearer Auth.
    	//String invalidateResponse = service.invalidateToken(token.getAccess_token(),getBearerHeaderValue(token));
    	
    	//LOGGER.info("Invalidate Key Response : " + invalidateResponse);
    }
    
    private String generateBasicAuthValue()	{
    	StringBuilder finalString = new StringBuilder();
    	String authString = consumerKey + ":" + secret;
    	String encodedString = Base64.getEncoder()
				.encodeToString(authString.getBytes());
    	finalString.append("Basic ").append(encodedString);
    	return finalString.toString();
    }
    
    private String getBearerHeaderValue(AuthToken token)	{
    	StringBuilder finalString = new StringBuilder();
    	finalString.append("Bearer ").append(token.getAccess_token()); 
    	return finalString.toString();
    }
    
}