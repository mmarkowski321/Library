package pl.javaApp.library.io.file;

import pl.javaApp.library.model.Library;

public interface FileManager {
    Library importData();
    void exportData(Library library);
}
