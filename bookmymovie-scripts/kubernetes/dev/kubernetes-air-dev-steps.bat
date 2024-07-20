@echo off
title Kubernetes Steps in Dev Environment

@REM ogin nd connect to GKE Cluster:
gcloud auth login --no-launch-browser
gcloud config set project sab-dev-cluster-367305
gcloud container clusters get-credentials sab-cluster-dev-01 --project=sab-dev-cluster-367305 --region=us-central1

@REM Execute Helm Chart in GKE:
set HELM_DIR=D:\Workspace-IntelliJ-POC\poc-sell-service\poc-sell-air\configuration\helm
echo %HELM_DIR%
helm lint %HELM_DIR%
helm install --set configDir=env/GCP-Dev/app-config poc-sell-air-helm --namespace poc-sell-air-namespace %HELM_DIR% --dry-run
helm install --set configDir=env/GCP-Dev/app-config poc-sell-air-helm --namespace poc-sell-air-namespace %HELM_DIR%
helm status poc-sell-air-helm --namespace poc-sell-air-namespace
helm list --namespace poc-sell-air-namespace

@REM Check Kubernetes Components created in GKE:
kubectl get configmap poc-sell-air-configmap -n poc-sell-air-namespace
kubectl get service poc-sell-air-service -n poc-sell-air-namespace
kubectl get deployments -n poc-sell-air-namespace
kubectl get pods -n poc-sell-air-namespace
kubectl logs --follow <pod-name> -n poc-sell-air-namespace -c poc-sell-air-container //for Istio
kubectl get gateway poc-sell-air-gateway -n poc-sell-air-namespace
kubectl get virtualservices -n poc-sell-air-namespace

@REM Cleanup GKE Environment:
helm uninstall poc-sell-air-helm --namespace poc-sell-air-namespace
kubectl delete namespace poc-sell-air-namespace
gcloud container images delete gcr.io/sab-dev-storage-368015/poc-sell-air-image:latest --force-delete-tags
gcloud container clusters delete sab-cluster-dev-01 --project=sab-dev-cluster-367305 --region=us-central1


<nul set /p "=Done! Press any key to exit..."
 pause >nul