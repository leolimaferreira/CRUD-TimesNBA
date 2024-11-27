CREATE DATABASE nbadb;

USE nbadb;

CREATE TABLE timesnba (
              id INT AUTO_INCREMENT NOT NULL,
              nome VARCHAR(100),
              cidade VARCHAR(100),
              nomeMascote VARCHAR(100),
              dataCriacao DATE,
              PRIMARY KEY(id)
);