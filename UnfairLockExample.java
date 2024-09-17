import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
// Locks are acquired in random order not on the basis of FIFS
public class UnfairLockExample {

    // If you want fairness than use below line
    // private final Lock lock = new ReentrantLock(true);   starvation is avoided. or sleep the current thread for some time if you want your threads processed in the same way you entered them.
    private final Lock unfairLock = new ReentrantLock();

    public void accessResources(){
        unfairLock.lock();
        try{
            System.out.println(Thread.currentThread().getName() + " acquired the lock");
            Thread.sleep(1000);
        }catch(InterruptedException e){
            Thread.currentThread().interrupt();
        }
        finally{
            System.out.println(Thread.currentThread().getName()+" released the lock");
            unfairLock.unlock();
        }
    }

    public static void main(String args[]){

        UnfairLockExample example = new UnfairLockExample();

        Runnable task = new Runnable() {
            @Override
            public void run(){
                example.accessResources();
            }
        };

        Thread t1 = new Thread(task,"Thread 1");
        Thread t2 = new Thread(task,"Thread 2");
        Thread t3 = new Thread(task,"Thread 3");

        t1.start();
        t2.start();
        t3.start();
    }
}
