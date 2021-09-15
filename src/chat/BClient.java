package chat;

import java.io.IOException;

public class BClient {
    public static void main(String[] args) {
        try {
            new ChatClient().startClient("Marry");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
