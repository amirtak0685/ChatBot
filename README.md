# Cinema-Mad Chatbot

**Author:** Amirali Takian  
**Date:** 4 November 2024  
**Version:** 3  

---

## Overview

Cinema-Mad is a Java-based interactive chatbot that talks to users about their favorite film series. The bot:  

- Introduces itself and asks the user’s name.  
- Greets the user personally.  
- Asks about the user’s favorite film series and responds differently depending on the series.  
- Asks follow-up questions for recognized series like *Avengers*.  
- Saves user responses in a text file named after the user.  

This chatbot demonstrates Java concepts including classes, objects, file I/O, arrays, user input, and basic control flow.

---

## Features

1. **Personalized Greeting:** The bot asks for the user’s name and greets them.  
2. **Dynamic Responses:** Replies change depending on the user’s favorite film series.  
3. **Follow-Up Questions:** For series like *Avengers*, the bot asks additional questions about scenes, actors, and villains.  
4. **File Storage:** All user answers are saved in a `.txt` file named after the user.  
5. **Repeated Questions:** Users can continue providing favorite series until they type “no”.  

---

## Requirements

- Java JDK 8 or higher  
- Command-line terminal or Java IDE (e.g., IntelliJ IDEA, Eclipse)  

---


## Setup and Compilation

1. Make sure both `answers.java` and `filmBot.java` are in the **same folder**.  

2. Open a terminal in that folder.  

3. Compile both files using `javac`:

```bash
javac answers.java filmBot.java

java filmBot.java
