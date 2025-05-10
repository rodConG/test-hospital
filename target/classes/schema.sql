DROP TABLE IF EXISTS doctor;
DROP TABLE IF EXISTS doctors_office;

CREATE TABLE doctor (
                        id BIGINT AUTO_INCREMENT PRIMARY KEY,
                        name VARCHAR(100) NOT NULL,
                        paternal_surname VARCHAR(100) NOT NULL,
                        maternal_surname VARCHAR(100),
                        specialty VARCHAR(100)
);

CREATE TABLE doctors_office (
                               id BIGINT AUTO_INCREMENT PRIMARY KEY,
                               number_doctors_office INT NOT NULL,
                               floor VARCHAR(100) NOT NULL
);
