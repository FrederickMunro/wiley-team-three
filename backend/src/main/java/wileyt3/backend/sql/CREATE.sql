CREATE TABLE `app_user` (
                            `id` INT AUTO_INCREMENT PRIMARY KEY,
                            `first_name` VARCHAR(100) NOT NULL,
                            `last_name` VARCHAR(100) NOT NULL,
                            `login` VARCHAR(100) NOT NULL,
                            `password` VARCHAR(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
