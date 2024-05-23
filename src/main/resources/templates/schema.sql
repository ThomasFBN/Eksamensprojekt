    CREATE DATABASE IF NOT EXISTS projectTool_db;
    USE projectTool_db;

    CREATE TABLE IF NOT EXISTS users (
        user_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
        username VARCHAR(50) NOT NULL UNIQUE,
        password VARCHAR(255) NOT NULL,
        role ENUM('admin', 'employee', 'pjManager') NOT NULL
        );

    CREATE TABLE IF NOT EXISTS projects (
        project_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
        projectName VARCHAR(50) NOT NULL UNIQUE,
        status ENUM('Ikke startet', 'I gang', 'Færdig') NOT NULL,
        user_id INT,
        FOREIGN KEY (user_id) REFERENCES users(user_id)
        );

    CREATE TABLE IF NOT EXISTS subprojects (
        subproject_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
        subprojectName VARCHAR(50) NOT NULL UNIQUE,
        status ENUM('Ikke startet', 'I gang', 'Færdig') NOT NULL,
        project_id INT NOT NULL,
        FOREIGN KEY (project_id) REFERENCES projects(project_id) ON DELETE CASCADE
        );

    CREATE TABLE IF NOT EXISTS tasks (
        task_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
        taskName VARCHAR(50) NOT NULL UNIQUE,
        subproject_id INT NOT NULL,
        startDate DATE,
        endDate DATE,
        estTime INT NOT NULL,
        status ENUM('Ikke startet', 'I gang', 'Færdig') NOT NULL,
        user_id INT,
        FOREIGN KEY (subproject_id) REFERENCES subprojects(subproject_id) ON DELETE CASCADE,
        FOREIGN KEY (user_id) REFERENCES users(user_id)
        );
