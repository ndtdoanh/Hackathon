# HaCoF - Platform for organizing and managing Hackathon competitions for FPT University students

## Introduction of this capstone project

**HaCoF (Hackathon Collaboration Framework)** is an innovative web-based platform tailored for managing university-level hackathons. It streamlines key processes such as participant registration, team collaboration, project submission, and evaluation. Designed to enhance efficiency and engagement, HaCoF provides real-time updates, intuitive tools, and data-driven insights, ensuring an exceptional experience for organizers, judges, and participants alike.

This project is developed using modern technologies such as **Spring Boot** for the backend, **NextJS** for the frontend, and **MySQL** for the database. Each team member has specific roles in the development and maintenance of the system.

--- --- ---- ------- -----

## Technologies Used

- **Backend**: Java Spring Boot
- **Frontend**: NextJS
- **Database/Storage**: MySQL, Redis, Firebase
- **Messaging/Queue**: Kafka
- **Cloud Services**: AWS
- **Development Tools**: Docker, Github
- **Architecture**: Microservices

![HaCoF Architecture](https://github.com/user-attachments/assets/e8f5ade6-2766-4a5f-bd57-a2e5f78424d0)

---

## Key Features

1. **Authentication & Roles**: Secure login via email/password or third-party authentication (Google, Facebook); role-based access control with predefined roles (Admin, Organizer, Judge, Mentor, Team Leader, Team Member, Guest).
2. **Hackathon Management**: Create, manage, and edit hackathons, schedules, and registrations; broadcast real-time announcements for updates.
3. **Participant Tools**: Register for hackathons via an intuitive form, create or join teams, submit project proposals and final deliverables with file uploads (PDF, images, video links).
4. **Team Collaboration**: Dedicated team workspaces with built-in chat, task boards for tracking progress, and file-sharing support.
5. **Mentorship Module**: Request mentorship, book consultation slots (virtual or in-person), chat/video call with mentors, and access mentor profiles. Automatic mentor assignment if no mentor is selected before the deadline.
6. **Judge Panel**: Judges access a dashboard to evaluate projects based on predefined criteria, provide feedback, and assign scores; automated score calculation and ranking; export evaluation reports.
7. **Event Management**: Drag-and-drop scheduling for sessions, workshops, and ceremonies; manage venue reservations, equipment, and other resources.
8. **Discussion Forum**: Engage with other participants, find teammates, discuss hackathon-related topics, and connect with mentors.
9. **Notifications**: Real-time announcements and updates via email, in-app alerts, and SMS notifications.
10. **Analytics**: Generate post-event reports with participant insights, feedback analysis, and exportable event data for future improvements.

---

## Project Structure

### Backend (Spring Boot):
1. **API Gateway**:
- Routes client requests to appropriate microservices.
2. **Identity Service**:
- Manages user accounts, authentication, and role-based access control.
- Handles user tracking and activity logging.
- Key entities: User, UserProfile, Role, Permission, ActivityLog, etc.
- Handles authentication and authorization via Identity Service.
3. **Hackathon Service**:
- Manages hackathon creation, rounds, locations, and event scheduling.
- Handles team formation, mentorship requests, and sponsorship management.
- Key entities: Hackathon, Team, Event, MentorshipRequest, Sponsorship, etc.
4. **Submission Service**:
- Handles project submissions and file uploads.
- Manages judge rounds, evaluation, and scoring criteria.
- Key entities: Submission, JudgeSubmission, RoundMarkCriterion, etc.
5. **Communication Service**:
- Provides messaging, notifications, discussion forums, and task management.
- Manages event scheduling and reminders.
- Key entities: Notification, Conversation, Task, ForumThread, ScheduleEvent, etc.
6. **Analytics Service**:
- Collects and processes event data for reports and insights.
- Manages feedback and post-event evaluations.
- Key entities: Feedback, FeedbackDetail, BlogPost.
7. **Eureka Server (Service Discovery)**:
- Manages dynamic service registration and discovery.
- Enables load balancing and fault tolerance across microservices.

### Frontend (NextJS):
1. **Dashboard (Admin, Organizer, Judge)**:
- Provides an intuitive UI for managing hackathons, evaluating projects, and viewing analytics.
- Fetches real-time data from backend services via API Gateway.
2. **Participant Portal**:
- Allows users to register, join teams, and submit projects.
- Integrated with Hackathon Service and Submission Service.
3. **Mentorship & Forum**:
- Enables mentorship requests, booking sessions, and discussions.
- Connected to Communication Service for real-time chat, discussions, and mentor scheduling.
4. **Submission Service**:
- Displays system alerts, mentorship updates, event changes, and deadline reminders.
- Fetches data from Notification Service (Kafka-based event-driven architecture).

### Shared Services:
1. **Database**:
- MySQL: Structured data storage for all core microservices.
- Redis: Caching frequently accessed data (e.g., user sessions, event schedules).
2. **Messaging/Queue**:
- Kafka: Asynchronous event-driven communication (e.g., notifications, mentorship requests, submissions).
3. **Cloud Services - AWS (EC2, S3, RDS)**:
- EC2: Deploy API Gateway & microservices.
- S3: Store project submissions, images, and resources.
- RDS (MySQL): Managed database service for scalability.
4. **Containerization & Orchestration**:
- Docker: Ensures consistent environment setup for backend & frontend.

---

## Team Members

1. **Nguyen Doan Trong Doanh** (Leader)
2. **Le Quang Huy**
3. **Nguyen Cao Trung Kien**
4. **Bui Phan Thanh Phuc**

---

## Setup and Running the Application

### Prerequisites

Ensure you have the following installed on your machine:
- **Java 21**
- **Maven 3.x**
- **Node.js 14.x or later**
- **npm 6.x or later**
  
### Backend (Spring Boot)

1. **Clone the repository**:
   ```bash
   git clone https://github.com/ndtdoanh/Hackathon.git
   ```
2. Install dependencies:
   ```bash
   mvn install
   ```
3. Run the application:
   ```bash
   mvn spring-boot:run
   ```

### Frontend (NextJS)

1. Clone the repository:
   ```bash
   git clone https://github.com/ndtdoanh/Hackathon.git
   ```
2. Install dependencies:
   ```bash
   npm install
   ```
3. Run the application:
   ```bash
   npm start
   ```

## Contact

If you have any questions about the project, feel free to reach out:

- Email: nguyentrongdoanh202@gmail.com
- GitHub: https://github.com/ndtdoanh/Hackathon
