package components;

public class Switchable extends Component {
    protected boolean isOn=false;

    public Switchable(String name, Component source) {
        super(name, source);
    }

    @Override
    public void engageLoads() {
        if(this.isOn) {
            super.engageLoads();
        }

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

        if(this.engaged()) {
            this.isOn=true;
            Reporter.report(this, Reporter.Msg.SWITCHING_ON);
            engageLoads();
//            this.source.changeDraw(this.draw);
        }

    }

    public void turnOff() {
        this.isOn=false;
        Reporter.report(this, Reporter.Msg.SWITCHING_OFF);
        if(this.engaged()) {
            this.source.changeDraw(-this.draw);
            this.draw-=this.draw;
        }

    }

    public boolean isSwitchOn() {
        return this.isOn;
    }


}
