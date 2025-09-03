+++++++++++++++++++++++++++++++++++++++++
Проект модульного (с перспективой под раздел на микросервисы) реактивного REST-сервиса.
Представляет собой исчерпывающий гайд по технологиям Spring, 
актуальным на середину 2024г.

Модули:
admin-server:
catalogue-service: модуль каталога товаров и услуг
feedback-service: 
customer-app: модуль потребителей товаров и услуг
manager-app: модуль управление доменными сущностями и процессами
+++++++++++++++++++++++++++++++++++++++++

1. Запуск БД PostgreSQL для catalogue-api:
docker run --name catalogue-db -p 5432:5432 -e POSTGRES_USER=postgres -e POSTGRES_PASSWORD=pass -e POSTGRES_DB=catalogue postgres:16

2. Запуск KeyCloak контейнера:
sudo docker run --name retail-keycloak -p 8082:8080 -e KEYCLOAK_ADMIN=admin -e KEYCLOAK_ADMIN_PASSWORD=admin quay.io/keycloak/keycloak:latest start-dev

Коротко по стеку:
Java: 21, Spring boot 3.2.2
БД: Postgres / MongoDB
Авторизация: Keycloack / OpenID Connect
Контейнеризация: Docker / Kubernetes
Cloud-решения: Spring Cloud Eureka, Spring Cloud Config, API Gateway
...
(описание дорабатывается...)
