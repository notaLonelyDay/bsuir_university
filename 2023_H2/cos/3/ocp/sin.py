import numpy as np
import matplotlib.pyplot as plt
import ipywidgets as widgets
from IPython.display import display

# Функция для рисования синусоиды
def plot_sinusoid(A, f, N, phi0):
    # Генерируем точки для синусоиды
    t = np.linspace(0, 2 * np.pi, N)
    y = A * np.sin(2 * np.pi * f * t + phi0)
    
    # Создаем график
    plt.figure(figsize=(8, 4))
    plt.plot(t, y)
    plt.title('Синусоидальный сигнал')
    plt.xlabel('Время')
    plt.ylabel('Амплитуда')
    plt.grid(True)
    plt.show()

# Создаем интерактивные виджеты для изменения параметров
A_slider = widgets.FloatSlider(value=1, min=0, max=5, step=0.1, description='Амплитуда:')
f_slider = widgets.FloatSlider(value=1, min=0, max=10, step=0.1, description='Частота:')
N_slider = widgets.IntSlider(value=100, min=10, max=1000, step=10, description='Количество точек:')
phi0_slider = widgets.FloatSlider(value=0, min=0, max=2*np.pi, step=0.1, description='Начальная фаза:')

# Создаем интерактивную панель
interactive_plot = widgets.interactive(
    plot_sinusoid, A=A_slider, f=f_slider, N=N_slider, phi0=phi0_slider)

# Отображаем интерактивную панель
display(interactive_plot)
