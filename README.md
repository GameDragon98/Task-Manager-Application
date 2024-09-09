# Task-Manager-Application

Hey there! 👋

Welcome to the Task Manager Application! I created this Java Swing project to help you manage your tasks with ease. You can add new tasks, update existing ones, and view them all organized by categories. Here’s a rundown of what the application does and how you can get started.

## What This Project Does

### Task Management
With this application, you can:

- **Add Tasks**: Input details like the task name, description, and category. The app saves your tasks to a local database for easy access.
- **Update Tasks**: Select tasks from a list and modify their details as needed.
- **View Tasks**: Filter and view tasks based on their categories to keep everything organized.

### File Operations
I’ve also included features for handling files:

- **Export Tasks**: Save your tasks to a text or CSV file for backup or sharing.
- **Import Tasks**: Load tasks from a text or CSV file into the application to quickly get your tasks set up.

## How It Works

### GUI Components
Here’s a peek at the key parts of the application:

- **Main Menu**: The starting point of the app. From here, you can navigate to different sections, such as adding new tasks, updating existing ones, viewing tasks, or handling file operations.
- **Add Task Frame**: Use this to enter details for new tasks. You can specify the task name, description, category, and completion status.
- **Update Task Frame**: This frame allows you to select a task from a dropdown list and update its details, including name, description, category, and completion status.

### Database Integration
The application uses an SQLite database to store tasks. If the database file does not exist when you run the application, it will automatically create one. This ensures that you can start using the application right away without any manual setup.

## Running the Project

1. **Clone the Repository**

   ```bash
   git clone <repository-url>
   cd <repository-directory>
   ```

2. **Set Up the Database**

   The application will automatically create the SQLite database (tasksDB) if it does not already exist. Just run the application, and it will handle the database setup for you.

3. **Compile and Run**

   Compile and run the application using your Java IDE or command line:

   ```bash
   javac -d bin src/Project/*.java
   java -cp bin Project.MainMenu
   ```

## Additional Notes

The project is organized into six files:

- **MainMenu**: Handles navigation and menu options.
- **AddTaskFrame**: Interface for adding new tasks.
- **UpdateTaskFrame**: Interface for updating existing tasks.
- **Tasks**: Represents individual tasks.
- **FileManager**: Manages exporting and importing tasks.
- **Database**: Manages the SQLite database interactions for storing and retrieving tasks.
The GUI uses Java Swing components to create a modern and user-friendly experience.

Feel free to dive into the code, make any changes you like, or use it as a base for your own projects. If you have any questions or feedback, I’d love to hear from you!

Happy task managing! 🚀

---

Let me know if there’s anything else you need!
