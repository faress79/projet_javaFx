package tn.esprit.projet3a.services;

import tn.esprit.projet3a.utils.MyDatabase;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class ClientService {

    private static ClientService instance;
    private Connection connection;

    private ClientService() {
        connection = MyDatabase.getInstance().getConnection();
    }

    public static ClientService getInstance() {
        if (instance == null) {
            instance = new ClientService();
        }
        return instance;
    }

    public void sendmail(File pdfFile) {
        // Initialize list to store client emails
        List<String> clientEmails = new ArrayList<>();

        // Query to retrieve client emails from the database
        String query = "SELECT prenom FROM client";

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            // Iterate over the result set and extract client emails
            while (resultSet.next()) {
                String email = resultSet.getString("prenom");
                clientEmails.add(email);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return;
        }

        // Configuration des propriétés SMTP
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com"); // Serveur SMTP Gmail
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "587"); // Port SMTP
        props.put("mail.smtp.starttls.enable", "true"); // Activation du chiffrement TLS

        // Création d'une session avec l'authentification
        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication("eroreror2001@gmail.com", "bdlt xnxa ucgx mzst");
                    }
                });

        // Iterate over client emails and send emails
        for (String email : clientEmails) {
            try {
                // Création d'un message MIME
                Message message = new MimeMessage(session);
                message.setFrom(new InternetAddress("eroreror2001@gmail.com"));
                message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
                message.setSubject("Nouvel événement ajouté SALIM DOBB"); // Sujet de l'e-mail

                // Create text part of the email body
                MimeBodyPart textPart = new MimeBodyPart();
                textPart.setText("Cher client,\n\nNous avons ajouté un nouvel événement. Consultez notre application pour plus de détails.\n\nCordialement,\nVotre équipe.");

                // Create MimeBodyPart for the PDF attachment
                MimeBodyPart attachmentPart = new MimeBodyPart();
                attachmentPart.attachFile(pdfFile);

                // Create a multipart message
                Multipart multipart = new MimeMultipart();
                multipart.addBodyPart(textPart); // Add text part
                multipart.addBodyPart(attachmentPart); // Add PDF attachment

                // Set the content of the message to the multipart
                message.setContent(multipart);
                // Envoi du message
                Transport.send(message);

                System.out.println("E-mail envoyé avec succès à " + email);

            } catch (MessagingException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }




}
