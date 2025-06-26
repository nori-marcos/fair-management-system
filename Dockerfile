# Usa a imagem com Java 24 (usando Eclipse Temurin, builds oficiais da Adoptium)
FROM eclipse-temurin:24-jdk-jammy

# Cria diretório de trabalho
WORKDIR /app

# Copia todos os arquivos do projeto
COPY . .

# Dá permissão para o Maven Wrapper
RUN chmod +x ./mvnw

# Compila o projeto (sem rodar testes)
RUN ./mvnw clean package -DskipTests

# Executa o JAR gerado
CMD ["java", "-jar", "target/*.jar"]
