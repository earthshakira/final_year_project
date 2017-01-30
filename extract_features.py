import numpy as np

import scipy.io.wavfile as wav
from python_speech_features import mfcc
from python_speech_features import logfbank
import sys

#
# emo = {
#     'W': 'anger',
#     'L': 'boredom',
#     'E': 'disgust',
#     'A': 'fear',
#     'F': 'happiness',
#     'T': 'sadness',
#     'N': 'neutral',
#     }

emo = {
    'W': '1',
    'L': '2',
    'E': '3',
    'A': '4',
    'F': '5',
    'T': '6',
    'N': '0',
    }

(rate, sig) = wav.read(sys.argv[1])
print("Wave File Read.");
mfcc_feat = mfcc(sig, rate);
print("MFCC Calculated.");
print("Writing to test file....")
rf = open("mfcc_file.te", "w")
for x in mfcc_feat:
    j = 0;
    rf.write("0 ");
    while j < 12:
        j += 1
        rf.write(str(j))
        rf.write(":")
        rf.write(str(x[j]))
        rf.write(" ")
    rf.write("\n")
        
print("Write Completed");
