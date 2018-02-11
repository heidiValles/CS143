import java.util.List;
import java.util.TreeMap;

/**
 * Supporting class for GrammarApp that will generate different grammars according to
 * client needs.
 * @author Heidi Valles
 * @version 02/13/2018
 */
public class GrammarGenerator extends TreeMap {
  
  /**
   * Constructor is passed a grammar as a List of Strings and stores the List so as to
   * later generate parts of the grammar
   * @param Grammar in the file that client submits to program
   * @throws IllegalArgumentException if there are 2+ entries in the grammar for the
   * same non-terminal
   */
  public GrammarGenerator(List<String> Grammar){
  
  }
  
  /**
   * Checks to see if the given symbol is a non-terminal of the grammar
   * @param symbol given by client to check for non-terminal/terminal status
   * @return whether symbol is non-terminal or not
   */
  public boolean contains(String symbol){
    return true;
  }
  
  /**
   * Will randomly generate tho given number of occurrences of the given symbol.
   * @param symbol which will be repeated by a client-specified amount of times
   * @param times that the symbol will be repeated
   * @return String that will contain the symbol x times
   */
  public String[] generate(String symbol, int times){
    String[] generateSymbols = new String[times];
    return generateSymbols;
  }
  
  /**
   * Gets the non-terminal symbols in a file and returns them as a string
   * @return String representation of non-terminal symbols in a file
   */
  public String getSymbols(){
  return "a";
  }
}
