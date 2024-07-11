package net.thunderstar.placeholder.render;

import net.thunderstar.placeholder.entities.Player;
import net.thunderstar.placeholder.util.Images;
import net.thunderstar.placeholder.util.Inventory;
import net.thunderstar.placeholder.util.Reference;
import net.thunderstar.placeholder.render.util.RenderFunctions;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.Timer;

public class RenderMain {
    public void run() {
        Reference.gameFrame.setVisible(true);
        Reference.invFrame.setVisible(true);
        //Reference.fpsFrame.setVisible(true);

        Reference.gameFrame.setSize(Reference.gameFrameSize);
        Reference.invFrame.setSize(Reference.invFrameSize);

        Reference.gameFrame.setResizable(false);
        Reference.invFrame.setResizable(false);
        //Reference.fpsFrame.setResizable(false);

        Reference.gameFrame.setLocation(Reference.gameFramePos);
        Reference.invFrame.setLocation(Reference.invFramePos);

        Reference.gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Reference.invFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Reference.gameFrame.requestFocus();

        Inventory.shouldUpdate = true;

        loop();
    }

    private void loop() {
        final boolean[] canScreenshot = {false};

        JLabel frame1 = new JLabel();
        frame1.setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));

        JLabel frame2 = new JLabel();

        //JLabel fpsLabel = new JLabel();

        final BufferedImage[] gameFrameBuffer = {new BufferedImage(320, 240, BufferedImage.TYPE_INT_RGB)};
        final BufferedImage[] invFrameBuffer = {new BufferedImage(320, 240, BufferedImage.TYPE_INT_RGB)};

        //final double[] fps = {0};
        //final double[] deltaTime = {1};
        //final double[] oldTime = {System.nanoTime() / 1e9};
        //final double[] newTime = {System.nanoTime() / 1e9};

        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                // Player.variables.posX = (float) (1e3 * Math.tan(System.nanoTime()*1e-9));

                //RenderFunctions.Game.render();
                RenderFunctions.Game.portalRender();

                //newTime[0] = System.nanoTime()/1e9;
                //deltaTime[0] = newTime[0] - oldTime[0];
                //if (deltaTime[0] != 0) {
                //    fps[0] = 1/ deltaTime[0];
                //}
                //oldTime[0] = newTime[0];

                gameFrameBuffer[0] = gameFrameRenderer();
                frame1.setIcon(new ImageIcon(gameFrameBuffer[0].getScaledInstance(Reference.gameFrameSize.width-16, Reference.gameFrameSize.height-39, Image.SCALE_FAST)));
                Reference.gameFrame.add(frame1);

                //fpsLabel.setText(String.valueOf(Math.round(10* fps[0])/10.0));
                //Reference.fpsFrame.add(fpsLabel);

                Reference.gameFrame.setLocation(Reference.gameFramePos);

                Reference.gameFrame.pack();
                //Reference.fpsFrame.pack();

                if (Inventory.shouldUpdate) {
                    //RenderFunctions.Inv.color(new Color(Color.HSBtoRGB(Player.variables.rotation/360, 1, 1)));

                    invFrameBuffer[0] = invFrameRenderer();
                    frame2.setIcon(new ImageIcon(invFrameBuffer[0].getScaledInstance(Reference.invFrameSize.width - 16, Reference.invFrameSize.height - 39, Image.SCALE_FAST)));
                    //frame2.setIcon(new ImageIcon(Images.INV_TEMPLATE.getImage().getScaledInstance(Reference.invFrameSize.width - 16, Reference.invFrameSize.height - 39, Image.SCALE_FAST)));
                    Reference.invFrame.add(frame2);

                    Reference.invFrame.setLocation(Reference.invFramePos);

                    Reference.invFrame.pack();

                    Inventory.shouldUpdate = false;
                }

                if (canScreenshot[0]) {
                    if (Reference.pressedKeys.screenshotKey) {
                        Date date = new Date();
                        Calendar calendar = Calendar.getInstance();
                        calendar.setTime(date);

                        int year = calendar.get(Calendar.YEAR);
                        int month = calendar.get(Calendar.MONTH);
                        int day = calendar.get(Calendar.DAY_OF_MONTH);
                        int hour = calendar.get(Calendar.HOUR_OF_DAY);
                        int minute = calendar.get(Calendar.MINUTE);
                        int second = calendar.get(Calendar.SECOND);

                        saveScreenshot(gameFrameBuffer[0], "screenshot_game_" + year + "_" + month + "_" + day + "_" + hour + "_" + minute + "_" + second);
                        saveScreenshot(invFrameBuffer[0], "screenshot_inv_" + year + "_" + month + "_" + day + "_" + hour + "_" + minute + "_" + second);

                        canScreenshot[0] = false;
                    }
                } else {
                    if (!Reference.pressedKeys.screenshotKey) {
                        canScreenshot[0] = true;
                    }
                }
            }
        };

        Timer timer = new Timer();

        long intervalPeriod = 40;

        timer.scheduleAtFixedRate(task, 0, intervalPeriod);
    }

    public BufferedImage gameFrameRenderer() {
        BufferedImage image = new BufferedImage(320, 240, BufferedImage.TYPE_INT_RGB);
        int[] rgbArray = new int[320 * 240];

        for (int Y = 0; Y < 240; Y++) {
            for (int X = 0; X < 320; X++) {
                Color color = Reference.gameColors[X][Y];
                if (color != null) {
                    rgbArray[Y * 320 + X] = color.getRGB();
                } else {
                    rgbArray[Y * 320 + X] = 0;
                }
            }
        }

        image.setRGB(0, 0, 320, 240, rgbArray, 0, 320);

        return image;
    }

    public BufferedImage invFrameRenderer() {
        BufferedImage image = new BufferedImage(320, 240, BufferedImage.TYPE_INT_RGB);
        int[] rgbArray = new int[320 * 240];

        for (int Y = 0; Y < 240; Y++) {
            for (int X = 0; X < 320; X++) {
                Color color = Reference.invColors[X][Y];
                if (color != null) {
                    rgbArray[Y * 320 + X] = color.getRGB();
                } else {
                    rgbArray[Y * 320 + X] = 0;
                }
            }
        }

        image.setRGB(0, 0, 320, 240, rgbArray, 0, 320);

        return image;
    }


    public void saveScreenshot(BufferedImage image, String file_name) {
        try {
            ImageIO.write(image, "png", new File(Images.defaultPath + "screenshots\\" + file_name + ".png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
