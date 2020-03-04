package components;

public class Outlet extends Component {
    public Outlet(String name, Component source) {
        super(name, source);
        Reporter.report(this, Reporter.Msg.CREATING);
        this.source.attach(this);
    }

    protected void attach(Component load) {
        Reporter.report(this, load, Reporter.Msg.ATTACHING );
        if(load.source.equals(this)) {
            if(this.engaged()) {

                load.engage();
            }
            addLoad(load);
        }
    }

    @Override
    public void display() {
        if(this.source.source!=null) {
            System.out.print("        ");
        }
        System.out.println("+ "+this.toString());
        for(Component load : this.loads) {

            load.display();
        }
    }
}
