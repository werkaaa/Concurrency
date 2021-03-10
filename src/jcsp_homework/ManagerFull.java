package jcsp_homework;

import org.jcsp.lang.*;

public class ManagerFull implements CSProcess {

    private Any2OneChannel<Any2OneChannelInt> inputFromConsumer;
    private Any2OneChannel<One2OneChannel<Any2OneChannelInt>> inputFromBuffer;

    ManagerFull(Any2OneChannel<Any2OneChannelInt> inputFromConsumer,
                Any2OneChannel<One2OneChannel<Any2OneChannelInt>> inputFromBuffer){
        this.inputFromBuffer = inputFromBuffer;
        this.inputFromConsumer = inputFromConsumer;
    }

    @Override
    public void run() {
        ChannelInput<Any2OneChannelInt> consumerChannelInput = inputFromConsumer.in();
        ChannelInput<One2OneChannel<Any2OneChannelInt>> bufferChannelInput = inputFromBuffer.in();

        while(true){
            Any2OneChannelInt outputToConsumer = consumerChannelInput.read();

            One2OneChannel<Any2OneChannelInt> outputToBuffer = bufferChannelInput.read();
            ChannelOutput<Any2OneChannelInt> bufferChannelOutput = outputToBuffer.out();

            bufferChannelOutput.write(outputToConsumer);
        }
    }
}
