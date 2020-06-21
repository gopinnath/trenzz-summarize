package ind.gopinnath.trenzz.bean;

import java.io.Serializable;
import java.time.LocalDateTime;

public class TrendId implements Serializable{

	private static final long serialVersionUID = 1L;

	private LocalDateTime trendHour;
	
	private Integer trendSequence;

	public TrendId() {
		super();
	}

	public TrendId(LocalDateTime trendHour, Integer trendSequence) {
		super();
		this.trendHour = trendHour;
		this.trendSequence = trendSequence;
	}

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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((trendHour == null) ? 0 : trendHour.hashCode());
		result = prime * result + ((trendSequence == null) ? 0 : trendSequence.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TrendId other = (TrendId) obj;
		if (trendHour == null) {
			if (other.trendHour != null)
				return false;
		} else if (!trendHour.equals(other.trendHour))
			return false;
		if (trendSequence == null) {
			if (other.trendSequence != null)
				return false;
		} else if (!trendSequence.equals(other.trendSequence))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "TrendId [trendHour=" + trendHour + ", trendSequence=" + trendSequence + "]";
	}
}
