package net.thunderstar.placeholder;

import net.thunderstar.placeholder.entities.Player;
import net.thunderstar.placeholder.render.util.RenderHelpFunctions;

import java.awt.*;

public class Test {
    public static void main(String[] args) {
        System.out.println("start");
        System.out.println(RenderHelpFunctions.checkInPolygon(Player.variables.current_level.l_sectors[0].s_vertices, new Point((int) Player.variables.posX, (int) Player.variables.posY)));
        System.out.println("end");
    }
}
