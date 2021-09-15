package files;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class FilesDemo1 {
    public static void main(String[] args) {
        String filePath = System.getProperty("user.dir") + "/resource/images";
        try {
            Files.createDirectory(Paths.get(filePath));
        } catch (IOException e) {
            System.out.println("文件夹已存在");
        }
        String file1 = System.getProperty("user.dir") + "/resource/test1.txt";
        String file2 = System.getProperty("user.dir") + "/resource/test5.txt";
        try {
            Files.copy(Paths.get(file1), Paths.get(file2), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
