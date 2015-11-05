__author__ = 'bartek'
import myIos
import matplotlib.pyplot as plt
from mpl_toolkits.mplot3d import Axes3D
import math
import types
import numpy as np

pointsDict = []

### class of nodes containing neeeded data, has to change to a list


class Node:
    def __init__(self,list, group =None):
        """
        :type list: list of floats represesentig properties
        :type group: int ; number of group to which the NOde belog to
        """
        self.values = list
        self.group = group

    def __str__(self):
        string = '['
        for i in range(0, len(self.values)):
            string += str(self.values[i]) + '] [ '
        string += str(self.group)+ ']'
        return string

    def __eq__(self, other): #return true if the values are equal and false if not
        assert isinstance(other, Node)
        if self.getValues() == other.getValues() and self.getGroup() == other.getGroup():
            return True
        else:
            return False

    def getDistance(self,  other): # calculates the distance between the two Nodes
        """
        :type other: Node
        """
        dist=0
        assert isinstance(other,Node)
        for i in range(0,len(self.getValues())):                          # for every property of Node
            newDist =  math.pow(self.getValues(i)-other.getValues(i) , 2) # get the difference between property of self and other Nodes and get square
            dist +=newDist                                                #add to total distance
        dist = dist ** (0.5)            # get the square root from total distance
        return dist

    # returns the list of values of the Node
    def getValues(self, fieldNr=None):
        if fieldNr == None:
            return self.values
        else:
            if fieldNr<len(self.values):
                return self.values[fieldNr]
            else:
                return False
    #return the group of the NOde
    def getGroup(self):
        return self.group

    #set the list of values of the Node
    def setValues(self,values):
        assert isinstance(values, types.ListType)
        self.values = values
    # set the group of a Node
    def setGroup(self,group):
        assert isinstance(group, int)
        self.group = group
#plot data from list of lists
def plotDataset3D(nodesList, centers):
    colors = np.linspace(0, 1, len(centers))
    groups = []
    nbGroups = 0
    fig = plt.figure()
    ax = fig.add_subplot(111, projection='3d')
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
        zpoints = []
        for nodes in nodesList:
            assert isinstance(nodes, Node)
            if nodes.group == groups[i]:
                xpoints.append(nodes.values[0])
                ypoints.append(nodes.values[1])
                zpoints.append(nodes.values[2])
        plt.scatter(xpoints, ypoints, zs = zpoints, zdir=u'z', s=20, c=plt.cm.jet(colors[i]), depthshade=True)

    # plot centers
    xpoints = []
    ypoints = []
    zpoints = []
    for nodes in centers:
        assert isinstance(nodes, Node)
        xpoints.append(nodes.values[0])
        ypoints.append(nodes.values[1])
        zpoints.append(nodes.values[2])
        i = nodes.getGroup()
        plt.scatter(xpoints, ypoints, zs = zpoints, zdir=u'z', s=100, c=plt.cm.jet(colors[i]), depthshade=False, marker ='^')
        plt.xlabel(" x ")
        plt.ylabel("y")
    plt.show()

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
        elif len(centers[0].getValues()) ==3:
            plotDataset3D(nodesList,centers)

# assign number of group to each Node
def assignNodesToGroups(nodesList, centers):

    for nodes in nodesList:             #each node
        assert isinstance(nodes, Node)
        disMin = 1000000                #set the min value bigger than expected
        for center in centers:          # find the closest center
            assert isinstance(center, Node)
            dist = nodes.getDistance(center)
            if dist < disMin:           # if distance to center is smaller than minimal, set it as minimal
                disMin = dist
                nodes.setGroup(center.getGroup())   #assign number of group as the center has


#seperate the list to list of lists containing only the nodes which are in the same group
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

# changes the values of the centers, so they are in the middle of the group they create
def countCenters(allGroups):
    centers = []

    for list in allGroups:
        if len(list)>0:
            averages = [0] *len(list[0].values)
            for nodes in list:
                assert isinstance(nodes, Node)
                for p in range(len(nodes.values)):
                    averages[p] += nodes.values[p]              # get the values of point and add them to aver
            for i in range(len(averages)):                      # count every average dividing by number o nodes
                averages[i] = averages[i]/float(len(list))
            center = Node(averages,list[0].getGroup())
            centers.append(center)
    return centers

# creates the list of Nodes from the list of values
def getListOfNodes(data):
    nodesList = []
    for value in data:          # for every value in data
        node1 = Node(value)     # make new node from values
        nodesList.append(node1) # append to list of nodes
    return nodesList

#read data from Iris file.
def readIrisData(filename, skip_first_line = False, ignore_first_column = False, bounds= [0,4]):

    f = open(filename,'r') #open file

    if skip_first_line:
        f.readline()

    data = []
    for line in f:      # for every line in a file
        line = line.split(",")          # separete the values by comma
        line = [ float(x) for x in line[:len(line)-1] ] # change all the values to float and delete the last one
        if ignore_first_column:
            line = line[1:]
        line  = line[bounds[0]:bounds[1]]              # if needed select only some properties
        if len(line):
            data.append(line)                           # add line to the data
    f.close()                                           # close file
    return data

def mainFunc():
    data = readIrisData('iris.csv',bounds = [0,3]) #read data from Iris file
    nodesList = getListOfNodes(data)                # generate the list of Nodes
    kmeans(3, nodesList)                            # run the function with 3 centers
    index = 0
    for d in nodesList:
        if index%50 ==0:
            print "-------------------------------------"
        print d
        index +=1
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

