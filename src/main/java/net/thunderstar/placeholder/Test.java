package net.thunderstar.placeholder;

import net.thunderstar.placeholder.entities.Player;
import net.thunderstar.placeholder.entities.one_to_ninety_nine.Entity_1;
import net.thunderstar.placeholder.render.RenderMain;
import net.thunderstar.placeholder.render.util.RenderFunctions;
import net.thunderstar.placeholder.render.util.RenderHelpFunctions;
import net.thunderstar.placeholder.sound.SoundMain;
import net.thunderstar.placeholder.util.Levels;

import java.awt.*;
import java.nio.ByteBuffer;

public class Test {
    public static void main(String[] args) {
        Levels.updatePortals();
        RenderFunctions.Game.portalRender();
    }
}
