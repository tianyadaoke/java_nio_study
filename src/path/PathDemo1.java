package path;

import java.nio.file.Path;
import java.nio.file.Paths;

public class PathDemo1 {
    public static void main(String[] args) {
        String filePath = System.getProperty("user.dir") + "/resource/test4.txt";
        Path path = Paths.get(filePath);
        System.out.println(path);
        Path normalize = path.normalize();
        System.out.println(normalize);
    }
}
