- [Introduction](#introduction)
- [About the Usecase](#about-the-usecase)
- [Twitter API Details](#twitter-api-details)
- [Live Application](#live-application)
- [Execution Options](#execution-options)
    - [Maven or Java Command](#maven-or-java-command)
    - [Local Docker Image](#local-docker-image)
    - [Heroku Dyno](#heroku-dyno)
- [Github Actions](#github-actions)

## Introduction

Trenzz Summarize is an application that is developed to showcase the capabilities of the Quarkus. The application uses Quarkus, Quarkus Resteasy, Quarkus Rest Client, Quarkus Scheduler, Quarkus Hibernate Orm, and Quarkus JDBC H2.

## About the Usecase

The usecase here is application that captures the Top 10 Trending keywords from Twitter for give place and displays that as a graphs on UI. HTML, Java Script and D3 Framework are used for UI rendering.

## Twitter API Details

We will be utilizing the Twitter Trends for location API. Please refere to [twitter documentation on trends API](https://developer.twitter.com/en/docs/trends/trends-for-location/overview) for more details. 

The application  needs 3 runtime parameters, Twitter Consumer API Key, Secret and Place Id. You can create an App by login into the [Twitter Developer site](https://developer.twitter.com/en/apps) and then you can create the API Key and Secret against your newly created App. 

For the Place Id, twitter takes the [WOEID](https://en.wikipedia.org/wiki/WOEID). For example for India, the WOEID is 23424848.

## Execution Options

For executing the application it needs 3 environment variables, twitter consumer key, twitter secret and place id. Please refere to [twitter documentation on trends API](https://developer.twitter.com/en/docs/trends/trends-for-location/overview) for more details.

### Maven or Java Command

For building the application the regular Quarkus Way by executing the mvn command below 

```
mvn compile -Dtwitter.consumerKey=<<Twitter Consumer API Key>> -Dtwitter.secret=<<API Secret>> -Dtwitter.woeid=<<WOEID>> quarkus:dev
```

or you can perform 2 steps.

Step 1 :

```
mvn compile
```

Stpe 2 :

```
java -jar -Dtwitter.consumerKey=<<Twitter Consumer API Key>> -Dtwitter.secret=<<API Secret>> -Dtwitter.woeid=<<WOEID>> target/trenzz-summarize-1.0.0-SNAPSHOT-runner.jar
```

### Local Docker Image

You can build and execute using below commands.

```
docker build -f src/main/docker/Dockerfile.jvm -t  trenzz-summarize/trenzz
```

```
docker run -p 8080:8080 -e twitter.consumerKey=<<Twitter Consumer API Key>> -e twitter.secret=<<API Secret>> -e twitter.woeid=<<WOEID>> trenzz-summarize/trenzz
```

### Heroku Dyno

The file "system.properties" has been added for picking Java 11 during the build process in Heroku.

The file "Procfile" has been added pass the port and timezone as dynamic runtime parameters.

Once you had deployed the container on Dyno, you need to set the environment variables "twitter.consumerKey", "twitter.secret" and "twitter.woeid" at the "Config Vars" Section under Settings menu of the App under Heroku

## Github Actions

Docker file "src/main/docker/Dockerfile.mvn" has been added and the action file ".github/workflows/main.yml" for building the docker image for running the application on Java Image.

Docker file "src/main/docker/Dockerfile.native" has been added the action file ".github/workflows/native.yml" for building the native quarkus application image.

NOTE : Since the application uses the H2 In-memory database, the native execution is not supported at this moment.
