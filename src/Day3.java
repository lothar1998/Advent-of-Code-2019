import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Day3 {
    public static void main(String[] args) throws IOException {
        File file = new File("data/input3.txt");
        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
        String[] path1 = bufferedReader.readLine().split(",");
        String[] path2 = bufferedReader.readLine().split(",");

        Line[] path1Lines = new Line[path1.length];
        Line[] path2Lines = new Line[path2.length];

        assignLineToPath(path1Lines, path1);
        assignLineToPath(path2Lines, path2);

        List<Coordinates> path1Coordinates = new ArrayList<>();
        List<Coordinates> path2Coordinates = new ArrayList<>();

        countCoordinates(path1Lines, path1Coordinates);
        countCoordinates(path2Lines, path2Coordinates);

        List<Pair<Coordinates>> resultMatches = findMatchesCoordinates(path1Coordinates, path2Coordinates);

        Coordinates minDistanceCoordinate = findClosestCrossedWires(resultMatches);

        System.out.println("manhattan distance: " + manhattanDistance(resultMatches.get(0).getA(), minDistanceCoordinate));
        System.out.println("fewest steps to reach an intersection: " + fewestCombinedStepsToReachAnIntersection(resultMatches, path1Coordinates, path2Coordinates));


    }

    private static class Pair<T>{
        private T a;
        private T b;

        public Pair(T a, T b) {
            this.a = a;
            this.b = b;
        }

        public T getA() {
            return a;
        }

        public void setA(T a) {
            this.a = a;
        }

        public T getB() {
            return b;
        }

        public void setB(T b) {
            this.b = b;
        }
    }

    private static class Coordinates{
        private int x;
        private int y;

        public Coordinates(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public int getX() {
            return x;
        }

        public void setX(int x) {
            this.x = x;
        }

        public int getY() {
            return y;
        }

        public void setY(int y) {
            this.y = y;
        }

        public boolean equalsCoordinates(Coordinates o){
            return this.x == o.x && this.y == o.y;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Coordinates that = (Coordinates) o;
            return x == that.x &&
                    y == that.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }
    }

    private static class Line{

        enum Direction{
            UP, DOWN, LEFT, RIGHT
        }

        private Direction direction;
        private int length;

        public Line(String line){
            String directionSign = line.substring(0,1);

            switch (directionSign) {
                case "U":
                    direction = Direction.UP;
                    break;
                case "D":
                    direction = Direction.DOWN;
                    break;
                case "R":
                    direction = Direction.RIGHT;
                    break;
                case "L":
                    direction = Direction.LEFT;
                    break;
                default:
                    direction = null;
                    break;
            }

            length = Integer.parseInt(line.substring(1));
        }

        public Line(Direction direction, int length) {
            this.direction = direction;
            this.length = length;
        }

        public Direction getDirection() {
            return direction;
        }

        public void setDirection(Direction direction) {
            this.direction = direction;
        }

        public int getLength() {
            return length;
        }

        public void setLength(int length) {
            this.length = length;
        }
    }

    private static Line countLineProperties(String line){
        return new Line(line);
    }

    private static void countCoordinates(Line[] path, List<Coordinates> pathCoordinates){
        Coordinates current = new Coordinates(0,0);
        pathCoordinates.add(current);

        for (Line currentLine : path) {
            for (int j = 0; j < currentLine.length; j++) {
                switch (currentLine.direction) {
                    case UP:
                        current = new Coordinates(current.x, current.y + 1);
                        pathCoordinates.add(current);
                        break;
                    case DOWN:
                        current = new Coordinates(current.x, current.y - 1);
                        pathCoordinates.add(current);
                        break;
                    case LEFT:
                        current = new Coordinates(current.x - 1, current.y);
                        pathCoordinates.add(current);
                        break;
                    case RIGHT:
                        current = new Coordinates(current.x + 1, current.y);
                        pathCoordinates.add(current);
                        break;
                }
            }
        }
    }

    private static void assignLineToPath(Line[] pathLine, String[] path){
        for(int i = 0; i < path.length; i++){
            pathLine[i] = countLineProperties(path[i]);
        }
    }

    private static List<Pair<Coordinates>> findMatchesCoordinates(List<Coordinates> coordinatesA, List<Coordinates> coordinatesB){

        List<Pair<Coordinates>> resultCoordinates = new ArrayList<>();

        for(Coordinates coordinateA : coordinatesA){
            for(Coordinates coordinateB : coordinatesB)
                if(coordinateA.equalsCoordinates(coordinateB))
                    resultCoordinates.add(new Pair<>(coordinateA, coordinateB));
        }

        return resultCoordinates;
    }

    private static int manhattanDistance(Coordinates coordinateA, Coordinates coordinateB){
        return Math.abs(coordinateB.y-coordinateA.y) + Math.abs(coordinateB.x-coordinateA.x);
    }

    private static Coordinates findClosestCrossedWires(List<Pair<Coordinates>> crossedWires){
        int minDistance = Integer.MAX_VALUE;
        int currentDistance;
        Coordinates minDistanceCoordinate = crossedWires.get(0).getA();
        for(int i = 1; i < crossedWires.size(); i++){
            currentDistance = manhattanDistance(crossedWires.get(0).getA(), crossedWires.get(i).getA());
            if(currentDistance < minDistance){
                minDistanceCoordinate = crossedWires.get(i).getA();
                minDistance = currentDistance;
            }
        }

        return minDistanceCoordinate;
    }

    private static int fewestCombinedStepsToReachAnIntersection(List<Pair<Coordinates>> matchedCoordinates, List<Coordinates> pathA, List<Coordinates> pathB){
        int minA = Integer.MAX_VALUE;
        int minB = Integer.MAX_VALUE;
        int overall = Integer.MAX_VALUE;
        int i;

        for(int k = 1; k < matchedCoordinates.size(); k++){

            i = 0;
            for(Coordinates current : pathA){
                if(current.equals(matchedCoordinates.get(k).getA())) {
                    minA = i;
                    break;
                }
                i++;
            }

            i = 0;
            for(Coordinates current : pathB){
                if(current.equals(matchedCoordinates.get(k).getB())) {
                    minB = i;
                    break;
                }
                i++;
            }

            if(minA + minB < overall)
                overall = minA + minB;

        }

        return overall;
    }
}
