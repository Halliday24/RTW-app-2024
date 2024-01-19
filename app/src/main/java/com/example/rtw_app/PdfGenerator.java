package com.example.rtw_app;

import android.content.Context;
import android.os.Environment;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

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

    public static void combinePdfFiles(Context context, String baseFileName, int totalFiles) {
        String combinedFilePath = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), baseFileName + "_combined.pdf").getAbsolutePath();

        try {
            PdfWriter writer = new PdfWriter(new FileOutputStream(combinedFilePath));
            com.itextpdf.kernel.pdf.PdfDocument combinedPdf = new com.itextpdf.kernel.pdf.PdfDocument(writer);

            // Iterate through all PDF files to be combined
            for (int i = 1; i <= totalFiles; i++) {
                String filePath = new File(context.getExternalFilesDir(null), baseFileName + i + ".pdf").getAbsolutePath();
                com.itextpdf.kernel.pdf.PdfDocument pdf = new com.itextpdf.kernel.pdf.PdfDocument(new PdfReader(filePath));
                pdf.copyPagesTo(1, pdf.getNumberOfPages(), combinedPdf);
                pdf.close();

            }

            combinedPdf.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
