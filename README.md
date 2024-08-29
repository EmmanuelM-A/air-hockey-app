# Desktop Air Hockey Game

The project is a Java air hockey game that features a single-player mode against a computer and a two-player mode where you can compete against a friend. A scoreboard tracks and displays the scores of player who beat the computer in single-player mode.

## Installation

### Prerequisites

- JDK 11 or higher
- MySQL database (for the scoreboard feature)
- Maven

### Instructions

1. Clone the repository:

    ```sh 
    git clone https://github.com/EmmanuelM-A/air-hockey-app.git
    ```

2. Change directory to the project:

    ```sh 
    cd air-hockey-app
    ```

3. Install dependencies:

    ```sh 
    mvn install
    ```

## Configurations

### Environment Variables

1. Create a `.env` file in the root directory.

2. Add the following variables:

    ```dotenv
    DB_URL = "jdbc:mysql://localhost:3306/airhockeydatabase"
    DB_USERNAME = YOUR_USERNAME
    DB_PASSWORD = YOUR_PASSWORD
    ```
    Replace `YOUR_USERNAME` and `YOUR_PASSWORD` with your actual MySQL credentials.

### Database Setup

1. Create the database schema and tables required for the scoreboard:
    - Log in to your MySQL server:

        ```sh
        mysql -u root -p
        ```

    - Create the database:

        ```sql
        CREATE DATABASE airhockeydatabase;
        ```

    - Use the database:

        ```sql
        USE airhockeydatabase;
        ```

    - Create the necessary table for the scoreboard:

        ```sql
        CREATE TABLE IF NOT EXISTS Scoreboard (
            id INT AUTO_INCREMENT NOT NULL,
            player_name VARCHAR(50)  NOT NULL,
            player_score int NOT NULL,
            date DATE NOT NULL,
            PRIMARY KEY(id)
        );
        ```

## Usage

1. Run the application:

    - If using an IDE like IntelliJ IDEA or Eclipse, open the project and run the `Main` class.
    - If using the command line, navigate to the `target` directory (for Maven), and run:

      ```sh
      java -jar air-hockey-app.jar
      ```

2. In the game, select either single-player mode or two-player mode from the main menu.

3. For single-player mode, the game will give you the option to save your score on the leaderboard.

4. If you beat the computer, your score will be saved to the database and displayed on the scoreboard.

## License

This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for details.

