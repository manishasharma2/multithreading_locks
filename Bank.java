import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Bank{

    private int balance = 100;

    private final Lock lock = new ReentrantLock();

    public void withdraw(int amount){
        System.out.println(Thread.currentThread().getName() + " amepting to withdraw " + amount );
        try{
            if(lock.tryLock(1000,TimeUnit.MILLISECONDS)){
                if(balance >= amount){
                    try{
                        System.err.println(Thread.currentThread().getName() + " proceeding to withdrawal");
                        Thread.sleep(3000);
                        balance -= amount;
                        System.out.println(Thread.currentThread().getName()+" completed withdrawal. Remaining balance is "+ balance);
                    }
                    catch(Exception e){
                        Thread.currentThread().interrupt();
                    }
                    finally{
                        lock.unlock();
                    }
                }else{
                    System.out.println(Thread.currentThread().getName() +" insufficient balance");
                }
            }else{
                System.out.println("Could not acquire the lock... try later");
            }
        }
        catch(Exception e){
            Thread.currentThread().interrupt();
        }
       
    }
}