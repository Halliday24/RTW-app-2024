package com.example.rtw_app;

import android.content.Context;
import android.os.Environment;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.utils.PdfMerger;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import android.content.Context;

import com.itextpdf.kernel.pdf.PdfAConformanceLevel;
import android.content.Context;
import android.os.ParcelFileDescriptor;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;


public class PdfGenerator {

    public static void generatePdf(Context context, String fileName, List<String[]> surveyAnswers, List<String> questionTexts, String MainQuestion) {
        String filePath = new File(context.getExternalFilesDir(null), fileName).getAbsolutePath();

        try {
            PdfWriter writer = new PdfWriter(new FileOutputStream(filePath));
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);
            document.add(new Paragraph(MainQuestion));
            document.add(new Paragraph("\n")); // Add some space between questions
            // Add survey questions and answers to the PDF
            for (int i = 0; i < questionTexts.size(); i++) {
                String question = questionTexts.get(i);
                String[] answers = surveyAnswers.get(0); // Assuming only one set of answers

                document.add(new Paragraph("Question: " + question));
                document.add(new Paragraph("Answer: " + answers[i]));
                document.add(new Paragraph("\n")); // Add some space between questions
            }

            document.close();

            // You can add code here to share or open the generated PDF if needed
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void createZipFile(Context context, String zipFileName, List<String> fileNames) {
        try {
            File documentsFolder = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), "YourAppFolder");
            documentsFolder.mkdirs(); // Create the folder if it doesn't exist

            File zipFile = new File(documentsFolder, zipFileName);
            FileOutputStream fos = new FileOutputStream(zipFile);
            ZipOutputStream zos = new ZipOutputStream(fos);

            for (String fileName : fileNames) {
                File pdfFile = new File(context.getExternalFilesDir(null), fileName);
                FileInputStream fis = new FileInputStream(pdfFile);

                zos.putNextEntry(new ZipEntry(fileName));

                byte[] buffer = new byte[1024];
                int length;
                while ((length = fis.read(buffer)) > 0) {
                    zos.write(buffer, 0, length);
                }

                zos.closeEntry();
                fis.close();
            }

            zos.close();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}



