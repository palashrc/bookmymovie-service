@echo off
title Kubernetes Steps in Dev Environment

@REM Istio installation check:
kubectl get service istio-ingressgateway -n istio-system

@REM Kubernetes Namespaces:
C:\Users\USER>E:                                                                        
E:\>cd Workspace-IntelliJ-POC\poc-sell-service\poc-sell-orchestrator\configuration
kubectl create -f namespaces.yaml
kubectl label namespace poc-sell-orchestrator-namespace istio-injection=enabled
kubectl get namespace -L istio-injection

@REM Kubernetes ConfigMaps:
cd E:\Workspace-IntelliJ-POC\poc-sell-service\poc-sell-orchestrator\configuration\env\GCP-Dev\app-config
kubectl create configmap poc-sell-orchestrator-configmap -n poc-sell-orchestrator-namespace --from-file=application-gke-env.yaml
kubectl get configmap poc-sell-orchestrator-configmap -n poc-sell-orchestrator-namespace
kubectl describe configmap poc-sell-orchestrator-configmap -n poc-sell-orchestrator-namespace

@REM Kubernetes Services:
cd E:\Workspace-IntelliJ-POC\poc-sell-service\poc-sell-orchestrator\configuration
kubectl create -f services.yaml
kubectl get service poc-sell-orchestrator-service -n poc-sell-orchestrator-namespace
kubectl describe service poc-sell-orchestrator-service -n poc-sell-orchestrator-namespace

@REM Kubernetes Deployments:
cd E:\Workspace-IntelliJ-POC\poc-sell-service\poc-sell-orchestrator\configuration
kubectl apply -f deployments.yaml
kubectl get deployments -n poc-sell-orchestrator-namespace
kubectl get pods -n poc-sell-orchestrator-namespace
kubectl logs --follow <pod-name> -n poc-sell-orchestrator-namespace -c poc-sell-orchestrator-container //for Istio

@REM Istio Gateways:
cd E:\Workspace-IntelliJ-POC\poc-sell-service\poc-sell-orchestrator\configuration
kubectl apply -f gateways.yaml
kubectl get gateway poc-sell-orchestrator-gateway -n poc-sell-orchestrator-namespace

@REM Istio VirtualServices:
cd E:\Workspace-IntelliJ-POC\poc-sell-service\poc-sell-orchestrator\configuration
kubectl apply -f virtualservices.yaml
kubectl get virtualservices -n poc-sell-orchestrator-namespace

@REM Cleanup Environment:
kubectl delete --ignore-not-found=true -f services.yaml
kubectl delete --ignore-not-found=true -f deployments.yaml
kubectl delete --ignore-not-found=true -f virtualservices.yaml
kubectl delete --ignore-not-found=true -f gateways.yaml
kubectl delete configmap poc-sell-orchestrator-configmap -n poc-sell-orchestrator-namespace
kubectl delete --ignore-not-found=true -f namespaces.yaml
gcloud container images delete gcr.io/sab-dev-cluster-367305/poc-sell-orchestrator-image:latest --force-delete-tags
gcloud container clusters delete sab-cluster-dev-01 --project=sab-dev-cluster-367305 --region=us-central1



<nul set /p "=Done! Press any key to exit..."
 pause >nul