package components.testing;

import components.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * DESCRIPTION
 *
 * @author Promise Omiponle, poo9724@rit.edu
 */
public class Overload {

    private HashMap<String, Component> components = new HashMap<> ();

    public static final int BAD_ARGS = 1;
    public static final int FILE_NOT_FOUND = 2;
    public static final int BAD_FILE_FORMAT = 3;
    public static final int UNKNOWN_COMPONENT = 4;
    public static final int REPEAT_NAME = 5;
    public static final int UNKNOWN_COMPONENT_TYPE = 6;
    public static final int UNKNOWN_USER_COMMAND = 7;
    public static final int UNSWITCHABLE_COMPONENT = 8;

    private static final String WHITESPACE_REGEX = "\\s+";
    private static final String[] NO_STRINGS = new String[ 0 ];

    private static final String PROMPT = "? ";

    static {
        Reporter.addError(
                BAD_ARGS, "Usage: java components.Overload <configFile>" );
        Reporter.addError( FILE_NOT_FOUND, "Config file not found" );
        Reporter.addError( BAD_FILE_FORMAT, "Error in config file" );
        Reporter.addError(
                UNKNOWN_COMPONENT,
                "Reference to unknown component in config file"
        );
        Reporter.addError(
                REPEAT_NAME,
                "Component name repeated in config file"
        );
        Reporter.addError(
                UNKNOWN_COMPONENT_TYPE,
                "Reference to unknown type of component in config file"
        );
        Reporter.addError(
                UNKNOWN_USER_COMMAND,
                "Unknown user command"
        );
    }

    public void checkFormat(String[] component) {
        switch(component[0]) {
            case "Outlet":
                if(component.length!=3) {
                    Reporter.usageError(BAD_FILE_FORMAT, component.toString());
                }
        }
    }

    public void checkComp(String action) {
        if(!components.containsKey(action)) {
            Reporter.usageError(UNKNOWN_COMPONENT, action);
        }
    }

//    public void checkFile(String[] args) {
//    }

    public void createCircuit(String[] args) {
        try ( Scanner configFile = new Scanner( new File( args[0] ) ) ) {
            int things=0;
            String root=null;
            while ( configFile.hasNextLine() ) {
                String line = configFile.nextLine();
                String[] component = line.split(" ", 4);
                if(components.containsKey(component[1])) {
                    Reporter.usageError(REPEAT_NAME, component[1]);
                } else {
                    checkFormat(component);
                    switch (component[0]) {
                        case "Appliance":
                            components.put(component[1], new Appliance(component[1],
                                    components.get(component[2]), Integer.parseInt(component[3])));
                            things++;
                            break;
                        case "CircuitBreaker":
                            components.put(component[1], new CircuitBreaker(component[1],
                                    components.get(component[2]), Integer.parseInt(component[3])));
                            things++;
                            break;
                        case "Outlet":
                            components.put(component[1],
                                    new Outlet(component[1], components.get(component[2])));
                            things++;
                            break;
                        case "PowerSource":
                            components.put(component[1], new PowerSource(component[1]));
                            root=component[1];
                            things++;
                            break;
                        default:
                            Reporter.usageError(UNKNOWN_COMPONENT_TYPE, component);
                    }
                }

            }
            System.out.println(things+" components created.");
            System.out.println("Starting up the main circuit(s).");
            Reporter.report(this.components.get(root), Reporter.Msg.POWERING_UP);
            this.components.get(root).engage();
        } catch (FileNotFoundException e) {
            Reporter.usageError(FILE_NOT_FOUND, args);
        }
    }

    public void doCircuit() {
        Scanner input = new Scanner(System.in);

        boolean a=true;
        while(a) {
            System.out.print(PROMPT+" -> ");
            String command = input.nextLine();
            if(command.equals("display")) {
                components.get("Home").display();
            } else if(command.equals("quit")) {
                a=false;
            } else {
                String[] action=command.split(" ");
                if(action[0].equals("toggle")) {
                    checkComp(action[1]);
                    if(components.containsKey(action[1])) {
                        Switchable switchable = (Switchable)components.get(action[1]);
                        if(switchable.isSwitchOn()) {
                            switchable.turnOff();
                        } else {
                            switchable.turnOn();
                        }
                    } else {
                        Reporter.usageError(UNSWITCHABLE_COMPONENT, action);
                    }

                } else if(action[0].equals("connect")) {
                    if(components.containsKey(action[2])) {
                        Reporter.usageError(REPEAT_NAME, action[2]);
                    } else {
                        switch(action[1]) {
                            case "CircuitBreaker":
                                components.put(action[2], new CircuitBreaker(action[2],
                                        components.get(action[3]), Integer.parseInt(action[4])));
                                break;
                            case "Outlet":
                                components.put(action[2],
                                        new Outlet(action[2], components.get(action[3])));
                                break;
                            case "Appliance":
                                components.put(action[2], new Appliance(action[2],
                                        components.get(action[3]), Integer.parseInt(action[4])));
                                break;
                            default:
                                Reporter.usageError(UNKNOWN_COMPONENT_TYPE, action[1]);
                        }
                    }

                } else {
                    Reporter.usageError(UNKNOWN_USER_COMMAND, action);
                }
            }

        }
    }

    public static void main( String[] args ) {
        System.out.println( "Overload Project, CS2" );
        Overload overload = new Overload();
        overload.createCircuit(args);
        overload.doCircuit();

    }

}
