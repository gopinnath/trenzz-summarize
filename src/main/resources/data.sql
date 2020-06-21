DROP TABLE IF EXISTS trenzz;
 
CREATE TABLE trenzz (
  trend_hour TIMESTAMP WITH TIME ZONE NOT NULL,
  trend_seq INT NOT NULL,
  name VARCHAR(256) NOT NULL,
  tweet_volume BIGINT DEFAULT NULL,
  PRIMARY KEY(trend_hour,trend_seq)
);