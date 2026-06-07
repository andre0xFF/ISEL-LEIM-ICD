# Dockerfile do servidor de jogo TCP autonomo (porta 8000).
# Build multi-etapa: compila com Maven/JDK 25 e corre apenas com o JRE.
# O JavaFX NAO e necessario em runtime — o servidor so usa o JDK.

# Build -
FROM docker.io/library/maven:3.9-eclipse-temurin-25 AS build
WORKDIR /app

COPY pom.xml .
#
RUN mvn -q -e -DskipTests dependency:go-offline || true

# Copia o codigo-fonte e empacota (gera tambem target/classes).
COPY src ./src
RUN mvn -q -e -DskipTests clean package

# Runtime -
FROM docker.io/library/eclipse-temurin:25-jre AS runtime
WORKDIR /app

# So precisamos das classes e recursos compilados (inclui /schemas no classpath).
COPY --from=build /app/target/classes /app/classes

# Diretorio de dados persistente (volume). O servidor e o unico escritor de
# Users.xml/Profiles.xml (Design A) e do audit.log.
VOLUME ["/data"]
EXPOSE 8000

# Arranca o servidor autonomo (sem Tomcat). O classpath e apenas /app/classes;
# o XSD e carregado de /schemas/Commands.xsd e os dados de /data.
ENTRYPOINT ["java", "-Ddab.data.dir=/data", "-cp", "/app/classes", "pt.isel.icd.ServerApplication"]
