PARSER_BEGIN(MySimpleParser)
package org.example;

import java.util.HashMap; // Import HashMap for storing obstacles
import java.util.Map;

public class MySimpleParser {
    private GameConfig gameConfig; // Instance variable to hold GameConfig

    public MySimpleParser() {
        // Initialize the GameConfig instance here (if needed)
        gameConfig = new GameConfig();
    }

    // Other methods...

    // Method to parse the full configuration
    void parseGameConfig() :
    {}
    {
        sizeConfig() startConfig() goalConfig() (obstacleConfig() | itemConfig() | pluginConfig() | scriptConfig())* <EOF>
        {
            // Use 'gameConfig' as needed
        }
    }

    // Other methods...
}
