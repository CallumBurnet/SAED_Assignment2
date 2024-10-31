package org.example.environment;

import org.example.GamePanel;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class GameLocalization {
    private ResourceBundle bundle;
    private Locale currentLocale;
    private GamePanel gamePanel;
    public GameLocalization(GamePanel gamePanel) {
        this.currentLocale = Locale.getDefault(); // Start with the system default locale
        this.gamePanel = gamePanel;
        gamePanel.gameDate = new GameDate(this);
        loadBundle(); // Load the bundle initially
    }

    private void loadBundle() {
        // Load the ResourceBundle with UTF-8 encoding support
        System.out.println("Loading bundle for locale: " + currentLocale);

        bundle = ResourceBundle.getBundle("MessageBundle/Bundle", currentLocale, new UTF8Control());
    }
    public Locale getCurrentLocal(){
        return currentLocale;
    }


    public String getText(String key) {
    
        return bundle.getString(key); // Retrieve text for the given key

        
    }

    public void setLocale(String languageTag) {
        System.out.println("Switching language to tag: " + languageTag);

        // Ensure the language tag has a minimum length
        if (languageTag.length() < 2) {
            System.out.println("Invalid language tag. Please provide a longer tag.");
            return;
        }

        this.currentLocale = Locale.forLanguageTag(languageTag);

        try {
            // Test if the new locale resources are available
            ResourceBundle testBundle = ResourceBundle.getBundle("MessageBundle/bundle", currentLocale, new UTF8Control());

            if (!testBundle.getLocale().equals(currentLocale)) {
                System.out.println("Requested locale not found. Falling back to " + testBundle.getLocale());
            } else {
                System.out.println("Locale successfully changed to " + currentLocale);
            }

            // Update the main bundle to the requested locale or fallback
            loadBundle();

        } catch (MissingResourceException e) {
            System.out.println("Invalid language tag or resources unavailable. Reverting to default.");
            this.currentLocale = Locale.getDefault();
            loadBundle();
        }
    }
}
