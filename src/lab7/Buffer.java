package lab7;

import java.util.ArrayList;
import java.util.LinkedList;


public class Buffer {
    private final int bufferMaxSize;
    private int bufferCurrentSize;
    private final LinkedList<Integer> buffer;

    public Buffer(int bufferMaxSize) {
        this.bufferMaxSize = bufferMaxSize;
        this.bufferCurrentSize = 0;
        this.buffer = new LinkedList<>();
    }

    public ArrayList<Integer> produce(int numElements, int elementValue) {
        ArrayList<Integer> produced = new ArrayList<>();
        for (int i = 0; i < numElements; i++) {
            produced.add(elementValue);
            this.buffer.add(elementValue);
            this.bufferCurrentSize += 1;
        }
        return produced;
    }

    public ArrayList<Integer> consume(int numElements) {
        ArrayList<Integer> consumed = new ArrayList<>();
        for (int i = 0; i < numElements; i++) {
            consumed.add(this.buffer.getFirst());
            this.buffer.removeFirst();
            this.bufferCurrentSize -= 1;
        }
        return consumed;
    }

    public boolean isEnoughPlace(int numElements) {
        return numElements + this.bufferCurrentSize <= this.bufferMaxSize;
    }

    public boolean areEnoughElements(int numElements) {
        return this.bufferCurrentSize - numElements >= 0;
    }
}
