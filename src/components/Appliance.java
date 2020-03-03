package components;

public class Appliance extends Switchable {
    private int rating;

    public Appliance(String name, Component source, int rating) {
        super(name, source);
        Reporter.report(this, Reporter.Msg.CREATING);
        this.source.attach(this);
        setRating(rating);
    }

    public int getRating() {
        return this.rating;
    }

    public void setRating(int rating) {
        this.rating=rating;
    }

    @Override
    public void engage() {
        this.engage = true;
        Reporter.report(this, Reporter.Msg.ENGAGING);
        if(this.isOn) {
            Reporter.report(this, Reporter.Msg.DRAW_CHANGE, this.rating);
            this.source.changeDraw(this.rating);
        }
    }

    @Override
    public void disengage() {
        Reporter.report(this, Reporter.Msg.DISENGAGING);
        if(this.engaged()) {
            this.engage = false;
            Reporter.report(this, Reporter.Msg.DRAW_CHANGE, -this.rating);
            this.source.changeDraw(-this.rating);
        }
    }

    @Override
    public void turnOn() {
        this.isOn=true;
        Reporter.report(this, Reporter.Msg.SWITCHING_ON);
        if(this.engaged()) {
            this.source.changeDraw(this.rating);
        }

    }
    @Override
    public void turnOff() {
        this.isOn=false;
        Reporter.report(this, Reporter.Msg.SWITCHING_OFF);
        if(this.engaged()) {
            this.source.changeDraw(-this.rating);
        }
    }

    @Override
    public void display() {
        if(this.source.source!=null) {
            System.out.print("      ");
        }
        System.out.print("      ");
        System.out.println("+ "+this.toString());

        }
}
