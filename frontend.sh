NS_NAME="${1:=dev}"
FED_NAME=hangman-fed

export FRONTEND_SERVICE_IP=$(kubectl get svc --namespace $NS_NAME $FED_NAME --template "{{ range (index .status.loadBalancer.ingress 0) }}{{.}}{{ end }}")
echo http://$FRONTEND_SERVICE_IP:3000

