package org.example.environment;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Locale;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

public class UTF8Control extends ResourceBundle.Control {
    @Override
    public ResourceBundle newBundle(String baseName, Locale locale, String format, ClassLoader loader, boolean reload) throws IOException {
        String bundleName = toBundleName(baseName, locale);
        System.out.println("BUNDLE NAME" + bundleName);

        // Attempt to load with .utf16.map extension first
        String resourceName = toResourceName(bundleName, "utf16.map");
        InputStream stream = loader.getResourceAsStream(resourceName);

        Charset charset = StandardCharsets.UTF_16;

        // If .utf16.map file is not found, fall back to .utf8.map
        if (stream == null) {
            System.out.println("Trying UTF-8 map file.");
            resourceName = toResourceName(bundleName, "utf8.map");
            stream = loader.getResourceAsStream(resourceName);
            charset = StandardCharsets.UTF_8;
        }

        // Load the stream with the detected encoding
        if (stream != null) {
            System.out.println("Loading resource with encoding: " + charset);
            try (InputStreamReader reader = new InputStreamReader(stream, charset)) {
                return new PropertyResourceBundle(reader);
            }
        }

        // Return null if no .utf16.map or .utf8.map resource is found
        return null;
    }
}
