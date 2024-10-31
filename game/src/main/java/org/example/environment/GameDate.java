package org.example.environment;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;

public class GameDate {
    private LocalDate currentDate;
    private Locale currentLocale;
    private GameLocalization gameLocal;
    public GameDate(GameLocalization gameLocal) {
        this.currentDate = LocalDate.now(); // Start with the current date
        this.gameLocal = gameLocal;
        this.currentLocale = gameLocal.getCurrentLocal();
    }

    // Method to format the date according to the current locale
    public String getFormattedDate() {
        this.currentLocale = gameLocal.getCurrentLocal();

        DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG).withLocale(currentLocale);
        return currentDate.format(formatter); // Format date according to locale
    }

    public void advanceDay() {
        currentDate = currentDate.plusDays(1); // Move to the next day
    }

    public void updateLocale(Locale newLocale) {
        this.currentLocale = newLocale; // Update the locale
    }
}
