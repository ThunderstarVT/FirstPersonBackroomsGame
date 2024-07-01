package net.thunderstar.placeholder.sim;

import net.thunderstar.placeholder.entities.Player;
import net.thunderstar.placeholder.util.Reference;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.Timer;
import java.util.TimerTask;

public class SimMain {
    public void run() throws AWTException {
        Reference.gameFrame.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == Reference.forwardKey) {
                    Reference.pressedKeys.forwardKey = true;
                }
                if (e.getKeyCode() == Reference.backwardKey) {
                    Reference.pressedKeys.backwardKey = true;
                }
                if (e.getKeyCode() == Reference.moveLeftKey) {
                    Reference.pressedKeys.moveLeftKey = true;
                }
                if (e.getKeyCode() == Reference.moveRightKey) {
                    Reference.pressedKeys.moveRightKey = true;
                }
                if (e.getKeyCode() == Reference.jumpKey) {
                    Reference.pressedKeys.jumpKey = true;
                }
                if (e.getKeyCode() == Reference.sprintKey) {
                    Reference.pressedKeys.sprintKey = true;
                }
                if (e.getKeyCode() == Reference.upKey) {
                    Reference.pressedKeys.upKey = true;
                }
                if (e.getKeyCode() == Reference.downKey) {
                    Reference.pressedKeys.downKey = true;
                }
                if (e.getKeyCode() == Reference.leftKey) {
                    Reference.pressedKeys.leftKey = true;
                }
                if (e.getKeyCode() == Reference.rightKey) {
                    Reference.pressedKeys.rightKey = true;
                }

                if (e.getKeyCode() == Reference.screenshotKey) {
                    Reference.pressedKeys.screenshotKey = true;
                }
            }

            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == Reference.forwardKey) {
                    Reference.pressedKeys.forwardKey = false;
                }
                if (e.getKeyCode() == Reference.backwardKey) {
                    Reference.pressedKeys.backwardKey = false;
                }
                if (e.getKeyCode() == Reference.moveLeftKey) {
                    Reference.pressedKeys.moveLeftKey = false;
                }
                if (e.getKeyCode() == Reference.moveRightKey) {
                    Reference.pressedKeys.moveRightKey = false;
                }
                if (e.getKeyCode() == Reference.jumpKey) {
                    Reference.pressedKeys.jumpKey = false;
                }
                if (e.getKeyCode() == Reference.sprintKey) {
                    Reference.pressedKeys.sprintKey = false;
                }
                if (e.getKeyCode() == Reference.upKey) {
                    Reference.pressedKeys.upKey = false;
                }
                if (e.getKeyCode() == Reference.downKey) {
                    Reference.pressedKeys.downKey = false;
                }
                if (e.getKeyCode() == Reference.leftKey) {
                    Reference.pressedKeys.leftKey = false;
                }
                if (e.getKeyCode() == Reference.rightKey) {
                    Reference.pressedKeys.rightKey = false;
                }

                if (e.getKeyCode() == Reference.screenshotKey) {
                    Reference.pressedKeys.screenshotKey = false;
                }
            }
        });

        Reference.gameFrame.addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseMoved(MouseEvent e) {
                Player.variables.mouseX = e.getX();
            }
        });

        loop();
    }

    private void loop() throws AWTException {
        Robot mouseMover = new Robot();

        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                if (Reference.gameFrame.isFocusOwner()) {
                    Player.variables.rotation += (Player.variables.mouseX - (float) (Reference.gameFrameSize.width)/2) * Player.constants.rotate_factor;
                    Player.variables.rotation %= 360;
                    mouseMover.mouseMove(
                            Reference.gameFramePos.x + (Reference.gameFrameSize.width)/2,
                            Reference.gameFramePos.y + (Reference.gameFrameSize.height)/2);
                }
            }
        };

        Timer timer = new Timer();

        long intervalPeriod = 4;

        timer.scheduleAtFixedRate(task, 0, intervalPeriod);
    }
}
