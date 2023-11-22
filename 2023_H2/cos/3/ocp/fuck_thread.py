from PyQt5.QtWidgets import QApplication, QMainWindow, QLabel, QWidget, QVBoxLayout, QHBoxLayout, QPushButton, QFileDialog, QComboBox, QSpinBox
from PyQt5.QtGui import QPixmap, QImage
from PIL import Image
import numpy as np
import subprocess  # Для запуска диалогового окна открытия файлов
from concurrent.futures import ThreadPoolExecutor


gaus_big_matrix = [
    [1, 1, 2, 2, 2, 1, 1],
    [1, 2, 2, 4, 2, 2, 1],
    [2, 2, 4, 8, 4, 2, 2],
    [2, 4, 8, 16, 8, 4, 2],
    [2, 2, 4, 8, 4, 2, 2],
    [1, 2, 2, 4, 2, 2, 1],
    [1, 1, 2, 2, 2, 1, 1]
]


ostriy_matrix = [    
    [0, -1, 0],
    [-1, 5, -1],
    [0, -1, 0]
]


erozie = [
    [1, 1, 1],
    [1, 1, 1],
    [1, 1, 1]
]



matrixes = {'Гаусово': gaus_big_matrix, 'Резкая матрица': ostriy_matrix, 'Эрозия': erozie}


class ImageFilterApp(QMainWindow):
    def __init__(self):
        super().__init__()
        self.kernel_size = 3

        self.initUI()


    def generate_matrix(name):
        
        pass

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
        self.matrix_combo.addItems(matrixes.keys())  # Замените на свои матрицы
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
        if matrix in matrixes:
            kernel = np.array(matrixes[matrix])
        else:
            kernel = np.ones((3, 3)) / 9  # Усредняющий фильтр 3x3

        img_array = np.array(image)
        filtered_array = np.zeros_like(img_array, dtype=float)

        kernel_size = len(kernel)
        res_height, res_width, _ = img_array.shape

        # Расширение изображения для учета границ
        padded_img = np.pad(img_array, ((kernel_size // 2, kernel_size // 2), (kernel_size // 2, kernel_size // 2), (0, 0)), mode='edge')

        return self.parallel_apply_filter(padded_img, kernel, kernel_size, res_height, res_width)

    def apply_filter_part(self, image_part, kernel):
        part_res_height, part_res_width, _ = image_part.shape
        filtered_array = np.zeros((part_res_height, part_res_width, 3), dtype=float)

        for i in range(part_res_height):
            for j in range(part_res_width):
                for k in range(3):  # Проход по каналам RGB
                    # Вычисление границы для ядра на частях изображения
                    i_end = i + len(kernel)
                    j_end = j + len(kernel[0])

                    # Обработка только тех частей, где полностью помещается ядро
                    if i_end <= part_res_height and j_end <= part_res_width:
                        # Применение ядра к фрагменту изображения
                        fragment = image_part[i:i_end, j:j_end, k]
                        filtered_array[i, j, k] = np.sum(kernel * fragment) // np.sum(kernel)

        # Нормализация значений пикселей
        filtered_array = np.clip(filtered_array, 0, 255).astype(np.uint8)

        return filtered_array

    def parallel_apply_filter(self, img_array, kernel, kernel_size, res_height, res_width):
        # Определение параметров для распределения по потокам
        num_parts = 1  # Можете изменить на желаемое количество частей

        part_height = res_height // num_parts
        part_width = res_width // num_parts

        # Создание пула потоков
        with ThreadPoolExecutor(max_workers=num_parts) as executor:
            # Выполнение apply_filter_part для каждой части изображения параллельно
            results = []
            for i in range(num_parts):
                for j in range(num_parts):
                    # Вычисление индексов частей изображения для обработки
                    start_i, end_i = i * part_height, (i + 1) * part_height
                    start_j, end_j = j * part_width, (j + 1) * part_width

                    # Подготовка данных для обработки в отдельном потоке
                    image_part = img_array[start_i:end_i, start_j:end_j]
                    future = executor.submit(self.apply_filter_part, image_part, kernel)
                    results.append((future, start_i, start_j))

            # Получение результатов из всех потоков
            part_results = []
            for future, start_i, start_j in results:
                part_result = future.result()
                part_results.append((part_result, start_i, start_j))

        # Объединение отфильтрованных частей изображения в одно изображение
        return self.combine_parts(part_results, res_height, res_width)

    def combine_parts(self, part_results, res_height, res_width):
        # Создание пустого массива для объединения отфильтрованных частей
        filtered_array = np.zeros((res_height, res_width, 3), dtype=np.uint8)

        # Объединение частей изображения в одно
        for part_result, start_i, start_j in part_results:
            filtered_array[start_i:start_i + part_result.shape[0], start_j:start_j + part_result.shape[1]] = part_result

        # Создание итогового изображения
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
