package ind.gopinnath.trenzz;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import ind.gopinnath.trenzz.bean.HourlySummary;
import ind.gopinnath.trenzz.bean.Summaries;
import ind.gopinnath.trenzz.bean.TrendEntity;
import ind.gopinnath.trenzz.bean.TrendResponse;

@ApplicationScoped
@Path("/api")
public class Resource {
	

    @Inject
    private EntityManager entityManager;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/trenzz")
    public Summaries summarize() {
    	@SuppressWarnings("unchecked")
		List<TrendEntity> trends = entityManager.createQuery("Select t from TrendEntity t").getResultList();
    	return buildSummaries(trends);
    }

	private Summaries buildSummaries(List<TrendEntity> trends) {
		Summaries summaries = new Summaries();
		Map<LocalDateTime,List<TrendEntity>> mappedResults = trends.stream()
				.collect(Collectors.groupingBy(TrendEntity::getTrendHour));
		mappedResults.forEach(
				(hour,trenzz) -> buildHourlySummary(hour,trenzz,summaries)
				);
		return summaries;
	}
	
	private void buildHourlySummary(LocalDateTime hour,List<TrendEntity> trenzz,Summaries summaries)	{
		HourlySummary hourlySummary = new HourlySummary();
		hourlySummary.setHour(hour);
		trenzz.forEach(entity -> buildTrenzz(entity,hourlySummary));
		summaries.getHourlySummaries().add(hourlySummary);		
	}
	
	private void buildTrenzz(TrendEntity entity,HourlySummary hourlySummary)	{
		TrendResponse trend = new TrendResponse();
		trend.setName(entity.getName());
		trend.setTrendSequence(entity.getTrendSequence());
		trend.setTweetVolume(entity.getTweetVolume());
		hourlySummary.getTrends().add(trend);
	}
    
}