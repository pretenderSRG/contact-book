# 📘 Contact Book
## ✅ v3.0 — React Contact Book (Web App)

A responsive contact management web application built with **React** and **Docker**.  
Users can view, add, edit, and delete contacts with client-side validation and mobile-friendly design.

---

### 📌 Features:

- View all contacts  
- Add a new contact  
- Edit existing contacts  
- Delete contacts  
- Cancel edit mode  
- **Form validation**:
  - ✅ Valid phone number (e.g. `+380XXXXXXXXX`)
  - ✅ Valid email format
- Responsive layout for mobile and desktop
- Simple and clean UI

---

### 🧰 Technologies Used:

- React  
- React Hooks (`useState`, `useEffect`)  
- CSS (custom styles & responsive design)  
- JSX  
- Docker (for containerized deployment)  
- ESLint + Prettier *(optional for formatting)*

---

### 🚀 Run Locally

#### Option 1: With Node.js

1. **Clone the repository and switch to version 3.0:**
   ```bash
   git clone https://github.com/pretenderSRG/contact-book.git
   cd contact-book
   git checkout v3.0
   ```

2. **Install dependencies:**
   ```bash
   npm install
   ```

3. **Start development server:**
   ```bash
   npm start
   ```

4. **Open in browser:**  
   [http://localhost:3000](http://localhost:3000)

---

#### Option 2: Run with Docker

1. **Make sure Docker is installed and running**

2. **Build and run the container:**
   ```bash
   docker build -t contact-book .
   docker run -p 3000:3000 contact-book
   ```

3. **Access the app in browser:**  
   [http://localhost:3000](http://localhost:3000)

---

### 📁 Folder Structure

```
src/
├── components/
│   ├── ContactList.js
│   ├── EditContactForm.js
│   ├── AddContactForm.js
├── App.js
├── index.js
├── styles/
│   └── EditContactForm.css
Dockerfile
```
