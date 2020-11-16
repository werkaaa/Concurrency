package lab6;

import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;


public class Buffer {

    private final int size;
    // private final HashMap<Integer, Integer> buffer;
    private final ConcurrentHashMap<Integer, Integer> buffer;


    public Buffer(int size) {
        this.size = size;
        // this.buffer = new HashMap<>(size);
        this.buffer = new ConcurrentHashMap<>(size);
    }

    void produce(int id) throws IllegalAccessException {
        if(buffer.containsKey(id)){
            throw new IllegalAccessException("Thread wanted to add element " + id +
                    ", which was already inside the hash map");
        }
        buffer.put(id, 10);
    }

    void consume(int id) throws Exception {
        if(!buffer.containsKey(id)){
            throw new IllegalAccessException("Thread wanted to access element " + id +
                                             ", which wasn't inside the hash map");
        }
        int buffer_size = buffer.size();
        if(buffer_size < 0){
            throw new Exception("Buffer size is negative :(\nBuffer size: " + buffer_size);
        }

        // System.out.println(buffer_size);

        buffer.get(id);
        buffer.remove(id);
    }

}