package ind.gopinnath.trenzz.bean;

import java.time.LocalDateTime;
import java.util.Arrays;

public class Summary {

	private LocalDateTime datetime;
	
	private Trend trends[];

	public LocalDateTime getDatetime() {
		return datetime;
	}

	public void setDatetime(LocalDateTime datetime) {
		this.datetime = datetime;
	}

	public Trend[] getTrends() {
		return trends;
	}

	public void setTrends(Trend[] trends) {
		this.trends = trends;
	}

	@Override
	public String toString() {
		return "Summary [datetime=" + datetime + ", trends=" + Arrays.toString(trends) + "]";
	}
}
