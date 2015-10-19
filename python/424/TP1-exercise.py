__author__ = 'bgorlewi'
import re



# r = re.compile(r"^([0-9]+);[^;]*;PAUL;")
# f = open("gen1551.csv",'r')
# for ligne in f:
#     for m in r.finditer(ligne):
#         print m.group(1)+" OK"
# f.close()

# r = re.compile(r"^([0-9]+);[^;]*;PAUL;[^;]*;PLOU")
# f = open("gen1551.csv",'r')
# for ligne in f:
#     for m in r.finditer(ligne):
#         print m.group(1)+" OK"
# f.close()
#

r = re.compile(r"^([0-9]+);[^;]*;PAUL;[^;]*;PLOU(.*)")
f = open("gen1551.csv",'r')
for ligne in f:
    for m in r.finditer(ligne):
        print m.group(1)+" OK"
f.close()

#TODO the rtest from the file
#TODO comments
r = re.compile(r"^([0-9]+);[^;]*;PAUL;(([0-9]{2}/[0-9]{2}/)([0-9]{4}))*;PLOU")



