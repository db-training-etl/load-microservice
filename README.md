<h1 align="center"> Load From Dir - LFD </h1> <br>

<p align="center">
  This microservice receives a JSON with a file route, reads it, depending on the contents, result will be delivered to RD or CBE.
</p>


## Table of Contents

- [Introduction](#introduction)
- [Features](#features)
- [Requirements](#requirements)
- [Quick Start](#quick-start)
- [Testing](#testing)
- [API](#requirements)
- [Acknowledgements](#acknowledgements)

## Introduction

![Tests](https://github.com/db-training-etl/load-microservice/actions/workflows/gradle.yml/badge.svg)
![CodeQL](https://github.com/db-training-etl/load-microservice/actions/workflows/codeql-analysis.yml/badge.svg)

This microservice depends on RD (Reference Data Microservice) and CBE (Counterparties and Books Enrichement microservice), its main reason of existing is to be able to find a file from a remote server and convert it to a json object and send it to other services.

Reads a file that could be very big, so uses a chunk strategy to read the file and sends every individual object stored on the file to the next step.

## Features

* Reads a file from a remote server (for now only reads from a local server).
* Sends the file to RD or CBE depending on the file content.
* Uses a chunk strategy to read the file and send it to the next step (to be implemented).

## Requirements
The application can be run locally or in a docker container, the requirements for each setup are listed below.

### Local
* [Java 17 SDK](https://www.oracle.com/java/technologies/javase-jdk17-downloads.html)
* [Gradle](https://gradle.org/install/)

## Quick Start
In order to run the application locally, you need to have the requirements installed, then you also need to copy the ```.env.example``` file and rename it to ```.env```, then you need to fill the variables with the correct values.

### Run Local
```bash
$ gradle bootRun
```

Application will run by default on port `8080`

Configure the port by changing `server.port` in __application.yml__ or using -Dserver.port=PORT if you have built the application as a jar.

## Testing
This project is configured to cover 100% of the code with tests, if you don't do this, the build will fail.

You can check if you are covering the code with tests by running the following command:
```bash

```bash
$ gradle check 
```

## API
TODO: API Reference with examples, or a link to a wiki or other documentation source.

## Acknowledgements
TODO: Show folks some love.
