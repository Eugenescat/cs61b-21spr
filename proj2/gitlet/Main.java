package gitlet;

import net.sf.saxon.functions.ConstantFunction;

import java.util.Arrays;
import java.util.List;

/** Driver class for Gitlet, a subset of the Git version-control system.
 *  @author TODO
 */
public class Main {

    /** Usage: java gitlet.Main ARGS, where ARGS contains
     *  <COMMAND> <OPERAND1> <OPERAND2> ... 
     */
    public static void main(String[] args) {
        // TODO: what if args is empty?
        if (args.length == 0) {
            System.out.println("Please enter a command.");
            return;
        }
        String firstArg = args[0];
        switch(firstArg) {
            case "init":
                // handle the `init` command
                validateNumArgs("init", args, 1);
                try {
                    Repository repo = new Repository("init");
                    repo.makeInitialCommit("initial commit");
                } catch (RuntimeException e) {
                    System.out.println(e.getMessage());
                    return;
                }
                break;
            case "add":
                // handle the `add [filename]` command
                validateNumArgs("add", args, 2);
                try {
                    Repository.getInstance().addFile(args[1]);
                } catch (RuntimeException e) {
                    System.out.println(e.getMessage());
                    return;
                }
                break;
            case "commit":
                // handle the `commit [message]` command
                validateNumArgs("commit", args, 2);
                try {
                    Repository.getInstance().makeCommit(args[1]);
                } catch (RuntimeException e) {
                    System.out.println(e.getMessage());
                    return;
                }
                break;
            case "checkout":
                // TODO: handle the `checkout [file name]` command

                // TODo: handle the `checkout [commit id] -- [file name]` command

                // TODO: handle the `checkout [branch name]` command

            case "log":
                // handle the `log` command
                validateNumArgs("log", args, 1);
                Repository.getInstance().printLog();

                // ------------above is checkpoint---------------

            case "rm":
                // handle the `rm [file name]` command
                validateNumArgs("rm", args, 2);
                try {
                    Repository.getInstance().removeFile(args[1]);
                } catch (RuntimeException e) {
                    System.out.println(e.getMessage());
                    return;
                }
            case "global-log":
                // ToDo: handle the `global-log` command
                validateNumArgs("global-log", args, 1);
                Repository.getInstance().printGlobalLog();
            case "find":
                // ToDo: handle the `find [commit message]` command
            case "status":
                // Todo: handle the `status` command
            case "branch":
                // Todo: handle the `branch [branch name]` command
            case "rm-branch":
                // Todo: handle the `rm-branch [branch name]` command
            case "reset":
                // Todo: handle the `reset [commit id]` command
            case "merge":
                // Todo: handle the `merge [branch name]` command
            default :
                System.out.println("No command with that name exists.");
        }
    }

    public static void validateNumArgs(String cmd, String[] args, int n) {
        if (args.length != n) {
            throw new RuntimeException(
                    String.format("Invalid number of arguments for: %s.", cmd));
        }
    }
}
