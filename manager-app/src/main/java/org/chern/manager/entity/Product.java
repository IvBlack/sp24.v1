package org.chern.manager.entity;

/*
Сущность становится record, т.к. со стороны приложения менеджера уже
не д.б. возможности редактировать товар.
*/
public record Product(int id, String title, String details) {
}
