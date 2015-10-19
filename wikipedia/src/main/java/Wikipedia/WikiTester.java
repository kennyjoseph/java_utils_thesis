package Wikipedia;

import de.tudarmstadt.ukp.wikipedia.api.*;
import de.tudarmstadt.ukp.wikipedia.api.exception.WikiApiException;

/**
 * Tutorial 1a
 *
 * Get the text of a wikipedia article.
 * The text will be formatted with MediaWiki markup.
 *
 * Throws an exception, if no page with the given title exists.
 *
 * @author zesch
 *
 */
public class WikiTester implements WikiConstants {

    public static void main(String[] args) throws WikiApiException {

        // configure the database connection parameters
        DatabaseConfiguration dbConfig = new DatabaseConfiguration();
        dbConfig.setHost("foundation.casos.cs.cmu.edu");
        dbConfig.setDatabase("wiki");
        dbConfig.setUser("kennyjoseph");
        dbConfig.setPassword("stanley57");
        dbConfig.setLanguage(Language.english);

        // Create a new German wikipedia.
        Wikipedia wiki = new Wikipedia(dbConfig);

        // Get the page with title "Hello world".
        // May throw an exception, if the page does not exist.
        Page page = wiki.getPage("poc");
        System.out.println(page.getPlainText());
        for (Category c : page.getCategories()){
            System.out.println(c.getTitle());
        }

    }
}