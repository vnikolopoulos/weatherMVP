# Temperture Report
A simple REST API endpoint providing the current weather conditions of the available weather stations of a country using the provided web services of
geonames.org

## Requirements

1. Java - 1.8.x

2. Maven - 3.x.x

## Clone, Build and Run
```bash
git clone https://github.com/vnikolopoulos/weatherMVP.git
cd weatherMVP
mvn spring-boot run
```
The app will start running at <http://localhost:8080>.

## Explore Rest APIs

The API endpoind accepts an [ISO 3166-1 alpha-2 country code]: https://en.wikipedia.org/wiki/ISO_3166-1_alpha-2

    GET /{countryCode}

A [Postman](https://www.getpostman.com/) collection is included: `weatherMVPRequests.json`

## Configuration
A cache mechanism is provided in order to reduce response time and minimize requests at geonames.org. By default the system caches the temperatures for each country for 1 hour. This can be cofigured at ehcache cofiguration file `src/main/resources/ehcache.xml`.

## Solution
//TODO
