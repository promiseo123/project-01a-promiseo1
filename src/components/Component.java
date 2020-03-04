package components;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashSet;

/**
 * Component serves as a general representation of a Component type.
 *
 * @author Promise Omiponle, poo9724@rit.edu
 */
public abstract class Component {

    /** name represents the name of the Component. */
    private String name;

    /** source is a Component that represents the power source of the Component. */
    protected Component source;

    /** loads represents a collection of all Components connected to this Component. */
    protected LinkedHashSet<Component> loads=new LinkedHashSet<>();

    /** draw represents the amount of current passing through this Component. */
    protected int draw=0;

    /** engage describes whether the component is receiving or providing power. */
    protected boolean engage=false;

    /**
     * Component initializes the fields of the Component.
     * @param name the name of this Component
     * @param source the Component providing this one with power
     */
    protected Component(String name, Component source) {
        this.name=name;
        this.source=source;
    }

    /**
     * getName returns the name of this Component.
     * @return the name of the Component
     */
    public String getName() {
        return this.name;
    }

    protected Component getSource() {
        return this.source;
    }

    /**
     * attach connects a Component to this one.
     * @param load the Component to be attached
     */
    protected void attach(Component load) {
        if(load.source.equals(this)) {
            if(this.engaged()) {
                load.engage();
            }
            addLoad(load);
        }
        Reporter.report(this, load, Reporter.Msg.ATTACHING );
    }

    /**
     * getDraw returns the amount of current draw of this Component.
     * @return the amount of current currently passing through this Component
     */
    protected int getDraw() {
        return this.draw;
    }

    protected Collection<Component> getLoads() {
        return Collections.unmodifiableCollection(this.loads);
    }

    /**
     * changeDraw changes the current draw of this Component.
     * @param delta the change to be applied to the draw
     */
    protected void changeDraw(int delta) {
        this.draw+=delta;
        Reporter.report(this, Reporter.Msg.DRAW_CHANGE, delta);
        if(this.source!=null) {
            this.source.changeDraw(delta);
        }


    }

    /**
     * addLoad adds a new Component to this components loads list.
     * @param newLoad the new Component to be added
     */
    protected void addLoad(Component newLoad) {
        loads.add(newLoad);
    }

    /**
     * engage enables current to pass through this Component.
     */
    public void engage() {
        this.engage=true;
        Reporter.report(this, Reporter.Msg.ENGAGING);
        engageLoads();
    }

    /**
     * disengage disables current from passing through this Component.
     */
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

    /**
     * engaged returns a boolean specifying whether current is able to pass through
     * this Component.
     * @return a boolean describing if this Component is engaged or not
     */
    protected boolean engaged() {
        return this.engage;
    }

    /**
     * engageLoads passes engage calls to all the loads of this Component.
     */
    protected void engageLoads() {
        for(Component load : this.loads) {
            load.engage();
        }
    }

    /***/
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
