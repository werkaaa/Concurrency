package lab1;

public class IntegerCounter {
    private int value;

    public IntegerCounter(int value){
        this.value = value;
    }
    public void increment(){
//        try {
//            Thread.sleep(4000);
//        }catch(InterruptedException e){
//            System.out.printf("err");
//        }
//        for(int i = 0; i < 10; i++) {
            this.value += 1;
//        }
    }

    public void decrement(){
//        for(int i = 0; i < 10; i++) {
            this.value -= 1;
//        }
    }

    public String toString(){
        return String.valueOf(this.value);
    }

}
