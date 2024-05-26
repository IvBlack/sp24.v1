���������� �������� �� Spring (2024)
���-���������� ������������ ����� CRUD-��������� �� ���������� �������� � �������.

������� Spring
standalone - ��� ������� ������� admin-server, catalogue-service, feedback-service, 
customer-app � manager-app ��� Spring Cloud Eureka, Spring Cloud Config, Docker � Kubernetes.

��������������

1. PostgreSQL
DB for catalogue-api using Docker:
docker run --name catalogue-db -p 5432:5432 -e POSTGRES_USER=postgres -e POSTGRES_PASSWORD=pass -e POSTGRES_DB=catalogue postgres:16

2. KeyCloak container:
sudo docker run --name retail-keycloak -p 8082:8080 -e KEYCLOAK_ADMIN=admin -e KEYCLOAK_ADMIN_PASSWORD=admin quay.io/keycloak/keycloak:latest start-dev