import face_recognition
import cv2
import sys
import numpy as np
import time

cap = cv2.VideoCapture(0)
if not cap.isOpened():
    print("CameraError")
    sys.exit()

print("Align your face — capturing in 3 seconds...")
start_time = time.time()
frame = None

while True:
    ret, frame = cap.read()
    if not ret:
        print("CaptureFailed")
        break

    cv2.imshow("Face Capture for Embedding", frame)

    if time.time() - start_time > 3:
        break

    if cv2.waitKey(1) & 0xFF == 27:  # ESC key
        print("UserCancelled")
        break

cap.release()
cv2.destroyAllWindows()

if frame is None:
    print("NoCapture")
    sys.exit()

try:
    encoding = face_recognition.face_encodings(frame)[0]
    if len(encoding) != 128:
        print("InvalidEncodingLength")
        sys.exit()
    encoding_str = ",".join(str(val) for val in encoding)
    print(encoding_str)
except:
    print("NoFace")
    sys.exit()

