package main.Maps;

import main.Graphics.ImageHandler;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;

public class MapManager {

    public static ArrayList<Map> maps;

    public MapManager(){
        maps = new ArrayList<>();
    }

    public MapManager(String path){
        maps = new ArrayList<>();
        addMap(path);
    }

    private void addMap(String path){
        String imagePath;

        int layer = 0;
        ImageHandler imageHandler;

        String[] layers = new String[10];

        try{
            DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder= builderFactory.newDocumentBuilder();
            File file = new File(getClass().getClassLoader().getResource(path).toURI());
            Document doc = builder.parse(new File(getClass().getClassLoader().getResource(path).toURI()));
            doc.getDocumentElement().normalize();

            NodeList list = doc.getElementsByTagName("tileset");
            Node node = list.item(0);
            Element element= (Element) node;

            imagePath = element.getAttribute("name");

            list = doc.getElementsByTagName("layer");
            layer = list.getLength();

            imageHandler = new ImageHandler("main/Graphics/TileMap/" + imagePath + ".png", 64, 64);

            for(int i = 0; i < layer; i++){
                int width;
                int height;
                node = list.item(i);
                element = (Element) node;
                width = Integer.parseInt(element.getAttribute("width"));
                height = Integer.parseInt(element.getAttribute("height"));
                layers[i] = element.getElementsByTagName("data").item(0).getTextContent();
                if(i == 0) {
                    maps.add(new MapNorm(layers[i], imageHandler, 20, 12, 64, 64, 27));
                }

                if(i == 0 || i ==2){
                    maps.add(new MapNorm(layers[i], imageHandler, width, height, 64, 64, 27));
                }
                else{
                    maps.add(new MapObj(layers[i], imageHandler, width, height, 64, 64, 27));
                }
            }
        }
        catch(Exception e){
            System.out.println("Error: Cannot read tilemap");
        }
    }

    public void render(Graphics2D g){
        for(int i = 0; i < maps.size(); i++){
            maps.get(i).render(g);
        }
    }
}
