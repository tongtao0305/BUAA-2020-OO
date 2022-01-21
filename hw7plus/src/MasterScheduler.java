import com.oocourse.TimableOutput;
import com.oocourse.elevator3.ElevatorRequest;
import com.oocourse.elevator3.PersonRequest;

import java.util.concurrent.CopyOnWriteArrayList;

public class MasterScheduler extends AbstractScheduler {

    private final SlaveScheduler subSchedulerA;
    private final SlaveScheduler subSchedulerB;
    private final SlaveScheduler subSchedulerC;
    private final CopyOnWriteArrayList<PersonRequest> transferPool;

    public MasterScheduler() {
        // 调用父类的构造方法
        super();

        // 更新时间戳，并启动电梯进程
        TimableOutput.initStartTimestamp();

        // 初始化乘客请求池
        transferPool = new CopyOnWriteArrayList<>();

        // 初始化三种类型电梯的从调度器
        subSchedulerA = new SlaveScheduler(this);
        subSchedulerB = new SlaveScheduler(this);
        subSchedulerC = new SlaveScheduler(this);

        // 添加初始的三部电梯
        subSchedulerA.createElevator("A", "A");
        subSchedulerB.createElevator("B", "B");
        subSchedulerC.createElevator("C", "C");
    }

    public void putElevator(ElevatorRequest request) {
        String id = request.getElevatorId();
        String type = request.getElevatorType();
        switch (type) {
            case "A":
                subSchedulerA.createElevator(id, type);
                break;
            case "B":
                subSchedulerB.createElevator(id, type);
                break;
            case "C":
                subSchedulerC.createElevator(id, type);
                break;
            default:
                System.out.println("Wrong Elevator Type!");
        }
    }

    public synchronized void putPerson(PersonRequest request) {
        LiftRequest tmpRequest = new LiftRequest(request);
        // 如果需要换乘，则加入换乘专属队列
        if (checkTransfer(request)) {
            transferPool.add(request);
        }
        // 将请求分配至从调度器，宏观调度
        if (checkElevator(tmpRequest, ELEVATOR_A)) {
            subSchedulerA.putPerson(tmpRequest);
            //subSchedulerA.notifyAllElevators();
        } else if (checkElevator(tmpRequest, ELEVATOR_B)) {
            subSchedulerB.putPerson(tmpRequest);
            //subSchedulerB.notifyAllElevators();
        } else if (checkElevator(tmpRequest, ELEVATOR_C)) {
            subSchedulerC.putPerson(tmpRequest);
            //subSchedulerC.notifyAllElevators();
        } else {
            System.out.println("PersonRequest failed!");
        }
    }

    @Override
    public synchronized void putPerson(LiftRequest request) {
        transferPool.remove(request.getRequest());
        // 将请求分配至从调度器，宏观调度
        if (checkElevator(request, ELEVATOR_A)) {
            subSchedulerA.putPerson(request);
        } else if (checkElevator(request, ELEVATOR_B)) {
            subSchedulerB.putPerson(request);
        } else if (checkElevator(request, ELEVATOR_C)) {
            subSchedulerC.putPerson(request);
        } else {
            System.out.println("PersonRequest failed!");
        }
    }

    @Override
    public void setEndInput() {
        super.setEndInput();
        subSchedulerA.setEndInput();
        subSchedulerB.setEndInput();
        subSchedulerC.setEndInput();
        notifyAllElevators();
    }

    @Override
    public boolean isEnd() {
        return super.isEnd() && transferPool.isEmpty();
    }

    public void notifyAllElevators() {
        subSchedulerA.notifyAllElevators();
        subSchedulerB.notifyAllElevators();
        subSchedulerC.notifyAllElevators();
    }
}