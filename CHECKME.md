Разработка проектов со Spring (2024)
Веб-приложение представляет собой CRUD-платформу по управлению товарами в ритейле.

Профили Spring
standalone - для запуска модулей admin-server, catalogue-service, feedback-service, 
customer-app и manager-app без Spring Cloud Eureka, Spring Cloud Config, Docker и Kubernetes.

Инфраструктура

1. PostgreSQL
   В проекте используется в качестве БД модуля каталога.

Запуск в Docker:
docker run --name catalogue-db -p 5432:5432 -e POSTGRES_USER=postgres -e POSTGRES_PASSWORD=pass -e POSTGRES_DB=catalogue postgres:16