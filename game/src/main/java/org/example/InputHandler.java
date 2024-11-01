package org.example;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;
import java.util.Map;

public class InputHandler implements KeyListener{
    GamePanel gp;
    public boolean upPressed, downPressed, leftPressed, rightPressed, tabPressed, enterPressed, escapePressed;
    private Map<Character, Integer> customKeyCodes = new HashMap<>();

    public InputHandler(GamePanel gp){
        this.gp = gp;
    }
    @Override
    public void keyTyped(KeyEvent e) {
        // TODO Auto-generated method stub
    }

    @Override
    public void keyPressed(KeyEvent e) {

        int code = e.getKeyCode();
        if(gp.gameState == gp.titleState){
            if(code == KeyEvent.VK_W){
                gp.ui.commandNum --;
                if(gp.ui.commandNum < 0){
                    gp.ui.commandNum = 2;
                }
            }
            if(code == KeyEvent.VK_S){
                gp.ui.commandNum ++;
                if(gp.ui.commandNum > 2){
                    gp.ui.commandNum = 0;
                }
            }if(code == KeyEvent.VK_ENTER){
                switch(gp.ui.commandNum){
                    case 0:
                        gp.gameState = gp.playState;
                        break;
                    case 1:
                        gp.gameState = gp.languageState;
                        break;
                    case 2:
                        System.exit(0);
                        break;
                }
            }
        }
        else if(gp.gameState == gp.playState){
            if(tabPressed == true){
                menuManager(code);
            }else{
                if(code == KeyEvent.VK_ESCAPE){
                    gp.gameState = gp.titleState;
                }
                if(code == KeyEvent.VK_W){
                    upPressed = true;
                }
                if(code == KeyEvent.VK_S){
                    downPressed = true;
                }
        
                if(code == KeyEvent.VK_A){
                    leftPressed = true;
                }
                if(code == KeyEvent.VK_D){
                    rightPressed = true;
                }if(code == KeyEvent.VK_ENTER){
                    enterPressed = true;
                }
                if(code == KeyEvent.VK_P){
                    if(gp.gameState == gp.playState){
                        gp.gameState = gp.pauseState;
                    }else if(gp.gameState == gp.pauseState){
                        gp.gameState = gp.playState;
                    }
                }
                for (Map.Entry<Character, Integer> entry : customKeyCodes.entrySet()) {
                    if (code == entry.getValue()) {

                        gp.gameAPI.notifyOnCustomKeyPressed(entry.getKey());

                        // Custom logic for this key can be added here
                    }
                }
                
            }
            if(code == KeyEvent.VK_X){
                if(tabPressed == true){
                    tabPressed = false;
                }else{
                    tabPressed = true;
                }
                
            }
        }else if(gp.gameState == gp.dialogueState){
            if(code == KeyEvent.VK_ENTER){
                gp.gameState = gp.playState;
            }
        }else if(gp.gameState == gp.languageState){
            char c = e.getKeyChar();
    
            
            // Check for Enter key to confirm input
            if (c == KeyEvent.VK_ENTER) {
                // Process the input (e.g., change locale)
                System.out.println(gp.ui.userInput);
                gp.gameLocal.setLocale(gp.ui.userInput);
                gp.ui.userInput = ""; // Reset input after confirmation

            }
            // Handle backspace for deleting characters
            else if (c == KeyEvent.VK_BACK_SPACE && gp.ui.userInput.length() > 0) {
                gp.ui.userInput = gp.ui.userInput.substring(0, gp.ui.userInput.length() - 1);
            }
            // Only add letters, numbers, or valid characters to the input
            else if (Character.isLetterOrDigit(c) || c == '-' || c == '_') {
                gp.ui.userInput += c;
            }else if(c == KeyEvent.VK_ESCAPE){
                gp.gameState = gp.playState;
            }

        }
        if(gp.gameState == gp.victoryState||gp.gameState == gp.deathState){
            if(code == KeyEvent.VK_W){
                gp.ui.commandNum --;
                if(gp.ui.commandNum < 0){
                    gp.ui.commandNum = 1;
                }
            }
            if(code == KeyEvent.VK_S){
                gp.ui.commandNum ++;
                if(gp.ui.commandNum > 1){
                    gp.ui.commandNum = 0;
                }
            }if(code == KeyEvent.VK_ENTER){
                switch(gp.ui.commandNum){
                    case 0:
                        gp.gameState = gp.restartState;
                        break;
                   
                        
                    case 1:
                        System.exit(0);
                        break;
                }
            }
        }
    }
    public void menuManager(int code){
        
        if(code == KeyEvent.VK_W && gp.ui.slotRow != 0){
            
            gp.ui.slotRow--;
        }
        if(code == KeyEvent.VK_S && gp.ui.slotRow != 3){
            gp.ui.slotRow++;
        }

        if(code == KeyEvent.VK_A&& gp.ui.slotCol != 0){
            gp.ui.slotCol--;
        }
        if(code == KeyEvent.VK_D&& gp.ui.slotCol != 5){
            gp.ui.slotCol++;
        }
    }
    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();

        if(code == KeyEvent.VK_W){
            upPressed = false;
        }
        if(code == KeyEvent.VK_S){
            downPressed = false;
        }

        if(code == KeyEvent.VK_A){
            leftPressed = false;
        }
        if(code == KeyEvent.VK_D){
            rightPressed = false;
        }if(code == KeyEvent.VK_ENTER){

        }
        
    }
    public void addCustomKey(Character letter) {
        // Convert the character to uppercase to match KeyEvent constants (e.g., VK_A)
        char upperLetter = Character.toUpperCase(letter);
        int keyCode = KeyEvent.getExtendedKeyCodeForChar(upperLetter);

        // Store the character and its key code in the map
        customKeyCodes.put(letter, keyCode);
        System.out.println("Added custom key: " + letter + " with key code: " + keyCode);
    }

}