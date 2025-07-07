# 📘 Contact Book 3.0

A simple full-stack contact management application built with React and Spring Boot. It allows users to store and manage their contacts, including personal details, address, and profile photo.

## 🚀 Tech Stack

- **Frontend:** React
- **Backend:** Java (Spring Boot)
- **Database:** PostgreSQL (runs in Docker container)
- **Containerization:** Docker

## 🧩 Features

- Add, edit, and delete contacts
- Fields: first name, last name, email, phone number, description
- Upload a contact photo (WIP)
- Add and display address
- Display contact's location on an embedded Google Map (WIP)

## 📦 Project Structure

contact-book/
├── backend/ # Spring Boot API
├── frontend/ # React frontend
├── docker-compose.yml (planned)
└── README.md

## 🐘 Database

PostgreSQL will run in a Docker container via `docker-compose`. The backend connects via standard JDBC configuration.

## 📸 Images (Upcoming)

User photos will be stored and served by the backend. You may need to run a static file server (or configure Spring to serve static resources).

## 🗺️ Maps (Upcoming)

Addresses will be visualized using **Google Maps Embed API**. You'll need:

- Google Maps API key
- React component to embed the map
- Optional: geocoding address → lat/lng (via Google API)

## 📍 Setup (Coming soon)

Instructions for setting up with `docker-compose` will be added.

## 🔧 Requirements

- Java 17+
- Node.js + npm
- Docker + Docker Compose
- Google Maps API key (for map functionality)

---

Made with ❤️ in Ukraine
