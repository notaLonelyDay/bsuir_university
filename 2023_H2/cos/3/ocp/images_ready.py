from PyQt5.QtWidgets import QApplication, QMainWindow, QLabel, QWidget, QVBoxLayout, QHBoxLayout, QPushButton, QFileDialog, QComboBox, QSpinBox
from PyQt5.QtGui import QPixmap, QImage
from PIL import Image
import numpy as np
import subprocess  # Для запуска диалогового окна открытия файлов



sharpness_matrixes = {
    3: np.array([
        [0, -1, 0],
        [-1, 5, -1],
        [0, -1, 0]
    ]),
    5: np.array([
    [-1, -1, -1, -1, -1],
    [-1,  2,  2,  2, -1],
    [-1,  2,  8,  2, -1],
    [-1,  2,  2,  2, -1],
    [-1, -1, -1, -1, -1]
]),

7: np.array([
    [-1, -1, -1, -1, -1, -1, -1],
    [-1, -1, -1, -1, -1, -1, -1],
    [-1, -1,  2,  2,  2, -1, -1],
    [-1, -1,  2,  8,  2, -1, -1],
    [-1, -1,  2,  2,  2, -1, -1],
    [-1, -1, -1, -1, -1, -1, -1],
    [-1, -1, -1, -1, -1, -1, -1]
]),

9: np.array([
    [-1, -1, -1, -1, -1, -1, -1, -1, -1],
    [-1, -1, -1, -1, -1, -1, -1, -1, -1],
    [-1, -1, -1, -1, -1, -1, -1, -1, -1],
    [-1, -1, -1,  2,  2,  2, -1, -1, -1],
    [-1, -1, -1,  2,  8,  2, -1, -1, -1],
    [-1, -1, -1,  2,  2,  2, -1, -1, -1],
    [-1, -1, -1, -1, -1, -1, -1, -1, -1],
    [-1, -1, -1, -1, -1, -1, -1, -1, -1],
    [-1, -1, -1, -1, -1, -1, -1, -1, -1]
]),

11: np.array([
    [-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1],
    [-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1],
    [-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1],
    [-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1],
    [-1, -1, -1, -1,  2,  2,  2, -1, -1, -1, -1],
    [-1, -1, -1, -1,  2,  8,  2, -1, -1, -1, -1],
    [-1, -1, -1, -1,  2,  2,  2, -1, -1, -1, -1],
    [-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1],
    [-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1],
    [-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1],
    [-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1]
])

}


# gaus_big_matrix = [
#     [1, 1, 2, 2, 2, 1, 1],
#     [1, 2, 2, 4, 2, 2, 1],
#     [2, 2, 4, 8, 4, 2, 2],
#     [2, 4, 8, 16, 8, 4, 2],
#     [2, 2, 4, 8, 4, 2, 2],
#     [1, 2, 2, 4, 2, 2, 1],
#     [1, 1, 2, 2, 2, 1, 1]
# ]


# ostriy_matrix = [    
#     [0, -1, 0],
#     [-1, 5, -1],
#     [0, -1, 0]
# ]


# erozie = [
#     [1, 1, 1],
#     [1, 1, 1],
#     [1, 1, 1]
# ]



# matrixes = {'Гаусово': gaus_big_matrix, 'Резкая матрица': ostriy_matrix, 'Эрозия': erozie}


class ImageFilterApp(QMainWindow):
    def __init__(self):
        super().__init__()
        self.kernel_size = 3

        self.matrixes = {'Блюр': self.generate_blur_matrix,
         'Резкая матрица': self.generate_sharpening_matrix,
          'Эрозия': self.generate_erosion_matrix}
        self.initUI()



    def generate_erosion_matrix(self, size):
        erosion_matrix = [[1] * size for _ in range(size)]
        return erosion_matrix



    def generate_sharpening_matrix(self, size):
        if size in sharpness_matrixes:
            return sharpness_matrixes[size]
        
        if size % 2 == 0 or size < 3:
            raise ValueError("Размер должен быть нечетным и больше или равен 3")
        
        center_value = size ** 2 - (size - 2) ** 2
        side_value = -(size - 2) ** 2
        
        sharpening_matrix = np.full((size, size), side_value)
        sharpening_matrix[1:-1, 1:-1] = 0
        sharpening_matrix[int(size / 2), int(size / 2)] = center_value
        
        return sharpening_matrix


    def generate_blur_matrix(self, size):
        return np.ones((size, size)) / (size * size)


    def generate_matrix(self, name):
        return self.matrixes[name](self.kernel_size)


    def initUI(self):
        self.setWindowTitle("Image Filter App")
        self.setGeometry(100, 100, 1200, 600)  # Увеличим размеры окна

        central_widget = QWidget()
        self.setCentralWidget(central_widget)

        layout = QVBoxLayout()  # Главный вертикальный контейнер
        central_widget.setLayout(layout)

        images_layout = QHBoxLayout()  # Контейнер для изображений (горизонтальный)
        layout.addLayout(images_layout)

        self.original_image_label = QLabel()
        images_layout.addWidget(self.original_image_label)

        self.filtered_image_label = QLabel()
        images_layout.addWidget(self.filtered_image_label)

        buttons_layout = QHBoxLayout()  # Контейнер для кнопок (горизонтальный)
        layout.addLayout(buttons_layout)

        open_button = QPushButton("Choose File")
        open_button.clicked.connect(self.open_image)
        buttons_layout.addWidget(open_button)

        self.matrix_combo = QComboBox()
        self.matrix_combo.addItems(self.matrixes.keys())  # Замените на свои матрицы
        buttons_layout.addWidget(self.matrix_combo)

        process_button = QPushButton("Process")
        process_button.clicked.connect(self.process_image)
        buttons_layout.addWidget(process_button)

        kernel_layout = QHBoxLayout()  # Создаем горизонтальный контейнер для поля ввода размера ядра
        layout.addLayout(kernel_layout)

        kernel_label = QLabel("Kernel Size:")
        kernel_layout.addWidget(kernel_label)

        self.kernel_spinbox = QSpinBox()  # Поле ввода числа для размера ядра
        self.kernel_spinbox.setMinimum(1)
        self.kernel_spinbox.setMaximum(15)  # Установим максимальное значение (можно изменить)
        self.kernel_spinbox.setValue(self.kernel_size)
        self.kernel_spinbox.valueChanged.connect(self.set_kernel_size)  # Подключаем сигнал изменения значения
        kernel_layout.addWidget(self.kernel_spinbox)


    def set_kernel_size(self):
        self.kernel_size = self.kernel_spinbox.value()  # Обновляем размер ядра при изменении значения поля ввода


    def apply_filter(self, image, matrix):
        if matrix in self.matrixes:
            kernel = np.array(self.generate_matrix(matrix))
        else:
            print('not founded')
            kernel = np.ones((3, 3)) / 9  # Усредняющий фильтр 3x3

        img_array = np.array(image)
        filtered_array = np.zeros_like(img_array, dtype=float)

        kernel_size = len(kernel)
        res_height, res_width, _ = img_array.shape

        # Расширение изображения для учета границ
        padded_img = np.pad(img_array, ((kernel_size // 2, kernel_size // 2), (kernel_size // 2, kernel_size // 2), (0, 0)), mode='edge')

        if matrix == 'Эрозия':
            for i in range(res_height):
                for j in range(res_width):
                    for k in range(3):  # Проход по каналам RGB
                        filtered_array[i, j, k] = np.min(
                            kernel * padded_img[i:i + kernel_size, j:j + kernel_size, k]
                        )
        else:
            for i in range(res_height):
                for j in range(res_width):
                    for k in range(3):  # Проход по каналам RGB
                        filtered_array[i, j, k] = np.sum(
                            kernel * padded_img[i:i + kernel_size, j:j + kernel_size, k]
                        ) // np.sum(kernel)

        # Нормализация значений пикселей
        filtered_array = np.clip(filtered_array, 0, 255).astype(np.uint8)

        return Image.fromarray(filtered_array)


    def open_image(self):
        file_dialog = QFileDialog()
        file_dialog.setNameFilter("Images (*.png *.jpg *.bmp)")
        filename, _ = file_dialog.getOpenFileName()

        if filename:
            original_image = Image.open(filename)
            original_pixmap = QPixmap(filename)
            self.original_image_label.setPixmap(original_pixmap.scaledToWidth(600))
            self.original_image = original_image  # Сохраняем изображение для обработки


    def process_image(self):
        selected_matrix = self.matrix_combo.currentText()  # Получаем выбранную матрицу
        if hasattr(self, 'original_image'):
            filtered_image = self.apply_filter(self.original_image, selected_matrix)
            filtered_pixmap = QPixmap.fromImage(QImage(filtered_image.tobytes(), filtered_image.size[0], filtered_image.size[1], QImage.Format_RGB888))
            self.filtered_image_label.setPixmap(filtered_pixmap.scaledToWidth(600))


if __name__ == "__main__":
    import sys

    app = QApplication(sys.argv)
    window = ImageFilterApp()
    window.show()
    sys.exit(app.exec_())
