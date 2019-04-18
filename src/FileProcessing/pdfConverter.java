package FileProcessing;
//
//import pdfWriter.PDFDocument;
//import pdfWriter.PDFGraphics;
//import pdfWriter.PDFPage;
//
//import javax.imageio.ImageIO;
//import java.awt.*;
//import java.awt.image.BufferedImage;
//import java.awt.print.PageFormat;
//import java.awt.print.Paper;
//import java.io.File;
//
public class pdfConverter {
//    public static void main(String[] args) {
//        // create document
//        PDFDocument pdfDoc = new PDFDocument();
//
//// create a PageFormat of standard letter size
//// with no margins
//        Paper p = new Paper ();
//        p.setSize(8.5 * 72, 11 * 72);
//        p.setImageableArea(0, 0, 8.5 * 72, 11 * 72);
//        PageFormat pf = new PageFormat ();
//        pf.setPaper(p);
//
//// create a new page and add it to the PDF (important!)
//        PDFPage page = pdfDoc.createPage(pf);
//        pdfDoc.addPage(page);
//
//// get graphics from the page
//// this object is a Graphics2D Object and you can draw anything
//// you would draw on a Graphics2D
//        PDFGraphics g2d = (PDFGraphics) page.createGraphics();
//
//// read an image (could be png, jpg, etc...)
//        BufferedImage image = ImageIO.read(new File("C:\\photo.jpg"));
//
//// draw the image on the page
//        g2d.drawImage(image, 0, 0, null);
//
//// set the font and color
//        g2d.setFont (PDFGraphics.HELVETICA.deriveFont(24f));
//        g2d.setColor(Color.BLUE);
//
//// draw text on the graphics object of the page
//        g2d.drawString("NEW TEXT", 200, 30);
//
//// Save the document
//        pdfDoc.saveDocument ("C:\\output.pdf");
//    }
}
