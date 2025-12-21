package org.chern.manager.security;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * Класс Scratch — простой экспериментальный класс
 * для базового использования BCrypt для преобразования простого
 * текста в криптографически защищённый хеш.
 */
public class Scratch {
    public static void main(String[] args) {
        System.out.println(new BCryptPasswordEncoder().encode("pass"));
    }
}
