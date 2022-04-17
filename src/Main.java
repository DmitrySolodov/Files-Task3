import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class Main {
    public static void openZip (String fromDirectory, String toDirectory) {
        try (ZipInputStream zin = new ZipInputStream(new FileInputStream(fromDirectory))) {
            ZipEntry entry;
            String name;
            while ((entry = zin.getNextEntry()) != null) {
                name = entry.getName();
                FileOutputStream fout = new FileOutputStream(new File(toDirectory, name));
                for (int i = zin.read(); i != -1 ; i = zin.read()) {
                    fout.write(i);
                }
                fout.flush();
                zin.closeEntry();
                fout.close();
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static GameProgress openProgress (String directory) {
        GameProgress gameProgress = null;
        try (FileInputStream fis = new FileInputStream(directory);
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            gameProgress = (GameProgress) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        return gameProgress;
    }

    public static void main(String[] args) {
        openZip("E:/Games/savegames/zip.zip", "E:/Games/savegames");
        System.out.println(openProgress("E:/Games/savegames/save1.dat"));
        System.out.println(openProgress("E:/Games/savegames/save2.dat"));
        System.out.println(openProgress("E:/Games/savegames/save3.dat"));
    }
}
