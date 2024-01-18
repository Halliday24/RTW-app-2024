package com.example.rtw_app;

import android.content.Context;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class PdfGenerator {

    public static void generatePdf(Context context, String fileName, List<String[]> surveyAnswers, List<String> questionTexts,String MainQuestion) {
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
}
