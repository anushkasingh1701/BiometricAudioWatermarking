import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class AudioSteganography {
    public static void embedMessageInAudio(File audioFile, String hiddenMessage) {
        try {
            FileInputStream fileInputStream = new FileInputStream(audioFile);
            double fileSize = audioFile.length();
            byte[] audioData = new byte[(int) fileSize];
            fileInputStream.read(audioData);

            // Convert the message to binary format using LSBSteganography
            String binaryMessage = LSBSteganography.stringToBinary(hiddenMessage);

            // Embed the binary message into the audio data
            LSBSteganography.embedMessage(audioData, binaryMessage);

            // Save the modified audio data into a new file
            File outputAudioFile = new File(audioFile.getParent(), "audio_with_message.wav");
            FileOutputStream fileOutputStream = new FileOutputStream(outputAudioFile);
            fileOutputStream.write(audioData);

            // Close the streams
            fileInputStream.close();
            fileOutputStream.close();

            System.out.println("Message embedded successfully in " + outputAudioFile.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
