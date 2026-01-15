package org.esiea.festicore;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;
import org.esiea.festicore.service.LogManager;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.Level;

public class PDFGenerator {



    // Generates a PDF ticket for a specific user and reservation

    public void generateTicket(User user, Reservation reservation) {
        Document document = new Document();
        String fileName = "Ticket_" + reservation.getId() + ".pdf";
        
        try {
            LogManager.log(Level.INFO, "Starting PDF generation for user: " + user.getName() + ", reservation ID: " + reservation.getId());
            
            try (FileOutputStream fileOutputStream = new FileOutputStream(fileName)) {
                // File name includes user ID and reservation ID for uniqueness
                PdfWriter.getInstance(document, fileOutputStream);
                LogManager.log(Level.FINE, "PDF Writer initialized for file: " + fileName);
                
                document.open();
                LogManager.log(Level.FINE, "PDF Document opened");
                
                // Adding content based on project requirements 
                document.add(new Paragraph("FESTICORE - OFFICIAL TICKET"));
                document.add(new Paragraph("---------------------------"));
                document.add(new Paragraph("Attendee: " + user.getName()));
                document.add(new Paragraph("Type: " + reservation.getClass().getSimpleName()));
                document.add(new Paragraph("Date: " + reservation.getValidityDate()));
                document.add(new Paragraph("Price: " + reservation.calculatePrice() + " EUR"));
                LogManager.log(Level.FINE, "Added ticket content for user: " + user.getName());
                
                // Add custom info based on reservation type
                if (reservation instanceof Tickets) {
                    Tickets ticket = (Tickets) reservation;
                    document.add(new Paragraph("Ticket Duration: " + ticket.getTicketType()));
                    LogManager.log(Level.FINE, "Added Ticket type: " + ticket.getTicketType());
                } else if (reservation instanceof Activity) {
                    Activity activity = (Activity) reservation;
                    document.add(new Paragraph("Activity Type: " + activity.getActivityType()));
                    document.add(new Paragraph("Artist: " + activity.getArtistName()));
                    document.add(new Paragraph("Start Time: " + activity.getStartTime()));
                    document.add(new Paragraph("Duration: " + activity.getDuration()));
                    LogManager.log(Level.FINE, "Added Activity details: " + activity.getActivityType() + " by " + activity.getArtistName());
                } else if (reservation instanceof Pass) {
                    Pass pass = (Pass) reservation;
                    document.add(new Paragraph("Pass Type: " + pass.getPassType()));
                    LogManager.log(Level.FINE, "Added Pass type: " + pass.getPassType());
                }

                document.close();
                LogManager.log(Level.INFO, "PDF Ticket generated successfully: " + fileName);
            }
            
        } catch (IOException e) {
            LogManager.log(Level.SEVERE, "IO Error writing PDF file: " + e.getMessage());
            LogManager.log(Level.SEVERE, "File path: " + fileName);
        } catch (DocumentException e) {
            LogManager.log(Level.SEVERE, "Document Error generating PDF: " + e.getMessage());
        } catch (Exception e) {
            LogManager.log(Level.SEVERE, "Unexpected error during PDF generation: " + e.getMessage());
        }
    }
}