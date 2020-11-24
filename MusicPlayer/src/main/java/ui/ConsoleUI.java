package ui;

import dao.UI;
import org.jline.builtins.Completers;
import org.jline.reader.Completer;
import org.jline.reader.LineReader;
import org.jline.reader.LineReaderBuilder;
import org.jline.reader.ParsedLine;
import org.jline.terminal.Terminal;

import java.io.File;

public class ConsoleUI implements UI {

    private Completer completer;
    private LineReader reader;
    private ParsedLine commandLine;

    public ConsoleUI(Terminal terminal) {
        
        completer = new Completers.FilesCompleter(new File(System.getProperty("user.home")));

        reader = LineReaderBuilder.builder().
            terminal(terminal)
            .completer(completer)
            .build();

    }

    @Override
    public void startApplication() {

        reader.readLine();

    }


}
