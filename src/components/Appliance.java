package components;

public class Appliance extends Switchable {
    private int rating;

    public Appliance(String name, Outlet source, int rating) {
        super(name, source);
        this.rating=rating;
        Reporter.report(this, Reporter.Msg.CREATING);
        this.loads=null;
    }

    public int getRating() {
        return this.rating;
    }
}
