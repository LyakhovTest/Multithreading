package finalVersion;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.concurrent.BlockingQueue;

public class Producer implements Runnable {
    private BlockingQueue<String> buffer;

    public Producer(BlockingQueue<String> buffer) {
        this.buffer = buffer;
    }

    @Override
    public void run() {
        String src = "C:\\Users\\Igorj\\Desktop\\Стажер\\Multithreading\\src\\text.txt";
            Path path = Path.of(src);
        List<String> list = null;
        try {
            list = Files.readAllLines(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (String number : list) {
            {
                try {
                    buffer.put(number);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        synchronized (buffer) {
            try {
                buffer.put(Main.EOF);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}