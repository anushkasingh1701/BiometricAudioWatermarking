import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class DecryptAudio{

    // Method to decrypt message from audio
    public static String extractMessageFromAudio(File audioFile, String password) {
        StringBuilder binaryMessage = new StringBuilder();

        try {
            FileInputStream fileInputStream = new FileInputStream(audioFile);
            byte[] audioData = new byte[(int) audioFile.length()];
            fileInputStream.read(audioData);
            fileInputStream.close();

            // Skip WAV header
            int headerSize = 44;

            for (int i = headerSize; i < audioData.length; i++) {
                int lsb = audioData[i] & 1;
                binaryMessage.append(lsb);
            }

            String extractedText = binaryToString(binaryMessage.toString());

            // Separate encrypted metadata and hash
            String[] parts = extractedText.split("::");
            if (parts.length != 2) {
                return "Invalid or incomplete data.";
            }

            String encrypted = parts[0];
            String extractedHash = parts[1];

            // Recalculate hash and verify
            String recalculatedHash = HashUtil.sha256(encrypted);
            if (!recalculatedHash.equals(extractedHash)) {
                return "Integrity check failed or data has been tampered.";
            }

            // Decrypt metadata
            return AESUtil.decrypt(encrypted, password);

        } catch (Exception e) {
            e.printStackTrace();
            return "Incorrect password.";
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
        File audioFileWithMessage = new File("audio_with_message.wav");

        // ✅ New version with password
        String password = "YourPasswordHere"; // Replace with actual password used during embedding

        String extractedMessage = extractMessageFromAudio(audioFileWithMessage, password);
        if (extractedMessage != null) {
            System.out.println("Extracted Message: " + extractedMessage);
        } else {
            System.out.println("No message found.");
        }
    }

}
