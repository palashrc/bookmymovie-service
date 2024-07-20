@echo off
title Kubernetes Steps in local Environment

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
cd C:\Palash\Workspaces\Workspace-IntelliJ-POC\poc-sell-service\poc-sell-air
docker image rm --force poc-sell-air-image
@REM docker image build -t poc-sell-air-image .
@REM Build an Docker Image directly in Minikube:
minikube image build -t poc-sell-air-image .
minikube image ls --format table
docker images
@REM minikube image load poc-sell-air-image

@REM Kubernetes Namespaces:
kubectl create namespace poc-sell-air-namespace
kubectl label namespace poc-sell-air-namespace istio-injection=enabled
kubectl get namespace -L istio-injection

@REM Execute Helm Chart:
set HELM_DIR=C:\Palash\Workspaces\Workspace-IntelliJ-POC\poc-sell-service\poc-sell-air\configuration\helm
echo %HELM_DIR%
helm lint %HELM_DIR%
helm install --set configDir=env/GCP-Dev/app-config poc-sell-air-helm --namespace poc-sell-air-namespace %HELM_DIR% --dry-run
helm install --set configDir=env/GCP-Dev/app-config poc-sell-air-helm --namespace poc-sell-air-namespace %HELM_DIR%
helm status poc-sell-air-helm --namespace poc-sell-air-namespace
helm list --namespace poc-sell-air-namespace

@REM Check Kubernetes Components:
kubectl get configmap poc-sell-air-configmap -n poc-sell-air-namespace
kubectl get service poc-sell-air-service -n poc-sell-air-namespace
kubectl get deployments -n poc-sell-air-namespace
kubectl get pods -n poc-sell-air-namespace
kubectl logs --follow <pod-name> -n poc-sell-air-namespace -c poc-sell-air-container //for Istio
kubectl get gateway poc-sell-air-gateway -n poc-sell-air-namespace
kubectl get virtualservices -n poc-sell-air-namespace

@REM Cleanup Environment:
helm uninstall poc-sell-air-helm --namespace poc-sell-air-namespace
kubectl delete namespace poc-sell-air-namespace
docker image rm --force poc-sell-air-image
minikube stop


<nul set /p "=Done! Press any key to exit..."
 pause >nul