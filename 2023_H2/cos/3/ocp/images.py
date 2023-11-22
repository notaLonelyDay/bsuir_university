from PIL import Image
import numpy as np


def apply_filter(image, kernel):
    width, height = image.size
    kernel_size = len(kernel)

    # Конвертация изображения в массив numpy
    img_array = np.array(image)

    # Размеры для результирующего изображения
    res_width = width - kernel_size + 1
    res_height = height - kernel_size + 1

    # Создание пустого результирующего изображения
    result = np.zeros((res_height, res_width, 3), dtype=np.uint8)

    # Применение фильтрации
    for i in range(res_height):
        for j in range(res_width):
            for k in range(3):  # Проход по каналам RGB
                result[i, j, k] = np.sum(kernel * img_array[i:i+kernel_size, j:j+kernel_size, k]) // np.sum(kernel)


    return Image.fromarray(result)


if __name__ == "__main__":
    img = Image.open('dora.jpg')

    kernel = np.array([
        [-1, -1, -1, -1, -1, -1, -1],
        [-1, -1, -1, -1, -1, -1, -1],
        [-1, -1, 49, 49, 49, -1, -1],
        [-1, -1, 49, 49, 49, -1, -1],
        [-1, -1, 49, 49, 49, -1, -1],
        [-1, -1, -1, -1, -1, -1, -1],
        [-1, -1, -1, -1, -1, -1, -1]
    ]) / 49  # Матрица увеличения резкости 7x7

    
    img_new = apply_filter(img, kernel) 
    img_new.save('temp_image.jpg')
    # img_new.show()
