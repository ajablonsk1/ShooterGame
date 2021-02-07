package main;

import java.awt.*;
import java.io.IOException;

public class GameLauncher {

    public GameLauncher() throws IOException, FontFormatException {
        new Window();
    }

    public static void main(String[] args) throws IOException, FontFormatException {
	    new GameLauncher();
    }
}
