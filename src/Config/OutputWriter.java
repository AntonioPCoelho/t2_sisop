package Config;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class OutputWriter implements AutoCloseable {
    private BufferedWriter writer;
    private String filePath = "Files/output.txt";

    public OutputWriter() throws IOException {
        this.writer = new BufferedWriter(new FileWriter(filePath));
    }

    public void writeLine(String line) {
        try {
            writer.write(line);
            writer.newLine();
        } catch (IOException e) {
            System.err.println("Erro ao escrever no arquivo de saída: " + e.getMessage());
        }
    }

    @Override
    public void close() throws IOException {
        if (writer != null) {
            writer.close();
        }
    }
}