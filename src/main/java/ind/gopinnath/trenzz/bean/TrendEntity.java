package ind.gopinnath.trenzz.bean;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Entity
@Table(name = "trenzz")
@IdClass(TrendId.class)
public class TrendEntity implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "trend_hour")
	private LocalDateTime trendHour;
	
	@Id
	@Column(name = "trend_seq")
	private Integer trendSequence;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "tweet_volume")
	private Long tweetVolume;

	public LocalDateTime getTrendHour() {
		return trendHour;
	}

	public void setTrendHour(LocalDateTime trendHour) {
		this.trendHour = trendHour;
	}

	public Integer getTrendSequence() {
		return trendSequence;
	}

	public void setTrendSequence(Integer trendSequence) {
		this.trendSequence = trendSequence;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getTweetVolume() {
		return tweetVolume;
	}

	public void setTweetVolume(Long tweetVolume) {
		this.tweetVolume = tweetVolume;
	}

	@Override
	public String toString() {
		return "TrendEntity [trendHour=" + trendHour + ", trendSequence=" + trendSequence + ", name=" + name
				+ ", tweetVolume=" + tweetVolume + "]";
	}
	
	
}
