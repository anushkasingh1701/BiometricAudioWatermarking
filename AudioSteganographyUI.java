import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDropEvent;
import java.io.File;
import java.util.List;
import javax.swing.*;
import javax.swing.border.LineBorder;

public class AudioSteganographyUI extends JFrame {
    private JLabel fileNameLabel;
    private JTextArea secretMessageTextArea;
    private JProgressBar progressBar;
    private JLabel charCountLabel;
    private File selectedFile;

    public AudioSteganographyUI() {
        // Set up frame
        setTitle("Audio Steganography");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Create main panel with background color
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(null);
        mainPanel.setBackground(new Color(173, 216, 230)); 
        add(mainPanel, BorderLayout.CENTER);

        // Title
        JLabel titleLabel = new JLabel("Audio Steganography", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 26));
        titleLabel.setBounds(150, 20, 300, 30);
        mainPanel.add(titleLabel);

        // File input button
        JButton fileInputButton = new JButton("Choose Audio File");
        fileInputButton.setBounds(225, 70, 150, 30);
        fileInputButton.addActionListener(e -> chooseFile());
        mainPanel.add(fileInputButton);

        // Drag and drop area
        JPanel dropArea = new JPanel();
        dropArea.setBounds(150, 120, 300, 80);
        dropArea.setBorder(new LineBorder(Color.WHITE, 2, true));
        dropArea.setBackground(new Color(173, 216, 230));
        dropArea.setLayout(new BorderLayout());
        JLabel dropAreaLabel = new JLabel("or Drag & Drop Audio File Here", SwingConstants.CENTER);
        dropAreaLabel.setForeground(Color.WHITE);
        dropArea.add(dropAreaLabel, BorderLayout.CENTER);

        // Enable drag-and-drop
        dropArea.setDropTarget(new DropTarget() {
            public synchronized void drop(DropTargetDropEvent evt) {
                try {
                    evt.acceptDrop(DnDConstants.ACTION_COPY);
                    List<File> droppedFiles = (List<File>) evt.getTransferable()
                            .getTransferData(DataFlavor.javaFileListFlavor);
                    if (droppedFiles.size() > 0) {
                        selectedFile = droppedFiles.get(0);
                        fileNameLabel.setText("File: " + selectedFile.getName());
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        mainPanel.add(dropArea);

        // File name display
        fileNameLabel = new JLabel("", SwingConstants.CENTER);
        fileNameLabel.setForeground(Color.BLACK);
        fileNameLabel.setBounds(150, 210, 300, 20);
        mainPanel.add(fileNameLabel);

        // Secret message input
        JLabel secretMessageLabel = new JLabel("Enter Secret Message:");
        secretMessageLabel.setBounds(150, 240, 200, 20);
        mainPanel.add(secretMessageLabel);

        secretMessageTextArea = new JTextArea();
        secretMessageTextArea.setBounds(150, 270, 300, 100);
        mainPanel.add(secretMessageTextArea);

        // Embed button
        JButton embedButton = new JButton("Embed Message");
        embedButton.setBounds(225, 400, 150, 30);
        embedButton.addActionListener(e -> startEmbedding());
        mainPanel.add(embedButton);
    }

    private void chooseFile() {
        JFileChooser fileChooser = new JFileChooser();
        int returnValue = fileChooser.showOpenDialog(this);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            selectedFile = fileChooser.getSelectedFile();
            fileNameLabel.setText("File: " + selectedFile.getName());
        }
    }

    private void startEmbedding() {
        if (selectedFile == null || secretMessageTextArea.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please choose a file and enter a secret message.");
            return;
        }

        // Call AudioSteganography to embed the message
        String secretMessage = secretMessageTextArea.getText();
        AudioSteganography.embedMessageInAudio(selectedFile, secretMessage);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            AudioSteganographyUI ui = new AudioSteganographyUI();
            ui.setVisible(true);
        });
    }
}
