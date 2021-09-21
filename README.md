# How to Deploy Hangman

You can deploy this setup either locally (Minikube) or in your OpenShift Sandbox by simple issuing:

```shell script
####Create namespace dev only if you wish to deploy in de
kubectl create ns dev
helm install hangman ./hangman/chart/hangman
```

Run the following script by specifying the name of the workspace you used to install the previous Helm chart (in this example dev):

```shell script
sh backend.sh dev
```

Replace the result in hangman/hangman-fed/values.yaml:

```yaml script
backend:
  hostname: a228c16976fb04064b199c95e98fa99d-2097135439.us-east-1.elb.amazonaws.com:8080
```

Finally run:

```shell script
helm install hangman-fed ./hangman-fed/chart/hangman-fed
```

Now access hangman at the URL obtained from:

```shell script
sh frontend.sh dev
```