package lab7;

import lab7.methodRequests.MethodRequestConsume;
import lab7.methodRequests.MethodRequestProduce;


public class ProxyBuffer {

    Buffer buffer;
    Scheduler scheduler;

    public ProxyBuffer(int bufferMaxSize) {
        this.buffer = new Buffer(bufferMaxSize);
        this.scheduler = new Scheduler();
        new Thread(this.scheduler).start();
    }

    Future produce(int howMany, int element, int producerId) {
        Future future = new Future();
        MethodRequestProduce new_method = new MethodRequestProduce(this.buffer, future, howMany, element, producerId);
        scheduler.enqueue(new_method);
        return future;
    }

    Future consume(int howMany, int consumerId) {
        Future future = new Future();
        MethodRequestConsume new_method = new MethodRequestConsume(this.buffer, future, howMany, consumerId);
        scheduler.enqueue(new_method);
        return future;
    }
}
