package lab2;

public class IntegerCounter {
    private int value;

    public IntegerCounter(int value){
        this.value = value;
    }
    public void increment(){
        this.value += 1;
    }

    public void decrement(){
            this.value -= 1;
    }

    public String toString(){
        return String.valueOf(this.value);
    }

}
