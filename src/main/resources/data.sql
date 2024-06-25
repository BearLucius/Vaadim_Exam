





-- Создание таблицы users
CREATE TABLE IF NOT EXISTS users (
                                     id INT AUTO_INCREMENT PRIMARY KEY,
                                     username VARCHAR(255) NOT NULL UNIQUE, -- Добавлено ограничение UNIQUE
    password VARCHAR(255) NOT NULL,
    role VARCHAR(255) NOT NULL,
    avatar VARCHAR(255) DEFAULT '/image/Group 125.png'
    );



