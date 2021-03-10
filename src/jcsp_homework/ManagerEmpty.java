package jcsp_homework;

import org.jcsp.lang.*;

public class ManagerEmpty implements CSProcess {

    private Any2OneChannel<One2OneChannel<Any2OneChannelInt>> inputFromProducer;
    private Any2OneChannel<Any2OneChannelInt> inputFromBuffer;

    ManagerEmpty(Any2OneChannel<One2OneChannel<Any2OneChannelInt>> inputFromProducer,
                 Any2OneChannel<Any2OneChannelInt> inputFromBuffer){
        this.inputFromBuffer = inputFromBuffer;
        this.inputFromProducer = inputFromProducer;
    }

    @Override
    public void run() {
        ChannelInput<One2OneChannel<Any2OneChannelInt>> producerChannelInput = inputFromProducer.in();
        ChannelInput<Any2OneChannelInt> bufferChannelInput = inputFromBuffer.in();
        while(true){
            One2OneChannel<Any2OneChannelInt> outputToProducer = producerChannelInput.read();
            ChannelOutput<Any2OneChannelInt> producerChannelOutput = outputToProducer.out();

            Any2OneChannelInt bufferChannelOutput = bufferChannelInput.read();
            producerChannelOutput.write(bufferChannelOutput);
        }
    }
}
