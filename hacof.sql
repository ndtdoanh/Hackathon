-- drop database hacof; 
CREATE DATABASE hacof;
USE hacof;
-- Insert sample data into Roles
select * from hackathons;


-- Insert sample data into Permissions
INSERT INTO Permissions (permission_name, description, created_by, last_updated_by) VALUES
('READ', 'Read permission', 'system', 'system'),
('WRITE', 'Write permission', 'system', 'system');

-- Insert sample data into Users
INSERT INTO Users (username, password, email, is_verified, created_by, last_updated_by) VALUES
('john_doe', 'password123', 'john@example.com', TRUE, 'system', 'system'),
('jane_smith', 'password123', 'jane@example.com', TRUE, 'system', 'system');

-- Insert sample data into Hackathons
INSERT INTO Hackathons (name, banner_image_url, description, start_date, end_date, organizer_id, created_by, last_updated_by) VALUES
('Hackathon 2023', 'http://example.com/banner.jpg', 'Annual Hackathon Event', '2023-01-01', '2023-01-10', 1, 'system', 'system');

-- Insert sample data into Teams
INSERT INTO Teams (name, hackathon_id, leader_id, created_by, last_updated_by) VALUES
('Team Alpha', 1, 1, 'system', 'system'),
('Team Beta', 1, 2, 'system', 'system');

-- Insert sample data into Awards
INSERT INTO Awards (name, description, prize_amount, hackathon_id, created_by, last_updated_by) VALUES
('Best Innovation', 'Award for the most innovative project', 1000.00, 1, 'system', 'system');

-- Insert sample data into CompetitionRounds
INSERT INTO CompetitionRounds (name, description, start_date, end_date, hackathon_id, max_team, created_by, last_updated_by) VALUES
('Qualifying', 'First round of the competition', '2023-01-02 09:00:00', '2023-01-02 17:00:00', 1, 10, 'system', 'system');

-- Insert sample data into Events
INSERT INTO Events (name, description, event_date, hackathon_id, organizer_id, campus_id, created_by, last_updated_by) VALUES
('Opening Ceremony', 'Kick-off event for the hackathon', '2023-01-01 10:00:00', 1, 1, 1, 'system', 'system');

select * from hackathons;
select * from teams;
-- USER MANAGEMENT: ROLE --
CREATE TABLE Roles (
    id INT AUTO_INCREMENT PRIMARY KEY,
    role_name VARCHAR(50) NOT NULL UNIQUE,
    INDEX(role_name),
    created_by VARCHAR(255) NOT NULL,
    created_at DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
    last_updated_by VARCHAR(255) NOT NULL,
    last_updated_at DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6),
    deleted_at DATETIME(6) DEFAULT NULL
);

CREATE TABLE Permissions (
    id INT AUTO_INCREMENT PRIMARY KEY,
    permission_name VARCHAR(100) NOT NULL UNIQUE,
    description text,
    created_by VARCHAR(255) NOT NULL,
    created_at DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
    last_updated_by VARCHAR(255) NOT NULL,
    last_updated_at DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6),
    deleted_at DATETIME(6) DEFAULT NULL
);

CREATE TABLE RolePermissions (
    id INT AUTO_INCREMENT PRIMARY KEY,
    role_id INT NOT NULL,
    permission_id INT NOT NULL,
    created_by VARCHAR(255) NOT NULL,
    created_at DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
    last_updated_by VARCHAR(255) NOT NULL,
    last_updated_at DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6),
    deleted_at DATETIME(6) DEFAULT NULL,
    FOREIGN KEY (role_id) REFERENCES Roles(id) ON DELETE CASCADE,
    FOREIGN KEY (permission_id) REFERENCES Permissions(id) ON DELETE CASCADE
);

CREATE TABLE Users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    last_login DATETIME DEFAULT NULL,
    is_verified BOOLEAN DEFAULT FALSE,
    refresh_token VARCHAR(255),
    token_expires_at DATETIME(6),
    status ENUM('ACTIVE', 'LOCKED') DEFAULT 'ACTIVE',
    created_by VARCHAR(255) NOT NULL,
    created_at DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
    last_updated_by VARCHAR(255) NOT NULL,
    last_updated_at DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6),
    deleted_at DATETIME(6) DEFAULT NULL,
    INDEX(email)
);

CREATE TABLE UserRoles (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    role_id INT NOT NULL,
    assigned_by INT NOT NULL,
    assigned_at DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
    created_by VARCHAR(255) NOT NULL,
    last_updated_by VARCHAR(255) NOT NULL,
    last_updated_at DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6),
    deleted_at DATETIME(6) DEFAULT NULL,
    FOREIGN KEY (user_id) REFERENCES Users(id) ON DELETE CASCADE,
    FOREIGN KEY (role_id) REFERENCES Roles(id) ON DELETE CASCADE,
    FOREIGN KEY (assigned_by) REFERENCES Users(id) ON DELETE CASCADE
);

CREATE TABLE UserAvatars (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    avatar_url VARCHAR(255) NOT NULL,
    uploaded_at DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
    is_current_avatar BOOLEAN DEFAULT FALSE, -- this field is current avater of user
    created_by VARCHAR(255) NOT NULL,
    created_at DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
    last_updated_by VARCHAR(255) NOT NULL,
    last_updated_at DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6),
    deleted_at DATETIME(6) DEFAULT NULL,
    FOREIGN KEY (user_id) REFERENCES USERS(id) ON DELETE CASCADE,
    INDEX(user_id)
);

CREATE TABLE UserProfiles (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    name VARCHAR(255) NOT NULL,
    phone_number VARCHAR(20) DEFAULT NULL,
    skill TEXT,
    current_avatar_id INT DEFAULT NULL,
    created_by VARCHAR(255) NOT NULL,
    created_at DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
    last_updated_by VARCHAR(255) NOT NULL,
    last_updated_at DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6),
    deleted_at DATETIME(6) DEFAULT NULL,
    FOREIGN KEY (user_id) REFERENCES Users(id) ON DELETE CASCADE,
    FOREIGN KEY (current_avatar_id) REFERENCES UserAvatars(id) ON DELETE CASCADE
);

CREATE TABLE ActivityLog (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    action VARCHAR(255) NOT NULL, -- for example: update avatar
    target VARCHAR(255) NOT NULL, -- for example: avatar
    changed_fields JSON, -- save all changes with JSON format
    status ENUM('Success', 'Failed') DEFAULT 'Success',  
    ip_address VARCHAR(45), -- ipv4 or ipv6
    device_details TEXT, -- chrome on windows 10, etc...
    created_by VARCHAR(255) NOT NULL,
    created_at DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
    last_updated_by VARCHAR(255) NOT NULL,
    last_updated_at DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6),
    deleted_at DATETIME(6) DEFAULT NULL,
    FOREIGN KEY (user_id) REFERENCES Users(id) ON DELETE CASCADE,
    INDEX(user_id)                          
);
-- END OF MANAGING USERS --
-- -----------------------------------------------------------------------------------

-- HACKATHON COMPETITIONS MANAGEMENT: campus, hackathon, round, event, checkin, checkout, form register hackathon and event, judge for each round --
CREATE TABLE Campuses (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    location VARCHAR(255) NOT NULL,
    created_by VARCHAR(255) NOT NULL,
    created_at DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
    last_updated_by VARCHAR(255) NOT NULL,
    last_updated_at DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6),
    deleted_at DATETIME(6) DEFAULT NULL
);

CREATE TABLE Hackathons (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    banner_image_url varchar(255),
    description TEXT,
    start_date DATE NOT NULL,
    end_date DATE NOT NULL,
    max_teams INT DEFAULT NULL,
    min_team_size INT DEFAULT 1,
    max_team_size INT DEFAULT 10,
    organizer_id INT NOT NULL,
    status ENUM('UPCOMING', 'ONGOING', 'COMPLETED') DEFAULT 'UPCOMING',
    created_by VARCHAR(255) NOT NULL,
    created_at DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
    last_updated_by VARCHAR(255) NOT NULL,
    last_updated_at DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6),
    deleted_at DATETIME(6) DEFAULT NULL,
    FOREIGN KEY (organizer_id) REFERENCES Users(id) ON DELETE CASCADE,
    INDEX idx_status (status),  
    INDEX idx_start_date (start_date),
    INDEX idx_end_date (end_date)
);

CREATE TABLE CompetitionRounds (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name ENUM('Qualifying', 'Semifinal', 'Final') NOT NULL,
    description TEXT,
    start_date DATETIME(6) NOT NULL,
    end_date DATETIME(6) NOT NULL, -- deadline for submitting solutions
    hackathon_id INT NOT NULL,
    max_team INT NOT NULL,
    is_video_round BOOLEAN DEFAULT FALSE, -- vong 1 thi video
    created_by VARCHAR(255) NOT NULL,
    created_at DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
    last_updated_by VARCHAR(255) NOT NULL,
    last_updated_at DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6),
    deleted_at DATETIME(6) DEFAULT NULL,
    FOREIGN KEY (hackathon_id) REFERENCES Hackathons(id) ON DELETE CASCADE
);

CREATE TABLE RegistrationForms (
    id INT AUTO_INCREMENT PRIMARY KEY,
    hackathon_id INT NOT NULL,
    user_id INT NOT NULL,
    status ENUM('SUCCESSFULLY', 'FAILED') DEFAULT 'SUCCESSFULLY',
    created_by VARCHAR(255) NOT NULL,
    created_at DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
    last_updated_by VARCHAR(255) NOT NULL,
    last_updated_at DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6),
    deleted_at DATETIME(6) DEFAULT NULL,
    FOREIGN KEY (hackathon_id) REFERENCES Hackathons(id) ON DELETE CASCADE,
    FOREIGN KEY (user_id) REFERENCES Users(id) ON DELETE CASCADE
);

CREATE TABLE Events (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    description TEXT,
    event_date DATETIME(6) NOT NULL,
    hackathon_id INT NOT NULL,
    organizer_id INT NOT NULL,
    campus_id INT NOT NULL,
    notification_sent BOOLEAN DEFAULT FALSE,
    event_type ENUM('ONLINE', 'OFFLINE') DEFAULT 'OFFLINE',
    created_by VARCHAR(255) NOT NULL,
    created_at DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
    last_updated_by VARCHAR(255) NOT NULL,
    last_updated_at DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6),
    deleted_at DATETIME(6) DEFAULT NULL,
    FOREIGN KEY (hackathon_id) REFERENCES Hackathons(id) ON DELETE CASCADE,
    FOREIGN KEY (organizer_id) REFERENCES Users(id) ON DELETE CASCADE,
    FOREIGN KEY (campus_id) REFERENCES Campuses(id) ON DELETE CASCADE
);

CREATE TABLE EventRegistrations ( --  save events that each user has joined
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    event_id INT NOT NULL,
    registration_date DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6),
    created_by VARCHAR(255) NOT NULL,
    created_at DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
    last_updated_by VARCHAR(255) NOT NULL,
    last_updated_at DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6),
    deleted_at DATETIME(6) DEFAULT NULL,
    FOREIGN KEY (user_id) REFERENCES Users(id) ON DELETE CASCADE,
    FOREIGN KEY (event_id) REFERENCES Events(id) ON DELETE CASCADE
);

CREATE TABLE CheckIn (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    event_id INT NOT NULL,
    check_in_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    check_out_time DATETIME DEFAULT NULL,
    created_by VARCHAR(255) NOT NULL,
    created_at DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
    last_updated_by VARCHAR(255) NOT NULL,
    last_updated_at DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6),
    deleted_at DATETIME(6) DEFAULT NULL,
    FOREIGN KEY (user_id) REFERENCES Users(id) ON DELETE CASCADE,
    FOREIGN KEY (event_id) REFERENCES Events(id) ON DELETE CASCADE
);

CREATE TABLE JudgeAssignments (
    id INT AUTO_INCREMENT PRIMARY KEY,
    judge_id INT NOT NULL,
    round_id INT NOT NULL,
    assigned_by VARCHAR(255) NOT NULL,
    created_by VARCHAR(255) NOT NULL,
    created_at DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
    last_updated_by VARCHAR(255) NOT NULL,
    last_updated_at DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6),
    deleted_at DATETIME(6) DEFAULT NULL,
    FOREIGN KEY (judge_id) REFERENCES Users(id) ON DELETE CASCADE,
    FOREIGN KEY (round_id) REFERENCES CompetitionRounds(id) ON DELETE CASCADE,
    INDEX(judge_id),
    INDEX(round_id)
);

-- RESOURCES MANAGEMENT OF EACH HACKATHON COMPETITIONS --
CREATE TABLE ResourceTypes (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,

    created_by VARCHAR(255) NOT NULL,
    created_at DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
    last_updated_by VARCHAR(255) NOT NULL,
    last_updated_at DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6),
    deleted_at DATETIME(6) DEFAULT NULL
);

CREATE TABLE ResourceStatuses (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    description TEXT,

    created_by VARCHAR(255) NOT NULL,
    created_at DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
    last_updated_by VARCHAR(255) NOT NULL,
    last_updated_at DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6),
    deleted_at DATETIME(6) DEFAULT NULL
);

CREATE TABLE Resources (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    resource_type_id INT,
    status_id INT,
    hackathon_id INT NOT NULL,

    created_by VARCHAR(255) NOT NULL,
    created_at DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
    last_updated_by VARCHAR(255) NOT NULL,
    last_updated_at DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6),
    deleted_at DATETIME(6) DEFAULT NULL,
    FOREIGN KEY (hackathon_id) REFERENCES Hackathons(id) ON DELETE CASCADE,
    FOREIGN KEY (resource_type_id) REFERENCES ResourceTypes(id) ON DELETE CASCADE,
    FOREIGN KEY (status_id) REFERENCES ResourceStatuses(id) ON DELETE CASCADE
);
-- END OF MANAGING HACKATHON COMPETITIONS --
-- -----------------------------------------------------------------------------------

-- TEAMS MANAGEMENT: member of each team, team amounts of each round, schedule --
CREATE TABLE Teams (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    hackathon_id INT NOT NULL,
    team_leader_id INT NOT NULL,
    campus_id INT NOT NULL,
    bio TEXT DEFAULT NULL,
    status ENUM('ACTIVE', 'DISTRIBUTED') DEFAULT 'ACTIVE',
    
    created_by VARCHAR(255) NOT NULL, 
    created_at DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
    last_updated_by VARCHAR(255) NOT NULL,
    last_updated_at DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6),
    deleted_at DATETIME(6) DEFAULT NULL,
    FOREIGN KEY (hackathon_id) REFERENCES Hackathons(id) ON DELETE CASCADE,
    FOREIGN KEY (team_leader_id) REFERENCES Users(id) ON DELETE CASCADE,
    FOREIGN KEY (campus_id) REFERENCES Campuses(id) ON DELETE CASCADE,
    INDEX(hackathon_id)
);

CREATE TABLE TeamMembers (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    team_id INT NOT NULL,
    joined_at DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6),
    left_at DATETIME(6) DEFAULT NULL, -- null if the member does not leave team

    created_by VARCHAR(255) NOT NULL,
    created_at DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
    last_updated_by VARCHAR(255) NOT NULL,
    last_updated_at DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6),
    deleted_at DATETIME(6) DEFAULT NULL,
    FOREIGN KEY (user_id) REFERENCES Users(id) ON DELETE CASCADE,
    FOREIGN KEY (team_id) REFERENCES Teams(id) ON DELETE CASCADE,
    INDEX(user_id)
);

CREATE TABLE TeamProgress (
    id INT AUTO_INCREMENT PRIMARY KEY,
    team_id INT NOT NULL,
    round_id INT NOT NULL,
    status ENUM('PASSED', 'FAILED') NOT NULL,

    created_by VARCHAR(255) NOT NULL,
    created_at DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
    last_updated_by VARCHAR(255) NOT NULL,
    last_updated_at DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6),
    deleted_at DATETIME(6) DEFAULT NULL,
    FOREIGN KEY (team_id) REFERENCES Teams(id) ON DELETE CASCADE,
    FOREIGN KEY (round_id) REFERENCES CompetitionRounds(id) ON DELETE CASCADE
);
-- END OF MANAGING TEAMS --
-- -----------------------------------------------------------------------------------

-- SUBMISSIONS MANAGEMENT: file video submission for first round, documents, submission url of each round --
CREATE TABLE Submissions (
    -- allow submit file many times before deadline: check deline through table CompetitionRounds
    id INT AUTO_INCREMENT PRIMARY KEY,
    team_id INT NOT NULL,
    hackathon_id INT NOT NULL,
    submission_url VARCHAR(255),
    round_id INT NOT NULL,  
    status ENUM('PENDING', 'APPROVED', 'REJECTED') DEFAULT 'PENDING',
    feedback TEXT,
    submitted_at DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6),

    created_by VARCHAR(255) NOT NULL,
    created_at DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
    last_updated_by VARCHAR(255) NOT NULL,
    last_updated_at DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6),
    deleted_at DATETIME(6) DEFAULT NULL,
    FOREIGN KEY (round_id) REFERENCES CompetitionRounds(id) ON DELETE CASCADE,
    FOREIGN KEY (team_id) REFERENCES Teams(id) ON DELETE CASCADE,
    FOREIGN KEY (hackathon_id) REFERENCES Hackathons(id) ON DELETE CASCADE,
    INDEX (team_id),
    INDEX (round_id)
);

CREATE TABLE SubmissionEvaluations (
    id INT AUTO_INCREMENT PRIMARY KEY,
    submission_id INT NOT NULL,
    judge_id INT NOT NULL,
    score FLOAT NOT NULL,
    feedback TEXT,
    evaluated_at DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6),

    created_by VARCHAR(255) NOT NULL,
    created_at DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
    last_updated_by VARCHAR(255) NOT NULL,
    last_updated_at DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6),
    deleted_at DATETIME(6) DEFAULT NULL,
    FOREIGN KEY (submission_id) REFERENCES Submissions(id) ON DELETE CASCADE,
    FOREIGN KEY (judge_id) REFERENCES Users(id) ON DELETE CASCADE,
    INDEX (submission_id),
    INDEX (judge_id)
);

CREATE TABLE EvaluationCriteria ( -- luu tru cac tieu chi danh gia
    id INT AUTO_INCREMENT PRIMARY KEY,
    hackathon_id INT NOT NULL,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    max_score INT DEFAULT 10,
    weight FLOAT DEFAULT 1.0,

    created_by VARCHAR(255) NOT NULL,
    created_at DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
    last_updated_by VARCHAR(255) NOT NULL,
    last_updated_at DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6),
    deleted_at DATETIME(6) DEFAULT NULL,
    FOREIGN KEY (hackathon_id) REFERENCES Hackathons(id) ON DELETE CASCADE
);

CREATE TABLE EvaluationScores (
    id INT AUTO_INCREMENT PRIMARY KEY,
    submission_id INT NOT NULL,
    criterion_id INT NOT NULL,
    score INT CHECK (score BETWEEN 0 AND 10),
    judge_id INT NOT NULL,
    comment TEXT DEFAULT NULL,

    created_by VARCHAR(255) NOT NULL,
    created_at DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
    last_updated_by VARCHAR(255) NOT NULL,
    last_updated_at DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6),
    deleted_at DATETIME(6) DEFAULT NULL,
    FOREIGN KEY (submission_id) REFERENCES Submissions(id) ON DELETE CASCADE,
    FOREIGN KEY (criterion_id) REFERENCES EvaluationCriteria(id) ON DELETE CASCADE,
    FOREIGN KEY (judge_id) REFERENCES JudgeAssignments(id) ON DELETE CASCADE
);

CREATE TABLE SubmissionFiles (
    id INT AUTO_INCREMENT PRIMARY KEY,
    submission_id INT NOT NULL,  
    round_id INT NOT NULL, 
    file_name VARCHAR(255) NOT NULL,
    file_url TEXT NOT NULL,
    file_type ENUM('VIDEO', 'DOCUMENT', 'IMAGE', 'OTHER') NOT NULL,  -- Phân loại file
    status ENUM('PENDING', 'APPROVED', 'REJECTED') DEFAULT 'PENDING',
    feedback TEXT,
    uploaded_at DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6),

    created_by VARCHAR(255) NOT NULL,
    created_at DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
    last_updated_by VARCHAR(255) NOT NULL,
    last_updated_at DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6),
    deleted_at DATETIME(6) DEFAULT NULL,
    FOREIGN KEY (submission_id) REFERENCES Submissions(id) ON DELETE CASCADE,
    FOREIGN KEY (round_id) REFERENCES CompetitionRounds(id) ON DELETE CASCADE,
    INDEX (submission_id),
    INDEX (round_id),
    INDEX (file_type),
    INDEX (status)
);

CREATE TABLE Documents (
    id INT AUTO_INCREMENT PRIMARY KEY,
    hackathon_id INT NOT NULL,
    title VARCHAR(255) NOT NULL,
    document_url VARCHAR(255) NOT NULL,
    document_type ENUM('GUIDELINES', 'ANNOUNCEMENTS', 'RULES', 'OTHER') DEFAULT 'OTHER', 
    uploaded_at DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6),

    created_by VARCHAR(255) NOT NULL,
    created_at DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
    last_updated_by VARCHAR(255) NOT NULL,
    last_updated_at DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6),
    deleted_at DATETIME(6) DEFAULT NULL,
    FOREIGN KEY (hackathon_id) REFERENCES Hackathons(id) ON DELETE CASCADE,
    INDEX (hackathon_id)
);
-- END OF MANAGING SUBMISSIONS --
-- -----------------------------------------------------------------------------------
-- AWARDS MANAGEMENT --
CREATE TABLE Awards (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL, -- first prize, second prize, third prize, promising prize
    description TEXT,
    amount_prize INT NOT NULL, -- the amount of each prize
    prize_money BIGINT NOT NULL, 
    hackathon_id INT NOT NULL,

    created_by VARCHAR(255) NOT NULL,
    created_at DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
    last_updated_by VARCHAR(255) NOT NULL,
    last_updated_at DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6),
    deleted_at DATETIME(6) DEFAULT NULL,
    FOREIGN KEY (hackathon_id) REFERENCES Hackathons(id) ON DELETE CASCADE
);

CREATE TABLE TeamAwards (
    id INT AUTO_INCREMENT PRIMARY KEY,
    award_id INT NOT NULL,
    team_id INT NOT NULL,
    awarded_at DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6),

    created_by VARCHAR(255) NOT NULL,
    created_at DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
    last_updated_by VARCHAR(255) NOT NULL,
    last_updated_at DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6),
    deleted_at DATETIME(6) DEFAULT NULL,
    FOREIGN KEY (award_id) REFERENCES Awards(id) ON DELETE CASCADE,
    FOREIGN KEY (team_id) REFERENCES Teams(id) ON DELETE CASCADE
);

CREATE TABLE Expenses (
    id INT AUTO_INCREMENT PRIMARY KEY,
    hackathon_id INT NOT NULL,
    team_id INT DEFAULT NULL,  -- expense if just relevant to one specific team
    event_id INT DEFAULT NULL, -- expense if just relevant to one specific event in Hackathon competition
    amount BIGINT NOT NULL,
    expense_date DATE NOT NULL,
    expense_type ENUM('Meals', 'Flights', 'Hotel', 'Prizes', 'Travel', 'Other') NOT NULL, -- type expense

    created_by VARCHAR(255) NOT NULL,
    created_at DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
    last_updated_by VARCHAR(255) NOT NULL,
    last_updated_at DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6),
    deleted_at DATETIME(6) DEFAULT NULL,
    FOREIGN KEY (hackathon_id) REFERENCES Hackathons(id) ON DELETE CASCADE,
    FOREIGN KEY (team_id) REFERENCES Teams(id) ON DELETE CASCADE, 
    FOREIGN KEY (event_id) REFERENCES Events(id) ON DELETE CASCADE, 
    INDEX idx_expense_type (expense_type), 
    INDEX idx_expense_date (expense_date) 
);
-- END OF MANAGING AWARDS --
-- -----------------------------------------------------------------------------------

-- NOTIFICATIONS MANAGEMENT --
CREATE TABLE Notifications (
    id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    content TEXT NOT NULL,
    user_id INT DEFAULT NULL,  -- NULL if does not send to individual
    team_id INT DEFAULT NULL,  -- NULL if does not send to team
    hackathon_id INT DEFAULT NULL,  -- NULL if does not send to hackathon competition
    audience ENUM('ALL', 'TEAM', 'INDIVIDUAL') DEFAULT 'ALL',  
    type ENUM('EMAIL', 'SMS', 'IN_APP', 'PUSH') DEFAULT 'IN_APP',
    is_read BOOLEAN DEFAULT FALSE,
    sent_at DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6),
    priority ENUM('LOW', 'MEDIUM', 'HIGH') DEFAULT 'MEDIUM',  
    expiry_date DATETIME DEFAULT NULL,  

    created_by VARCHAR(255) NOT NULL,
    created_at DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
    last_updated_by VARCHAR(255) NOT NULL,
    last_updated_at DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6),
    deleted_at DATETIME(6) DEFAULT NULL,
    FOREIGN KEY (user_id) REFERENCES Users(id) ON DELETE CASCADE,
    FOREIGN KEY (team_id) REFERENCES Teams(id) ON DELETE CASCADE,
    FOREIGN KEY (hackathon_id) REFERENCES Hackathons(id) ON DELETE CASCADE
);
-- END OF MANAGING NOTIFICATIONS --
-- -----------------------------------------------------------------------------------


-- END OF MANAGING RESOURCES --
-- -----------------------------------------------------------------------------------

-- MENTOR MANAGEMENT --
CREATE TABLE Mentors (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    expertise VARCHAR(255),
    bio TEXT DEFAULT NULL,
    availability ENUM('AVAILABLE', 'FULL', 'OFFLINE') DEFAULT 'AVAILABLE',
    rating FLOAT DEFAULT NULL,
    campus_id INT,

    created_by VARCHAR(255) NOT NULL,
    created_at DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
    last_updated_by VARCHAR(255) NOT NULL,
    last_updated_at DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6),
    deleted_at DATETIME(6) DEFAULT NULL,
    FOREIGN KEY (user_id) REFERENCES Users(id) ON DELETE CASCADE,
    FOREIGN KEY (campus_id) REFERENCES Campuses(id) ON DELETE CASCADE
);

CREATE TABLE TrainingSessions (
    id INT AUTO_INCREMENT PRIMARY KEY,
    hackathon_id INT NOT NULL,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    mentor_id INT NOT NULL,
    campus_id INT NOT NULL,
    date_training DATETIME(6) NOT NULL,

    created_by VARCHAR(255) NOT NULL,
    created_at DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
    last_updated_by VARCHAR(255) NOT NULL,
    last_updated_at DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6),
    deleted_at DATETIME(6) DEFAULT NULL,
    FOREIGN KEY (hackathon_id) REFERENCES Hackathons(id) ON DELETE CASCADE,
    FOREIGN KEY (mentor_id) REFERENCES Mentors(id) ON DELETE CASCADE,
    FOREIGN KEY (campus_id) REFERENCES Campuses(id) ON DELETE CASCADE
);

CREATE TABLE MentorBookings (
    id INT AUTO_INCREMENT PRIMARY KEY,
    mentor_id INT NOT NULL,
    user_id INT NOT NULL,
    booking_date DATETIME(6) NOT NULL,
    status ENUM('PENDING', 'CONFIRMED', 'CANCELLED') DEFAULT 'PENDING',
    created_by VARCHAR(255) NOT NULL,
    created_at DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
    last_updated_by VARCHAR(255) NOT NULL,
    last_updated_at DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6),
    deleted_at DATETIME(6) DEFAULT NULL,
    FOREIGN KEY (mentor_id) REFERENCES Mentors(id) ON DELETE CASCADE,
    FOREIGN KEY (user_id) REFERENCES Users(id) ON DELETE CASCADE
);
-- END OF MANAGING MENTORS --
-- -----------------------------------------------------------------------------------


-- TASKS MANAGEMENT --
CREATE TABLE Tasks (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    description TEXT,
    comment TEXT,
    document_url TEXT,
    status ENUM('TODO', 'IN_PROGRESS', 'DONE') DEFAULT 'TODO',
    priority ENUM('LOW', 'MEDIUM', 'HIGH') DEFAULT 'MEDIUM',
    deadline DATE,
    assigned_to INT NOT NULL,
    mentor_id INT NOT NULL,
    list_name VARCHAR(255),  
    board_name VARCHAR(255),  

    created_by VARCHAR(255) NOT NULL,
    created_at DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
    last_updated_by VARCHAR(255) NOT NULL,
    last_updated_at DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6),
    deleted_at DATETIME(6) DEFAULT NULL,
    FOREIGN KEY (assigned_to) REFERENCES Users(id) ON DELETE CASCADE,
    FOREIGN KEY (mentor_id) REFERENCES Mentors(id) ON DELETE CASCADE
);
-- END OF MANAGING TASKS --
-- -----------------------------------------------------------------------------------

-- FORUMS MANAGEMENT --
CREATE TABLE ForumThreads (
    id INT AUTO_INCREMENT PRIMARY KEY,
    hackathon_id INT NOT NULL,
    title VARCHAR(255) NOT NULL,
    status ENUM('ACTIVE', 'LOCKED', 'DELETED') DEFAULT 'ACTIVE',

    created_by VARCHAR(255) NOT NULL,
    created_at DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
    last_updated_by VARCHAR(255) NOT NULL,
    last_updated_at DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6),
    deleted_at DATETIME(6) DEFAULT NULL,
    FOREIGN KEY (hackathon_id) REFERENCES Hackathons(id) ON DELETE CASCADE,
    INDEX(hackathon_id)
);

CREATE TABLE ForumComments (
    id INT AUTO_INCREMENT PRIMARY KEY,
    thread_id INT NOT NULL,
    user_id INT NOT NULL,
    comment TEXT NOT NULL,
    parent_comment_id INT DEFAULT NULL, 
    status ENUM('ACTIVE', 'DELETED') DEFAULT 'ACTIVE',

    created_by VARCHAR(255) NOT NULL,
    created_at DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
    last_updated_by VARCHAR(255) NOT NULL,
    last_updated_at DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6),
    deleted_at DATETIME(6) DEFAULT NULL,
    FOREIGN KEY (thread_id) REFERENCES ForumThreads(id) ON DELETE CASCADE,
    FOREIGN KEY (user_id) REFERENCES Users(id) ON DELETE CASCADE,
    FOREIGN KEY (parent_comment_id) REFERENCES ForumComments(id) ON DELETE CASCADE,
    INDEX(thread_id),
    INDEX(user_id)
);
-- END OF MANAGING FORUMS --
-- -----------------------------------------------------------------------------------

-- MESSAGES MANAGEMENT --
CREATE TABLE ChatMessages (
    id INT AUTO_INCREMENT PRIMARY KEY,
    sender_id INT NOT NULL,
    recipient_id INT NOT NULL,
    team_id INT DEFAULT NULL,
    hackathon_id INT NOT NULL,
    message TEXT NOT NULL,

    sent_at DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
    last_updated_by VARCHAR(255) NOT NULL,
    last_updated_at DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6),
    deletde_at DATETIME(6) DEFAULT NULL,
    FOREIGN KEY (sender_id) REFERENCES Users(id) ON DELETE CASCADE,
    FOREIGN KEY (recipient_id) REFERENCES Users(id) ON DELETE CASCADE,
    FOREIGN KEY (team_id) REFERENCES Teams(id) ON DELETE CASCADE,
    FOREIGN KEY (hackathon_id) REFERENCES Hackathons(id) ON DELETE CASCADE,
    INDEX(sender_id),
    INDEX(recipient_id),
    INDEX(team_id),
    INDEX(hackathon_id)
);
-- END OF MANAGING MESSAGES --
-- -----------------------------------------------------------------------------------

-- LOGIN FROM 3rdPARTY MANAGEMENT --
CREATE TABLE ThirdPartyAuthProviders (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    provider_name ENUM('GOOGLE', 'FACEBOOK') NOT NULL,
    provider_user_id VARCHAR(255) NOT NULL,

    created_by VARCHAR(255) NOT NULL,
    created_at DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
    last_updated_by VARCHAR(255) NOT NULL,
    last_updated_at DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6),
    deleted_at DATETIME(6) DEFAULT NULL,
    FOREIGN KEY (user_id) REFERENCES Users(id) ON DELETE CASCADE
);
-- END OF MANAGING LOGIN --
-- -----------------------------------------------------------------------------------

-- FEEDBACKS MANAGEMENT --
CREATE TABLE UserRatings ( -- user feedback to each other
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    rated_user_id INT NOT NULL,
    rating INT CHECK (rating BETWEEN 1 AND 5),
    comment TEXT DEFAULT NULL, 

    created_by VARCHAR(255) NOT NULL,
    created_at DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
    last_updated_by VARCHAR(255) NOT NULL,
    last_updated_at DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6),
    deleted_at DATETIME(6) DEFAULT NULL,
    FOREIGN KEY (user_id) REFERENCES Users(id) ON DELETE CASCADE,
    FOREIGN KEY (rated_user_id) REFERENCES Users(id) ON DELETE CASCADE
);

CREATE TABLE Feedback ( -- user feedback to hackathon competition, taem, submission
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    hackathon_id INT NOT NULL,
    team_id INT DEFAULT NULL,
    submission_id INT DEFAULT NULL,
    feedback TEXT NOT NULL,
    rating INT CHECK (rating BETWEEN 1 AND 5) DEFAULT NULL,

    created_by VARCHAR(255) NOT NULL,
    created_at DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
    last_updated_by VARCHAR(255) NOT NULL,
    last_updated_at DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6),
    deleted_at DATETIME(6) DEFAULT NULL,
    FOREIGN KEY (user_id) REFERENCES Users(id) ON DELETE CASCADE,
    FOREIGN KEY (hackathon_id) REFERENCES Hackathons(id) ON DELETE CASCADE,
    FOREIGN KEY (team_id) REFERENCES Teams(id) ON DELETE CASCADE,
    FOREIGN KEY (submission_id) REFERENCES Submissions(id) ON DELETE CASCADE
);
-- END OF MANAGING FEEDBACKS --
-- -----------------------------------------------------------------------------------

-- BLOGS MANAGEMENT --
CREATE TABLE BlogPosts (
    id INT AUTO_INCREMENT PRIMARY KEY,
    hackathon_id INT NOT NULL,
    author_id INT NOT NULL,
    title VARCHAR(255) NOT NULL,
    content TEXT NOT NULL,
    status ENUM('PENDING', 'APPROVED', 'REJECTED', 'PUBLISHED') DEFAULT 'PENDING',  

    created_by VARCHAR(255) NOT NULL,
    created_at DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
    last_updated_by VARCHAR(255) NOT NULL,
    last_updated_at DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6),
    deleted_at DATETIME(6) DEFAULT NULL,
    FOREIGN KEY (author_id) REFERENCES Users(id) ON DELETE CASCADE,
    FOREIGN KEY (hackathon_id) REFERENCES Hackathons(id) ON DELETE CASCADE
);

CREATE TABLE BlogComments ( -- comment on each blog
    id INT AUTO_INCREMENT PRIMARY KEY,
    post_id INT NOT NULL,
    user_id INT NOT NULL,
    comment TEXT NOT NULL,
    status ENUM('APPROVED', 'HIDEN') DEFAULT 'APPROVED',  

    created_by VARCHAR(255) NOT NULL,
    created_at DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
    last_updated_by VARCHAR(255) NOT NULL,
    last_updated_at DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6),
    deleted_at DATETIME(6) DEFAULT NULL,
    FOREIGN KEY (post_id) REFERENCES BlogPosts(id) ON DELETE CASCADE,
    FOREIGN KEY (user_id) REFERENCES Users(id) ON DELETE CASCADE,
    INDEX idx_post_id (post_id)
);
-- END OF MANAGING BLOGS --
-- -----------------------------------------------------------------------------------

-- ------------------------------------------- INSERT SAMPLE DATA -------------------------------------------------------