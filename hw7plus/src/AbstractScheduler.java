import com.oocourse.elevator3.PersonRequest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public abstract class AbstractScheduler {
    private boolean endInput;
    private final CopyOnWriteArrayList<Elevator> elevatorPool;
    private final CopyOnWriteArrayList<LiftRequest> requestPool;

    static final ArrayList<Integer> ELEVATOR_A = new ArrayList<>(Arrays.
            asList(-3, -2, -1, 1, 15, 16, 17, 18, 19, 20));
    static final ArrayList<Integer> ELEVATOR_B = new ArrayList<>(Arrays.
            asList(-2, -1, 1, 2, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15));
    static final ArrayList<Integer> ELEVATOR_C = new ArrayList<>(Arrays.
            asList(1, 3, 5, 7, 9, 11, 13, 15));
    private final HashMap<String, ArrayList<Integer>> stopFloors;

    public AbstractScheduler() {
        endInput = false;

        // 初始化电梯线程池和乘客请求池
        requestPool = new CopyOnWriteArrayList<>();
        elevatorPool = new CopyOnWriteArrayList<>();

        // 保存三种类型电梯可以停靠的楼层
        stopFloors = new HashMap<>();
        stopFloors.put("A", ELEVATOR_A);
        stopFloors.put("B", ELEVATOR_B);
        stopFloors.put("C", ELEVATOR_C);
    }

    public synchronized void putPerson(LiftRequest request) {
        requestPool.add(request);
        notifyAll();
    }

    public synchronized LiftRequest popPerson() {
        while (requestPool.isEmpty()) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        LiftRequest tmpRequest = requestPool.get(0);
        requestPool.remove(tmpRequest);
        return tmpRequest;
    }

    public synchronized CopyOnWriteArrayList<LiftRequest> popPersons(
            int curFloor, boolean upwards, int capacity) {
        int count = 0;

        // 查询所有相同起始楼层的请求
        CopyOnWriteArrayList<LiftRequest> inRequests;
        inRequests = new CopyOnWriteArrayList<>();

        for (LiftRequest request : requestPool) {
            int fromFloor = request.getFromFloor();
            int toFloor = request.getToFloor();
            if (count == capacity) {
                break;
            }
            if (fromFloor == curFloor) {
                boolean tmpUp = toFloor > fromFloor;
                boolean flag = (upwards & tmpUp) || (!upwards & !tmpUp);
                if (flag) {
                    inRequests.add(request);
                    count++;
                }
            }
        }

        for (LiftRequest tmpRequest : inRequests) {
            requestPool.remove(tmpRequest);
        }

        notifyAll();
        return inRequests;
    }

    public void putElevator(Elevator elevator) {
        elevatorPool.add(elevator);
    }

    public void startElevators() {
        for (Elevator elevator : elevatorPool) {
            elevator.start();
        }
    }

    public HashMap<String, ArrayList<Integer>> getStopFloors() {
        return stopFloors;
    }

    public boolean checkTransfer(PersonRequest request) {
        int fromFloor = request.getFromFloor();
        int toFloor = request.getToFloor();

        boolean throughA = ELEVATOR_A.contains(fromFloor) && ELEVATOR_A.contains(toFloor);
        boolean throughB = ELEVATOR_B.contains(fromFloor) && ELEVATOR_B.contains(toFloor);
        boolean throughC = ELEVATOR_C.contains(fromFloor) && ELEVATOR_C.contains(toFloor);

        return ((!throughA) && (!throughB) && (!throughC));
    }

    public boolean checkElevator(LiftRequest request,
                                 ArrayList<Integer> stopFloors) {

        int fromFloor = request.getFromFloor();
        int toFloor = request.getToFloor();
        return (stopFloors.contains(fromFloor) && stopFloors.contains(toFloor));
    }

    public void setEndInput() {
        this.endInput = true;
    }

    public boolean isEnd() {
        return endInput && requestPool.isEmpty();
    }

    public boolean isEmptyRequestPool() {
        return requestPool.isEmpty();
    }
}
