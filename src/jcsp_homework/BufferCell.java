package jcsp_homework;

import org.jcsp.lang.*;

public class BufferCell implements CSProcess {

    private Any2OneChannelInt inputFromProducer;
    private One2OneChannel<Any2OneChannelInt> inputFromManagerFull;
    private Any2OneChannel<One2OneChannel<Any2OneChannelInt>> outputForManagerFull;
    private Any2OneChannel<Any2OneChannelInt> outputForManagerEmpty;
    private int field;
    private int bufferCellId;

    BufferCell(Any2OneChannelInt inputFromProducer,
               One2OneChannel<Any2OneChannelInt> inputFromManagerFull,
               Any2OneChannel<One2OneChannel<Any2OneChannelInt>> outputForManagerFull,
               Any2OneChannel<Any2OneChannelInt> outputForManagerEmpty,
               int bufferCellId){
        this.inputFromProducer = inputFromProducer;
        this.inputFromManagerFull = inputFromManagerFull;
        this.outputForManagerEmpty = outputForManagerEmpty;
        this.outputForManagerFull = outputForManagerFull;
        this.bufferCellId = bufferCellId;
    }

    @Override
    public void run() {
        AltingChannelInputInt producerChannelInput = inputFromProducer.in();
        ChannelInput<Any2OneChannelInt> managerFullChannelInput = inputFromManagerFull.in();
        ChannelOutput<One2OneChannel<Any2OneChannelInt>> managerFullChannelOutput = outputForManagerFull.out();
        ChannelOutput<Any2OneChannelInt> managerEmptyChannelOutput = outputForManagerEmpty.out();
        while(true){
            //System.out.println(producerChannelInput.pending());
            managerEmptyChannelOutput.write(inputFromProducer);
            this.field = producerChannelInput.read();
            System.out.println("B" + this.bufferCellId + ": " + this.field);
            managerFullChannelOutput.write(inputFromManagerFull);
            Any2OneChannelInt outputForConsumer = managerFullChannelInput.read();
            ChannelOutputInt consumerChannelOutput = outputForConsumer.out();
            consumerChannelOutput.write(this.field);
            System.out.println("B" + this.bufferCellId + " is empty");
        }
    }
}
