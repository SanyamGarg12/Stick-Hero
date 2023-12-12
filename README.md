# Stick-Hero
Shreyansh Srivastav 2022485
Sanyam Garg 2022448

//Description of the game
Stick Hero is a game where the player has to cross the gap between two platforms by creating a stick.
The stick should be long enough to cross the gap.
If the stick is too short or too long, the player will fall down.

//Design Patterns used
1. Singleton Pattern - The singleton pattern is a design pattern that restricts the instantiation of a class to one object.
Usage - We used it for Hero class as we want only one hero in the game.
2. Decorator Pattern
Usage - We used it for Input Output stuff.

Note - On the Top left of screen, There is high score printed!.

Basic OOps concepts used -
1. Inheritance - the Stick class inherits the properties of the Rectangle class.
2. Polymorphism - the Hero class has a method called equals which overrides the equals method of the Object class.


## How to run the code

**ONLY WORKS USING MAVEN**


1.Open the terminal and run the following command:
a> cd .\stick_hero (basically i want u to enter in folder of pom.xml file)
b> mvn clean install compile
c> mvn javafx:run
