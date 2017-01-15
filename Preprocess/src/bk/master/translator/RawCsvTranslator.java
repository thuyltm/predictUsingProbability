package bk.master.translator;

public interface RawCsvTranslator {
   void translateCsvToJson(String filePath, String outputPath);
   void translateDBToJson(String filePath, String outputPath);
   void translateFolderCsvToJson(String folderPath, String outputPath);
}
