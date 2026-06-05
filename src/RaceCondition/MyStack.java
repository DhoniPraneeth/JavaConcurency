package RaceCondition;

//Race Condition -used synchronization
public class MyStack<T> {
    private T[] a;
    private int capacity;
    private int top;
    public MyStack(int capacity){
        this.capacity=capacity;
        this.a=(T[])new Object[capacity];
        this.top=-1;
    }
    public T pop(){
        synchronized (this) {
            if(top==-1){
                System.out.println("Stack is empty");
                return null;
            }
            try{
                Thread.sleep(100);
            }catch (InterruptedException e){
                System.out.println(e.getMessage());
            }
            T x=a[top];
            a[top]=null;
            top--;
            System.out.println("Removed element"+x);
            return x;
        }
    }
    public boolean push(T x){
        synchronized (this){
            if(top==capacity-1){
                System.out.println("Stack is full");
                return false;
            }
            try{
                Thread.sleep(100);
            }catch (InterruptedException e){
                System.out.println(e.getMessage());
            }
            ++top;
            a[top]=x;
            System.out.println("Element added into stack ");
            return true;
        }
    }

    public static void main(String[] args) {
        MyStack<Integer> st=new MyStack<>(5);
        new Thread(()->{
            for(int i=0;i<5;i++){
                int cnt=0;
                while(cnt<50) {
                    st.push(10);
                    cnt++;
                }
            }
        },"Push").start();
        new Thread(()->{
            for(int i=0;i<5;i++){
                int cnt=0;
                while(cnt<50) {
                    st.push(10);
                    cnt++;
                }
            }
        },"Push1").start();
        new Thread(()->{
            for(int i=0;i<5;i++) {
                int cn = 0;
                while (cn < 50) {
                    st.pop();
                    cn++;
                }
            }
        },"pop").start();
        new Thread(()->{
            for(int i=0;i<5;i++) {
                int cn = 0;
                while (cn < 50) {
                    st.pop();
                    cn++;
                }
            }
        },"pop1").start();
    }
}
