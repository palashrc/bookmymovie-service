@echo off
title Kubernetes and Skaffold Steps in local Environment

@REM Check Version and Status:
kubectl version --client -o json
minikube status

@REM Start Minikube:
@REM minikube start --driver=docker
@REM minikube start --memory=16384 --cpus=4 --kubernetes-version=v1.20.2
minikube start
minikube docker-env
@FOR /f "tokens=*" %i IN ('minikube -p minikube docker-env --shell cmd') DO @%i //Windows
eval $(minikube -p minikube docker-env) //Linux

@REM Istio installation check:
kubectl get service istio-ingressgateway -n istio-system
minikube tunnel //Run it in different terminal
kubectl get service istio-ingressgateway -n istio-system

@REM Kubernetes Details after startup:
minikube status
kubectl cluster-info
kubectl get nodes
kubectl get nodes -o wide

@REM Docker Images:
docker images
minikube image ls --format table
docker rmi gcr.io/sab-dev-storage-368015/poc-sell-air-image:<TAG>

@REM Kubernetes Namespaces:
kubectl create namespace poc-sell-air-namespace
kubectl label namespace poc-sell-air-namespace istio-injection=enabled
kubectl get namespace -L istio-injection

@REM Trigger Skaffold Pipeline: 
C:\Users\Palash>D:
cd Workspace-IntelliJ-POC\poc-sell-service\poc-sell-air
skaffold run

@REM Check Kubernetes Components:
kubectl get configmap poc-sell-air-configmap -n poc-sell-air-namespace
kubectl get service poc-sell-air-service -n poc-sell-air-namespace
kubectl get deployments -n poc-sell-air-namespace
kubectl get pods -n poc-sell-air-namespace
kubectl logs --follow <pod-name> -n poc-sell-air-namespace -c poc-sell-air-container //for Istio
kubectl get gateway poc-sell-air-gateway -n poc-sell-air-namespace
kubectl get virtualservices -n poc-sell-air-namespace

@REM Cleanup Environment:
skaffold delete
kubectl delete namespace poc-sell-air-namespace
docker rmi gcr.io/sab-dev-storage-368015/poc-sell-air-image:<TAG>
minikube stop


<nul set /p "=Done! Press any key to exit..."
 pause >nul