public class MainClass {
    public static void main(String[] args) {
        // 创建通信进程并且启动电梯进程（消费者）
        MasterScheduler scheduler = new MasterScheduler();
        scheduler.startElevators();

        // 创建并启动输入进程（生产者）
        InputHandler inputThread = InputHandler.getInputHandler(scheduler);
        inputThread.start();
    }
}
