

---

# EchoCrypt: Hiding Secret Data in Audio

## Project Overview

EchoCrypt is an audio steganography tool designed to secure sensitive data by embedding hidden messages within audio files. Using the Least Significant Bit (LSB) coding technique, EchoCrypt conceals information in a way that is both secure and imperceptible to the human ear, making it an ideal solution for covert communication, digital watermarking, and enhanced data privacy.

## Key Features

Stealthy Message Embedding: Utilizes LSB steganography to hide binary data within the least significant bits of audio samples, ensuring that the hidden message remains undetectable.

High Audio Integrity: Maintains high audio quality, preserving the original sound to the human ear while embedding hidden data.

Robust Security: The data embedding process ensures that the message is both hidden and secure, resilient against simple processing alterations.

Wide Compatibility: Works with various audio formats (e.g., WAV) and adaptable to different use cases in secure communication.

## Technical Overview

1. Embedding Process:
   Binary Conversion: The input message is converted into a binary stream.
   LSB Modification: The binary data is embedded by altering the least significant bits of audio samples, achieving a balance between security and audio fidelity.
   Output: Produces an audio file with hidden information, visually identical to the original file but carrying secure data within.

2. Extraction Process:
   LSB Reading: The utility reads the least significant bits of the modified audio samples.
   Binary Reconstruction: Reconstructs the embedded binary data to retrieve the hidden message in its original form.

## Applications

EchoCrypt offers versatile applications, including:

Military and Law Enforcement: Enables covert communication without revealing the existence of a message.

Digital Watermarking: Embeds copyright or ownership data within audio files for intellectual property protection.

Confidential Messaging: Facilitates private communication in personal and business settings, protecting sensitive information.

## Installation & Usage

### Prerequisites

Ensure you have the necessary audio processing libraries and dependencies installed.

### Instructions

1. Clone the Repository:
   ```bash
   git clone https://github.com/YourUsername/EchoCrypt.git
   ```

2. Navigate to the Project Directory:
   ```bash
   cd EchoCrypt
   ```

3. Install Dependencies:
   ```bash
   pip install -r requirements.txt
   ```

4. Run the Program:
   For embedding a message:
   ```bash
   python embed.py -input your-audio.wav -message "Your secret message"
   ```

   For extracting a message:
   ```bash
   python extract.py -input your-audio.wav
   ```

For further details, refer to the [user guide](./docs/user-guide.md) for more in-depth instructions.

## License

This project is licensed under the MIT License. See the [LICENSE](./LICENSE) file for more details.

---


