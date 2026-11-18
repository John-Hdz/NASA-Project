package com.example.nasaproject.baseDatos.datosCompartidos;
import com.example.nasaproject.baseDatos.ApodDao;
import com.example.nasaproject.modelsMysql.Apod;
import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.UnitValue;

import java.io.IOException;
import java.util.List;

public class RepoteApod {
    public void createPdf(String dest) throws IOException {
        //Initialize PDF writer
        PdfWriter writer = new PdfWriter(dest);

        //Initialize PDF document
        PdfDocument pdf = new PdfDocument(writer);

        // Initialize document
        Document document = new Document(pdf, PageSize.A3.rotate());
        document.setMargins(20, 20, 20, 20);

        PdfFont font = PdfFontFactory.createFont(StandardFonts.TIMES_ROMAN);
        PdfFont bold = PdfFontFactory.createFont(StandardFonts.TIMES_BOLD);
        Table table = new Table(UnitValue.createPercentArray(new float[]{2, 4, 1, 2, 1})).useAllAvailableWidth();

        // Add table header
        process(table, null, bold, true);

        // Database connection
        ApodDao apodDao = new ApodDao();
        List<Apod> apods = apodDao.getReporte();

        for (Apod apod : apods) {
            process(table, apod, font, false);
        }

        Paragraph title = new Paragraph("REPORTE APOD");
        title.setFont(font);

        document.add(title);
        document.add(table);

        //Close document
        document.close();
    }

    public void process(Table table, Apod apod, PdfFont font, boolean isHeader) throws IOException {
        if (isHeader) {
            table.addHeaderCell(new Cell().add(new Paragraph("TITULO").setFont(font)));
            table.addHeaderCell(new Cell().add(new Paragraph("DESCRIPCION").setFont(font)));
            table.addHeaderCell(new Cell().add(new Paragraph("TIPO").setFont(font)));
            table.addHeaderCell(new Cell().add(new Paragraph("URL ").setFont(font)));
            table.addHeaderCell(new Cell().add(new Paragraph("FECHA").setFont(font)));
        } else {
            Cell titleCell = new Cell();
            titleCell.add(new Paragraph(String.valueOf(apod.getTitulo())).setFont(font));

            // Load and add the image
            ImageData imageData = ImageDataFactory.create(apod.getUrl());
            Image img = new Image(imageData);
            img.setAutoScale(true);

            titleCell.add(img); // Add image below the text
            table.addCell(titleCell);

            table.addCell(new Cell().add(new Paragraph(String.valueOf(apod.getDescripcion())).setFont(font)));
            table.addCell(new Cell().add(new Paragraph(String.valueOf(apod.getTipo())).setFont(font)));
            table.addCell(new Cell().add(new Paragraph(String.valueOf(apod.getUrl())).setFont(font)));
            table.addCell(new Cell().add(new Paragraph(String.valueOf(apod.getFecha())).setFont(font)));
        }
    }
}
