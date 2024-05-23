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

    INSERT INTO users (username, password, role) VALUES
        ('test', '123', 'admin'),
        ('test2', '123', 'employee'),
        ('test3', '123', 'pjManager'),
        ('test4', '123', 'pjManager'),
        ('test5', '123', 'employee'),
        ('test6', '123', 'employee'),
        ('test7', '123', 'employee');

    INSERT INTO projects (projectName, status, user_id) VALUES
        ('Project A', 'I gang', 3),
        ('Project B', 'Færdig', 3),
        ('Project C', 'Ikke startet', 4);

    INSERT INTO subprojects (subprojectName, status, project_id) VALUES
        ('Subproject A1', 'I gang', 1),
        ('Subproject A2', 'Færdig', 1),
        ('Subproject A3', 'I gang', 1),
        ('Subproject B1', 'I gang', 2),
        ('Subproject B2', 'Ikke startet', 2),
        ('Subproject B3', 'I gang', 2),
        ('Subproject C1', 'I gang', 3);

    INSERT INTO tasks (taskName, subproject_id, startDate, endDate, estTime, status, user_id) VALUES
        ('Task 1', 1, '2024-05-10', '2024-05-15', 20, 'I gang', 1),
        ('Task 2', 1, '2024-05-12', '2024-05-18', 15, 'I gang', 2),
        ('Task 3', 2, '2024-05-15', '2024-05-20', 25, 'Færdig', 3),
        ('Task 4', 3, '2024-05-08', '2024-05-14', 30, 'Ikke startet', 4),
        ('Task 5', 5, '2024-05-16', '2024-05-20', 25, 'I gang', 5),
        ('Task 6', 5, '2024-05-16', '2024-05-20', 25, 'I gang', 2);


