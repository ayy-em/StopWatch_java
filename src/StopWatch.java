import javax.swing.*;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import static java.lang.Thread.interrupted;
import static java.lang.Thread.sleep;

public abstract class StopWatch extends SwingWorker<Long, Long> {
    
    int msToGive = 0;
    int secToGive = 0;
    int minToGive = 0;
    private AtomicBoolean paused = new AtomicBoolean(false);

    @Override
    protected Long doInBackground() throws Exception {

        long timeTemp = -System.currentTimeMillis();
        boolean paused = false;

        while (!Thread.interrupted()) {

            if (this.paused.get()) {
                if (!paused) {
                    timeTemp = System.currentTimeMillis() + timeTemp;
                    paused = true;
                }
            } else {
                if (paused) {
                    timeTemp = timeTemp - System.currentTimeMillis();
                    paused = false;

                }
                long timeFin = System.currentTimeMillis();
                timeFin = timeFin + timeTemp;
                publish(timeFin);
            }

            sleep(10);
        }

        return System.currentTimeMillis() + timeTemp;
    }

    @Override
    protected void process(List<Long> chunks) {
        super.process(chunks);
        long hz = chunks.stream().max(Long::compare).get();
        process(hz);
    }

    protected abstract void process(long value);

    public boolean isPaused() {
        return paused.get();
    }

    public void setPaused(boolean paused) {
        this.paused.set(paused);
    }

}
