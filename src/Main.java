import Config.Config;
import Config.ReaderConfig;

public class Main {
    public static void main(String[] args) {
        ReaderConfig readerConfig = new ReaderConfig();
        Config config = readerConfig.readConfigFile();
        System.out.println(config.toString());
    }
}