__author__ = 'bartek'
import myIos
import matplotlib.pyplot as plt
import math
import types
import numpy as np
import scipy.stats as stats
import time
# class of nodes containing needed data, has to change to a list
class Node:
    def copy(self,other):
        self.__dict__ = other.__dict__.copy()
    def __init__(self, list, group=None):
        self.values = list
        self.group = group

    def __str__(self):
        string = '['
        for i in range(0, len(self.values)):
            string += str(self.values[i]) + ' '
        string += str(self.group) + ']'
        return string

    def __eq__(self, other):
        assert isinstance(other, Node)
        if self.getValues() == other.getValues() and self.getGroup() == other.getGroup():
            return True
        else:
            return False

    def getDistance(self, other):
        dist = 0
        assert isinstance(other, Node)
        for i in range(0, len(self.getValues())):
            newDist = math.pow(self.getValues()[i] - other.getValues()[i], 2)
            dist += newDist
        dist = dist ** (0.5)
        return dist


        return
    def getValues(self):
        return self.values

    def getGroup(self):
        return self.group

    def setValues(self, values):
        assert isinstance(values, types.ListType)
        self.values = values

    def setGroup(self, group):
        assert isinstance(group, int)
        self.group = group

class childCenter(Node):
    def __init__(self, gValue):
        super(self).__init__()
        self.gValue = gValue

##TODO class to keep the last value of center from which it is born


# main function of the program
def kmeans(nodesList, centers):
    nbCenters = len(centers)

    ##assigning nodes to the groups on base of distance to centers
    for i in range(5):
        plotDataset(nodesList,centers)
        assignNodesToGroups(nodesList, centers)
        allGroups = separeteGroups(nodesList, nbCenters)
        centers = countCenters(allGroups)

    return allGroups, centers

    # separete general list to sublists of groups


def plotDataset(nodesList, centers):
    colors = np.linspace(0, 1, len(centers))
    for i in range(len(centers)):
        xpoints = []
        ypoints = []
        for nodes in nodesList:
            assert isinstance(nodes, Node)
            if nodes.group == centers[i].group:
                xpoints.append(nodes.values[0])
                ypoints.append(nodes.values[1])
        plt.plot(xpoints, ypoints, 'o', color=plt.cm.jet(colors[i]), ms = 3)

    # plot centers
    xpoints = []
    ypoints = []
    for nodes in centers:
        assert isinstance(nodes, Node)
        xpoints.append(nodes.values[0])
        ypoints.append(nodes.values[1])
    plt.plot(xpoints, ypoints, 'o', color='0.75')
    plt.show()
    #time.sleep(1)

    # count the averahe centers of the groups


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


def separeteGroups(nodesList, nbCenters):
    allGroups = []  ## wil hold lists of nodes in groups
    for i in range(0, nbCenters):  # for each center
        groupList = []
        for nodes in nodesList:  # for each node
            assert isinstance(nodes, Node)
            if nodes.getGroup() == i:  # if calue node.group is same as certain number adds to list
                groupList.append(nodes)
        allGroups.append(groupList)  # append list of one group to cumulative list

    return allGroups


def countCenters(allGroups):
    centers = []
    vectorSize = len(allGroups[0][0].values)
    for list in allGroups:
        averages = [0] * vectorSize
        for nodes in list:
            assert isinstance(nodes, Node)
            for p in range(len(nodes.values)):
                averages[p] += nodes.values[p]  # get the values of point and add them to average

        for i in range(len(averages)):  # caunt every average dividing by number o nodes
            try:
                averages[i] = averages[i] / float(len(list))
            except ZeroDivisionError:
                averages[i] = 0
        center = Node(averages, nodes.group)
        centers.append(center)
    return centers


def getListOfNodes(data):
    nodesList = []
    for value in data:
        node1 = Node([float(value[0]), float(value[1])])
        nodesList.append(node1)
    return nodesList


def plotHistogram(x, title = ''):
    fig = plt.figure()
    ax = fig.add_subplot(111)
    n, bins, patches = plt.hist(x, 100, normed=1, histtype='stepfilled')
    plt.setp(patches, 'facecolor', 'g', 'alpha', 0.75)
    ax.set_title(title)
    ax.set_title(title)
    plt.show()


def isGaussianMeans(allGroups, centers):
    newCenterCreated = False
    newCenters = []
    for group in allGroups:
        pointMatrix = []
        xMatrix = []
        yMatrix = []
        newCenter1 = Node([])
        centerNode = Node([])
        for node in group:
            assert isinstance(node, Node)
            for center in centers:
                if center.getGroup() == node.getGroup():
                    centerNode = center
           # centerNode = centers[node.group]
            point = centerNode.getDistance(node)
            pointMatrix.append(point)
            xMatrix.append(centerNode.getValues()[0] - node.getValues()[0])
            yMatrix.append(centerNode.getValues()[1] - node.getValues()[1])
        # plotHistogram(pointMatrix)
        gaussX = stats.normaltest(xMatrix)
        gaussY = stats.normaltest(yMatrix)
        plotHistogram(xMatrix, "gauss x " + str(gaussX[1]))
        plotHistogram(yMatrix, "gauss y " + str(gaussY[1]))
        print "x " + str(gaussX[1]) + " y " + str(gaussY[1])
        if  gaussX[1] < 0.01 or gaussY[1] < 0.01:
            ##separete the center to two and check if it is better



            newCenter1.setValues([centerNode.getValues()[0] + 0.01, centerNode.getValues()[1] + 0.01])
            newCenter1.setGroup(len(centers) + len(newCenters))
            newCenters.append(newCenter1)
            newCenterCreated = True
    if newCenterCreated:
        centers.extend(newCenters)
        newCenters = []
        allGroups, centers = kmeans(nodesList, centers)
        isGaussianMeans(allGroups,centers)


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

allGroups, centers = kmeans(nodesList, centers)
plotDataset(nodesList,centers)
isGaussianMeans(allGroups, centers)
## check if gaussian distribution

## TODO  check if distribution is gaussian, if yes seperate the center into two and run k-means again
## TODO if every is gaussian end


plotDataset(nodesList, centers)
