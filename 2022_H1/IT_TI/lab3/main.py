import math
import sys

from PyQt5.QtWidgets import *

from ui import Ui_Form


def isPrime(a):
    for i in range(2, int(math.sqrt(a) + 2)):
        if a % i == 0:
            return False
    return True


def gcd_extended(num1, num2):
    if num1 == 0:
        return num2, 0, 1
    else:
        div, x, y = gcd_extended(num2 % num1, num1)
    return div, y - (num2 // num1) * x, x


def binPow(a, k):
    pass


def generateKey(p, q, e):
    r = p * q
    f = (p - 1) * (q - 1)
    d, x, y = gcd_extended(f, e)
    # print(d, x, y)
    return y


class MainWindow(QWidget, Ui_Form):
    def __init__(self):
        super(MainWindow, self).__init__()
        self.file = None
        self.setupUi(self)
        self.chooseFileButton.clicked.connect(self.loadFile)
        self.applyButton.clicked.connect(self.validateData)
        self.encryptButton.clicked.connect(self.encrypt)

    def isModeEncrypt(self):
        return self.modeComboBox.currentIndex() == 0

    def loadFile(self):
        d = QFileDialog()
        d.show()
        file = d.getOpenFileName()[0]
        self.file = file
        try:
            with open(file, "rb") as f:
                bytes = f.read(1024)

                if self.isModeEncrypt():
                    bs = ' '.join(str(int(i)) for i in bytes)
                    self.fromEdit.setPlainText(bs)
                else:
                    bs = []
                    for i in range(0, len(bytes), 2):
                        bs.append(bytes[i:i + 2])
                    bs = ' '.join(str(int.from_bytes(i, 'big')) for i in bs)
                    self.fromEdit.setPlainText(bs)

        except Exception as e:
            self.showError("Can't open file")

    def validateData(self):
        if self.isModeEncrypt():
            p = self.pEdit.text()
            q = self.qEdit.text()
            d = self.dEdit.text()
            try:
                p = int(p)
                q = int(q)
                d = int(d)
            except ValueError:
                self.showError("P, D and Q must be integers")
                return

            if not isPrime(p) or not isPrime(q):
                self.showError("P and Q must be prime")
                return
            if not math.gcd((p - 1) * (q - 1), d):
                self.showError("D must be co-prime to (p-1)*(q-1)")
            if p * q not in range(255, 65025):
                self.showError("p*q must be in range (255, 65025)")
                return

            r = p * q
            self.rEdit.setText(str(r))

            self.r = r
            self.p = p
            self.q = q
            self.d = d

            # generateKey(p, q, d)

    def validateEncryptionData(self):
        d = self.dEdit.text()
        r = self.rEdit.text()
        try:
            r = int(r)
            d = int(d)
        except ValueError:
            self.showError("R, D must be integers")
            return
        self.r = r
        self.d = d

    def encrypt(self):
        if self.isModeEncrypt():
            self.validateData()
        else:
            self.validateEncryptionData()
        key = generateKey(self.p, self.q, self.d) if self.isModeEncrypt() else self.d
        text = self.fromEdit.toPlainText().strip()
        a = [int(i) for i in text.split()]
        encryptedA = [pow(i, key, self.r) for i in a]
        encryptedText = ' '.join((str(i) for i in encryptedA))
        self.toEdit.setPlainText(encryptedText)

        if not self.file:
            self.showError("No file selected")
            return

        d = QFileDialog()
        d.setObjectName("encrypted.enc")
        d.show()
        file = d.getSaveFileName()[0]
        with open(self.file, "rb") as fr:
            with open(file, "wb") as fw:
                bytes = fr.read(256)
                if self.isModeEncrypt():
                    while bytes:
                        for b in bytes:
                            fw.write(pow(int(b), key, self.r).to_bytes(2, 'big'))
                        bytes = fr.read(256)
                else:
                    while bytes:
                        bts = []

                        for i in range(0, len(bytes), 2):
                            bts.append(bytes[i:i+2])
                        for b in bts:
                            fw.write(pow(int.from_bytes(b, 'big'), key, self.r).to_bytes(1, 'big'))

                        bytes = fr.read(256)

    def showError(self, error):
        print(error)
        msg = QMessageBox()
        msg.setIcon(QMessageBox.Critical)
        msg.setText("Error")
        msg.setInformativeText(error)
        msg.setWindowTitle("Error")
        msg.exec_()


if __name__ == '__main__':
    app = QApplication(sys.argv)
    main = MainWindow()
    main.show()
    sys.exit(app.exec())
