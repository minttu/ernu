package fi.imberg.juhani.ernu.cli.exceptions;

public class FileNotFoundException extends CLIException {
    public FileNotFoundException(String fileName) {
        super("The file \"" + fileName + "\" wasn't found.");
    }
}
