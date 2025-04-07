---

# 🩸 DonorLink

**DonorLink** is a JavaFX-based desktop application designed to enhance and streamline the blood donation process. It connects donors, recipients, hospitals, and donation centers through an intuitive interface backed by a robust MySQL database.

---

## ✨ Features

### 🔐 User Registration and Login
- Secure sign-up and login system.
- Stores essential user details including blood type and contact info.

### 🩸 Blood Donation Events
- View and register for upcoming blood donation drives.
- See event-specific details like location, timing, and organizer.

### 🆘 Blood Requests
- Submit urgent blood requests for specific blood groups.
- Notify nearby and eligible donors automatically.

### 📍 Donor Locator
- Locate nearby blood banks and donation centers.
- View details and get directions from within the app.

### 📅 Appointment Scheduling
- Schedule donation appointments at preferred locations.
- View and manage your donation schedule.

### 🩺 Health Screening & Eligibility Check
- Fill out health screening forms before donating.
- Instantly check eligibility based on user input and donation history.

### 👤 Donor Profiles
- Create, edit, and manage donor profiles.
- Track past donations and receive recognition for consistent contributions.

### 📚 Education & Awareness
- Browse educational content about blood donation.
- Participate in awareness drives and campaigns.

### ⭐ Feedback & Ratings
- Leave ratings and feedback for donation centers and events.
- Help improve the quality of services offered.

---

## 🛠 Tech Stack

- **Frontend:** JavaFX
- **Backend:** Java (JDBC)
- **Database:** MySQL
- **IDE:** IntelliJ IDEA / Eclipse
- **Build Tool:** Maven / Gradle (optional)

---

## ⚙️ Setup & Installation

### Requirements
- JDK 17 or above
- MySQL Server
- JavaFX SDK
- MySQL JDBC Driver

### Steps

1. **Clone the Repository**
   ```bash
   git clone https://github.com/your-username/donorlink.git
   cd donorlink
   ```

2. **Open in IDE**
   - Import into IntelliJ IDEA or Eclipse.
   - Make sure JavaFX SDK is properly configured.

3. **Database Configuration**
   - Create a new MySQL database.
   - **⚠️ Note:** All required tables must be **manually created** based on the application’s code logic. There is no auto-generation of schema.
   - Update database connection details in your configuration file (e.g., `DBUtil.java`).

4. **Run the Application**
   - Compile and run `Main.java` or your app’s entry point.

---

## 🔒 Security

- User authentication is handled securely.
- Passwords should be hashed using secure algorithms (e.g., BCrypt) — implementation recommended.

---


---

## 📄 License

This project is licensed under the [MIT License](LICENSE).

---
