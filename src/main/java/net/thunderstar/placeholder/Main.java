package net.thunderstar.placeholder;

import net.thunderstar.placeholder.render.RenderMain;
import net.thunderstar.placeholder.sim.SimMain;
import net.thunderstar.placeholder.sound.SoundMain;
import net.thunderstar.placeholder.util.GenerateResources;
import net.thunderstar.placeholder.util.Levels;

import java.awt.*;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        try {
            GenerateResources.readFiles();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        GenerateResources.generateBSPTrees();

        Levels.updatePortals();

        Thread renderThread = new Thread(() -> {
            new RenderMain().run();
        });

        Thread soundThread = new Thread(() -> {
            new SoundMain().run();
        });

        Thread simThread = new Thread(() -> {
            try {
                new SimMain().run();
            } catch (AWTException e) {
                throw new RuntimeException(e);
            }
        });

        simThread.start();
        renderThread.start();
        soundThread.start();
    }
}

