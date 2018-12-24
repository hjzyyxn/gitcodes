# -*- coding: utf-8 -*-
from __future__ import unicode_literals

from django.test import TestCase

if name and name[1:-1] == 'changecluster':
    unuseidlist = request.POST.get("unuseidlist", None)
    clustersno = request.POST.get("clustersno", None)
    peoplename = request.GET['peoplename']
    print unuseidlist, clustersno
    finallist, unuselist, numlist = showresult(peoplename)
    name_list = loadhistory()
    return render(request, "index.html", {"data": finallist, "unuse": unuselist, "namels": name_list})

else:
    pagenum = request.POST.get("pagenum", None)
    numofword = request.POST.get("numofword", None)
    type = request.POST.get("type", None)
    if name:
        savehistory(name)
    name_list = loadhistory()
    if type == 'crawl':
        webcrawler(name, pagenum)
        webparse(name)
        finallist, unuselist = showwebpage(name)
        return render(request, "crawl.html", {"data": finallist, "unuse": unuselist, "namels": name_list})
    elif type == 'cluster':
        webcrawler(name, pagenum)
        webparse(name)
        webmanipulate(name, numofword)
        finallist, unuselist = webalgorithm(name)
        return render(request, "index.html", {"data": finallist, "unuse": unuselist, "namels": name_list})
# Create your tests here.
