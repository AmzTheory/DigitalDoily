/**
 * this class is intented for the exception that
 * could occur if the user forgot to select the
 * tool
 */
public class NoToolException extends Throwable {
    public NoToolException(){
        super("make sure you have choose Tool");
    }
}
