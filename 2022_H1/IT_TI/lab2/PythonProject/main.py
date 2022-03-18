# This is a sample Python script.

# Press Shift+F10 to execute it or replace it with your code.
# Press Double Shift to search everywhere for classes, files, tool windows, actions, and settings.
import os.path
import random
import sys
import threading
from functools import partial

import bitstring
from bitstring import BitArray

previewSize = 1000
# if this ne kratno vosmi ono ne pashet vrod
blocksize = 64000

regsize = 40
keymask = (1 << regsize) - 1
xors = [4, 39]

from PyQt5 import QtCore, QtGui, QtWidgets
from PyQt5.QtGui import QWindow
from PyQt5.QtWidgets import QApplication, QWidget, QFileDialog, QMessageBox

from ui.window import Ui_Encrypter2000


def kthBit(source, k):
    return (source & (1 << (k - 1))) >> (k - 1)


class Window(QWidget, Ui_Encrypter2000):
    def __init__(self):
        super(Window, self).__init__()
        self.setupUi(self)

        self.connectSignals()
        self.generateKey()
        self.isFileEncrypted = False

    def generateKey(self):
        self.initialEdit.setText(''.join(random.choice(['0', '1']) for _ in range(regsize-1)))

    def connectSignals(self):
        self.encryptButton.clicked.connect(partial(self.previewFile, encrypted=False))
        self.decryptButton.clicked.connect(partial(self.previewFile, encrypted=True))

        self.saveOriginal.clicked.connect(partial(self.saveFileInThread, encrypted=False))
        self.saveEncrypted.clicked.connect(partial(self.saveFileInThread, encrypted=True))

    def getPreviewKey(self, size, start=None):
        key = start
        if key is None:
            key = self.getInitialKey()
        if key == -1:
            return None

        ans = []
        for i in range(size):
            left = kthBit(key, regsize-1)
            ans.append(str(left))
            right = kthBit(key, xors[0])
            for j in range(1, len(xors)):
                right ^= kthBit(key, xors[j])
            key = key << 1
            key |= right
            key &= keymask

        return ''.join(ans)

    def getXoredPreview(self, original, text) -> str:
        ans = []
        for a, b in zip(original, text):
            if a == b:
                ans.append('0')
            else:
                ans.append('1')
        return ''.join(ans)

    def previewFile(self, encrypted=False):
        self.isFileEncrypted = encrypted
        rawEdit = self.originalText
        encryptedEdit = self.encryptedText

        if encrypted:
            rawEdit, encryptedEdit = encryptedEdit, rawEdit

        path = self.getFilePath()
        if path is None:
            return
        file = self.getFile(path)
        if file is None:
            return
        rawPart = file.read(previewSize)
        file.close()
        bitArray = BitArray(rawPart)
        rawText = bitArray.bin[2:]
        rawKey = self.getPreviewKey(len(rawText))
        if rawKey is None:
            return 0
        rawEdit.setText(rawText)
        self.keyText.setText(rawKey)
        encryptedEdit.setText(self.getXoredPreview(rawText, rawKey))

    def getFilePath(self):
        path = self.fileEdit.text()
        if not os.path.exists(path):
            self.showError("File doesn't exists")
            return None
        return path

    def getFile(self, path):
        try:
            return open(path, "rb")
        except Exception:
            self.showError("File couldn't be read!")
            return None

    def getInitialKey(self) -> int:
        """Returns -1 if can't get key"""
        keytext = self.initialEdit.text().strip()
        if len(keytext) != regsize-1:
            self.showError(f"Inital encryption bytes must be size of {regsize}")
            return -1
        ans = -1
        try:
            ans = int(keytext, 2)
        except Exception:
            self.showError("Inital encryption bytes must be only 0 and 1")
        return ans

    def generateKey1(self, size, old):
        ans = bitstring.BitString()
        key = old
        for i in range(size):
            for j in range(8):
                left = kthBit(key, regsize-1)
                ans.append(bitstring.Bits(uint=left, length=1))
                right = kthBit(key, xors[0])
                for j in range(1, len(xors)):
                    right ^= kthBit(key, xors[j])
                key = key << 1
                key |= right
                key &= keymask

        return ans, key

    def saveFileInThread(self, encrypted=False):
        name, _ = QFileDialog.getSaveFileName()
        x = threading.Thread(target=self.saveFile, args=(name, encrypted))
        x.start()

    def saveFile(self, name, encrypted=False):
        if self.isFileEncrypted == encrypted:
            self.showError("There is no sense to download uploaded file")
            return None

        path = self.getFilePath()
        if path is None:
            return
        file = self.getFile(path)
        if file is None:
            return
        oldpart = self.getInitialKey()
        ansFile = open(name, "wb")
        while True:
            data = BitArray(file.read(blocksize))
            if len(data) == 0:
                break
            keyData, oldpart = self.generateKey1(len(data), oldpart)
            merged = BitArray()
            for a, b in zip(keyData, data):
                merged.append(bitstring.BitString(uint=a ^ b, length=1))
            ansFile.write(merged.tobytes())

        ansFile.close()
        file.close()

    def validateData(self):
        pass

    def showError(self, message: str):
        msg = QMessageBox()
        msg.setIcon(QMessageBox.Critical)
        msg.setText("Error")
        msg.setInformativeText(message)
        msg.setWindowTitle("Error")
        msg.exec_()


if __name__ == '__main__':
    app = QApplication(sys.argv)
    win = Window()
    win.show()
    sys.exit(app.exec())
