package fi.imberg.juhani.ernu.cli.command;

import fi.imberg.juhani.ernu.cli.exceptions.FileNotFoundException;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public abstract class FileCommand implements Command {
    public String fileToString(String fileName) throws FileNotFoundException {
        byte[] bytes = new byte[0];
        try {
            bytes = Files.readAllBytes(Paths.get(fileName));
        } catch (IOException ex) {
            throw new FileNotFoundException(fileName);
        }
        return new String(bytes, StandardCharsets.UTF_8);
    }
}
