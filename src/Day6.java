import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Day6 {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(new File("data/input6.txt")));
        List<GalaxyObject<String>> loadedData = new ArrayList<>();
        String line;
        while ((line = bufferedReader.readLine()) != null)
            loadedData.add(parseSingleData(line));

        System.out.println("Total number of orbits: " + countTotalNumberOfOrbits(loadedData));
        System.out.println("Required number of orbital transfers: " + countMinPathFromYouToSan(loadedData));

    }

    private static int countTotalNumberOfOrbits(List<GalaxyObject<String>> galaxyObjects){
        List<GalaxyObject<String>> centreOfMass = findGalaxyObjectByKey(galaxyObjects, "COM");

        return countNumberOfOrbits(galaxyObjects, centreOfMass.get(0).getNextObject(), 0);
    }

    private static int countMinPathFromYouToSan(List<GalaxyObject<String>> galaxyObjects){
        List<GalaxyObject<String>> com = findGalaxyObjectByValue(galaxyObjects, "COM");

        List<GalaxyObject<String >> pathToYou = new ArrayList<>();
        List<GalaxyObject<String >> pathToSan = new ArrayList<>();
        findPathTo(galaxyObjects, com.get(0), pathToYou, "YOU") ;
        findPathTo(galaxyObjects, com.get(0), pathToSan, "SAN") ;

        int i;
        int j = 0;

        boolean sem = false;
        for(i = 0; i < pathToYou.size(); i++) {
            for (j = 0; j < pathToSan.size(); j++) {
                if (pathToYou.get(i).getObject().equals(pathToSan.get(j).getObject())) {
                    sem = true;
                    break;
                }
            }
            if (sem)
                break;
        }

        return i + j + 2;
    }

    private static boolean findPathTo(List<GalaxyObject<String>> galaxyObjects, GalaxyObject<String> currentObject, List<GalaxyObject<String>> result, String toFound){
        List<GalaxyObject<String>> foundObjects = findGalaxyObjectByValue(galaxyObjects, currentObject.getNextObject());

        for(GalaxyObject<String> object : foundObjects)
            if(object.getNextObject().equals(toFound)) {
                result.add(currentObject);
                return true;
            }

        if(foundObjects.isEmpty())
            return false;

        boolean nodeBelongToPath = false;

        for(GalaxyObject<String> object : foundObjects)
             if(findPathTo(galaxyObjects, object, result, toFound)) {
                 result.add(currentObject);
                 nodeBelongToPath = true;
             }

        return nodeBelongToPath;
    }

    private static int countNumberOfOrbits(List<GalaxyObject<String>> galaxyObjects, String galaxyObject, int currentValue){
        List<GalaxyObject<String>> foundObjects = findGalaxyObjectByKey(galaxyObjects, galaxyObject);
        currentValue++;

        if(foundObjects.isEmpty())
            return currentValue;

        int result = 0;

        for(GalaxyObject<String> object : foundObjects)
            result += countNumberOfOrbits(galaxyObjects, object.getNextObject(), currentValue);

        return result + currentValue;
    }

    private static List<GalaxyObject<String>> findGalaxyObjectByKey(List<GalaxyObject<String>> galaxyObjects, String key){
        List<GalaxyObject<String>> result = new ArrayList<>();
        for(GalaxyObject<String> object : galaxyObjects)
            if(object.object.equals(key))
                result.add(object);

            return result;
    }

    private static List<GalaxyObject<String>> findGalaxyObjectByValue(List<GalaxyObject<String>> galaxyObjects, String value){
        List<GalaxyObject<String>> result = new ArrayList<>();
        for(GalaxyObject<String> object : galaxyObjects)
            if(object.getObject().equals(value))
                result.add(object);

        return result;
    }

    private static GalaxyObject<String> parseSingleData(String input){
        String[] temp = input.split("\\)");
        return new GalaxyObject<>(temp[0], temp[1]);
    }

    private static class GalaxyObject<T>{
        T object;
        T nextObject;

        public GalaxyObject(T object, T nextObject) {
            this.object = object;
            this.nextObject = nextObject;
        }

        public T getObject() {
            return object;
        }

        public void setObject(T object) {
            this.object = object;
        }

        public T getNextObject() {
            return nextObject;
        }

        public void setNextObject(T nextObject) {
            this.nextObject = nextObject;
        }
        }
    }

