# How to Deploy Hangman

You can deploy this setup either locally or in your Kubernetes cluster by simple issuing:

```shell script
####Create namespace dev only if you wish to deploy in this namespace. Otherwise you can use the project/namespace you wish
kubectl create ns dev
helm install backend ./backend/chart/backend
```

Run the following script by specifying the name of the workspace you used to install the previous Helm chart (in this example dev):

```shell script
sh backend.sh dev
```

Replace the result in _frontend/chart/fed/values.yaml_:

```yaml script
backend:
  hostname: hostname:8080
```

Finally run:

```shell script
helm install fed ./frontend/chart/fed
```

Now access hangman at the URL obtained from:

```shell script
sh frontend.sh dev
```
