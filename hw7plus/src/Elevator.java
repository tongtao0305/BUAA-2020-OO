import com.oocourse.TimableOutput;
import com.oocourse.elevator3.ElevatorRequest;

import java.util.concurrent.CopyOnWriteArrayList;

public class Elevator extends Thread {
    private final MasterScheduler supScheduler;
    private final SlaveScheduler subScheduler;
    private final CopyOnWriteArrayList<LiftRequest> requestPool;

    private final String id;
    private final String type;
    private int speed;
    private int maxCapacity;

    private int curFloor;
    private boolean upwards;

    public Elevator(MasterScheduler supScheduler, SlaveScheduler subScheduler,
                    String id, String type) {
        this.supScheduler = supScheduler;
        this.subScheduler = subScheduler;
        requestPool = new CopyOnWriteArrayList<>();

        this.id = id;
        this.type = type;
        this.curFloor = 1;
        this.upwards = true;

        updateTypeInformation(this.type);
    }

    private void updateTypeInformation(String type) {
        switch (type) {
            case "A":
                speed = 400;
                maxCapacity = 6;
                break;
            case "B":
                speed = 500;
                maxCapacity = 8;
                break;
            case "C":
                speed = 600;
                maxCapacity = 7;
                break;
            default:
                System.out.println("Wrong Elevator Type!");
        }
    }

    @Override
    public void run() {
        while (!(subScheduler.isEnd() && requestPool.isEmpty())) {
            if (requestPool.isEmpty()) {
                // 如果电梯内部没有请求，从 subScheduler 获取主请求
                LiftRequest tmpRequest = subScheduler.popPerson();
                if (tmpRequest == null) {
                    continue;
                }
                requestPool.add(tmpRequest);

                CopyOnWriteArrayList<LiftRequest> inRequests;
                inRequests = subScheduler.popPersons(
                        tmpRequest.getFromFloor(),
                        (tmpRequest.getToFloor() > tmpRequest.getFromFloor()),
                        maxCapacity - requestPool.size());
                requestPool.addAll(inRequests);

                // 若主请求不在当前楼层，直接前往主请求的起始楼层，不管捎带请求
                gotoFloor(tmpRequest.getFromFloor(), true);
                responseRequests(false);
            }
            // 前往主请求的目标楼层
            gotoFloor(requestPool.get(0).getToFloor(), false);
        }
        // 若电梯运行结束，通知其他等待中的电梯检查是否需要结束
        supScheduler.notifyAllElevators();
    }

    private void gotoFloor(int tmpFloor, boolean goDirectly) {
        int i;
        if (tmpFloor > curFloor) {
            // 电梯向上运行
            upwards = true;
            for (i = curFloor + 1; i <= tmpFloor; i++) {
                takeSleep(speed);
                if (i == 0) {
                    i++;
                }
                curFloor = i;
                TimableOutput.println(String.format("ARRIVE-%d-%s", i, id));
                if (!goDirectly &&
                        subScheduler.getStopFloors().get(type).contains(i)) {
                    responseRequests(true);
                }
            }
        } else if (tmpFloor < curFloor) {
            // 电梯向下运行
            upwards = false;
            for (i = curFloor - 1; i >= tmpFloor; i--) {
                takeSleep(speed);
                if (i == 0) {
                    i--;
                }
                curFloor = i;
                TimableOutput.println(String.format("ARRIVE-%d-%s", i, id));
                if (!goDirectly &&
                        subScheduler.getStopFloors().get(type).contains(i)) {
                    responseRequests(true);
                }
            }
        }
    }

    private void responseRequests(boolean mainResponded) {
        // 找出所有在当前楼层需要上下的请求
        CopyOnWriteArrayList<LiftRequest> outRequests = getOutRequests();
        CopyOnWriteArrayList<LiftRequest> inRequests;
        inRequests = subScheduler.popPersons(curFloor, upwards,
                maxCapacity - requestPool.size());

        if (outRequests.isEmpty() && inRequests.isEmpty() && mainResponded) {
            return;
        }

        TimableOutput.println(String.format("OPEN-%d-%s", curFloor, id));
        takeSleep(400);

        for (LiftRequest tmpRequest : outRequests) {
            TimableOutput.println(String.format("OUT-%d-%d-%s",
                    tmpRequest.getPersonId(), curFloor, id));
            requestPool.remove(tmpRequest);

            // 对需要换乘的请求进行进一步处理
            if (tmpRequest.getNeedTransfer()) {
                if (!tmpRequest.getIsTransfered()) {
                    tmpRequest.setTransfer();
                    supScheduler.putPerson(tmpRequest);
                }
            }
        }

        if (!mainResponded) {
            for (LiftRequest tmpRequest : requestPool) {
                TimableOutput.println(String.format("IN-%d-%d-%s",
                        tmpRequest.getPersonId(), curFloor, id));
            }
        }

        for (LiftRequest tmpRequest : inRequests) {
            TimableOutput.println(String.format("IN-%d-%d-%s",
                    tmpRequest.getPersonId(), curFloor, id));
            requestPool.add(tmpRequest);
        }

        TimableOutput.println(String.format("CLOSE-%d-%s", curFloor, id));
    }

    private CopyOnWriteArrayList<LiftRequest> getOutRequests() {
        // 查询所有到达该楼层的请求
        CopyOnWriteArrayList<LiftRequest> outRequests;
        outRequests = new CopyOnWriteArrayList<>();

        for (LiftRequest tmpRequest : requestPool) {
            if (tmpRequest.getToFloor() == curFloor) {
                outRequests.add(tmpRequest);
            }
        }

        return outRequests;
    }

    private synchronized void takeSleep(int time) {
        try {
            sleep(time);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}