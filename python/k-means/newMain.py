__author__ = 'bartek'
import myIos
import matplotlib.pyplot as plt
import math
import types
import numpy as np

pointsDict = []

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
            a = self.getValues(i)
            b = other.getValues(i)
            newDist =  math.pow(a-b , 2)
            dist +=newDist
        dist = dist ** (0.5)
        return dist

    def getValues(self, fieldNr=None):
        if fieldNr == None:
            return self.values
        else:
            if fieldNr<len(self.values):
                return self.values[fieldNr]
            else:
                return False
    def getGroup(self):
        return self.group
    def setValues(self,values):
        assert isinstance(values, types.ListType)
        self.values = values
    def setGroup(self,group):
        assert isinstance(group, int)
        self.group = group
#plot data from list of lists

def plotDataset(nodesList, centers):

    groups = []
    nbGroups = 0
    for node in nodesList:
        assert isinstance(node,Node)
        try:
            groups.index(node.getGroup())
        except ValueError:
            groups.append(node.getGroup())
            nbGroups +=1
    colors = np.linspace(0, 1, nbGroups)
    for i in range(nbGroups):
        xpoints = []
        ypoints = []
        for nodes in nodesList:
            assert isinstance(nodes, Node)
            if nodes.group == groups[i]:
                xpoints.append(nodes.values[0])
                ypoints.append(nodes.values[1])
        plt.plot(xpoints, ypoints, 'o', color=plt.cm.jet(colors[i]), ms=5)

    # plot centers
    xpoints = []
    ypoints = []
    for nodes in centers:
        assert isinstance(nodes, Node)
        xpoints.append(nodes.values[0])
        ypoints.append(nodes.values[1])
    plt.plot(xpoints, ypoints, 'o', color='0', ms=7)
    plt.show()

#main function of the program
def kmeans(nbCenters, nodesList):
    centers = []
    ## Creation the ditionary holding the centers
    for i in range(0, nbCenters):
        center = Node([])
        assert isinstance(center, Node)
        center.__dict__ = nodesList[i].__dict__.copy()
        center.setGroup(i)
        centers.append(center)

    ##assigning nodes to the groups on base of distance to centers
    for i in range(20):
        assignNodesToGroups(nodesList,centers)
        allGroups = separeteGroups(nodesList,nbCenters)
        centers = countCenters(allGroups)
        if len(centers[0].getValues()) ==2:
            plotDataset(nodesList,centers)

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
        if len(list)>0:
            averages = [0] *len(list[0].values)
            for nodes in list:
                assert isinstance(nodes, Node)
                for p in range(len(nodes.values)):
                    averages[p] += nodes.values[p]          # get the values of point and add them to aver
            for i in range(len(averages)):                      #caunt every average dividing by number o nodes
                averages[i] = averages[i]/float(len(list))
            center = Node(averages,nodes.group)
            centers.append(center)
    return centers

def getListOfNodes(data):
    nodesList = []
    for value in data:
        node1 = Node(value)
        nodesList.append(node1)
    return nodesList


def readIrisData(filename, skip_first_line = False, ignore_first_column = False, bounds= [0,4]):
    f = open(filename,'r')
    if skip_first_line:
        f.readline()

    data = []
    for line in f:
        line = line.split(",")
        line = [ float(x) for x in line[:len(line)-1] ]
        if ignore_first_column:
            line = line[1:]
        line  = line[bounds[0]:bounds[1]]
        if len(line):
            data.append(line)
    f.close()
    return data

def mainFunc():
    data = readIrisData('iris.csv',)

    print data
    nodesList = getListOfNodes(data)
    # for d in nodesList:
    #     print d
    # print "-----------------------------------------------------------------------------"
    kmeans(3, nodesList)
    for d in nodesList:
        print d
    print len(nodesList)
    nbOnes= 0
    nbTwos = 0
    nbThrees = 0

    for node in nodesList:
        group = node.getGroup()
        if group ==0:
            nbOnes +=1
        elif group ==1:
            nbTwos +=1
        elif group ==2:
            nbThrees +=1

    print "Number of setosa  " + str(nbOnes) + " nb of versicolor " + str(nbTwos) + " nb of virginica " + str(nbThrees)

mainFunc()

