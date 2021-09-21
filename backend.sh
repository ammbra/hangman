NS_NAME="${1:=dev}"
BACKEND_NAME=hangman
export BACKEND_SERVICE_IP=$(kubectl get svc --namespace $NS_NAME $BACKEND_NAME --template "{{ range (index .status.loadBalancer.ingress 0) }}{{.}}{{ end }}")
echo http://$BACKEND_SERVICE_IP:8080


