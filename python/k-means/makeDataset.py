__author__ = 'bartek'

import myIos
import matplotlib.pyplot as plt
data = []
xpoints = []
ypoints = []
data = []
def save(data, Filename = 'out.csv' ):
    myIos.write_data(data, Filename)
    return True


def plotData(data):
    xpoints = []
    ypoints = []
    for value in data:
        xpoints.append(value[0])
        ypoints.append(value[1])

    a = plt.Figure()

    plt.plot(xpoints,ypoints,'ro',ms = 0.4)
    plt.show()
    plt.close("all")
def generate():
    data = myIos.genRandomGaussianGroups(5000,5)
    plotData(data)
    return data

def plotSaved():
    data = myIos.read_data('out.csv')
    plotData(data)

def joe():
    print 'bwanakubwa'

def menu(argument):
    global data
    if argument =='s':
        save(data)
    elif argument =='n':
        data = generate()
    elif argument == 'p':
        plotSaved()
    elif argument == 'k':
        joe()
    elif argument =='e':
        pass


# data  = ios.read_data('out.csv')

k = "n"
while k!= 'e':
    k = raw_input('Save data (\'s\'), generate new set(\'n\'), plot saved data(\'p\'), exit(\'e\')?')
    menu(k)


