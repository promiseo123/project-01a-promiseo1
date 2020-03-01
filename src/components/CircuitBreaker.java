package components;

public class CircuitBreaker extends Switchable {
    private int limit;

    public CircuitBreaker(String name, PowerSource source, int limit) {
        super(name, source);
        this.limit=limit;
        Reporter.report(this, Reporter.Msg.CREATING);
    }

    public int getLimit(){
        return this.limit;
    }

    @Override
    public void changeDraw(int delta) {
        this.draw+=delta;
        if(this.draw<=limit) {
            if (this.source != null) {
                this.source.changeDraw(delta);
            }
        } else {
            this.turnOff();
            this.disengageLoads();
            }
        }
    }


