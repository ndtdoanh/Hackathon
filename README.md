
# Hackathon Project

## Introduction

**HaCoF (Hackathon Collaboration Framework)** is an innovative web-based platform tailored for managing university-level hackathons. It streamlines key processes such as participant registration, team collaboration, project submission, and evaluation. Designed to enhance efficiency and engagement, HaCoF provides real-time updates, intuitive tools, and data-driven insights, ensuring an exceptional experience for organizers, judges, and participants alike.

This project is developed using modern technologies such as **Spring Boot** for the backend, **ReactJS** for the web frontend, and **Flutter** for the mobile app. Each team member has specific roles in the development and maintenance of the system.

## Technologies Used

- **Backend**: Java Spring Boot
- **Frontend**: ReactJS
- **Database/Storage**: MySQL, Redis, Firebase
- **Messaging/Queue**: Kafka
- **Cloud Services**: AWS
- **Development Tools**: Docker, Github
- **Architecture**: Microservices
  ![image](https://github.com/user-attachments/assets/af03f90b-a089-4be3-bd41-18cd5520fa85)

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

## Project Structure

- **Backend (Spring Boot)**:
    - Handles API requests from the frontend and mobile apps.
    - Manages user data, messages, and other core functionalities.

- **Frontend (ReactJS)**:
    - Provides the user interface for features like hackathon management, messaging, and forums.

- **Mobile (Flutter)**:
    - A mobile version with similar features as the web app, optimized for mobile devices.

## Team Members

1. **Member 1**: Backend Developer (Spring Boot)
2. **Member 2**: Frontend Developer (ReactJS)
3. **Member 3**: Mobile Developer (Flutter)
4. **Member 4**: Full-stack Developer, AI Integration (Chatbot)

## Setup and Running the Application

### Backend (Spring Boot)

1. Clone the repository:
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
- GitHub: https://github.com/ndtdoanh
