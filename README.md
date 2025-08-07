# Contact Book 📒

✅ ***v1.0 — Basic Console Implementation Contacts are stored in a binary file (contact_book.bin).***

📌 _Features: Add a new contact_

> - View all contacts

> - Search contacts by name, phone number, or email

🔍 _Validation:_

> - Phone number format: +380 XX XXX XX XX or without spaces

> - Email format: simplified validation

🧪 _Unit Tests:_

- ContactValidator – fully tested

- ContactManager – partially tested

⚙️ _Technologies used:_

- Java 17+

- Maven

- SLF4J + Logback

- JUnit 5

⚙️ _Installation & Run:_

Clone the project:

- git clone https://github.com/pretenderSRG/contact-book.git
- cd contact-book
- git checkout v1.0

Build the project with Maven:

- mvn clean package

Run the application:

- java -jar target/contact-book.jar

Data file:

- A file named contact_book.bin will be created automatically to store contact data.
