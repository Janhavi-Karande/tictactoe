# Tic-Tac-Toe Game

This project is a classic Tic-Tac-Toe game designed for the command-line interface. It demonstrates the application of the MVC architectural pattern to separate the game's data (Model), presentation (View), and control logic (Controller). Additionally, it utilizes various design patterns to promote code reusability, flexibility, and maintainability. You can enjoy a game against a friend or test your skills against an AI opponent with different difficulty settings.

## Features

* **Two-Player Mode:** Play against another human player on the same machine.
* **Bot Player:** Engage in a single-player game against an intelligent AI opponent.
* **Adjustable Bot Difficulty:** Choose between "Easy" and "Hard" difficulty levels to vary the challenge.
* **Customizable Symbols:** Human players can personalize their game by selecting their preferred symbols (e.g., X, O, or others).
* **Command-Line Interface (CLI):** Enjoy a straightforward gaming experience directly within your terminal.
* **Single Bot per Game:** The game is designed for a maximum of one bot player per match.
* **Model-View-Controller (MVC) Architecture:** The codebase is structured following the MVC pattern, ensuring a clear separation of concerns for improved organization and maintainability.
* **Strategic Bot Implementation:** The "Hard" difficulty bot employs intelligent strategies to provide a challenging opponent.
* **Undo Option (vs. Bot):** Players have the ability to undo their last move when playing against the bot.
* **Clear Game Outcome:** The game clearly announces the winner or declares a draw upon completion.

## Technologies Used

* **Java:** The primary programming language used for the game logic and implementation.
* **MVC Architecture:** The foundational architectural pattern for organizing the codebase.
* **Design Patterns:** Employed strategically throughout the project to enhance code quality (e.g., Factory pattern for creating game components, Strategy pattern for different bot difficulty levels). *(Consider listing specific patterns if you want to highlight them)*

## Getting Started

Follow these instructions to get the Tic-Tac-Toe game running on your local machine.

### Prerequisites

* **Java Development Kit (JDK):** Ensure you have a compatible JDK installed (version 8 or higher is recommended). You can download it from [Oracle Java Downloads](https://www.oracle.com/java/technologies/downloads/) or [OpenJDK](https://openjdk.java.net/).

### Installation

1.  **Clone the Repository:**
    ```bash
    git clone https://github.com/Janhavi-Karande/tictactoe.git
    cd tictactoe
    ```

### Running the Game

1.  **Compile the Java Source Files:**
    ```bash
    javac -d bin src/*.java
    ```
    This command compiles all the `.java` files in the `src` directory and places the resulting `.class` files in the `bin` directory.

2.  **Execute the Game:**
    ```bash
    java -cp bin Main
    ```
    This command runs the `Main` class located in the `bin` directory, which will start the Tic-Tac-Toe game.

## How to Play

1.  Upon running the game, you will be prompted to choose the game mode (two players or one player vs. bot).
2.  If playing against the bot, you will be asked to select the difficulty level ("Easy" or "Hard").
3.  Human players will be prompted to enter their desired symbols.
4.  The game board will be displayed in the terminal, with each cell numbered.
5.  Players take turns entering the number corresponding to the cell where they want to place their symbol.
6.  When playing against the bot, you may have the option to "undo" your last move (follow the on-screen instructions).
7.  The game continues until one player achieves three of their symbols in a row (horizontally, vertically, or diagonally), or until all cells are filled, resulting in a draw.
8.  The outcome of the game (winner or draw) will be displayed in the terminal.

## Future Enhancements (Optional)

* Implement a graphical user interface (GUI) for a more visually appealing experience.
* Add more sophisticated AI difficulty levels.
* Allow players to customize the board size.
* Implement a scoring system to track wins and losses.
* Persist game statistics.
