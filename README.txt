SecureVault - JavaFX + MySQL (XAMPP) Project

Requirements:
- Java 17+
- Maven
- XAMPP MySQL running

Setup:
1. Start MySQL in XAMPP.
2. Create the database by importing database_setup.sql in phpMyAdmin or MySQL terminal.
3. Confirm DB credentials in src/main/java/com/securevault/db/DBUtil.java
   Default:
   - database: securevault_db
   - user: root
   - password: empty
4. Open the project folder in VS Code.
5. Run in terminal:
   mvn clean javafx:run

Features:
- Master password login
- Add, view, update, delete credentials
- Search credentials
- Generate password
- Copy password to clipboard
