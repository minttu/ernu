package fi.imberg.juhani.ernu.cli.exceptions;

/**
 * Thrown when a file was not found.
 */
public class FileNotFoundException extends CLIException {
    public FileNotFoundException(String fileName) {
        super("The file \"" + fileName + "\" wasn't found.");
    }
}
