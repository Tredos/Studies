from unittest import TestCase
import newMain as mn
__author__ = 'bartek'


def TestCountCenters(self, TestCase):

    nodes = [mn.Node([3,3],1),mn.Node([1,1],1),mn.Node([-1,-1],1)]

    allgroups = [nodes, nodes]
    centers  = mn.countCenters(allgroups)
    for i in centers:
        assert isinstance(i, mn.Node)

    pass
