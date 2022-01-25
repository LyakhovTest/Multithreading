package finalVersion;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;

public class Consumer2 implements Runnable {
    private BlockingQueue<String> buffer;
    private List<String> stringsLessHundred = new ArrayList<>();
    private Map<String,List<String>> listsOfString;

    public Consumer2(BlockingQueue<String> buffer, Map<String,List<String>> listsOfString) {
        this.buffer = buffer;
        this.listsOfString = listsOfString;
    }

    @Override
    public void run() {

        while (true) {
            synchronized (buffer) {
                if (buffer.isEmpty()) {
                    continue;
                }
                if (buffer.peek().equals(Main.EOF)) {
                    System.out.println(Thread.currentThread().getName() + " exiting.");
                    listsOfString.put(Thread.currentThread().getName(), stringsLessHundred);
                    break;
                } else {
                    if (buffer.peek().length() > 100) {
                        try {
                            String s = buffer.take();
                            System.out.println(Thread.currentThread().getName() + " - " + s);
                            stringsLessHundred.add(s);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }
}