package components;

public class Switchable extends Component {
    protected boolean isOn;

    public Switchable(String name, Component source) {
        super(name, source);
        Reporter.report(this, Reporter.Msg.CREATING);
    }

    @Override
    public void engage() {
        if(!this.engaged()&&getSource()==null) {
            this.engage=true;
            if(this.isOn) {
                for(Component load : this.loads) {
                    load.engage();
                    this.changeDraw(load.getDraw());
                }
            }
        } else {
            this.source.engage();
        }
        Reporter.report(this, Reporter.Msg.ENGAGING);
    }

    @Override
    public void disengage() {
        if(this.engaged()) {
            this.engage=false;
            if(this.isOn) {
                for(Component load : this.loads) {
                    load.disengage();
                    this.changeDraw(-load.getDraw());
                }
            }
        }
        Reporter.report(this, Reporter.Msg.DISENGAGING);
    }

    public void turnOn() {
        this.isOn=true;
        if(this.engaged()) {
            this.source.changeDraw(this.draw);
        }
        Reporter.report(this, Reporter.Msg.SWITCHING_ON);
    }

    public void turnOff() {
        this.isOn=false;
        if(this.engaged()) {
            this.source.changeDraw(-this.draw);
        }
        Reporter.report(this, Reporter.Msg.SWITCHING_OFF);
    }

    public boolean isSwitchOn() {
        return this.isOn;
    }

}
