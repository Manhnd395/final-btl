package lma.librarymanagementapplication;

import java.util.*;

public class MemoryGame {

    private int turns = 0;
    private int points = 0;
    private final int boardLength = 4;
    private final int boardSize = boardLength * boardLength;
    private final Random random = new Random();

    private final ArrayList<String> memoryBoard = new ArrayList<>(Collections.nCopies(16, ""));
    private final ArrayList<String> memoryOptions = new ArrayList<>(Arrays.asList("a", "a", "b", "b", "c", "c", "d", "d", "e", "e", "f", "f", "g", "g", "h", "h"));

    public void setupGame(){
        setupMemoryBoard();
        System.out.println(memoryBoard);
    }

    public String getOptionAtIndex(int index){
        return memoryBoard.get(index);
    }

    private void setupMemoryBoard(){
        for (int i = 0; i < boardSize; i++) {
            String memoryOption = memoryOptions.get(i);
            int position = random.nextInt(boardSize);

            while (!Objects.equals(memoryBoard.get(position), "")){
                position = random.nextInt(boardSize);
            }
            memoryBoard.set(position, memoryOption);
        }
    }

    public boolean checkTwoPositions(int firstIndex, int secondIndex){
        return memoryBoard.get(firstIndex).equals(memoryBoard.get(secondIndex));
    }
}