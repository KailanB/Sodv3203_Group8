


-- Insert Locations (Canadian Cities & Provinces)
INSERT INTO Location (city, province) VALUES
('Toronto', 'ON'),
('Vancouver', 'BC'),
('Montreal', 'QC'),
('Calgary', 'AB'),
('Edmonton', 'AB');

-- Insert Categories
INSERT INTO Category (category) VALUES
('Trades'),
('Computer Science'),
('Sports');

-- Insert Skills
INSERT INTO Skill (skill_name, category_id, skill_description, start_time, end_time, available) VALUES
-- Trades
('Electrician', 1, 'Expert in electrical wiring', '2024-03-10 08:00:00', '2024-03-10 12:00:00', 1),
('Plumbing', 1, 'Experienced plumber', '2024-03-11 09:00:00', '2024-03-11 13:00:00', 1),

-- Computer Science
('C#', 2, 'Software development in C#', '2024-03-12 10:00:00', '2024-03-12 14:00:00', 1),
('C++', 2, 'Expert in C++ programming', '2024-03-13 11:00:00', '2024-03-13 15:00:00', 1),
('Python', 2, 'Machine Learning & AI', '2024-03-14 12:00:00', '2024-03-14 16:00:00', 1),

-- Sports
('Soccer', 3, 'Professional soccer coach', '2024-03-15 13:00:00', '2024-03-15 17:00:00', 1),
('Football', 3, 'Football tactics and strategy', '2024-03-16 14:00:00', '2024-03-16 18:00:00', 1);

-- Insert Users (Located in Canada)
INSERT INTO Users (name, email, password, profile_intro, description, preferences, location_id, profile_picture) VALUES
('Alice', 'alice@example.com', 'pass123', 'Software Engineer', 'Loves coding in C++', 'Prefers remote learning', 1, 0x00),
('Bob', 'bob@example.com', 'securepass', 'Plumbing Expert', 'Has 10 years of experience', 'Prefers in-person training', 2, 0x00),
('Charlie', 'charlie@example.com', 'mypassword', 'Football Coach', 'Played professionally for 5 years', 'Prefers hands-on learning', 3, 0x00),
('David', 'david@example.com', 'strongpass', 'Data Scientist', 'Python enthusiast', 'Prefers video tutorials', 4, 0x00),
('Eve', 'eve@example.com', 'evepass', 'Electrician & Mentor', 'Loves teaching trade skills', 'Enjoys one-on-one mentoring', 5, 0x00);

-- Assign User Skills (Each user gets 3 skills)
INSERT INTO UserSkills (skill_id, user_id, skill_description) VALUES
(3, 1, 'Alice codes in C# professionally'),
(4, 1, 'Alice has deep knowledge in C++'),
(5, 1, 'Alice uses Python for AI projects'),

(2, 2, 'Bob is a master plumber'),
(1, 2, 'Bob also does electrical work'),
(6, 2, 'Bob plays soccer as a hobby'),

(7, 3, 'Charlie coaches soccer professionally'),
(6, 3, 'Charlie is also good at football'),
(2, 3, 'Charlie has plumbing experience'),

(5, 4, 'David is a Python data scientist'),
(4, 4, 'David also teaches C++'),
(3, 4, 'David develops C# applications'),

(1, 5, 'Eve is an expert electrician'),
(2, 5, 'Eve is also a skilled plumber'),
(6, 5, 'Eve enjoys soccer coaching');

-- Assign Skills Users Seek (Each user wants 3 skills)
INSERT INTO UserSeeksSkills (skill_id, user_id, skill_seekers_description) VALUES
(1, 1, 'Alice wants to learn electrical work'),
(2, 1, 'Alice wants to understand plumbing'),
(7, 1, 'Alice is interested in soccer training'),

(3, 2, 'Bob wants to learn C#'),
(4, 2, 'Bob is curious about C++'),
(5, 2, 'Bob wants to try Python'),

(1, 3, 'Charlie wants to become an electrician'),
(3, 3, 'Charlie wants to develop in C#'),
(5, 3, 'Charlie is interested in Python for analytics'),

(6, 4, 'David wants to get better at soccer'),
(7, 4, 'David is learning football strategy'),
(2, 4, 'David wants to pick up plumbing skills'),

(3, 5, 'Eve wants to explore C# coding'),
(4, 5, 'Eve is interested in C++'),
(5, 5, 'Eve wants to learn Python for automation');

-- Insert Friendships
INSERT INTO Friendship (user_id, friend_id, status) VALUES
(1, 2, 'accepted'),
(1, 3, 'pending'),
(2, 4, 'accepted'),
(3, 5, 'rejected'),
(4, 5, 'accepted');

-- Insert Skill Swap Requests
INSERT INTO SkillSwapRequest (request_status, user_id_to, user_id_from, appointment_time, details, status) VALUES
('pending', 1, 2, '2024-03-20 10:00:00', 'Hi Bob, I was hoping you could teach me about some plumbing', 'pending'),
('accepted', 3, 4, '2024-03-22 14:00:00', 'Charlie I was wondering if you could teach me some tips on football', 'accepted'),
('rejected', 5, 1, '2024-03-24 16:00:00', 'Hi Eve I would love to learn electrical from you', 'rejected'),
('pending', 2, 3, '2024-03-25 12:00:00', 'Charlie. This is Bob. Teach me C++. Tks.', 'pending');



SELECT * FROM Location
SELECT * FROM Users
SELECT * FROM Category
SELECT * FROM Skill
SELECT * FROM UserSkills
SELECT * FROM UserSeeksSkills
SELECT * FROM SkillSwapRequest
SELECT * FROM Friendship