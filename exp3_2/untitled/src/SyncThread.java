class SyncThread implements Runnable {
    private static int count;

    public SyncThread() {
        count = 0;
    }

    @Override
    public synchronized void run() {
        for (int i = 0; i < 5; i++) {
            try {
                System.out.println(Thread.currentThread().getName()
                        + ":" + (count++));
                this.wait(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}