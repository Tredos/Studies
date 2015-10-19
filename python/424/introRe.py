__author__ = 'bgorlewi'

import re
r = re.compile(r"[a-rt-z]*s[a-z]*")
for m in r.finditer(" sui Le bon chasseur sachant chasser sait chasser sans son chien"):
    print m.group()
print "---------------------------------------------------------------"
r = re.compile(r"s+")
m = r.sub(r"ch","Le bon chasseur sachant chasser sait chasser sans son chien")
print m
print "---------------------------------------------------------------"
r = re.compile(r"s+([a-z]+)")
m = r.sub(r"ch\1","asa Le bon chasseur sachant chasser sait chasser sans son chien")
print m
print "--------------------------------------------------------------------"
def ecrire_en_hexa ( entree ):
    return hex(int(entree.group()))

r = re.compile(r"[0-9]+")
m = r.sub( ecrire_en_hexa,"toto 123 blabla 456 titi" )
print m