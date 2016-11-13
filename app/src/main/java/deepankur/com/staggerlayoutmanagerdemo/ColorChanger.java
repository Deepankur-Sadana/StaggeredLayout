package deepankur.com.staggerlayoutmanagerdemo;

import android.util.Log;
import android.util.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static deepankur.com.staggerlayoutmanagerdemo.ColorChanger.Neighbor.*;

/**
 * Created by deepankur on 11/14/16.
 */

class ColorChanger {

    enum Neighbor {LEFT, RIGHT, TOP, BOTTOM}

    private final int PALETTE_HEIGHT = 3, PALLETTE_WIDTH = 4;
    private Neighbor[] allNeighbors = new Neighbor[]{LEFT, RIGHT, TOP, BOTTOM};
    private int[][] pallete = new int[PALLETTE_WIDTH][PALETTE_HEIGHT];//x and y
    /**
     * represent all the pixels processed in current session
     */
    private ArrayList<Pair<Integer, Integer>> pairArrayList;
    private String TAG = getClass().getSimpleName();
    /**
     * Since we are asked to represent an image by 2-D array we will have this data structure
     * whose
     * {@link Pair#first} will represent the x co-ordinate
     * {@link Pair#second} will represent the y co-ordinate
     * <p>
     * and the value in the hashMap will represent the color itself
     */
    private HashMap<Pair<Integer, Integer>, Integer> paintData = new HashMap<>();

    /**
     * This is the end point from where user will enter the x/y coordinates and color for the first time
     */
    private void onInitialColorAndCoordinateProvided(int x, int y, int desiredColor) {
        pairArrayList.clear();
        if (shouldUpdateColor(x, y, desiredColor))
            changeColor(x, y, desiredColor);
    }

    /**
     * @param x        :the x coordinate
     * @param y        :the y coordinate
     * @param newColor :the color of the pixel
     * @return true if processed false otherwise
     * <p>
     * Note:-- it is a recursive function and will  keep on adding on stack and processing pixels
     * until desired result achived
     */
    private boolean changeColor(int x, int y, int newColor) {

        for (int i = 0; i < allNeighbors.length; i++) {
            int[] neighbour = getNeighbour(x, y, allNeighbors[i]);
            if (neighbour == null)
                return false;
            else if (shouldUpdateColor(neighbour[0], neighbour[1], newColor)) {
                writeNewColorAtThePixel(neighbour[0], neighbour[1], newColor);
                changeColor(neighbour[0], neighbour[1], newColor);
                return true;
            } else {
                return false;
            }
        }
        return true;
    }

    /**
     * @param neighbor: the neighbor to which search for
     * @return the integer array of length 2 {x,y} if found null otherWise
     */
    private int[] getNeighbour(int x, int y, Neighbor neighbor) {
        if (x < 0 || x > PALLETTE_WIDTH - 1 || y < 0 || y > PALETTE_HEIGHT - 1) {
            Log.d(TAG, "getNeighbour: InvalidPixel provided");
            return null;
        }

        switch (neighbor) {
            case LEFT:
                if (x < 1)
                    return null;
                else return new int[]{x - 1, y};
            case TOP:
                if (y < 1)
                    return null;
                else return new int[]{x, y - 1};
            case RIGHT:
                if (x > PALLETTE_WIDTH - 2)
                    return null;
                else return new int[]{x + 1, y};
            case BOTTOM:
                if (y > PALETTE_HEIGHT - 2)
                    return null;
                else return new int[]{x + 1, y};
        }
        Log.d(TAG, "getNeighbour: undefined neighbour " + neighbor);
        return null;
    }

    private int getColorAtAPixel(int x, int y) {
        for (Map.Entry<Pair<Integer, Integer>, Integer> pixelEntry : paintData.entrySet()) {
            Pair<Integer, Integer> key = pixelEntry.getKey();
            int storedX = key.first;
            int storedY = key.second;
            if (storedX == x && storedY == y)
                return pixelEntry.getValue();
        }
        throw new NullPointerException("No color found at pixel  x: " + x + " y: " + y);
    }

    /**
     * @return true if the color at particular pixel is different from the new color and
     * hence should be updated
     */
    private boolean shouldUpdateColor(int x, int y, int newColor) {
        for (Map.Entry<Pair<Integer, Integer>, Integer> pixelEntry : paintData.entrySet()) {
            Pair<Integer, Integer> key = pixelEntry.getKey();
            int storedX = key.first;
            int storedY = key.second;
            if (storedX == x && storedY == y) {
                int oldColor = pixelEntry.getValue();
                return oldColor != newColor;
            }
        }
        throw new RuntimeException("shouldUpdateColor: invalidPixel");
    }

    /**
     * @param newColor is the color by which {@link #paintData} will be updated by
     */
    private void writeNewColorAtThePixel(int x, int y, int newColor) {
        for (Map.Entry<Pair<Integer, Integer>, Integer> pixelEntry : paintData.entrySet()) {
            Pair<Integer, Integer> key = pixelEntry.getKey();
            int storedX = key.first;
            int storedY = key.second;
            if (storedX == x && storedY == y) {
                notifyCoordinateProcessed(x, y);
                pixelEntry.setValue(newColor);//updated color successfully
                return;
            }
        }
        Log.d(TAG, "writeNewColorAtThePixel: failed for x: " + x + " y: " + y);
    }

    private void notifyCoordinateProcessed(int x, int y) {
        if (isAlreadyProcessed(x, y))
            throw new RuntimeException("Tried to process a pixel at x: " + x + " y: " + y + " multiple times");
        pairArrayList.add(new Pair<>(x, y));
    }

    /**
     * @param x coordinate
     * @param y coordinate
     * @return true if already processed, false other wise
     */
    private boolean isAlreadyProcessed(int x, int y) {
        for (int i = 0; i < pairArrayList.size(); i++) {
            Pair<Integer, Integer> coordinatePairs = pairArrayList.get(i);
            if (coordinatePairs.first.equals(x) && coordinatePairs.second.equals(y))
                return true;
        }
        return false;
    }


}
