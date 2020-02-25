package components.testing;

import components.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * DESCRIPTION
 *
 * @author YOUR NAME HERE
 */
public class Overload {

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

    public static void main( String[] args ) {
        System.out.println( "Overload Project, CS2" );
    }

}
