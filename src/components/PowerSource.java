package components;

public class PowerSource extends Component {
    private boolean engage=false;

    public PowerSource(String name) {
        super(name, null);
        Reporter.report(this, Reporter.Msg.CREATING);

    }
}
