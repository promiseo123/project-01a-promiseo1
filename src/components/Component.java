package components;

import java.util.Collection;
import java.util.HashSet;

public abstract class Component {
    private String name;
    protected Component source;
    protected HashSet<Component> loads=new HashSet<>();
    protected int draw=0;
    protected boolean engage=false;

    protected Component(String name, Component source) {
        this.name=name;
        this.source=source;
        Reporter.report(this, Reporter.Msg.CREATING);
    }

    public String getName() {
        return this.name;
    }

    protected Component getSource() {
        return this.source;
    }

    protected void attach(Component load) {
        if(load.source.equals(this)) {
            if(this.engaged()) {
                load.engage();
            }
            addLoad(load);
        }
        Reporter.report(this, Reporter.Msg.ATTACHING);
    }

    protected int getDraw() {
        return this.draw;
    }

    protected Collection<Component> getLoads() {
        return this.loads;
    }

    protected void changeDraw(int delta) {
        this.draw+=delta;
        if(this.source!=null) {
            this.source.changeDraw(delta);
        }
        Reporter.report(this, Reporter.Msg.DRAW_CHANGE);
    }

    protected void addLoad(Component newLoad) {
        loads.add(newLoad);
    }

    public void engage() {
        if(!this.engaged()&&getSource()==null) {
            this.engage=true;
            for(Component load : this.loads) {
                load.engage();
                this.changeDraw(load.draw);
            }
        } else {
            this.source.engage();
        }
        Reporter.report(this, Reporter.Msg.ENGAGING);
    }

    public void disengage() {
        if(this.engaged()) {
            this.engage=false;
            for(Component load : this.loads) {
                load.disengage();
                this.changeDraw(-load.draw);
            }
        }
        Reporter.report(this, Reporter.Msg.DISENGAGING);

    }

    protected void setDraw(int draw) {
        this.draw=draw;
    }

    protected boolean engaged() {
        return this.engage;
    }

    protected void engageLoads() {
        for(Component load : this.loads) {
            load.engage();
        }
    }

    protected void disengageLoads() {
        for(Component load : this.loads) {
            load.disengage();
        }
    }

    public void display() {
        System.out.println("+ "+this.toString());
        for(Component load : this.loads) {
            System.out.print("    ");
            load.display();
        }
    }

    public String toString() {
        return Reporter.identify(this);
    }

}
