# **Redis Failover Strategy using Spring Boot with Resilience4j**

This repository will show you how to implement a redis failover strategy using Spring Boot and Resilience4j. Resilience4j is a fault tolerance library for Java 8 and functional programming. It helps to add fault tolerance to your applications by providing various functionalities such as circuit breaking, retries, and rate limiting.

## **Prerequisites**

- Java 8 or later
- Spring Boot 2.3.3 or later
- Resilience4j 1.1.0 or later
- Redis Lettuce

## **Setting up Redis**

Before we start, you need to have a Redis server running and accessible. You can either use a local Redis server or a remote one. In this guide, we will use a local Redis server for demonstration purposes. You should be set up 2 redis instance, in this code, we set up 2 [localhost](http://localhost) redis with diferent port 6379 & 6380 using docker.

## How to Start Testing

Clone this repository & run the apps, after running you can hit the api continuesly using postman run collection / jmeter. While running stop master redis, and wait for a while and the backend will failover to redis slave.
