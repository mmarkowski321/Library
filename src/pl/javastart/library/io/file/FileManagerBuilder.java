package pl.javastart.library.io.file;

import pl.javastart.library.exceptions.NoSuchFileTypeException;
import pl.javastart.library.io.ConsolePrinter;
import pl.javastart.library.io.DataReader;

public class FileManagerBuilder {
    private DataReader dataReader;
    private ConsolePrinter printer;

    public FileManagerBuilder(DataReader dataReader, ConsolePrinter printer) {
        this.dataReader = dataReader;
        this.printer = printer;
    }
    public FileManager build(){
        printer.printLine("Wybierz format danych:");
        FileType fileType = getFileType();
        switch (fileType) {
            case SERIAL :
                return new SerializableFileManager();
            case CSV:
                return new CsvFileManager();
            default:
                throw new NoSuchFileTypeException("Nieobslugiwany typ danych");
        }
    }

    private FileType getFileType() {
        boolean typeOk = false;
        FileType fileType = null;
        do {
            printTypes();
            String type = dataReader.getString().toUpperCase();
            try{
                fileType = FileType.valueOf(type);
                typeOk = true;
            }catch (IllegalArgumentException e){
                printer.printLine("Nieobslugiwany typ danych, wybierz ponownie");
            }
        }while(!typeOk);
        return fileType;
    }

    private void printTypes() {
        for (FileType value : FileType.values()) {
            printer.printLine(value.toString());
        }
    }
}
