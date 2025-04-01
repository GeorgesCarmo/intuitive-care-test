package com.georges.intuitive_care_test;

import java.io.*;
import java.net.URL;
import java.nio.file.*;
import java.util.zip.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class WebScraper {
    public static void main(String[] args) {
        String url = "https://www.gov.br/ans/pt-br/acesso-a-informacao/participacao-da-sociedade/atualizacao-do-rol-de-procedimentos";
        String downloadDir = "./downloads/"; // Altere para o diretório desejado
        String zipFileName = "anexos.zip";

        try {
            Files.createDirectories(Paths.get(downloadDir));
            Document doc = Jsoup.connect(url).get();

            for (Element link : doc.select("a[href$=.pdf]")) {
                String fileUrl = link.absUrl("href");
                if (fileUrl.contains("Anexo_I") || fileUrl.contains("Anexo_II")) {
                    downloadFile(fileUrl, downloadDir);
                }
            }
            zipFiles(downloadDir, zipFileName);
            System.out.println("Download e compactação concluídos.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void downloadFile(String fileURL, String saveDir) throws IOException {
        String fileName = fileURL.substring(fileURL.lastIndexOf('/') + 1);
        try (InputStream in = new URL(fileURL).openStream()) {
            Files.copy(in, Paths.get(saveDir, fileName), StandardCopyOption.REPLACE_EXISTING);
            System.out.println("Baixado: " + fileName);
        }
    }

    private static void zipFiles(String folder, String zipFile) throws IOException {
        try (FileOutputStream fos = new FileOutputStream(zipFile);
                ZipOutputStream zos = new ZipOutputStream(fos)) {
            Files.walk(Paths.get(folder)).filter(Files::isRegularFile).forEach(path -> {
                try {
                    ZipEntry zipEntry = new ZipEntry(path.getFileName().toString());
                    zos.putNextEntry(zipEntry);
                    Files.copy(path, zos);
                    zos.closeEntry();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }
    }
}
