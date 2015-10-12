# -*- coding: utf-8 -*-

import random
import sys

def read_data(filename, skip_first_line = False, ignore_first_column = False):
    '''
    Loads data from a csv file and returns the corresponding list.
    All data are expected to be floats, except in the first column.
    
    @param filename: csv file name.
    
    @param skip_first_line: if True, the first line is not read.
        Default value: False.
    
    @param ignore_first_column: if True, the first column is ignored.
        Default value: False.
    
    @return: a list of lists, each list being a row in the data file.
        Rows are returned in the same order as in the file.
        They contains floats, except for the 1st element which is a string
        when the first column is not ignored.
    '''
    
    f = open(filename,'r')
    if skip_first_line:
        f.readline()
    
    data = []
    for line in f:
        line = line.split(",")
        line[1:] = [ float(x) for x in line[1:] ]
        if ignore_first_column:
            line = line[1:]
        data.append(line)
    f.close()
    return data

def write_data(data, filename):
    '''
    Writes data in a csv file.

    @param data: a list of lists

    @param filename: the path of the file in which data is written.
        The file is created if necessary; if it exists, it is overwritten.
    '''
    # If you're curious, look at python's module csv instead, which offers
    # more powerful means to write (and read!) csv files.
    f = open(filename, 'w')
    for item in data:
        f.write(','.join([repr(x) for x in item]))
        f.write('\n')
    f.close()

def generate_random_data(nb_objs, nb_attrs, frand = random.gauss):
    '''
    Generates a matrix of random data.
    
    @param frand: the fonction used to generate random values.
        It defaults to random.random.
        Example::

            import random
            generate_random_data(5, 6, lambda: random.gauss(0, 1))
    
    @return: a matrix with nb_objs rows and nb_attrs+1 columns. The 1st
        column is filled with line numbers (integers, from 1 to nb_objs).
    '''
    data = []
    for i in range(nb_objs):
        #line = [i+1]
        #for j in range(numAtt):
        #    line.append(frand())
        mu = abs(random.random())
        sigma  = random.random()
        random.gauss(mu, sigma)
        line = [i+1] + map(lambda x: frand(), range(nb_attrs))
        data.append(line)
    return(data)

def genRandomGaussianGroups(nbNodes, nbGroups, nbAttributes =2):
    data =[]
    for i in range(nbGroups):
        center_x = random.random()
        center_y = random.random()
        sigma = float(random.randrange(8,15))/100
        print 'center_x = ' + str(center_x) + ' center_y = ' + str(center_y) + ' sigma = '+str(sigma)
        for j in range( int(nbNodes/nbGroups)):
            point_x = random.gauss(center_x,sigma)
            poimt_y = random.gauss(center_y,sigma)
            data.append([point_x,poimt_y])
    return data



