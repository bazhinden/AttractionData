# Используем OpenJDK 17
FROM openjdk:17-jdk-alpine

# Устанавливаем рабочую директорию
WORKDIR /app

# Копируем скомпилированный JAR-файл
COPY target/attraction-data-0.0.1-SNAPSHOT.jar app.jar

# Открываем порт 8080
EXPOSE 8080

# Команда для запуска приложения
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
