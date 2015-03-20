/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mage.tournament.cubes.utils;

import java.util.ArrayList;
import mage.game.draft.DraftCube;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * @author crenshawda
 */
public class CubeTutorUtils {
    private final static String cubeTutorBaseUrl = "http://www.cubetutor.com/viewcube/";
    
    // TODO: Ask Ben from CubeTutor if we can get a caching API so this doesn't 
    //       rack up unnessary bandwidth?
    public static ArrayList<String> getByCubeTutorId(String cubeId) {
        ArrayList<String> cubeList = new ArrayList();
        String cubeUrl = CubeTutorUtils.cubeTutorBaseUrl + cubeId;
        
        try {
            Document doc = Jsoup.connect(cubeUrl).get();
            Elements cards = doc.select(".cardPreview");
            
            for (Element e : cards) {
                cubeList.add(e.text());
            }
        } catch (Exception e) {
            // TODO: better handling here
            System.out.println(e);
        }
        
        return cubeList;
    }
    
    public void populateCube(DraftCube cube, String cubeId) {
        cube.setCubeCards(CubeTutorUtils.getByCubeTutorId(cubeId));
    }
}
