__author__ = 'bartek'

import myIos
import matplotlib.pyplot as plt
data = []
xpoints = []
ypoints = []

def save(Filename = 'out.csv' ):
    global data
    myIos.write_data(data, Filename)
    return True


def plotData(data):

    for value in data:
        xpoints.append(value[0])
        ypoints.append(value[1])

    plt.plot(xpoints,ypoints,'ro')
    plt.show()

def generate():
    global data
    data = myIos.genRandomGaussianGroups(500,5)
    plotData(data)

def plotSaved():
    data = myIos.read_data('out.csv')
    plotData(data)


def joe():
    print 'bwanakubwa'

def menu(argument):

    if argument =='s':
        save()
    elif argument =='n':
        generate()
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


