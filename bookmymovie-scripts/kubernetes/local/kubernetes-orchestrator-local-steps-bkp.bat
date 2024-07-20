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
cd C:\Palash\Workspaces\Workspace-IntelliJ-POC\poc-sell-service\poc-sell-orchestrator
docker image rm --force poc-sell-orchestrator-image
@REM docker image build -t poc-sell-orchestrator-image .
@REM Build an Docker Image directly in Minikube:
minikube image build -t poc-sell-orchestrator-image .
minikube image ls --format table
docker images
@REM minikube image load poc-sell-orchestrator-image

@REM Kubernetes Namespaces:
cd C:\Palash\Workspaces\Workspace-IntelliJ-POC\poc-sell-service\poc-sell-orchestrator\configuration
kubectl create -f namespaces.yaml
kubectl label namespace poc-sell-orchestrator-namespace istio-injection=enabled
kubectl get namespace -L istio-injection

@REM Kubernetes ConfigMaps:
cd C:\Palash\Workspaces\Workspace-IntelliJ-POC\poc-sell-service\poc-sell-orchestrator\configuration\env\GCP-Dev\app-config
kubectl create configmap poc-sell-orchestrator-configmap -n poc-sell-orchestrator-namespace --from-file=application-gke-env.yaml
kubectl get configmap poc-sell-orchestrator-configmap -n poc-sell-orchestrator-namespace
kubectl describe configmap poc-sell-orchestrator-configmap -n poc-sell-orchestrator-namespace

@REM Kubernetes Services:
cd C:\Palash\Workspaces\Workspace-IntelliJ-POC\poc-sell-service\poc-sell-orchestrator\configuration
kubectl create -f services.yaml
kubectl get service poc-sell-orchestrator-service -n poc-sell-orchestrator-namespace
kubectl describe service poc-sell-orchestrator-service -n poc-sell-orchestrator-namespace
minikube ip //required for NodePort
minikube service poc-sell-orchestrator-service -n poc-sell-orchestrator-namespace --url //required for NodePort

@REM Kubernetes Deployments:
cd C:\Palash\Workspaces\Workspace-IntelliJ-POC\poc-sell-service\poc-sell-orchestrator\configuration
kubectl apply -f deployments.yaml
kubectl get deployments -n poc-sell-orchestrator-namespace
kubectl get pods -n poc-sell-orchestrator-namespace
kubectl logs --follow <pod-name> -n poc-sell-orchestrator-namespace //for NodePort
kubectl logs --follow <pod-name> -n poc-sell-orchestrator-namespace -c poc-sell-orchestrator-container //for Istio

@REM Kubernetes HPA:
cd C:\Palash\Workspaces\Workspace-IntelliJ-POC\poc-sell-service\poc-sell-orchestrator\configuration
kubectl create -f hpa.yaml
kubectl get hpa poc-sell-orchestrator-hpa -n poc-sell-orchestrator-namespace

@REM Istio Gateways:
cd C:\Palash\Workspaces\Workspace-IntelliJ-POC\poc-sell-service\poc-sell-orchestrator\configuration
kubectl apply -f gateways.yaml
kubectl get gateway poc-sell-orchestrator-gateway -n poc-sell-orchestrator-namespace

@REM Istio VirtualServices:
cd C:\Palash\Workspaces\Workspace-IntelliJ-POC\poc-sell-service\poc-sell-orchestrator\configuration
kubectl apply -f virtualservices.yaml
kubectl get virtualservices -n poc-sell-orchestrator-namespace

@REM Cleanup Environment:
kubectl delete --ignore-not-found=true -f services.yaml
kubectl delete --ignore-not-found=true -f deployments.yaml
kubectl delete --ignore-not-found=true -f virtualservices.yaml
kubectl delete --ignore-not-found=true -f gateways.yaml
kubectl delete --ignore-not-found=true -f hpa.yaml
kubectl delete configmap poc-sell-orchestrator-configmap -n poc-sell-orchestrator-namespace
kubectl delete --ignore-not-found=true -f namespaces.yaml
docker image rm --force poc-sell-orchestrator-image
minikube stop


<nul set /p "=Done! Press any key to exit..."
 pause >nul