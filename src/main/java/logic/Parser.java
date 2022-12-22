package logic;

import logic.enums.MapType;
import logic.enums.MutationType;
import logic.enums.PlantType;

import java.io.*;
import java.lang.reflect.Field;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

public abstract class Parser {


    public static Starter fromFile(File file) throws IOException, IllegalAccessException {
        Properties properties = new Properties();
        InputStream stream = new FileInputStream(file);
        properties.load(stream);
        Starter s = new Starter();
        Field[] fields = Starter.class.getDeclaredFields();
        String tmp;
        for (Field e:fields) {
            e.setAccessible(true);
            tmp = (String)properties.get(e.getName());
            if(isNumeric(tmp)){
                e.set(s,Integer.parseInt(tmp));
            }
            else{
                switch (e.getName()){
                    case "mapType" -> e.set(s,MapType.valueOf(tmp));
                    case "plantType" -> e.set(s, PlantType.valueOf(tmp));
                    case "mutationType" -> e.set(s, MutationType.valueOf(tmp));
                    case "animalBehaviour" -> e.set(s, AnimalBehaviour.valueOf(tmp));
                }
            }

        }
        return s;
    }
    private static boolean isNumeric(String str){
        try{
            Integer.parseInt(str);
            return true;
        }catch(NumberFormatException e) {
            return false;
        }
    }
}
