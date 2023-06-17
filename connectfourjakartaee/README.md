# Connect Four Web App

A proxy server that serves the Connect Four game, and a web app that allows users to play the game.

## Deployment

Use your build tool of preference to generate the `WAR` file. For example, if you are using IntelliJ's build tool,
here's how to configure it:
![](/Users/andrefonseca/Projects/ISEL/LEIM/ICD/connectfourjakartaee/docs/artifact_exploded.png)

![](/Users/andrefonseca/Projects/ISEL/LEIM/ICD/connectfourjakartaee/docs/artifact_archive.png)

The web app is deployed using Docker Compose. To deploy the web app, run the following command:

```
export ARTIFACT_DIR=<artifact directory>
export ARTIFACT_FILE=<artifact file name>

docker-compose up
```
