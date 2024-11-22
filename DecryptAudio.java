import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class DecryptAudio{

    // Method to decrypt message from audio
    public static String extractMessageFromAudio(File audioFile) {
        StringBuilder binaryMessage = new StringBuilder();

        try {
            // Read the audio file into a byte array
            FileInputStream fileInputStream = new FileInputStream(audioFile);
            byte[] audioData = new byte[(int) audioFile.length()];
            fileInputStream.read(audioData);
            fileInputStream.close();

            // WAV file typically has a 44-byte header; skip it
            int headerSize = 44;

            // Extract the least significant bit (LSB) from each byte after the header
            for (int i = headerSize; i < audioData.length; i++) {
                int lsb = audioData[i] & 1; // Extract LSB
                binaryMessage.append(lsb);
            }

            // Convert binary string to text
            return binaryToString(binaryMessage.toString());

        } catch (IOException e) {
            System.out.println("Error reading the audio file.");
            e.printStackTrace();
            return null;
        }
    }

    // Convert a binary string into an ASCII string
    private static String binaryToString(String binaryMessage) {
        StringBuilder message = new StringBuilder();

        // Process the binary string 8 bits at a time (1 byte)
        for (int i = 0; i < binaryMessage.length() - 7; i += 8) {
            String byteString = binaryMessage.substring(i, i + 8); // Get 8 bits (1 byte)
            int charCode = Integer.parseInt(byteString, 2); // Convert binary to integer
            if (charCode == 0) { // Null character (ASCII 0) denotes end of message
                break;
            }
            message.append((char) charCode); // Convert integer to character and append
        }
        return message.toString(); // Return the decoded message
    }

    // Main function for testing the decryption
    public static void main(String[] args) {
        // Specify the path to your audio file
        File audioFileWithMessage = new File("C:\\Users\\HITESH RATHEE\\OneDrive\\Desktop\\minor_pjct\\audio_with_message.wav");

        // Extract and print the hidden message from the audio file
        String extractedMessage = extractMessageFromAudio(audioFileWithMessage);
        if (extractedMessage != null) {
            System.out.println("Extracted Message: " + extractedMessage);
        } else {
            System.out.println("No message found.");
        }
    }
}
