from unittest import TestCase
import newMain as main
import math
__author__ = 'bartek'


class TestNode(TestCase):
  def test_getDistance(self):
    num = 4
    eq = num**(1/2.0)
    self.assertEqual(eq,2)
    num =2
    eq = 16**(1/float(num))
    self.assertEqual(eq,4)

    nodes = []
    power = math.pow(2,2)
    self.assertEqual(power,4)


    node1 = main.Node([0,0])
    node2 = main.Node([1,0])
    dist = node1.getDistance(node2)
    self.assertEqual(dist, 1, 'pierwszy')
    node3 = main.Node([0,1])
    node4 = main.Node([1,1])
    nodes.append(node1)
    nodes.append(node2)
    nodes.append(node3)
    nodes.append(node4)

    expected = [0,1,1,math.sqrt(2)]
    for i in range(0,len(expected)-1):
      self.assertEqual(nodes[0].getDistance(nodes[i]), expected[i])

