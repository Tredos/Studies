__author__ = 'bartek'
import ios
import myIos
import matplotlib.pyplot as plt
import math

pointsDict = []
xpoints = []
ypoints = []
x =0
y =1
### class of nodes containing neeeded data, has to change to a list
class node:
    def __init__(self, x, y):
        self.x = x
        self.y = y
        self.group = None

    def __str__(self):
        string = '[' + str(self.x) + ' ' + str(self.y) +' ' + str(self.group)+ ']'
        return string

    def __eq__(self, other):
        if self.x == other.x and self.y == other.y:
            return True
        else:
            return False

    def getDistance(self, node2):
       if isinstance(node2,node):
            dist = math.sqrt( abs(self.x- node2.x + self.y - node2.y))
            return dist

#plot data from list of lists
def plotData(data):
    for value in data:
        xpoints.append(value[0])
        ypoints.append(value[1])

    plt.plot(xpoints, ypoints, 'ro')
    plt.show()

#main function of the program
def kmeans(nbCenters, nodesList):
    centers = {}
    ## Creation the ditionary holding the centers
    for i in range(0, nbCenters):
        centers.update({str(i): nodesList[i]})

    ##assigning nodes to the groups on base of distance to centers
    for nodes in nodesList:
        assert isinstance(nodes, node)
        disMin = 1000000
        for key,value in centers.items():
            if nodes.getDistance(value) < disMin:
                print nodes.getDistance(value)
                disMin =nodes.getDistance(value)
                nodes.group = key

    ##------------------------------------Count the centers---------------------------------------

    #separete general list to sublists of groups
    allGroups = [] ## wil hold lists of nodes in groups
    for i in range(0, nbCenters):  # for each center
        groupList = []
        for nodes in nodesList:     # for each node
            if nodes.group == str(i):   # if calue node.group is same as certain number adds to list
                groupList.append(nodes)
        allGroups.append(groupList)     #append list of one group to cumulative list

#---------------------Plot-------------------------
    ##plot nodes before changes
    xpoints = []
    ypoints =[]
    for nodes in nodesList:
        xpoints.append(nodes.x)
        ypoints.append(nodes.y)
    plt.plot(xpoints,ypoints,'bo',)

    #plot centers
    xpoints =[]
    ypoints = []
    for key,value in centers.items():
        print str(key) + ' ' + str(value) #calculate centers
        assert isinstance(value,node)
        xpoints.append(value.x)
        ypoints.append(value.y)

    plt.plot(xpoints,ypoints,'ro')
    plt.show()

    #---------------------Plot-------------------------
    ##plot nodes before changes
    xpoints = []
    ypoints =[]
    for nodes in nodesList:
        xpoints.append(nodes.x)
        ypoints.append(nodes.y)
    plt.plot(xpoints,ypoints,'bo',)

    #plot centers
    xpoints =[]
    ypoints = []
    for key,value in centers.items():
        print str(key) + ' ' + str(value) #calculate centers
        assert isinstance(value,node)
        xpoints.append(value.x)
        ypoints.append(value.y)

    plt.plot(xpoints,ypoints,'ro')
    plt.show()


    ## count the averahe centers of the groups
    for list in allGroups:
        xAverage=0
        yAverage =0
        for nodes in list:
            assert isinstance(nodes, node)
            xAverage += nodes.x
            yAverage += nodes.y
        xAverage = float(xAverage)/len(list)
        yAverage = float(yAverage)/len(list)
        node1 = node(xAverage,yAverage)
        centers.update({str(list[0].group): node1})



def getListOfNodes(data):
    nodesList = []
    for value in data:
        node1 = node(float(value[0]), float(value[1]))
        nodesList.append(node1)
    return nodesList


data = myIos.read_data('out.csv')
nodesList = getListOfNodes(data)

kmeans(5, nodesList)

