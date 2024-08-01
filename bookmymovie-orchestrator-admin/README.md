# Book My Movie Application Orchestrator Admin

## About

This Application is working as an entrypoint of Book My Movie Microservice ecosystem for the Admin. Roles/Responsibilities: 
1. Request data Validation. 
2. Orchestration/Agreegration.
3. Hiding Backend API.

## Contents

### Files

* CODEOWNERS - list of code owners for this directory, if applicable

* Dockerfile - instructions for container build on GKE

* pom.xml - project-level POM file for Java service

* local_values.yaml - default values for helm

* skaffold.yaml - deployment configuration

### Directories

* configuration - Deploymeny and Kubernetes Components

* service/main - Java application

* service/test - unit tests