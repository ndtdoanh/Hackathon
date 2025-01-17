# HaCoF - Platform for organizing and managing Hackathon competitions for FPT University students

## Introduction

**HaCoF (Hackathon Collaboration Framework)** is an innovative web-based platform tailored for managing university-level hackathons. It streamlines key processes such as participant registration, team collaboration, project submission, and evaluation. Designed to enhance efficiency and engagement, HaCoF provides real-time updates, intuitive tools, and data-driven insights, ensuring an exceptional experience for organizers, judges, and participants alike.

This project is developed using modern technologies such as **Spring Boot** for the backend, **ReactJS** for the frontend, and **MySQL** for the database. Each team member has specific roles in the development and maintenance of the system.

---

## Technologies Used

- **Backend**: Java Spring Boot
- **Frontend**: ReactJS
- **Database/Storage**: MySQL, Redis, Firebase
- **Messaging/Queue**: Kafka
- **Cloud Services**: AWS
- **Development Tools**: Docker, Github
- **Architecture**: Microservices

![HaCoF Architecture](https://github.com/user-attachments/assets/af03f90b-a089-4be3-bd41-18cd5520fa85)

---

## Key Features

1. **Authentication & Roles**: Secure login with email/Google, role-based access control (Admin, Organizer, Judge, Mentor, Participant, Guest).
2. **Hackathon Management**: Create/manage hackathons, schedules, and registrations; broadcast announcements.
3. **Participant Tools**: Register for hackathons, create/join teams, submit projects with file uploads.
4. **Team Collaboration**: Integrated chat, task boards, and file sharing for teams.
5. **Mentorship Module**: Book sessions, chat/video call with mentors, access mentor profiles.
6. **Judge Panel**: Evaluate projects, provide feedback, automated scoring, export evaluation reports.
7. **Event Management**: Drag-and-drop schedules, manage workshops, and publish resources.
8. **Discussion Forum**: Find teammates, collaborate, and connect with mentors.
9. **Notifications**: Real-time updates via email, in-app, and SMS.
10. **Analytics**: Post-event feedback, participant insights, and exportable reports.

---

## Project Structure

### Backend (Spring Boot):
- **API Gateway**: Routes client requests to appropriate microservices, handles authentication and authorization.
- **User Service**: Manages user accounts, authentication, and role-based access control.
- **Hackathon Service**: Handles the creation, management, and scheduling of hackathons, as well as announcements and participant registration.
- **Team Service**: Manages team creation, joining, collaboration, and task assignments.
- **Submission Service**: Handles project submissions, file uploads, and versioning.
- **Mentorship Service**: Allows mentor session scheduling, chat, and video call integration.
- **Judging Service**: Manages project evaluation, feedback collection, and scoring algorithms.
- **Notification Service**: Sends real-time updates via email, SMS, and in-app notifications.
- **Analytics Service**: Collects and processes event data for reports and insights.

### Frontend (ReactJS):
- **Dashboard**: Intuitive UI for administrators, organizers, and judges to manage hackathons, evaluate projects, and view analytics.
- **Participant Portal**: Allows participants to register, join teams, and submit projects.
- **Mentorship & Forum**: Provides a collaborative space for discussions, mentor bookings, and connecting participants.
- **Real-time Notifications**: Displays updates and alerts to users via a responsive design.

### Shared Services:
- **Database**: MySQL for structured data storage and Redis for caching.
- **Messaging/Queue**: Kafka for asynchronous communication between microservices.
- **Cloud Services**: AWS for hosting, file storage, and scaling the application.
- **Containerization**: Docker for consistent environment setup across development and production.

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

### Frontend (ReactJS)

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
