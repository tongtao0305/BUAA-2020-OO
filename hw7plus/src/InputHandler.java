import com.oocourse.elevator3.ElevatorInput;
import com.oocourse.elevator3.ElevatorRequest;
import com.oocourse.elevator3.PersonRequest;
import com.oocourse.elevator3.Request;

import java.io.IOException;

public class InputHandler extends Thread {
    private static volatile InputHandler inputThread;
    private final MasterScheduler scheduler;

    private InputHandler(MasterScheduler scheduler) {
        this.scheduler = scheduler;
    }

    public static InputHandler getInputHandler(MasterScheduler scheduler) {
        // 使用单例模式的双检锁校验，能在安全的前提下保持高性能
        if (inputThread == null) {
            synchronized (InputHandler.class) {
                if (inputThread == null) {
                    inputThread = new InputHandler(scheduler);
                }
            }
        }
        return inputThread;
    }

    @Override
    public void run() {
        ElevatorInput elevatorInput = new ElevatorInput(System.in);
        while (true) {
            Request request = elevatorInput.nextRequest();
            if (request == null) {
                scheduler.setEndInput();
                break;
            } else {
                if (request instanceof PersonRequest) {
                    scheduler.putPerson((PersonRequest) request);
                } else if (request instanceof ElevatorRequest) {
                    scheduler.putElevator((ElevatorRequest)request);
                }
            }
        }

        try {
            elevatorInput.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
