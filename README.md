# Contact Book 📒

✅ ***v2.0 — Console App with SQLite Database***

Contacts are now stored in an SQLite database (contacts.db), instead of a binary file.

📌 *Features:*
> * Add a new contact

> * View all contacts

> * Search contacts by name, phone number, or email

🔍 *Validation:*
> * Phone number format: +380 XX XXX XX XX or without spaces

> * Email format: simplified validation

🧪 *Unit Tests:*

- ContactValidator – fully tested

- ContactManager – partially tested

🧰 *Technologies used:*
- Java 17+

- Maven

- SQLite (via JDBC)

- SLF4J + Logback

- JUnit 5

⚙️ *Installation & Run:*
Clone the project:


> - git clone https://github.com/pretenderSRG/contact-book.git
> - cd contact-book
> - git checkout v2.0

_Build the project with Maven:_


> -mvn clean package
_Run the application:_


> - java -jar target/contact-book.jar

_Database file:_

A new SQLite database file contacts.db will be created in the project root (or defined location).

