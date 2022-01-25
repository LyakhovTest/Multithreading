import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class Test {
    private static BlockingQueue<Integer> queue = new ArrayBlockingQueue<>(10);
    public static void main(String[] args) throws InterruptedException {
        Thread threadProduce = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    produce();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        Thread threadConsumer = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    consumer();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        threadProduce.start();
        threadConsumer.start();

        threadProduce.join();
        threadConsumer.join();
    }

    private static void produce() throws InterruptedException {
        Random random = new Random();
        while (true){
            queue.put(random.nextInt(100));
        }
    }

    private static void consumer() throws InterruptedException {
        while (true){
            Thread.sleep(100);
            System.out.println(queue.take());
            System.out.println("Queue size is "+ queue.size());
        }
    }
}
