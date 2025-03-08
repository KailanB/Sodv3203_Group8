


-- Drop Database if it exists
IF EXISTS (SELECT * FROM sys.databases WHERE name = 'SkillSwapDB')
BEGIN
    -- added this line because of an error when trying to drop and re-create
    ALTER DATABASE SkillSwapDB SET SINGLE_USER WITH ROLLBACK IMMEDIATE 

    DROP DATABASE SkillSwapDB;
END
GO

--Create Database
CREATE DATABASE SkillSwapDB;
GO
USE SkillSwapDB;

CREATE TABLE Location (
    location_id INT IDENTITY(1,1) PRIMARY KEY,
    city VARCHAR(255),
    province CHAR(2) NOT NULL
);


CREATE TABLE Users (
    user_id INT IDENTITY(1,1) PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    profile_intro VARCHAR(255),
    description VARCHAR(255),
    preferences VARCHAR(255),
    location_id INT FOREIGN KEY REFERENCES Location(location_id),
    profile_picture VARBINARY(MAX) NOT NULL
);


CREATE TABLE Friendship (
    user_id INT NOT NULL,
    friend_id INT NOT NULL,
    status VARCHAR(30) CHECK (status IN ('pending', 'accepted', 'rejected')) NOT NULL,
    PRIMARY KEY (user_id, friend_id),
    FOREIGN KEY (user_id) REFERENCES Users(user_id),
    FOREIGN KEY (friend_id) REFERENCES Users(user_id)
);


CREATE TABLE Category (
    category_id INT IDENTITY(1,1) PRIMARY KEY,
    category VARCHAR(255) NOT NULL
);


CREATE TABLE Skill (
    skill_id INT IDENTITY(1,1) PRIMARY KEY,
    skill_name VARCHAR(255) NOT NULL,
    category_id INT NOT NULL,
    skill_description VARCHAR(255),
    start_time DATETIME,
    end_time DATETIME,
    available TINYINT,
    FOREIGN KEY (category_id) REFERENCES Category(category_id)
);


CREATE TABLE UserSkills (
    skill_id INT NOT NULL,
    user_id INT NOT NULL,
    skill_description VARCHAR(255),
    PRIMARY KEY (skill_id, user_id),
    FOREIGN KEY (skill_id) REFERENCES Skill(skill_id),
    FOREIGN KEY (user_id) REFERENCES Users(user_id)
);


CREATE TABLE UserSeeksSkills (
    skill_id INT NOT NULL,
    user_id INT NOT NULL,
    skill_seekers_description VARCHAR(255),
    PRIMARY KEY (skill_id, user_id),
    FOREIGN KEY (skill_id) REFERENCES Skill(skill_id),
    FOREIGN KEY (user_id) REFERENCES Users(user_id)
);


CREATE TABLE SkillSwapRequest (
    request_id INT IDENTITY(1,1) PRIMARY KEY,
    request_status VARCHAR(30) CHECK (request_status IN ('pending', 'accepted', 'rejected')) NOT NULL,
    user_id_to INT NOT NULL,
    user_id_from INT NOT NULL,
    appointment_time DATETIME NOT NULL,
    details VARCHAR(255),
    status VARCHAR(255),
    FOREIGN KEY (user_id_to) REFERENCES Users(user_id),
    FOREIGN KEY (user_id_from) REFERENCES Users(user_id)
);





