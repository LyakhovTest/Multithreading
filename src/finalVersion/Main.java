package finalVersion;

import java.util.*;
import java.util.concurrent.*;

public class Main {
    public static final String EOF = "EOF";
    public static void main(String[] args) {
        BlockingQueue<String> buffer = new ArrayBlockingQueue<>(8);
        Map<String,List<String>> listsOfString = new HashMap();

        ExecutorService executorService = Executors.newCachedThreadPool();
        executorService.submit(new Producer(buffer));
        executorService.submit(new Consumer(buffer, listsOfString));
        executorService.submit(new Consumer2(buffer, listsOfString));
        executorService.shutdown();
        try {
            executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        for(Map.Entry<String,List<String>> entry:listsOfString.entrySet()){
            int numOfWords = 0;
            for(String str: entry.getValue()) {
                int blockCount = str.split(" +").length;
                numOfWords+=blockCount;
            }
            System.out.println(entry.getKey()+" включает в себя строк: "+entry.getValue().size()+"! А количество слов в списке: "+ numOfWords);
        }
    }
}