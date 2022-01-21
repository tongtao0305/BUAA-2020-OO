public class ThreadPrint {
    public static void main(String[] args) throws InterruptedException {
        Object a = new Object();
        Object b = new Object();
        MyThread threadA = new MyThread("A",b,a);
        MyThread threadB = new MyThread("B",a,b);
        new Thread(threadA).start();
        Thread.sleep(100);
        new Thread(threadB).start();
        Thread.sleep(100);
    }
}
