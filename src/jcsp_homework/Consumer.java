package jcsp_homework;

import org.jcsp.lang.*;

public class Consumer implements CSProcess {

    private Any2OneChannelInt inputFromBuffer;
    private Any2OneChannel<Any2OneChannelInt> requestForManager;
    private int id;

    Consumer(Any2OneChannelInt inputFromBuffer,
             Any2OneChannel<Any2OneChannelInt> requestForManager,
             int id){
        this.inputFromBuffer = inputFromBuffer;
        this.requestForManager = requestForManager;
        this.id = id;
    }

    @Override
    public void run() {
        ChannelOutput<Any2OneChannelInt> requestForManagerOutput = requestForManager.out();
        AltingChannelInputInt bufferChannelInput = inputFromBuffer.in();
        while(true){
            //System.out.println(bufferChannelInput.pending());
            requestForManagerOutput.write(inputFromBuffer);
            int consumed = bufferChannelInput.read();
            System.out.println("C" + this.id + " consumed " + consumed);
        }
    }
}
