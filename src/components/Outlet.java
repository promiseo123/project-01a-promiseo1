package components;

public class Outlet extends Component {
    public Outlet(String name, CircuitBreaker source) {
        super(name, source);
        Reporter.report(this, Reporter.Msg.CREATING);
        this.source.attach(this);
    }

    @Override
    public void display() {
        if(this.source.source!=null) {
            System.out.print("        ");
        }
//        System.out.print("      ");
        System.out.println("+ "+this.toString());
        for(Component load : this.loads) {

            load.display();
        }
    }
}
