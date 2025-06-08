
import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDropEvent;
import java.io.File;
import java.util.List;
import javax.swing.*;
import javax.swing.border.LineBorder;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.BufferedWriter;
import java.io.OutputStreamWriter;



public class AudioSteganographyUI extends JFrame {
    private JLabel fileNameLabel;
    private JTextArea messageTextArea;
    private JTextField ownerField, titleField, emailField;
    private JPasswordField passwordField;
    private File selectedFile;

    // Declare at class level
    private JComboBox<String> ownershipDropdown;
    private JComboBox<String> licenseDropdown;
    private JCheckBox confirmCheckbox;

    JButton captureAndVerifyBtn;

    private boolean isFaceVerified = false;
    private String embeddedBiometricEncoding = null;





    public AudioSteganographyUI() {
        setTitle("Audio Steganography with Metadata");
        setSize(600, 830);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(null);
        mainPanel.setBackground(new Color(173, 216, 230));
        add(mainPanel, BorderLayout.CENTER);

        JLabel titleLabel = new JLabel("Audio Watermarking", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 26));
        titleLabel.setBounds(150, 20, 300, 30);
        mainPanel.add(titleLabel);

        JButton fileInputButton = new JButton("Choose Audio File");
        fileInputButton.setBounds(225, 70, 150, 30);
        fileInputButton.addActionListener(e -> chooseFile());
        mainPanel.add(fileInputButton);

        JPanel dropArea = new JPanel();
        dropArea.setBounds(150, 120, 300, 80);
        dropArea.setBorder(new LineBorder(Color.WHITE, 2, true));
        dropArea.setBackground(new Color(173, 216, 230));
        dropArea.setLayout(new BorderLayout());
        JLabel dropAreaLabel = new JLabel("or Drag & Drop Audio File Here", SwingConstants.CENTER);
        dropAreaLabel.setForeground(Color.WHITE);
        dropArea.add(dropAreaLabel, BorderLayout.CENTER);
        dropArea.setDropTarget(new DropTarget() {
            public synchronized void drop(DropTargetDropEvent evt) {
                try {
                    evt.acceptDrop(DnDConstants.ACTION_COPY);
                    List<File> droppedFiles = (List<File>) evt.getTransferable().getTransferData(DataFlavor.javaFileListFlavor);
                    if (!droppedFiles.isEmpty()) {
                        selectedFile = droppedFiles.get(0);
                        fileNameLabel.setText("File: " + selectedFile.getName());
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
        mainPanel.add(dropArea);

        fileNameLabel = new JLabel("", SwingConstants.CENTER);
        fileNameLabel.setForeground(Color.BLACK);
        fileNameLabel.setBounds(150, 210, 300, 20);
        mainPanel.add(fileNameLabel);

        JLabel ownerLabel = new JLabel("Owner Name:");
        ownerLabel.setBounds(150, 240, 200, 20);
        mainPanel.add(ownerLabel);
        ownerField = new JTextField();
        ownerField.setBounds(150, 260, 300, 25);
        mainPanel.add(ownerField);

        JLabel titleLabel2 = new JLabel("Title:");
        titleLabel2.setBounds(150, 290, 200, 20);
        mainPanel.add(titleLabel2);
        titleField = new JTextField();
        titleField.setBounds(150, 310, 300, 25);
        mainPanel.add(titleField);

        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setBounds(150, 340, 200, 20);
        mainPanel.add(emailLabel);
        emailField = new JTextField();
        emailField.setBounds(150, 360, 300, 25);
        mainPanel.add(emailField);

        // Ownership Type Dropdown
        JLabel lblOwnership = new JLabel("Ownership Type:");
        lblOwnership.setBounds(150, 390, 120, 25);
        mainPanel.add(lblOwnership);

        String[] ownershipTypes = {"Individual", "Joint", "Organization", "Work for Hire", "Government"};
        ownershipDropdown = new JComboBox<>(ownershipTypes);
        ownershipDropdown.setBounds(150, 410, 230, 25);
        mainPanel.add(ownershipDropdown);

        // License Type Dropdown
        JLabel lblLicense = new JLabel("License Type:");
        lblLicense.setBounds(150, 440, 120, 25);
        mainPanel.add(lblLicense);

        String[] licenseTypes = {
                "All Rights Reserved", "CC BY", "CC BY-SA", "CC BY-ND",
                "CC BY-NC", "Public Domain (CC0)", "Commercial Use Allowed with Credit", "Custom License"
        };
        licenseDropdown = new JComboBox<>(licenseTypes);
        licenseDropdown.setBounds(150, 460, 230, 25);
        mainPanel.add(licenseDropdown);




        JLabel messageLabel = new JLabel("Enter your Watermark:");
        messageLabel.setBounds(150, 490, 200, 20);
        mainPanel.add(messageLabel);
        messageTextArea = new JTextArea();
        messageTextArea.setBounds(150, 510, 300, 60);
        mainPanel.add(messageTextArea);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(150, 580, 200, 20);
        mainPanel.add(passwordLabel);
        passwordField = new JPasswordField();
        passwordField.setBounds(150, 600, 300, 25);
        mainPanel.add(passwordField);

        JButton verifyFaceBtn = new JButton("Verify Face");
        verifyFaceBtn.setBounds(240, 640, 120, 25);
        mainPanel.add(verifyFaceBtn);

        verifyFaceBtn.addActionListener(e -> {
            try {
                String pythonPath = "E:\\Major-2 - Copy\\AudioCipher\\venv_py311\\Scripts\\python.exe";  // <- your venv Python
                ProcessBuilder pb = new ProcessBuilder(pythonPath, "scripts/generate_encoding.py");
                pb.redirectErrorStream(true);
                Process process = pb.start();

                BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                String line;
                String faceEncoding = null;

                while ((line = reader.readLine()) != null) {
                    System.out.println("Python Output: " + line);
                    if (line.contains(",")) {
                        faceEncoding = line;
                        break;
                    }
                }

                process.waitFor();

                if (faceEncoding == null || faceEncoding.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Face capture failed.");
                    isFaceVerified = false;
                } else {
                    embeddedBiometricEncoding = faceEncoding;
                    isFaceVerified = true;
                    JOptionPane.showMessageDialog(this, "Facial encoding captured.");
                }

            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error running face capture.");
            }
        });




        // Confirmation Checkbox
        JCheckBox confirmCheckbox = new JCheckBox("I confirm I own this content and agree to the terms.");
        confirmCheckbox.setBounds(150, 680, 300, 25);
        mainPanel.add(confirmCheckbox);

        JButton embedButton = new JButton("Embed Watermark");
        embedButton.setBounds(140, 730, 150, 30);
        embedButton.addActionListener(e -> startEmbedding());
        mainPanel.add(embedButton);

        JButton decryptButton = new JButton("Extract Watermark");
        decryptButton.setBounds(310, 730, 150, 30);
        decryptButton.addActionListener(e -> startDecryption());
        mainPanel.add(decryptButton);
    }

    private void chooseFile() {
        JFileChooser fileChooser = new JFileChooser();
        if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            selectedFile = fileChooser.getSelectedFile();
            fileNameLabel.setText("File: " + selectedFile.getName());
        }
    }

    private void startEmbedding() {
        if (selectedFile == null || messageTextArea.getText().isEmpty() || passwordField.getPassword().length == 0 || !isFaceVerified || embeddedBiometricEncoding == null) {
            JOptionPane.showMessageDialog(this, "Please fill all required fields and verify face.");
            return;
        }

        String owner = ownerField.getText();
        String title = titleField.getText();
        String email = emailField.getText();
        String message = messageTextArea.getText();
        String password = new String(passwordField.getPassword());
        String ownership = (String) ownershipDropdown.getSelectedItem();
        String license = (String) licenseDropdown.getSelectedItem();


        AudioSteganography.embedMessageInAudio(selectedFile, owner, title, email, message, password, ownership, license, embeddedBiometricEncoding);
        JOptionPane.showMessageDialog(this, "Message embedded successfully!");
    }
    private void startDecryption() {
        if (selectedFile == null) {
            JOptionPane.showMessageDialog(this, "Please choose an audio file to decrypt.");
            return;
        }

        String password = JOptionPane.showInputDialog(this, "Enter password to decrypt:");
        if (password == null || password.isEmpty()) return;

        String extractedMessage = DecryptAudio.extractMessageFromAudio(selectedFile, password);
        if (extractedMessage != null) {
            String[] parts = extractedMessage.split("\\|");
            String biometricData = null;

            for (String part : parts) {
                if (part.toLowerCase().startsWith("biometric:")) {
                    biometricData = part.substring("biometric:".length());
                    break;
                }
            }

            if (biometricData == null || biometricData.isEmpty()) {
                JOptionPane.showMessageDialog(this, "No biometric data found in audio.");
                return;
            }

            try {
                String pythonPath = "E:/Major-2 - Copy/AudioCipher/venv_py311/Scripts/python.exe"; // use full path to venv
                ProcessBuilder pb = new ProcessBuilder(pythonPath, "scripts/verify_encoding.py");
                pb.redirectErrorStream(true);
                Process process = pb.start();

                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(process.getOutputStream()));
                writer.write(biometricData);
                writer.newLine();
                writer.flush();
                writer.close();

                BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                String result = null;
                while ((result = reader.readLine()) != null) {
                    result = result.trim();
                    System.out.println("Python result: " + result);
                    if (result.equalsIgnoreCase("Verified")) break;
                }


                if (!"Verified".equalsIgnoreCase(result)) {
                    JOptionPane.showMessageDialog(this, "Facial verification failed.");
                    return;
                }


            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Biometric verification error.");
                return;
            }

            // Display decrypted message
            StringBuilder formatted = new StringBuilder("Extracted Metadata:\n\n");
            for (String field : parts) {
                if (field.toLowerCase().startsWith("biometric:")) continue; // skip biometric line
                formatted.append(field.replace(":", ": ")).append("\n");
            }


            JOptionPane.showMessageDialog(this, formatted.toString());

        } else {
            JOptionPane.showMessageDialog(this, "Decryption failed or invalid password.");
        }
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new AudioSteganographyUI().setVisible(true));
    }
}
