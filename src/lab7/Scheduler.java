package lab7;

import lab7.methodRequests.MethodRequest;
import lab7.methodRequests.MethodRequestConsume;
import lab7.methodRequests.MethodRequestProduce;

import java.util.LinkedList;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.LinkedBlockingQueue;


public class Scheduler implements Runnable {

    // private final ConcurrentLinkedQueue<MethodRequest> inputQueue = new ConcurrentLinkedQueue<>();
    private final LinkedBlockingQueue<MethodRequest> inputQueue = new LinkedBlockingQueue<>();

    private final LinkedList<MethodRequest> commonQueue = new LinkedList<>();
    private final LinkedList<MethodRequest> consumersQueue = new LinkedList<>();
    private final LinkedList<MethodRequest> producersQueue = new LinkedList<>();

    public void enqueue(MethodRequest methodRequest) {
        try {
            this.inputQueue.put(methodRequest);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void updateQueues() {
//        while (!inputQueue.isEmpty()) {
//            MethodRequest methodToSort = this.inputQueue.poll();
        MethodRequest methodToSort = null;
        try {
            methodToSort = this.inputQueue.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        this.commonQueue.add(methodToSort);

            if (methodToSort instanceof MethodRequestConsume) {
                this.consumersQueue.add(methodToSort);
            } else if (methodToSort instanceof MethodRequestProduce) {
                this.producersQueue.add(methodToSort);
            }

      //  }
    }

    public void showQueues() {
        System.out.println("Input queue: " + inputQueue);
        System.out.println("Common queue: " + commonQueue);
        System.out.println("Consumers queue: " + consumersQueue);
        System.out.println("Producers queue: " + producersQueue);
    }

    @Override
    public void run() {

        while (true) {

            if (!this.commonQueue.isEmpty()) {

                MethodRequest firstMethod = this.commonQueue.peek();

                if (!firstMethod.canExecute()) {
                    if (firstMethod instanceof MethodRequestConsume) {
                        while (!firstMethod.canExecute()) {

                            if (!this.producersQueue.isEmpty()) {
                                MethodRequest firstProduceMethod = this.producersQueue.poll();
                                firstProduceMethod.execute();
                            }
                            else{
                                updateQueues();
                                showQueues();
                            }
                        }
                    } else if (firstMethod instanceof MethodRequestProduce) {
                        while (!firstMethod.canExecute()) {

                            if (!this.consumersQueue.isEmpty()) {
                                MethodRequest firstConsumeMethod = this.consumersQueue.poll();
                                firstConsumeMethod.execute();
                            }
                            else{
                                updateQueues();
                                showQueues();
                            }
                        }
                    }
                }

                if (firstMethod instanceof MethodRequestConsume) {
                    this.consumersQueue.poll();
                } else if (firstMethod instanceof MethodRequestProduce) {
                    this.producersQueue.poll();
                }

                firstMethod.execute();

                while (firstMethod != null && firstMethod.isDone()) {
                    this.commonQueue.poll();
                    if (!commonQueue.isEmpty()) {
                        firstMethod = commonQueue.peek();
                    } else {
                        firstMethod = null;
                    }
                }
            }
            else{
                updateQueues(); //tu powinno się zawiesić, jeśli nie ma zadań
                showQueues();
            }

        }
    }

}
