import matplotlib.pyplot as plt
import scipy.stats as stats
import myIos
#
# col =  plt.cm.jet(.3)
# x = [0,1]
# y = [1,0]
# plt.plot(x,y,'o',color =col )
# plt.show()


# data = myIos.read_data('out.csv')
# nodesList = newMain.getListOfNodes(data)

x = stats.norm.rvs(size = 100)
y = stats.uniform.rvs(size = 100)
p =  stats.normaltest(x)
print p
# n = [1]*len(x)
# plt.plot(x,n,'bo')
# plt.plot(y,n,'ro')
# plt.show()
#

# one = mn.Node([1,2,3])
# two = mn.Node([])
# two = one
# one.setValues([2,1,4])
# print one
# print two
# k = 5
# a = ['a','2','f','d','s','c']
# for i in a:
#     print i
# print 1 ** (1/2.0
#             )
# for i in range(k):
#     print i