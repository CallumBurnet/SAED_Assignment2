package org.example;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class InputHandler implements KeyListener{
    GamePanel gp;
    public boolean upPressed, downPressed, leftPressed, rightPressed, tabPressed, enterPressed;
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
                        gp.gameState = gp.playState;
                        break;
                    case 1:
                        System.exit(0);
                        break;
                }
            }
        }
        else if(gp.gameState == gp.playState){
            if(tabPressed == true){
                menuManager(code);
            }else{
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
                
            }
            if(code == KeyEvent.VK_X){
                if(tabPressed == true){
                    tabPressed = false;
                }else{
                    tabPressed = true;
                    System.out.println("TABBY");
                }
                
            }
        }else if(gp.gameState == gp.dialogueState){
            if(code == KeyEvent.VK_ENTER){
                gp.gameState = gp.playState;
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
        }
        
    }

}