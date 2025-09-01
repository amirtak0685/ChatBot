/* ***************************************
  @author    Amirali Takian
  @date      4 November 2024
  @version   3

    A chat bot that introduces itself to the user, asks for their name and greets them, and has a conversation with them about their favourite film series. The bot's answers differ based on the user's favourite film series.
   ****************************************/

   import java.util.*;
   import java.io.*;
   
   class filmBot {
   
       public static void main(String[] a) throws IOException {
           bot();
       }
       //END main
   
       //Creates a new file in the user's name
       public static File createFile(String name) throws IOException {
           File myFile = new File(name + ".txt");
           if (myFile.exists()) {
               System.out.println("It exists");
           } else {
               PrintWriter outputStream = new PrintWriter(new FileWriter(name + ".txt"));
               outputStream.println("I created it");
               outputStream.close(); // ✅ close writer
               System.out.println("File created: " + name + ".txt");
           }
           return myFile;
       }
       //END createFile
   
       //Adds content to the file
       public static void writeToFile(File file, String content) throws IOException {
           FileWriter writer = new FileWriter(file, true);
           writer.write(content + "\n");
           writer.close();
       }
       //END writeToFile
   
       //Searches the file for 3 specific strings
       public static boolean fileSearch(File file, String firstSearch, String secondSearch, String thirdSearch) throws IOException {
           boolean found = false;
           BufferedReader reader = new BufferedReader(new FileReader(file));
           String line;
           while ((line = reader.readLine()) != null) {
               if (line.contains(firstSearch) || line.contains(secondSearch) || line.contains(thirdSearch)) {
                   found = true;
                   break;
               }
           }
           reader.close();
           return found;
       }
       //END fileSearch
   
       public static answers setKnown(answers a, String[] answers) {
           a.knownAnswers = answers;
           return a;
       }
   
       public static String[] getKnown(answers a) {
           return a.knownAnswers;
       }
   
       public static answers setQuestions(answers a, String[] questions) {
           a.questions = questions;
           return a;
       }
   
       public static String[] getQuestions(answers a) {
           return a.questions;
       }
   
       public static answers setActor(answers a, String[] actors) {
           a.actorQuestions = actors;
           return a;
       }
   
       public static String[] getActor(answers a) {
           return a.actorQuestions;
       }
   
       public static answers setScene(answers a, String[] scene) {
           a.sceneQuestions = scene;
           return a;
       }
   
       public static String[] getScene(answers a) {
           return a.sceneQuestions;
       }
   
       public static answers setVillain(answers a, String[] villain) {
           a.villainQuestions = villain;
           return a;
       }
   
       public static String[] getVillain(answers a) {
           return a.villainQuestions;
       }
   
       public static answers setAsked(answers a, boolean asked) {
           a.asked = asked;
           return a;
       }
   
       public static boolean getAsked(answers a) {
           return a.asked;
       }
   
       public static answers setName(answers a, String name) {
           a.name = name;
           return a;
       }
   
       public static String getName(answers a) {
           return a.name;
       }
   
       //Asks user for string input along with a message
       public static String inputString(String message) {
           final Scanner scanner = new Scanner(System.in);
           System.out.println(message);
           return scanner.nextLine();
       }
   
       //Intro
       public static void intro(answers answers) throws IOException {
           final String name = inputString("Hello! My name is cinema-mad, and as you can probably guess, I love watching films. What's your name?");
           System.out.println("Nice to meet you " + name + "! I like all kinds of movies, but my two favourite genres are comedy and mystery.");
           setName(answers, name);
           File file = createFile(name);
           repeatedQuestions(answers, file);
       }
   
       //Creates answers record
       public static answers createAnswers() {
           answers answers = new answers();
           setKnown(answers, new String[]{"Avengers", "AVENGERS", "avengers", "Batman", "BATMAN", "batman"});
           setQuestions(answers, new String[]{
               "What is your favourite scene? ",
               "Who is you favourite actor throughout the movies? ",
               "Who is your favourite marvel villain? "
           });
           setActor(answers, new String[]{
               "Your actor choice is also great! Marvel really picked some great actors for their films. ",
               "I also agree with the actor choice! Marvel would have been very different if another actor had played that role. "
           });
           setVillain(answers, new String[]{
               "You've chosen a great villain too! Although villains are supposed to be bad, I kind of agree with some of their points. ",
               "But really? That villain? The whole city nearly got destroyed because of them. "
           });
           setScene(answers, new String[]{
               "Your scene choice is very good! I really commend the writers for how they made it happen. ",
               "That's a good choice for favourite scene! The creators really created a masterpiece with that scene. "
           });
           setAsked(answers, false);
           return answers;
       }
   
       //Comment based on film series
       public static String comment(String series) {
           answers answers = createAnswers();
           String[] knownAnswers = getKnown(answers);
           if (series.equals(knownAnswers[0]) || series.equals(knownAnswers[1]) || series.equals(knownAnswers[2])) {
               return "Mine too! My favourite part from all the movies is where Iron Man snaps his fingers in Endgame.";
           } else if (series.equals(knownAnswers[3]) || series.equals(knownAnswers[4]) || series.equals(knownAnswers[5])) {
               return "That's a nice one, but too many Batman movies have the same story.";
           } else {
               return "I haven't watched that yet, but I definitely will very soon!";
           }
       }
   
       //Ask about favourite film series
       public static String[] favourite(answers answers, File file) throws IOException {
           String[] data = {"", "", ""};
           String answer;
           String[] knownAnswers = getKnown(answers);
           String[] questions = getQuestions(answers);
           String series = inputString("What's your favourite film series? ");
           String favourite = comment(series);
           boolean search = fileSearch(file, knownAnswers[0], knownAnswers[1], knownAnswers[2]);
           writeToFile(file, series);
   
           if (favourite.startsWith("Mine too!")) {
               if (search) {
                   System.out.println("We already talked about Avengers. Please choose another movie.");
               } else {
                   System.out.println(favourite);
                   for (int i = 0; i < 3; i++) {
                       answer = inputString(questions[i]);
                       data[i] = answer;
                       writeToFile(file, answer);
                   }
                   System.out.println("Answers successfully written to the file.");
               }
           } else {
               System.out.println(favourite);
           }
           return data;
       }
   
       //Ask about repeated favourites
       public static boolean repeatedQuestions(answers answers, File file) throws IOException {
           boolean asked = firstAnswer(answers, file);
           String[] data = {"", "", ""};
           String[] questions = getQuestions(answers);
           String[] knownAnswers = getKnown(answers);
           String questionAnswer = "";
           String filmAnswer = "";
   
           while (!filmAnswer.equalsIgnoreCase("no")) {
               filmAnswer = inputString("Do you have another favourite? ");
               boolean search = fileSearch(file, knownAnswers[0], knownAnswers[1], knownAnswers[2]);
               writeToFile(file, filmAnswer);
   
               if (filmAnswer.equalsIgnoreCase("no")) {
                   System.out.println("It was fun talking movies with you, and I hope to do it again very soon. Goodbye for now!");
               } else if (filmAnswer.equalsIgnoreCase("avengers")) {
                   if (search) {
                       System.out.println("We already talked about Avengers. Please choose another movie.");
                   } else {
                       asked = true;
                       String comment = comment(filmAnswer);
                       System.out.println(comment);
                       for (int i = 0; i < 3; i++) {
                           questionAnswer = inputString(questions[i]);
                           data[i] = questionAnswer;
                           writeToFile(file, questionAnswer);
                       }
                       moreAnswers(data, answers, file);
                   }
               } else {
                   String comment = comment(filmAnswer);
                   System.out.println(comment);
               }
           }
           return asked;
       }
   
       //Prints random comments when Avengers is first answer
       public static boolean firstAnswer(answers answers, File file) throws IOException {
           Random random = new Random();
           String[] sceneQuestions = getScene(answers);
           String[] actorQuestions = getActor(answers);
           String[] villainQuestions = getVillain(answers);
           String[] firstAnswer = favourite(answers, file);
   
           if (!"".equals(firstAnswer[0])) {
               setAsked(answers, true);
               int index = random.nextInt(sceneQuestions.length);
               System.out.println(sceneQuestions[index]);
               index = random.nextInt(actorQuestions.length);
               System.out.println(actorQuestions[index]);
               index = random.nextInt(villainQuestions.length);
               System.out.println(villainQuestions[index]);
           }
           return getAsked(answers);
       }
   
       //Prints random comments when Avengers is not first answer
       public static boolean moreAnswers(String[] data, answers answers, File file) {
           Random random = new Random();
           String[] sceneQuestions = getScene(answers);
           String[] actorQuestions = getActor(answers);
           String[] villainQuestions = getVillain(answers);
   
           if (!"".equals(data[0])) {
               setAsked(answers, true);
               int index = random.nextInt(sceneQuestions.length);
               System.out.println(sceneQuestions[index]);
               index = random.nextInt(actorQuestions.length);
               System.out.println(actorQuestions[index]);
               index = random.nextInt(villainQuestions.length);
               System.out.println(villainQuestions[index]);
           }
           return getAsked(answers);
       }
   
       //Runs the program
       public static void bot() throws IOException {
           answers answers = createAnswers();
           intro(answers);
       }
   
   } // END filmBot