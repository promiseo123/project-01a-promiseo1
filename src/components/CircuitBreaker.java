package components;

public class CircuitBreaker extends Switchable {
    private int limit;

    public CircuitBreaker(String name, PowerSource source, int limit) {
        super(name, source);
        Reporter.report(this, Reporter.Msg.CREATING);
        this.source.attach(this);
        setLimit(limit);

    }

    public int getLimit(){
        return this.limit;
    }

    public void setLimit(int limit) {
        this.limit=limit;
    }

    @Override
    public void engage() {
        this.engage=true;
        Reporter.report(this, Reporter.Msg.ENGAGING);
        if(this.isOn) {
            engageLoads();
        }
    }

    @Override
    public void changeDraw(int delta) {
        if (this.isOn) {
            this.draw += delta;
            if (this.draw <= limit) {
                if (this.source != null) {
                    Reporter.report(this, Reporter.Msg.DRAW_CHANGE, delta);
                    this.source.changeDraw(delta);

                }
            } else {
                Reporter.report(this, Reporter.Msg.BLOWN, this.draw);
                this.turnOff(delta);
                disengageLoads();
            }
        }
    }

    public void turnOff(int delta) {
        this.isOn=false;
        if(this.engaged()) {
            Reporter.report(this, Reporter.Msg.SWITCHING_OFF);
             if(this.draw>limit) {
                this.draw-=delta;
            }
            this.source.changeDraw(-this.draw);
        }
        this.draw-=this.draw;
    }

    @Override
    public void display() {
        System.out.println("+ "+this.toString());
        for(Component load : this.loads) {
            load.display();
        }
    }
    }


