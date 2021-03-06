package ind.gopinnath.trenzz;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.List;
import java.util.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import ind.gopinnath.trenzz.bean.AuthToken;
import ind.gopinnath.trenzz.bean.Trend;
import ind.gopinnath.trenzz.bean.TrendEntity;
import ind.gopinnath.trenzz.bean.Trends;
import ind.gopinnath.util.DateUtil;
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

    @Inject
    private EntityManager entityManager;
    
    @Scheduled(cron = "0 1 * ? * *")
    @Transactional
    public void queryAndCaptureTrend() {
    	AuthToken token = service.authenticate("client_credentials",generateBasicAuthValue());
    	Trends trends = service.fetchTrends(woeid,getBearerHeaderValue(token))[0];
    	persistTrends(trends);
    }

    @Scheduled(cron="0 5 1 ? * *")
    @Transactional
    public void purgeOldTrends() {
    	TypedQuery<TrendEntity> query = entityManager.createQuery("Select t from TrendEntity t where t.trendHour < :previousSunday",TrendEntity.class);
		List<TrendEntity> trends = query.setParameter("previousSunday", DateUtil.getPreviuosSunday()).getResultList();
		for(int i=0; i< trends.size() ; i++)	{
			entityManager.remove(trends.get(i));
		}
		LOGGER.info(trends.size() + " records purged.");
    }

    
    private void persistTrends(Trends trends) {
		LocalDateTime tweetHour = buildTweetHour();
		for(int i = 0;i < 10;i++)	{
			entityManager.persist(buildTrendEntity(trends.getTrends()[i],tweetHour,i+1));
		}
		LOGGER.info("Trend Info Loaded For Hour " + tweetHour);
	}

    private TrendEntity buildTrendEntity(Trend trend, LocalDateTime tweetHour,Integer trendSequence) {
		TrendEntity trendEntity = new TrendEntity();
		trendEntity.setName(trend.getName());
		trendEntity.setTweetVolume(trend.getTweet_volume());
		trendEntity.setTrendSequence(trendSequence);
		trendEntity.setTrendHour(tweetHour);
		return trendEntity;
	}

	private LocalDateTime buildTweetHour() {
		return LocalDateTime.now().truncatedTo(ChronoUnit.HOURS);
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
