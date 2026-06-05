package ProducerConsumer;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.lang.*;

public class BlockingQueue<T> {
    private Queue<T> q;
    int capacity;
    public BlockingQueue(int capacity){
        this.q=new ArrayDeque<>();
        this.capacity=capacity;
    }
    public boolean add(T x) throws InterruptedException {
        synchronized (q){
            if(q.size()==capacity){
                System.out.println("Queue is FUll - waiting");
                q.wait();
            }
            q.offer(x);
            q.notifyAll();
        }
        return true;
    }
    public T remove() throws InterruptedException {
        synchronized (q){
            if(q.isEmpty()){
                System.out.println("Queue is empty - waiting");
                q.wait();
            }
            T x=q.poll();
            q.notifyAll();
            return x;
        }
    }

    public static void main(String[] args) {
        BlockingQueue<Integer> q=new BlockingQueue<>(5);
        new Thread(()->{
            int cnt=0;
            while(cnt<10){
                try {
                    System.out.println("Element added?:"+cnt+" "+(q.add(cnt)?"Yes":"No"));
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                cnt++;
            }
        },"Enqueue").start();
        new Thread(()->{
            int cnt=0;
            while(cnt<5){
                try{
                    System.out.println(q.remove());
                }catch (InterruptedException e){
                    System.out.println(e.getMessage());
                }
                cnt++;
            }
        },"Dequeue").start();

    }
}