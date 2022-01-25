import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ProducerConcumer {
    private static BlockingQueue<String> queue = new ArrayBlockingQueue<>(10);
    private static List<String> lessThanHundredChar = new ArrayList<>();
    private static List<String> moreThanHundredChar = new ArrayList<>();


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
                    System.out.println("Количество строк содержащих меньше 100 символов = "+lessThanHundredChar.size());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        Thread threadConsumer2 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    consumer2();
                    System.out.println("Количество строк содержащих больше 100 символов = "+moreThanHundredChar.size());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        ExecutorService executorService = Executors.newCachedThreadPool();

//        threadConsumer.setName("Consumer1");
//        threadConsumer2.setName("Consumer2");

        executorService.submit(threadProduce);
        executorService.submit(threadConsumer);
        executorService.submit(threadConsumer2);
        executorService.shutdown();


//
//        threadProduce.start();
//        threadConsumer.start();
//        threadConsumer2.start();
//
//        threadProduce.join();
//        threadConsumer.join();
//        threadConsumer2.join();

    }

    private static void produce() throws InterruptedException {
        String src = "C:\\Users\\Igorj\\Desktop\\Стажер\\Multithreading\\src\\text.txt";
        try {
            Path path = Path.of(src);
            List<String> list = Files.readAllLines(path);

            for (String str : list) {
                System.out.println(str);
                System.out.println(str.length());
                queue.put(str);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private  static void consumer() throws InterruptedException {
       // Thread.sleep(100);
        while (true){
            String string = queue.peek();
           // if(string==null)return;

            if(string.length()<101){
                lessThanHundredChar.add(queue.take());
                System.out.println("Строка: " + string);
                System.out.println("Количество символов: "+ string.length());
                System.out.println("Название потока: "+Thread.currentThread().getName());
                System.out.println();
            }
            //else Thread.yield();
        }
    }

    private static void consumer2() throws InterruptedException {
       // Thread.sleep(100);
        while (true){

            String string = queue.peek();
            //if(string==null)return;

            if(string.length()>100){
                moreThanHundredChar.add(queue.take());
                System.out.println("Строка: " + string);
                System.out.println("Количество символов: "+ string.length());
                System.out.println("Название потока: "+Thread.currentThread().getName());
                System.out.println();
            }
            else Thread.yield();
        }
    }
}
