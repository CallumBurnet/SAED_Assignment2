package org.engine;

import org.python.util.PythonInterpreter;

public class ScriptHandler {
    private GameAPI api;

    public ScriptHandler(GameAPI api) {
        this.api = api;
    }

    public void runScript(String pythonScript) {
        PythonInterpreter interpreter = new PythonInterpreter();
        interpreter.set("api", api); // Bind API
        
        // Execute script code
        interpreter.exec(pythonScript);
        interpreter.close();
    }
}
