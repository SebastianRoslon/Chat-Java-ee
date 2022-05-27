package pl.roslon.chat.service;

import javax.enterprise.context.ApplicationScoped;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

@ApplicationScoped
public class FileService {

    public boolean saveFile(String roomName, String fileName, InputStream inputStream) {
        File folderPath = new File(System.getProperty("user.dir") + "\\files" + "\\" + roomName);
        String fileFullPath = folderPath + "\\" + fileName;
        try (FileOutputStream dataOutputStream = new FileOutputStream(fileFullPath)) {
            int bytes = 0;
            byte[] buffer = new byte[4 * 1024];
            while ((bytes = inputStream.read(buffer)) != -1) {
                dataOutputStream.write(buffer, 0, bytes);
                dataOutputStream.flush();
            }
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public File getFile(String roomName, String fileName) throws FileNotFoundException {
        File file = new File(System.getProperty("user.dir") + "\\files" + "\\" + roomName + "\\" + fileName);
        if (file.exists()) {
            return file;
        } else {
            throw new FileNotFoundException();
        }
    }
}
