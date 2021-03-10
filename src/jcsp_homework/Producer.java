package jcsp_homework;

import org.jcsp.lang.*;

public class Producer implements CSProcess {

    private One2OneChannel<Any2OneChannelInt> inputFromManager;
    private Any2OneChannel<One2OneChannel<Any2OneChannelInt>> requestForManager;
    private int id;

    Producer(One2OneChannel<Any2OneChannelInt> inputFromManager,
             Any2OneChannel<One2OneChannel<Any2OneChannelInt>> requestForManager,
             int id){
        this.inputFromManager = inputFromManager;
        this.requestForManager = requestForManager;
        this.id = id;
    }

    @Override
    public void run() {
        ChannelOutput<One2OneChannel<Any2OneChannelInt>> requestForManagerOutput = requestForManager.out();
        ChannelInput<Any2OneChannelInt> managerChannelInput = inputFromManager.in();
        while(true){
            requestForManagerOutput.write(inputFromManager);
            Any2OneChannelInt bufferChannel = managerChannelInput.read();
            ChannelOutputInt bufferChannelInput = bufferChannel.out();
            bufferChannelInput.write(this.id);
            System.out.println("P" + id + " produced");
        }
    }
}
