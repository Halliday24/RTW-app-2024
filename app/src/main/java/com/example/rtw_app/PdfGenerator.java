package com.example.rtw_app;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.io.FileInputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class PdfGenerator {

    public static void generatePdf(Context context, String fileName, List<String[]> surveyAnswers, List<String> questionTexts, String mainQuestion) {
        String filePath = new File(context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS), fileName).getAbsolutePath();

        try {
            PdfWriter writer = new PdfWriter(new FileOutputStream(filePath));
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);
            document.add(new Paragraph(mainQuestion));
            document.add(new Paragraph("\n")); // Add some space between questions

            for (int i = 0; i < questionTexts.size(); i++) {
                String question = questionTexts.get(i);
                document.add(new Paragraph("Question: " + question));
                for (String[] answers : surveyAnswers) {
                    if (answers != null && i < answers.length && answers[i] != null) { // Check if an answer exists for this question
                        String answer = answers[i];
                        document.add(new Paragraph("Answer: " + answer));
                    }
                }
                document.add(new Paragraph("\n")); // Add some space between questions
            }

            document.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void createZipFile(Context context, String zipFileName, List<String> fileNames) {
        if (zipFileName == null || fileNames == null) {
            Log.e("PdfGenerator", "Zip file name or file list is null");
            return;
        }

        File documentsFolder = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), "RTWApp");
        if (!documentsFolder.exists() && !documentsFolder.mkdirs()) {
            Log.e("PdfGenerator", "Failed to create directory");
            return;
        }

        File zipFile = new File(documentsFolder, zipFileName);
        try (FileOutputStream fos = new FileOutputStream(zipFile); ZipOutputStream zos = new ZipOutputStream(fos)) {
            for (String fileName : fileNames) {
                File pdfFile = new File(context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS), fileName);
                try (FileInputStream fis = new FileInputStream(pdfFile)) {
                    zos.putNextEntry(new ZipEntry(fileName));

                    byte[] buffer = new byte[1024];
                    int length;
                    while ((length = fis.read(buffer)) > 0) {
                        zos.write(buffer, 0, length);
                    }
                    zos.closeEntry();
                } catch (IOException e) {
                    Log.e("PdfGenerator", "Error processing file " + fileName, e);
                }
            }
        } catch (IOException e) {
            Log.e("PdfGenerator", "Error creating zip file", e);
        }
    }


}
