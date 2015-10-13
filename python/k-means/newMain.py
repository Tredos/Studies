__author__ = 'bartek'
import myIos
import matplotlib.pyplot as plt
import math
import types
import numpy as np
import scipy.stats as stats
pointsDict = []
xpoints = []
ypoints = []
x =0
y =1
### class of nodes containing neeeded data, has to change to a list
class Node:
    def __init__(self,list, group =None):
        self.values = list
        self.group = group

    def __str__(self):
        string = '['
        for i in range(0, len(self.values)):
            string += str(self.values[i]) + ' '
        string += str(self.group)+ ']'
        return string

    def __eq__(self, other):
        assert isinstance(other, Node)
        if self.getValues() ==other.getValues() and self.getGroup() ==other.getGroup():
            return True
        else:
            return False

    def getDistance(self,  other):
        dist=0
        assert isinstance(other,Node)
        for i in range(0,len(self.getValues())):
            newDist =  math.pow(self.getValues()[i] - other.getValues()[i], 2)
            dist +=newDist
        dist = dist ** (0.5)
        return dist
    def getValues(self):
        return self.values
    def getGroup(self):
        return self.group
    def setValues(self,values):
        assert isinstance(values, types.ListType)
        self.values = values
    def setGroup(self,group):
        assert isinstance(group, int)
        self.group = group

#plot data from list of lists
def plotData(data):
    for value in data:
        xpoints.append(value[0])
        ypoints.append(value[1])



#main function of the program
def kmeans( nodesList, centers):
    nbCenters = len(centers)

    ##assigning nodes to the groups on base of distance to centers
    for i in range(10):
        #plotDataset(nodesList,centers)
        assignNodesToGroups(nodesList,centers)
        allGroups = separeteGroups(nodesList,nbCenters)
        centers = countCenters(allGroups)

    return allGroups, centers
        # str = '[ '
        # for i in centers:
        #         str += i.__str__()
        # str += ']'
        # print str

    ##------------------------------------Count the centers---------------------------------------

    #separete general list to sublists of groups

def plotDataset(nodesList,centers):
#---------------------Plot-------------------------
    ##plot nodes before changes


    colors = np.linspace(0,1,len(centers))

    for i in range(len(centers)):
        xpoints = []
        ypoints =[]
        for nodes in nodesList:
            assert isinstance(nodes,Node)
            if nodes.group == centers[i].group:
                xpoints.append(nodes.values[0])
                ypoints.append(nodes.values[1])
        plt.plot(xpoints,ypoints,'o',color = plt.cm.jet(colors[i]))

    #plot centers
    xpoints =[]
    ypoints = []
    for nodes in centers:
        assert isinstance(nodes,Node)
        xpoints.append(nodes.values[0])
        ypoints.append(nodes.values[1])

    plt.plot(xpoints,ypoints,'o', color = '0.75')
    plt.show()

    ## count the averahe centers of the groups

def assignNodesToGroups(nodesList, centers):

    for nodes in nodesList:
        assert isinstance(nodes, Node)
        disMin = 1000000
        for center in centers:
            assert isinstance(center, Node)
            dist = nodes.getDistance(center)
            if dist < disMin:
                disMin = dist
                nodes.setGroup(center.getGroup())

def separeteGroups(nodesList,nbCenters):
    allGroups = [] ## wil hold lists of nodes in groups
    for i in range(0, nbCenters):  # for each center
        groupList = []
        for nodes in nodesList:     # for each node
            assert isinstance(nodes,Node)
            if nodes.getGroup()== i:   # if calue node.group is same as certain number adds to list
                groupList.append(nodes)
        allGroups.append(groupList)     #append list of one group to cumulative list

    return allGroups
def countCenters(allGroups):
    centers = []

    for list in allGroups:
        averages = [0] *len(list[0].values)
        for nodes in list:
            assert isinstance(nodes, Node)
            for p in range(len(nodes.values)):
                averages[p] += nodes.values[p]          # get the values of point and add them to average

        for i in range(len(averages)):                      #caunt every average dividing by number o nodes
            averages[i] = averages[i]/float(len(list))
        center = Node(averages,nodes.group)
        centers.append(center)
    return centers

def getListOfNodes(data):
    nodesList = []
    for value in data:
        node1 = Node([float(value[0]), float(value[1])])
        nodesList.append(node1)
    return nodesList

def plotHistogram(x):
    n, bins, patches = plt.hist(x, 100, normed=1, histtype='stepfilled')
    plt.setp(patches, 'facecolor', 'g', 'alpha', 0.75)
    plt.show()


data = myIos.read_data('out.csv')
nodesList = getListOfNodes(data)
centers = []
center = Node([])
for i in range(0, 1):
    center = Node([])
    assert isinstance(center, Node)
    center.__dict__ = nodesList[i].__dict__.copy()
    center.setGroup(i)
    centers.append(center)

allGroups, centers = kmeans(nodesList,centers)
## check if gaussian distribution

## TODO  check if distribution is gaussian, if yes seperate the center into two and run k-means again
## TODO if every is gaussian end
for group in allGroups:
    pointMatrix = []
    for node in group:
        assert isinstance(node,Node)
        centerNode = centers[node.group]
        point = centerNode.getDistance(node)
        pointMatrix.append(point)
    plotHistogram(pointMatrix)
    gauss = stats.normaltest(pointMatrix)
    if gauss[1] < 0.1:
        ##separete the center to two and check if it is better
        centers.remove(centerNode)
        newCenter1 = Node([])
        newCenter2 = Node([])
        newCenter1.__dict__ = centerNode.__dict__.copy()
        newCenter2.__dict__ = centerNode.__dict__.copy()
        newCenter1.setValues([newCenter1.getValues()[0] + 0.01, newCenter1.getValues()[1] + 0.01 ])
        newCenter2.setValues([newCenter2.getValues()[0] + 0.01, newCenter2.getValues()[1] + 0.01 ])
        centers.append(newCenter1)
        centers.append(newCenter2)

plotDataset(nodesList,centers)

### begin with one center
##do kmeans with one center
##check distribution
## separete to two centers
##check if result is better
##if yes keep them
##if not delete them and go back
#kmeans(nodesList)

# ## to be used
#         ## Creation the list holding the centers
#     for i in range(0, nbCenters):
#         center = Node([])
#         assert isinstance(center, Node)
#         center.__dict__ = nodesList[i].__dict__.copy()
#         center.setGroup(i)
#         centers.append(center)