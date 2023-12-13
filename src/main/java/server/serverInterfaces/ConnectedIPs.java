package server.serverInterfaces;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;
@Getter
public final class ConnectedIPs {
    private final List<String> connectedIPs = new ArrayList<>();
    static Semaphore semaphore = new Semaphore(1);
    public void addIP(String ip) throws InterruptedException {
        while(!semaphore.tryAcquire()){
            wait();
        }
        connectedIPs.add(ip);
        semaphore.release();
    }

    public void removeIP(String ip) throws InterruptedException {
        while(!semaphore.tryAcquire()){
            wait();
        }
        connectedIPs.remove(ip);
        semaphore.release();
    }

}
