import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Main {
    public static void main(String[] args) {
        List<GameProgress> gameProgressList = Arrays.asList(
                new GameProgress(1, 2, 3, 4.0),
                new GameProgress(10, 20, 30, 40.0),
                new GameProgress(100, 200, 300, 400.0));
        List<String> pathList = Arrays.asList(
                "E:/Games/savegames/save1.dat",
                "E:/Games/savegames/save2.dat",
                "E:/Games/savegames/save3.dat");

        for (int i = 0; i < pathList.size(); i++) {
            saveGame(pathList.get(i), gameProgressList.get(i));
        }
        zipFiles("E:/Games/savegames/zip.zip", pathList);

        for (String path : pathList) {
            File file = new File(path);
        }
    }

    public static void saveGame(String path, GameProgress gameProgress) {
        try (FileOutputStream fos = new FileOutputStream(path);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(gameProgress);
        } catch (IOException exception) {
            System.out.println(exception.getMessage());
        }

    }

    public static void zipFiles(String zipPath, List<String> pathList) {
        try (ZipOutputStream zipOutputStream = new ZipOutputStream(new FileOutputStream(zipPath))) {
            FileInputStream fis;
            ZipEntry zipEntry;
            for (int i = 0; i < pathList.size(); i++) {
                fis = new FileInputStream(pathList.get(i));
                zipEntry = new ZipEntry("save" + (i + 1) + ".dat");
                zipOutputStream.putNextEntry(zipEntry);
                byte[] buffer = new byte[fis.available()];
                fis.read(buffer);
                zipOutputStream.write(buffer);
                zipOutputStream.closeEntry();
                fis.close();
            }
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

}
