package jcsp_homework;

import org.jcsp.lang.*;

public class Main {
    public static void main(String[] args){
        int bufferSize = 10;
        int producersNumber = 20;
        int consumersNumber = 20;

        Any2OneChannel<Any2OneChannelInt> bufferToManagerEmptyChannel = Channel.any2one();
        Any2OneChannel<One2OneChannel<Any2OneChannelInt>> bufferToManagerFullChannel = Channel.any2one();
        Any2OneChannel<One2OneChannel<Any2OneChannelInt>> producerToManagerEmptyChannel = Channel.any2one();
        Any2OneChannel<Any2OneChannelInt> consumerToManagerFullChannel = Channel.any2one();

        CSProcess[] processList = new CSProcess[bufferSize + producersNumber + consumersNumber + 2];
        int cnt = 0;
        for(int i = 0; i < bufferSize; i++){
            Any2OneChannelInt producerToBufferChannel = Channel.any2oneInt();
            One2OneChannel<Any2OneChannelInt> managerFullToBufferChannel = Channel.one2one();

            processList[cnt] = new BufferCell(
                    producerToBufferChannel,
                    managerFullToBufferChannel,
                    bufferToManagerFullChannel,
                    bufferToManagerEmptyChannel,
                    i);
            cnt++;
        }

        for(int i = 0; i < producersNumber; i++){
            One2OneChannel<Any2OneChannelInt> managerEmptyToProducerChannel = Channel.one2one();

            processList[cnt] = new Producer(
                    managerEmptyToProducerChannel,
                    producerToManagerEmptyChannel,
                    i);
            cnt++;
        }

        for(int i = 0; i < consumersNumber; i++){
            Any2OneChannelInt bufferToConsumerChannel = Channel.any2oneInt();

            processList[cnt] = new Consumer(
                    bufferToConsumerChannel,
                    consumerToManagerFullChannel,
                    i);
            cnt++;
        }
        processList[cnt++] = new ManagerEmpty(producerToManagerEmptyChannel, bufferToManagerEmptyChannel);
        processList[cnt] = new ManagerFull(consumerToManagerFullChannel, bufferToManagerFullChannel);
        Parallel parallel = new Parallel(processList);
        parallel.run();
    }
}
