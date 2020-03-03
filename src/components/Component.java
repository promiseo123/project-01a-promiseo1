package components;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashSet;

public abstract class Component {
    private String name;
    protected Component source;
    protected LinkedHashSet<Component> loads=new LinkedHashSet<>();
    protected int draw=0;
    protected boolean engage=false;

    protected Component(String name, Component source) {
        this.name=name;
        this.source=source;
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
        Reporter.report(this, load, Reporter.Msg.ATTACHING );
    }

    protected int getDraw() {
        return this.draw;
    }

    protected Collection<Component> getLoads() {
        return Collections.unmodifiableCollection(this.loads);
    }

    protected void changeDraw(int delta) {
        this.draw+=delta;
        Reporter.report(this, Reporter.Msg.DRAW_CHANGE, delta);
        if(this.source!=null) {
            this.source.changeDraw(delta);
        }


    }

    protected void addLoad(Component newLoad) {
        loads.add(newLoad);
    }

    public void engage() {
        this.engage=true;
        Reporter.report(this, Reporter.Msg.ENGAGING);
        engageLoads();
    }

    public void disengage() {
        Reporter.report(this, Reporter.Msg.DISENGAGING);
        if(this.engaged()) {
            this.engage=false;
            disengageLoads();
        }

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
        System.out.println("");
        System.out.println("+ "+this.toString());
        for(Component load : this.loads) {
            System.out.print("    ");
            load.display();
        }
        System.out.println("");
    }

    public String toString() {
        return Reporter.identify(this);
    }

}
