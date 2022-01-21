public class SlaveScheduler extends AbstractScheduler {

    private final MasterScheduler supScheduler;

    public SlaveScheduler(MasterScheduler masterScheduler) {
        // 调用父类的构造方法
        super();
        this.supScheduler = masterScheduler;
    }

    public void createElevator(String id, String type) {
        Elevator elevator = new Elevator(supScheduler, this, id, type);
        super.putElevator(elevator);
        elevator.start();
    }

    @Override
    public synchronized LiftRequest popPerson() {
        while (isEmptyRequestPool()) {
            try {
                wait();
                if (isEnd()) {
                    return null;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        notifyAll();
        return super.popPerson();
    }

    @Override
    public boolean isEnd() {
        return super.isEnd() && supScheduler.isEnd();
    }

    @Override
    public void setEndInput() {
        super.setEndInput();
        notifyAllElevators();
    }

    public synchronized void notifyAllElevators() {
        notifyAll();
    }
}
