package secondTry;

import java.io.IOException;
import java.lang.reflect.MalformedParameterizedTypeException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Test {
//    private static BlockingQueue<String> queue = new ArrayBlockingQueue<>(10);
//    private static Map<String,List<String>> listsOfString= new HashMap();
    public static void main(String[] args) {
        BlockingQueue<String> queue = new ArrayBlockingQueue<>(8);
        //Map<String,List<String>> listsOfString= new HashMap();

        ExecutorService executorService = Executors.newCachedThreadPool();
        executorService.submit(new Producer(queue));
        executorService.submit(new Consumer(queue));
        executorService.submit(new Consumer2(queue));
        executorService.shutdown();
    }
}

class Producer implements Runnable{
    private static BlockingQueue<String> queue1;

    public Producer(BlockingQueue<String> queue){
        queue1 = queue;
    }
    @Override
    public void run() {
            String src = "C:\\Users\\Igorj\\Desktop\\Стажер\\Multithreading\\src\\text.txt";
            try {
                Path path = Path.of(src);
                List<String> list = Files.readAllLines(path);

                for (String str : list) {
                    System.out.println(str);
                    System.out.println(str.length());
                    queue1.put(str);
                }
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
    }
}
class Consumer implements Runnable{
    private static BlockingQueue<String> queue1;

    public Consumer(BlockingQueue<String> queue) {
        queue1=queue;
    }

    @Override
    public void run() {
        while (true){
            String string = queue1.peek();
//             if(string==null) {
//                 try {
//                     Thread.currentThread().wait();
//                 } catch (InterruptedException e) {
//                     e.printStackTrace();
//                 }
//             }

            if(string.length()<101){
                try {
                    //lessThanHundredChar.add(queue1.take());
                    queue1.take();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("Строка: " + string);
                System.out.println("Количество символов: "+ string.length());
                System.out.println("Название потока: "+Thread.currentThread().getName());
                System.out.println();
            }
            //else Thread.yield();
        }
    }
}

class Consumer2 implements Runnable{
    private static BlockingQueue<String> queue1;

    public Consumer2(BlockingQueue<String> queue) {
        queue1=queue;
    }

    @Override
    public void run() {
        while (true){
            String string = queue1.peek();
            // if(string==null)return;

            if(string.length()>100){
                try {
                    //lessThanHundredChar.add(queue1.take());
                    queue1.take();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("Строка: " + string);
                System.out.println("Количество символов: "+ string.length());
                System.out.println("Название потока: "+Thread.currentThread().getName());
                System.out.println();
            }
            //else Thread.yield();
        }

    }
}
