@echo off
title Kubernetes Steps Order

@REM Docker Images:
cd /d F:\Workspace-IntelliJ-POC\bookmymovie-service\bookmymovie-order
minikube image build -t bookmymovie-order-image . //Build an Docker Image directly in Minikube
minikube image ls --format table

@REM Execute Helm Chart:
set HELM_DIR=F:\Workspace-IntelliJ-POC\bookmymovie-service\bookmymovie-order\configuration\helm
echo %HELM_DIR%
helm lint %HELM_DIR%
helm install --set configDir=env/GCP-Dev/app-config bookmymovie-order-helm --namespace bookmymovie-namespace %HELM_DIR% --dry-run
helm install --set configDir=env/GCP-Dev/app-config bookmymovie-order-helm --namespace bookmymovie-namespace %HELM_DIR%
helm status bookmymovie-order-helm --namespace bookmymovie-namespace
helm list --namespace bookmymovie-namespace

@REM Check Kubernetes Components:
kubectl get configmap bookmymovie-order-configmap -n bookmymovie-namespace
kubectl get service bookmymovie-order-service -n bookmymovie-namespace
kubectl get deployments -n bookmymovie-namespace
kubectl get pods -n bookmymovie-namespace
kubectl logs --follow <pod-name> -n bookmymovie-namespace -c bookmymovie-order-container //for Istio
kubectl get virtualservices -n bookmymovie-namespace

@REM Cleanup Environment:
helm uninstall bookmymovie-order-helm --namespace bookmymovie-namespace
kubectl delete namespace bookmymovie-namespace
docker image rm --force bookmymovie-order-image
minikube stop


<nul set /p "=Done! Press any key to exit..."
 pause >nul