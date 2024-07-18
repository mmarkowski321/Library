package pl.javaApp.library.io.file;

import pl.javaApp.library.exceptions.DataExportException;
import pl.javaApp.library.exceptions.DataImportException;
import pl.javaApp.library.model.Library;

import java.io.*;

public class SerializableFileManager implements FileManager{
    private static final String FILE_NAME = "Library.o";
    @Override
    public Library importData() {

        try (FileInputStream fis = new FileInputStream(FILE_NAME);
             ObjectInputStream ois = new ObjectInputStream(fis);
                ){
            return (Library) ois.readObject();
        }catch (FileNotFoundException e){
            throw new DataImportException("Brak pliku " + FILE_NAME);
        } catch (IOException e) {
            throw new DataImportException("Blad odczytu pliku " + FILE_NAME);
        } catch (ClassNotFoundException e) {
            throw new DataImportException("Niezgodny typ danych pliku " + FILE_NAME);
        }

    }

    @Override
    public void exportData(Library library) {
        try(FileOutputStream fos = new FileOutputStream(FILE_NAME);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            ){
            oos.writeObject(library);
        }catch (FileNotFoundException e){
            throw new DataExportException("Brak pliku " + FILE_NAME);
        }catch (IOException e){
            throw new DataExportException("Blad odczytu pliku " + FILE_NAME);
        }

    }
}
