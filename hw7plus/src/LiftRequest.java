import com.oocourse.elevator3.PersonRequest;

import java.util.ArrayList;
import java.util.Arrays;

public class LiftRequest {
    private final PersonRequest personRequest;
    private final int startFloor;
    private final int endFloor;
    private int midFloor;
    private final boolean needTransfer;
    private boolean isTransfered;

    private static final ArrayList<Integer> ELEVATOR_A = new ArrayList<>(Arrays.
            asList(-3, -2, -1, 1, 15, 16, 17, 18, 19, 20));
    private static final ArrayList<Integer> ELEVATOR_B = new ArrayList<>(Arrays.
            asList(-2, -1, 1, 2, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15));
    private static final ArrayList<Integer> ELEVATOR_C = new ArrayList<>(Arrays.
            asList(1, 3, 5, 7, 9, 11, 13, 15));

    public LiftRequest(PersonRequest personRequest) {
        this.personRequest = personRequest;

        int fromFloor = personRequest.getFromFloor();
        int toFloor = personRequest.getToFloor();

        boolean throughA = ELEVATOR_A.contains(fromFloor) && ELEVATOR_A.contains(toFloor);
        boolean throughB = ELEVATOR_B.contains(fromFloor) && ELEVATOR_B.contains(toFloor);
        boolean throughC = ELEVATOR_C.contains(fromFloor) && ELEVATOR_C.contains(toFloor);

        needTransfer = (!throughA) && (!throughB) && (!throughC);
        isTransfered = false;

        startFloor = fromFloor;
        endFloor = toFloor;

        if (needTransfer) {
            if (toFloor >= 10) {
                midFloor = 15;
            } else {
                midFloor = 1;
            }
        }

    }

    public PersonRequest getRequest() {
        return personRequest;
    }

    public int getPersonId() {
        return personRequest.getPersonId();
    }

    public boolean getNeedTransfer() {
        return needTransfer;
    }

    public boolean getIsTransfered() {
        return isTransfered;
    }

    public int getFromFloor() {
        if (needTransfer && isTransfered) {
            return midFloor;
        } else {
            return startFloor;
        }
    }

    public int getToFloor() {
        if (needTransfer && !isTransfered) {
            return midFloor;
        } else {
            return endFloor;
        }
    }

    public void setTransfer() {
        isTransfered = true;
    }
}
