 import java.io.File;
 import java.net.HttpURLConnection;
 import java.net.URL;
 import java.io.*;
 import java.nio.file.Paths;
 import java.util.*;
 

/**
 * GrammarApp contains a main program that prompts a user for the name of a
 * grammar file and then gives the user the opportunity to generate random
 * versions of various elements of the grammar.
 * Test url (has been shortened): https://tinyurl.com/bgoldner4
 *
 *
 * @author: B. Goldner, Heidi Valles
 * @version 02/13/2018
 */

public class GrammarApp {
  
  static boolean fileExists = false; //stores boolean state of client's file existence
  static File checkFile; //stores instances of fileName to check if they exist
  static String fileName; //fileName entered by user
  
  /**
   * Retrieves all of the rules from a given file and stores them in an arrayList
   * @return ArrayList of rules from file given by client
   */
  public  static List<String> loadRules(String fileName) {
    Scanner s = new Scanner(fileName);
    List<String> rulesList = new ArrayList<>();
    while (s.hasNext()) {
      rulesList.add(s.next());
    }
    return rulesList;
  }
  
  /**
   * Method found online at:  http://www.rgagnon.com/javadetails/java-0059.html to see
   * if an URL exists
   * @param URLName to be checked
   * @return whether an url exists
   */
  public static boolean exists(String URLName){
    try {
      Properties systemSettings = System.getProperties();
      systemSettings.put("proxySet", "true");
      systemSettings.put("http.proxyHost","proxy.mycompany.local") ;
      systemSettings.put("http.proxyPort", "80") ;
    
      URL u = new URL(URLName);
      HttpURLConnection con = (HttpURLConnection) u.openConnection();
      //
      // it's not the greatest idea to use a sun.misc.* class
      // Sun strongly advises not to use them since they can
      // change or go away in a future release so beware.
      //
      sun.misc.BASE64Encoder encoder = new sun.misc.BASE64Encoder();
      String encodedUserPwd =
        encoder.encode("domain\\username:password".getBytes());
      con.setRequestProperty
        ("Proxy-Authorization", "Basic " + encodedUserPwd);
      con.setRequestMethod("HEAD");
      System.out.println
        (con.getResponseCode() + " : " + con.getResponseMessage());
      return (con.getResponseCode() == HttpURLConnection.HTTP_OK);
    }
    catch (Exception e) {
      e.printStackTrace();
      return false;
    }
  }
  
   public static void main(String[] args) throws IOException {
     Scanner keyboard = new Scanner(System.in);
     //ask for new user input if file is not found
     do {
       fileName = intro(keyboard);
       //checks to see if file name given by client is a local file or a web address
       if (fileName.contains("http:") || fileName.contains("https:")) {
         System.out.println("Found URL. Checking for validity");
         URL oracle = new URL(fileName);
         String sOracle = oracle.toString();
         System.out.println(exists(fileName));
         if (exists(sOracle)) {
           checkFile = new File(oracle.getFile());
           BufferedReader in = new BufferedReader(
             new InputStreamReader(oracle.openStream()));
           String inputLine;
           while ((inputLine = in.readLine()) != null)
             System.out.println(inputLine);
           in.close();
         }
       }
         else {
           checkFile = new File(fileName);
         }
         if (checkFile.exists()) {
           fileExists = true;
         } else {
           System.out.println("File not found, please enter valid file name: ");
         }
       
     }
     while (!fileExists);
   
       
    
       // run through generating grammar if file is found
       do {
         System.out.println("File has been found");
         List<String> grammar = loadRules(fileName);
         // construct the grammar generator
         GrammarGenerator gen =
           new GrammarGenerator(grammar);
         // interact with user to generate expressions from the loaded grammar
         String target = getSymbol(keyboard, gen);
         while (target.length() != 0) {
           int count = getCount(keyboard);
           String[] answers = gen.generate(target, count);
           System.out.println("****************************************");
           for (int i = 0; i < count; i++) {
             System.out.println(answers[i]);
           }
           target = getSymbol(keyboard, gen);
         }
       }
       while (fileExists);
     }
   
  
    /**
     * Display introduction and prompt for grammar filename
     * @return filename from user
     */
  
  public static String intro(Scanner input) {
    System.out.println("Welcome to the random elements generator.");
    System.out.println();
    
    // open grammar file and read it in
    System.out.print("What is the name of the grammar file? ");
    return input.nextLine();
  }
  
  /**
   * Prompts user for symbol from GrammarGenerator
   *
   * @param in  source of input
   * @param gg current GrammarGenerator of rules
   * @return either retuns a valid symbol from GrammarGenerator, or string of length 0
   * to indicate the user wants to quit
   */
  public static String getSymbol(Scanner in, GrammarGenerator gg) {
    printPrompt(gg);
    
    System.out.print("What do you want generated (return to quit)? ");
    String target = in.nextLine();
    while (target.length() != 0 && !gg.contains(target)) {
      System.out.println("Illegal symbol. Please try again\n");
      System.out.print("What do you want generated (return to quit)? ");
      target = in.nextLine();
    }
    return target;
  }
  
  /**
   * Prints the list of possible symbols from gen
   *
   * @param gen current set of rules
   */
  public static void printPrompt(GrammarGenerator gen) {
    System.out.println();
    System.out.println("Available symbols to generate are:");
    System.out.println(gen.getSymbols());
  }
  
  /**
   * Asks use for number of examples to generate. Performs input validation
   *
   * @param in the source of user input
   * @return the valid input >= 0
   */
  public static int getCount(Scanner in) {
    int userCount = -1;  // flag value until have legitimate input
  
    while (userCount == -1) {
      System.out.print("How many do you want me to generate? ");
      if (!in.hasNextInt()) {
        System.out.println("that's not an integer");
        System.out.println();
        in.nextLine();  // consume the garbage from the input buffer
      } else {
        // read in the number and see if it is legit
        userCount = in.nextInt();
        in.nextLine();  // consume whitespace left over from the integer input
        if (userCount <= 0) {
          System.out.println("must be positive input");
          System.out.println();
          userCount = -1;
        }
      }
    }
    return userCount;
  }
}
