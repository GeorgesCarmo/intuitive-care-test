package com.georges.intuitive_care_test;

import java.io.*;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;

import technology.tabula.*;
import technology.tabula.extractors.SpreadsheetExtractionAlgorithm;
import com.opencsv.CSVWriter;

public class DataTransform {
    public static void main(String[] args) {
        String pdfFilePath = "./downloads/Anexo_I_Rol_2021RN_465.2021_RN627L.2024.pdf"; // Altere para o caminho do seu arquivo PDF
        String csvFilePath = "Rol_de_Procedimentos.csv";
        String zipFilePath = "Teste_Georges.zip";

        try {
            extractTableFromPDF(pdfFilePath, csvFilePath);
            zipFile(csvFilePath, zipFilePath);
            System.out.println("✅ Processo concluído! Arquivo ZIP gerado: " + zipFilePath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void extractTableFromPDF(String pdfFilePath, String csvFilePath) throws IOException {
        File pdfFile = new File(pdfFilePath);
        CSVWriter writer = new CSVWriter(new FileWriter(csvFilePath));

        String[] header = {
            "PROCEDIMENTO", "RN (alteração)", "VIGÊNCIA", "Seg. Odontológica", "Seg. Ambulatorial",
            "HCO", "HSO", "REF", "PAC", "DUT", "SUBGRUPO", "GRUPO", "CAPÍTULO"
        };
        writer.writeNext(header);

        try (PDDocument document = Loader.loadPDF(pdfFile);
             ObjectExtractor extractor = new ObjectExtractor(document)) {
            
            SpreadsheetExtractionAlgorithm sea = new SpreadsheetExtractionAlgorithm();

            for (int page = 1; page <= document.getNumberOfPages(); page++) {
                Page pdfPage = extractor.extract(page);
                List<Table> tables = sea.extract(pdfPage);

                for (Table table : tables) {
                    boolean firstRowIgnored = false; // Flag para garantir que ignoramos o cabeçalho

                    for (List<RectangularTextContainer> row : table.getRows()) {
                        String[] rowData = row.stream()
                                              .map(cell -> normalizeText(cell.getText())) // Remove espaços extras
                                              .toArray(String[]::new);
                        rowData = replaceAbbreviations(rowData); // Substitui as abreviações

                        if (!firstRowIgnored && isHeaderRow(rowData, header)) {
                            firstRowIgnored = true;
                            continue;
                        }

                        if (!isEmptyRow(rowData) && rowData.length == header.length) {
                            writer.writeNext(rowData);
                        }
                    }
                }
            }
        }
        writer.close();
    }

    private static String normalizeText(String text) {
        return text.replaceAll("\\s+", " ").trim(); // Remove espaços extras e quebras de linha
    }

    private static boolean isEmptyRow(String[] rowData) {
        for (String value : rowData) {
            if (value != null && !value.trim().isEmpty()) {
                return false;
            }
        }
        return true;
    }

    private static String[] replaceAbbreviations(String[] rowData) {
        for (int i = 0; i < rowData.length; i++) {
            if (rowData[i].equalsIgnoreCase("OD")) {
                rowData[i] = "Seg. Odontológica";
            } else if (rowData[i].equalsIgnoreCase("AMB")) {
                rowData[i] = "Seg. Ambulatorial";
            }
        }
        return rowData;
    }

    private static boolean isHeaderRow(String[] rowData, String[] header) {
        if (rowData.length != header.length) {
            return false;
        }
        for (int i = 0; i < rowData.length; i++) {
            if (!rowData[i].equalsIgnoreCase(header[i])) {
                return false;
            }
        }
        return true;
    }

    private static void zipFile(String filePath, String zipPath) throws IOException {
        try (FileOutputStream fos = new FileOutputStream(zipPath);
             ZipOutputStream zos = new ZipOutputStream(fos);
             FileInputStream fis = new FileInputStream(filePath)) {

            ZipEntry zipEntry = new ZipEntry(new File(filePath).getName());
            zos.putNextEntry(zipEntry);

            byte[] buffer = new byte[1024];
            int length;
            while ((length = fis.read(buffer)) >= 0) {
                zos.write(buffer, 0, length);
            }
            zos.closeEntry();
        }
    }
}