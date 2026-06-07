# Build - etapa de compilacao do .war com Maven/JDK 25.
FROM docker.io/library/maven:3.9-eclipse-temurin-25 AS build
WORKDIR /app

COPY pom.xml .
# Baixa as dependencias em cache (o build falha se o repositorio remoto nao estiver disponivel, mas o cache local e mantido).
RUN mvn -q -e -DskipTests dependency:go-offline || true

COPY src ./src
# Gera o .war (o JavaFX e' excluido pelo maven-war-plugin).
RUN mvn -q -e -DskipTests clean package

# Runtime - etapa de execucao do Tomcat 11 com o .war implantado.
FROM docker.io/library/tomcat:11.0-jdk25-temurin AS runtime

# Remove as aplicacoes por omissao do Tomcat.
RUN rm -rf /usr/local/tomcat/webapps/*

# Implanta o .war como ROOT (fica acessivel em http://localhost:8080/).
COPY --from=build /app/target/dotsandboxes-*.war /usr/local/tomcat/webapps/ROOT.war

EXPOSE 8080
