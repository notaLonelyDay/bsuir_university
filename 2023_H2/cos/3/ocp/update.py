
import dash 
from dash.dependencies import Output, Input
from dash import dcc 
from dash import html 
import plotly 
import plotly.graph_objs as go 
import numpy as np
import cmath


MIN_X = 0
MAX_X = 6
X_STEP = 0.01


def moving_average(pnts, kernel):
    result = [0.0] * len(pnts)
    m = (kernel - 1) // 2

    for i in range(len(pnts)):
        kernel_arr = []
        for j in range(i - m, i + m + 1):
            if j < 0:
                kernel_arr.append(pnts[0])
            elif j >= len(pnts):
                kernel_arr.append(pnts[-1])
            else:
                kernel_arr.append(pnts[j])

        total_sum = sum(kernel_arr)
        result[i] = total_sum / kernel

    return result

def parabolic_smooth(pnts, kernel):
    koeffs_fuck = [
        [1],                # Размер окна (ядра) 1
        [1, 2, 1],          # Размер окна (ядра) 3
        [1, 4, 6, 4, 1],    # Размер окна (ядра) 5
        [1, 6, 15, 20, 15, 6, 1]  # Размер окна (ядра) 7
    ]
    delivers_fuck = [sum(arr) for arr in koeffs_fuck]

    koeffs = koeffs_fuck[(kernel - 1) // 2]
    delivers = delivers_fuck[(kernel - 1) // 2]

    result = [0.0] * len(pnts)
    filter_size = len(koeffs)
    m = (filter_size - 1) // 2

    for i in range(len(pnts)):
        summation = 0
        for j in range(filter_size):
            ind = i + j - m
            el = pnts[0] if ind < 0 else pnts[-1] if ind >= len(pnts) else pnts[ind]
            summation += koeffs[j] * el
        result[i] = summation / delivers

    return result


def median_smooth(pnts, kernel):
    result = [0.0] * len(pnts)
    m = (kernel - 1) // 2

    for i in range(len(pnts)):
        kernel_arr = []
        for j in range(i - m, i + m + 1):
            if j < 0:
                kernel_arr.append(pnts[0])
            elif j >= len(pnts):
                kernel_arr.append(pnts[-1])
            else:
                kernel_arr.append(pnts[j])

        kernel_arr.sort()
        result[i] = kernel_arr[m]

    return result


# TODO: window width, restore with phase and not

def discrete_fourier_transform(x):
    N = len(x)
    X = [0] * N
    
    for k in range(N):
        X[k] = sum(x[n] * cmath.exp(2j * cmath.pi * k * n / N) for n in range(N))
    
    return X
    
def fft(x):
    N = len(x)
    if N <= 1:
        return x

    even = fft(x[0::2])
    odd = fft(x[1::2])

    T = [cmath.exp(2j * cmath.pi * k / N) * odd[k] for k in range(N // 2)]

    return [even[k] + T[k] for k in range(N // 2)] + [even[k] - T[k] for k in range(N // 2)]

def compute_amplitude_and_phase(x, is_on_filter, freq_granici):
    N = len(x)
    X = discrete_fourier_transform(x)
    # X = fft(x)

    if is_on_filter:
        for i in range(N):
            if (i < freq_granici[0] or i > freq_granici[1]):
                X[i] = 0+0.0j
            else:
                X[i] *= 2
            if i > N / 2:
                X[i] = 0j

    amplitudes = [abs(X[k]) for k in range(N)]  # Вычисляем амплитуды
    phases = [cmath.phase(X[k]) for k in range(N)]  # Вычисляем фазы


    return X, amplitudes, phases

def ifft(X):
    N = len(X)
    if N <= 1:
        return X

    X = [x.conjugate() for x in X]

    Y = discrete_fourier_transform(X)

    y = [y.conjugate() / N for y in Y]

    return y

def reverse_fourier(amplitudes, phases):
    res = []
    n = len(amplitudes)

    for i in range(n):
        signal = 0
        for j in range(n // 2):
            signal += amplitudes[j] * np.cos(2 * np.pi * i * j / n - phases[j])
        res.append(signal)
    
    return res

def reverse_fourier_ignore_phase(amplitudes):
    res = []
    n = len(amplitudes)

    for i in range(n):
        signal = 0
        for j in range(n // 2):
            signal += amplitudes[j] * np.cos(2 * np.pi * i * j / n)
        res.append(signal)
    
    return res
  
fig = go.Figure(
        go.Scatter(
            visible=False,
            line=dict(color="#00CED1", width=6),
            name="sin" ,
            x=np.arange(MIN_X, MAX_X, X_STEP),
            y=np.sin(np.arange(MIN_X, MAX_X, X_STEP))
    ))
  
app = dash.Dash(__name__) 
  
app.layout = html.Div( 
    [ 
        dcc.Dropdown(['Sinus', 'White noise', 'Saw', 'Square', 'Triangle', 'Harmonics', 'Amplitude Modulation'], 'Sinus', id='chart-id'),
        html.Div([
            html.Div([
                html.Div([
                    html.Div(id='div-amplitude', children='Amplitude: 1'),
                    dcc.Slider(0, 20, 1, value=1, id='amplitude', marks=None, tooltip={"placement": "bottom", "always_visible": True}),
                    html.Div(id='div-frequency', children='Frequency: 1'),
                    dcc.Slider(0, 64, 1, value=1, id='frequency', marks=None, tooltip={"placement": "bottom", "always_visible": True}),
                    html.Div(id='div-phase', children='Phase: 0'),
                    dcc.Slider(0, 6.28, 0.01, value=0, id='phase', marks=None, tooltip={"placement": "bottom", "always_visible": True}),
                    html.Div(id='div-samplingFreq', children='Частота дискретизации: 1'),
                    dcc.Slider(1, 256, 1, value=1,  id='samplingFreq', marks=None, tooltip={"placement": "bottom", "always_visible": True}),
                    html.Div(id='div-windowWidth', children='Window Width: 0'),
                    dcc.Slider(0, 2, 0.01, value=1, id='windowWidth', marks=None, tooltip={"placement": "bottom", "always_visible": True}),
            ]),
            html.Div([
                    html.Div(id='div-period', children='Period: 0', style={'visibility':'hidden'}),
                    dcc.Slider(0, 20, 1, value=0, id='period', disabled=True,),
                    html.Div(id='div-fillingF', children='Filling Factor: 0', style={'visibility':'hidden'}),
                    dcc.Slider(0, 1, 0.01, marks=None, value=0, id='fillingF'),

            ], style={'visibility':'hidden'}),

                html.Div([
                    html.Div(id='div-amplitude2', children='Амплитуда 2: 1'),
                    dcc.Slider(0, 20, 1, value=1, id='amplitude2', marks=None, tooltip={"placement": "bottom", "always_visible": True}),
                    html.Div(id='div-frequency2', children='Частота сигнала 2: 1'),
                    dcc.Slider(0, 64, 1, value=1, id='frequency2', marks=None, tooltip={"placement": "bottom", "always_visible": True}),
                    html.Div(id='div-phase2', children='Фаза 2: 0'),
                    dcc.Slider(0, 6.28, 0.01,  value=0, id='phase2', marks=None, tooltip={"placement": "bottom", "always_visible": True}),

                    html.Div(id='div-amplitude3', children='Амплитуда 3: 1'),
                    dcc.Slider(0, 20, 1, value=1, id='amplitude3', marks=None, tooltip={"placement": "bottom", "always_visible": True}),
                    html.Div(id='div-frequency3', children='Частота сигнала 3: 1'),
                    dcc.Slider(0, 64, 1, value=1, id='frequency3', marks=None, tooltip={"placement": "bottom", "always_visible": True}),
                    html.Div(id='div-phase3', children='Фаза 3: 0'),
                    dcc.Slider(0, 6.28, 0.01, value=0, id='phase3', marks=None, tooltip={"placement": "bottom", "always_visible": True}),

                ], id = 'harmonicssliders', style={'visibility': 'none', 'color': 'brown'}),
                # FILTERS
                html.Div([
                    dcc.Checklist(['Включить фильтры'], style={'margin-bottom': '10px'}, id='on_filters', value=[]),
                    html.Div(id='div-min_freq', children='Минимальная частота'),
                    dcc.Slider(0, 200, 0, value=0, id='min_freq', marks=None, tooltip={"placement": "bottom", "always_visible": True}),
                    html.Div(id='div-max_freq', children='Максимальная частота'),
                    dcc.Slider(0, 200, 0, value=200, id='max_freq', marks=None, tooltip={"placement": "bottom", "always_visible": True}),
                ], style={'margin-top': '50px'}),
                # SGLAGIVANIE
                html.Div([
                    dcc.Checklist(['Включить сглаживание'], style={'margin-bottom': '10px'}, id='sglag_on', value=[]),
                    html.Div(id='div-sglag-type', children='тип сглаживания'),
                    dcc.Dropdown(['Медианное','Усреднённое', 'Параболическое'], 'Медианное', id='sglag_type', style={'margin-top':'10px','margin-bottom':'10px'}),
                    html.Div(id='div-sglag-kernel', children='Ядро'),
                    dcc.Slider(0, 20, 1, value=1, id='kernel', marks=None, tooltip={"placement": "bottom", "always_visible": True}),
                ], style={'margin-top': '50px'})

            ], style={'flex': 2}),

            html.Div([
                dcc.Graph(id = 'live-graph', animate = True, figure = fig), 
                dcc.Graph(id = 'amplitude-spectre', animate = True,), 
                dcc.Graph(id = 'phase-spectre', animate = True,), 
            ], style={'flex': 10}),
        ], style = {'padding': 10, 'display': 'flex', 'flex-direction':'row'})
    ] 
) 

currentPlot = 0

def computeSquare(x, period):
    return np.divide(np.mod(x, period), period)
  
@app.callback( 
    Output('live-graph', 'figure'), 
    Output('amplitude-spectre', 'figure'), 
    Output('phase-spectre', 'figure'), 
    
    Input('chart-id', 'value'),
    Input('amplitude', 'value'),
    Input('frequency', 'value'),
    Input('phase', 'value'),
    Input('samplingFreq', 'value'),
    Input('windowWidth', 'value'),
    Input('period', 'value'),
    Input('fillingF', 'value'),
    Input('amplitude2', 'value'),
    Input('frequency2', 'value'),
    Input('phase2', 'value'),
    Input('amplitude3', 'value'),
    Input('frequency3', 'value'),
    Input('phase3', 'value'),
    
    Input('on_filters', 'value'),
    Input('min_freq', 'value'),
    Input('max_freq', 'value'),

    Input('sglag_on', 'value'),
    Input('sglag_type', 'value'),
    Input('kernel', 'value')
) 
def update_graph_scatter(chart, amplitude, freq, phase, sampling, width, period, fillingF,
                         amplitude2, freq2, phase2, amplitude3, freq3, phase3,
                         on_filters, min_freq, max_freq, 
                         sglag_on, sglag_type, kernel): 
    turn_on_filters = len(on_filters) == 1
    
    is_sglag = len(sglag_on) == 1

    processedValues = []
    amplitudes = []
    phases = []
    values = []
    x_step = 1

    if chart == 'Sinus':
        if freq != 0:
            x_step = X_STEP / freq
        else:
            x_step = X_STEP
        
        values = amplitude * np.sin(np.add(np.multiply(np.arange(MIN_X, MAX_X, x_step), 2 * np.pi * freq), phase))
        # print((MAX_X - MIN_X) / 2 / sampling)
        discreteValues = amplitude * np.sin(np.add(np.multiply(np.arange(MIN_X, width, 1 / sampling), 2 * np.pi * freq), phase))

    elif chart == 'Square':
        xx = np.arange(MIN_X, MAX_X, X_STEP)
        values = np.piecewise(xx, [computeSquare(xx, period) < fillingF, computeSquare(xx, period) >= fillingF ], [amplitude, -amplitude])
        x_step = X_STEP
        xx = np.arange(MIN_X, width, 1 / sampling)
        discreteValues = np.piecewise(xx, [computeSquare(xx, period) < fillingF, computeSquare(xx, period) >= fillingF ], [amplitude, -amplitude])
        # values = np.div(np.mod(np.arange(MIN_X, MAX_X, X_STEP / freq), period), period)

    elif chart == 'Triangle':
        x_step = X_STEP / freq
        values = 2 * amplitude / np.pi * np.arcsin(np.sin(np.add(np.multiply(np.arange(MIN_X, MAX_X, x_step), 2 * np.pi * freq), phase)))
        discreteValues = 2 * amplitude / np.pi * np.arcsin(np.sin(np.add(np.multiply(np.arange(MIN_X, width, 1 / sampling), 2 * np.pi * freq), phase)))
    elif chart == 'Saw':
        x_step = X_STEP / freq
        values = -2 * amplitude / np.pi * np.arctan( 1 / np.tan(np.add(np.multiply(np.arange(MIN_X, MAX_X, x_step), np.pi * freq), phase)))
        discreteValues = -2 * amplitude / np.pi * np.arctan(1 / np.tan(np.add(np.multiply(np.arange(MIN_X, width, 1 / sampling), np.pi * freq), phase)))

    elif chart == 'Harmonics':
        x_step = X_STEP

        values1 = amplitude * np.sin(np.add(np.multiply(np.arange(MIN_X, MAX_X, x_step), 2 * np.pi * freq), phase))
        values2 = amplitude2 * np.sin(np.add(np.multiply(np.arange(MIN_X, MAX_X, x_step), 2 * np.pi * freq2), phase2))
        values3 = amplitude3 * np.sin(np.add(np.multiply(np.arange(MIN_X, MAX_X, x_step), 2 * np.pi * freq3), phase3))

        values = np.add(values3, np.add(values1, values2))
        discreteValues1 = amplitude * np.sin(np.add(np.multiply(np.arange(MIN_X, width, 1 / sampling), 2 * np.pi * freq), phase))
        discreteValues2 = amplitude2 * np.sin(np.add(np.multiply(np.arange(MIN_X, width, 1 / sampling), 2 * np.pi * freq2), phase2))
        discreteValues3 = amplitude3 * np.sin(np.add(np.multiply(np.arange(MIN_X, width, 1 / sampling), 2 * np.pi * freq3), phase3))

        discreteValues = np.add(discreteValues3, np.add(discreteValues1, discreteValues2))

    elif chart == 'Amplitude Modulation':
        x_step = X_STEP

        values1 = amplitude * np.sin(np.add(np.multiply(np.arange(MIN_X, MAX_X, x_step), 2 * np.pi * freq), phase))
        values2 = amplitude2 * np.sin(np.add(np.multiply(np.arange(MIN_X, MAX_X, x_step), 2 * np.pi * freq2), phase2))

        # values = np.mul(values1, values2)
        discreteValues1 = amplitude * np.sin(np.add(np.multiply(np.arange(MIN_X, width, 1 / sampling), 2 * np.pi * freq), phase))
        discreteValues2 = amplitude2 * np.sin(np.add(np.multiply(np.arange(MIN_X, width, 1 / sampling), 2 * np.pi * freq2), phase2))

        values = np.copy(values1)
        for i in range(len(values1)):
            values[i] = values[i] * values2[i]

        # discreteValues = np.add(discreteValues3, np.add(discreteValues1, discreteValues2))
        discreteValues = np.copy(discreteValues1)

        for i in range(len(discreteValues1)):
            discreteValues[i] = discreteValues[i] * discreteValues2[i]

    if is_sglag:
        print(sglag_type)
        if sglag_type == 'Медианное':
            discreteValues = median_smooth(discreteValues, kernel)
        elif sglag_type == 'Усреднённое':
            discreteValues = moving_average(discreteValues, kernel)
        elif sglag_type == 'Параболическое':
            discreteValues = parabolic_smooth(discreteValues, kernel)

    fourierValues, amplitudes, phases = compute_amplitude_and_phase(discreteValues, turn_on_filters, [min_freq, max_freq])
    
    xs = []
    for i in range(len(amplitudes)):
        if amplitudes[i] > 0.1:
            xs.append(i)
        else:
            amplitudes[i] = 0
    
    phases_temp = []

    for i in range(len(phases)):
        if i in xs:
            phases_temp.append(phases[i])
        else:
            phases_temp.append(0)

    phases = phases_temp

    max_ampl = 0
    if (amplitudes):
        max_ampl = max(amplitudes)
        amplitudes[0] = 0
        
    if max_ampl != 0:
        for i in range(len(amplitudes)):
            amplitudes[i] = amplitudes[i] / max_ampl * amplitude
    processedValues = ifft(fourierValues)

    for i in range(len(processedValues)):
        processedValues[i] = processedValues[i].real

    # processedValues = reverse_fourier(amplitudes, phases)

    prevlen = len(processedValues)
    i = prevlen
        
    if prevlen != 0:
        while i / sampling <= MAX_X:
            processedValues.append(processedValues[i % prevlen])
            i += 1
  
    chartData = plotly.graph_objs.Scatter( 
            x=np.arange(MIN_X, MAX_X, x_step),
            y=values,
            name=chart, 
            mode= 'lines'
            # mode= 'lines+markers'
    ) 

    fourierData = plotly.graph_objs.Scatter( 
            x=np.arange(MIN_X, MAX_X, 1 / sampling),
            y=processedValues,
            name= chart+' Fourier', 
            mode= 'lines'
    ) 
    
    print(amplitudes)
    print(len(amplitudes))
    print(phases)
    print(len(phases))
  
    return {'data': [chartData, fourierData] }, {'data': 
    [
        {
            'x': np.arange(0, len(amplitudes)),
            'y': amplitudes,
            'type': 'bar',
            'name': 'Amplitudes'
        }
    ] }, {'data': [
        {
            'x': np.arange(0, len(phases)),
            'y': phases,
            'type': 'bar',
            'name': 'Phases'
        }
    ]}
            # 'layout' : go.Layout(xaxis=dict(range=[min(X),max(X)]),yaxis = dict(range = [min(Y),max(Y)]),)} 
            
@app.callback(
    Output('amplitude', 'disabled'),
    Output('frequency', 'disabled'),
    Output('samplingFreq', 'disabled'),
    Output('phase', 'disabled'),
    Output('windowWidth', 'disabled'),
    Output('period', 'disabled'),
    Output('fillingF', 'disabled'),
    Output('amplitude2', 'disabled'),
    Output('frequency2', 'disabled'),
    Output('phase2', 'disabled'),
    Output('amplitude3', 'disabled'),
    Output('frequency3', 'disabled'),
    Output('phase3', 'disabled'),

    Input('chart-id', 'value')
)
def update_sliders(chartValue):
    if chartValue == 'Sinus':
        return False, False, False, False, False, True, True, True, True, True, True, True, True
    elif chartValue == 'Triangle':
        return False, False, False, False, False, True, True, True, True, True, True, True, True
    elif chartValue == 'Saw':
        return False, False, False, False, False, True, True, True, True, True, True, True, True
    elif chartValue == 'Square':
        return False, True, False, True, False, False, False, True, True, True, True, True, True
    elif chartValue == 'Harmonics':
        return False, False, False, False, False, True, True, False, False, False, False, False, False
    elif chartValue == 'Amplitude Modulation':
        return False, False, False, False, False, True, True, False, False, False, True, True, True
    
    return True, True, True, True, True, True, True, True, True, True, True, True, True


@app.callback(
    Output('div-amplitude', 'children'),
    Input('amplitude', 'value')
)
def update_amplitude_div(value):
    return f'Амплитуда: {value}'

@app.callback(
    Output('div-frequency', 'children'),
    Input('frequency', 'value')
)
def update_frequency_div(value):
    return f'Частота сигнала: {value}'

@app.callback(
    Output('div-phase', 'children'),
    Input('phase', 'value')
)
def update_phase_div(value):
    return f'Фаза: {value}'

@app.callback(
    Output('div-samplingFreq', 'children'),
    Input('samplingFreq', 'value')
)
def update_sampl_freq_div(value):
    return f'Частота дискретизации: {value}'
  
@app.callback(
    Output('div-windowWidth', 'children'),
    Input('windowWidth', 'value')
)
def update_sampl_freq_div(value):
    return f'Ширина окна: {value}'

# @app.callback(
#     Output('div-period', 'children'),
#     Input('period', 'value')
# )
# def update_sampl_freq_div(value):
#     return f'Период: {value}'

# @app.callback(
#     Output('div-fillingF', 'children'),
#     Input('fillingF', 'value')
# )
# def update_sampl_freq_div(value):
#     return f'Filling Factor: {value}'

@app.callback(
    Output('div-amplitude2', 'children'),
    Input('amplitude2', 'value')
)
def update_amplitude_div(value):
    return f'Амплитуда 2: {value}'

@app.callback(
    Output('div-frequency2', 'children'),
    Input('frequency2', 'value')
)
def update_frequency_div(value):
    return f'Частота сигнала 2: {value}'

@app.callback(
    Output('div-phase2', 'children'),
    Input('phase2', 'value')
)
def update_phase_div(value):
    return f'Фаза 2: {value}'

@app.callback(
    Output('div-amplitude3', 'children'),
    Input('amplitude3', 'value')
)
def update_amplitude_div(value):
    return f'Амплитуда 3: {value}'

@app.callback(
    Output('div-frequency3', 'children'),
    Input('frequency3', 'value')
)
def update_frequency_div(value):
    return f'Частота сигнала 3: {value}'

@app.callback(
    Output('div-phase3', 'children'),
    Input('phase3', 'value')
)
def update_phase_div(value):
    return f'Фаза 3: {value}'

# @app.callback(
#     Input('min_freq', 'value'),
# def update_min_freq(value):
#     return ''

# @app.callback(
#     Input('max_freq', 'value')
# )
# def update_min_freq(value):
#     return ''


if __name__ == '__main__': 
    app.run_server()
