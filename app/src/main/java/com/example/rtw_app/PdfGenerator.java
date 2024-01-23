package com.example.rtw_app;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.utils.PdfMerger;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class PdfGenerator {

    public static void generatePdf(Context context, String fileName, List<String[]> surveyAnswers, List<String> questionTexts, String mainQuestion) {
        String filePath = new File(context.getExternalFilesDir(null), fileName).getAbsolutePath();

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

    public static void createCombinedPdf(Context context, String combinedPdfFileName, List<String> pdfFileNames) {
        if (combinedPdfFileName == null || pdfFileNames == null) {
            Log.e("PdfGenerator", "Combined PDF file name or PDF file list is null");
            return;
        }

        // Ensure that the combinedPdfFileName has a ".pdf" extension
        if (!combinedPdfFileName.toLowerCase().endsWith(".pdf")) {
            combinedPdfFileName += ".pdf";
        }

        File documentsFolder = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), "RTWApp");
        if (!documentsFolder.exists() && !documentsFolder.mkdirs()) {
            Log.e("PdfGenerator", "Failed to create directory");
            return;
        }

        File combinedPdfFile = new File(documentsFolder, combinedPdfFileName);

        try (FileOutputStream fos = new FileOutputStream(combinedPdfFile);
             PdfWriter writer = new PdfWriter(fos);
             PdfDocument pdfDocument = new PdfDocument(writer)) {

            PdfMerger pdfMerger = new PdfMerger(pdfDocument);

            for (String pdfFileName : pdfFileNames) {
                File pdfFile = new File(context.getExternalFilesDir(null), pdfFileName);
                try (PdfDocument inputPdfDocument = new PdfDocument(new PdfReader(pdfFile.getAbsolutePath()))) {
                    pdfMerger.merge(inputPdfDocument, 1, inputPdfDocument.getNumberOfPages());
                } catch (IOException e) {
                    Log.e("PdfGenerator", "Error processing PDF file " + pdfFileName, e);
                }
            }

            // Close the PdfMerger manually
            pdfMerger.close();

        } catch (IOException e) {
            Log.e("PdfGenerator", "Error creating combined PDF file", e);
        }
    }
}
