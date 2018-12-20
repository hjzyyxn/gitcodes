# -*- coding: utf-8 -*-
from __future__ import unicode_literals

from django.shortcuts import render
from django.shortcuts import HttpResponse
import os
import sys
import time
import random
import pymysql
import ssl
import json
import urllib2
from bs4 import BeautifulSoup
import re
import nltk
from nltk import FreqDist
from nltk.corpus import stopwords
from nltk.stem.snowball import SnowballStemmer
import numpy as np
import pandas as pd
import codecs
from collections import defaultdict
import datetime
import time
import collections
from nltk.stem.snowball import SnowballStemmer
ssl._create_default_https_context = ssl._create_unverified_context


sys.path.append(os.path.dirname(os.path.dirname(__file__)))
from MagicGoogle import MagicGoogle

name_list = []
# Create your views here.
def index(request):
    #return HttpResponse("hello world!")
    if request.method == "POST":
        file_obj = request.FILES.get('crawlfile', None)
        if file_obj:
            pagenum2 = request.POST.get("pagenum2", None)
            crawllist = handle_uploaded_file(file_obj)
            crawlnameinfile(crawllist, pagenum2)
            for i in crawllist:
                savehistory(i)
        name = request.POST.get("name", None)
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
            webcrawler(name,pagenum)
            webparse(name)
            webmanipulate(name, numofword)
            finallist, unuselist = webalgorithm(name)
            return render(request, "index.html", {"data": finallist, "unuse": unuselist,"namels": name_list})
    if request.method == "GET":

        if not request.GET:
            name_list = loadhistory()
            return render(request, "index.html", {"namels": name_list})

        else:
            name = request.GET['name']
            print name
            if name[1:-1] == 'delete':
                deletehistory()
                name_list = loadhistory()
                return render(request, "index.html", {"namels": name_list})
            elif name[1:-1] == 'deletedata':
                deletesql = request.GET['deletesql']
                peoplename = request.GET['peoplename']
                print ('deletesql',deletesql)
                deletepage(peoplename, deletesql)
                finallist, unuselist, numlist = showresult(peoplename)
                name_list = loadhistory()
                return render(request, "index.html",
                              {"data": finallist, "unuse": unuselist, "namels": name_list, "List": numlist,
                               "peoplename": peoplename, "dict": json.dumps(finallist)})

            else:
                name = name[1:-1]
                print name
                name_list = loadhistory()
                if (table_exists(name) != 1):
                    print "webalgorithm"
                    webmanipulate(name, 200)
                    finallist, unuselist, numlist = webalgorithm(name)
                else:
                    print "showresult"
                    finallist, unuselist, numlist = showresult(name)
                return render(request, "index.html", {"data": finallist, "unuse": unuselist, "namels": name_list, "List": numlist, "peoplename": name, "dict": json.dumps(finallist)})
            name_list = loadhistory()
            return render(request, "index.html", {"namels": name_list})

def input(request):
    #return HttpResponse("hello world!")
    if request.method == "POST":
        name = request.POST.get("name", None)
        pagenum = request.POST.get("pagenum", None)
        numofword = request.POST.get("numofword", None)
        type = request.POST.get("type", None)
        if name:
            savehistory(name)
        name_list = loadhistory()

        webcrawler(name,pagenum)
        webparse(name)
        webmanipulate(name, numofword)
        finallist, unuselist, numlist = webalgorithm(name)
        return render(request, "input.html",
                      {"data": finallist, "unuse": unuselist, "namels": name_list, "List": numlist,
                       "dict": json.dumps(finallist)})
    elif request.method == "GET":

        if not request.GET:
            name_list = loadhistory()
            return render(request, "input.html", {"namels": name_list})

        else:
            name = request.GET['name']
            print name
            if name[1:-1] == 'delete':
                deletehistory()
                name_list = loadhistory()
                return render(request, "input.html", {"namels": name_list})
            else:
                name = name[1:-1]
                print name
                name_list = loadhistory()
                webmanipulate(name, 200)
                finallist, unuselist, numlist = webalgorithm(name)
                return render(request, "index.html",
                              {"data": finallist, "unuse": unuselist, "namels": name_list, "List": numlist,
                               "dict": json.dumps(finallist)})
            name_list = loadhistory()
            return render(request, "input.html", {"namels": name_list})

def file(request):
    #return HttpResponse("hello world!")
    if request.method == "POST":
        file_obj = request.FILES.get('crawlfile', None)
        pagenum2 = request.POST.get("pagenum2", None)
        crawllist = handle_uploaded_file(file_obj)
        crawlnameinfile(crawllist, pagenum2)
        for i in crawllist:
            savehistory(i)
        name_list = loadhistory()
        stringmessage = "爬取成功"
        return render(request, "file.html", {"namels": name_list, "strmessage":stringmessage})
    if request.method == "GET":

        if not request.GET:
            name_list = loadhistory()
            return render(request, "file.html", {"namels": name_list})

        else:
            name = request.GET['name']
            print name
            if name[1:-1] == 'delete':
                deletehistory()
                name_list = loadhistory()
                return render(request, "index.html", {"namels": name_list})
            else:
                name = name[1:-1]
                print name
                name_list = loadhistory()
                webmanipulate(name, 200)
                finallist, unuselist, numlist = webalgorithm(name)
                return render(request, "index.html",
                              {"data": finallist, "unuse": unuselist, "namels": name_list, "List": numlist,
                               "dict": json.dumps(finallist)})
            name_list = loadhistory()
            return render(request, "file.html", {"namels": name_list})

def crawl(request):
    #return HttpResponse("hello world!")
    if request.method == "POST":
        name = request.POST.get("name", None)
        pagenum = request.POST.get("pagenum", None)
        numofword = request.POST.get("numofword", None)
        type = request.POST.get("type", None)
        if name:
            savehistory(name)
        name_list = loadhistory()

        webcrawler(name, pagenum)
        webparse(name)
        finallist, unuselist = showwebpage(name)
        return render(request, "crawl.html", {"data": finallist, "unuse": unuselist, "namels": name_list})

    if request.method == "GET":

        if not request.GET:
            name_list = loadhistory()
            return render(request, "crawl.html", {"namels": name_list})

        else:
            name = request.GET['name']
            print name
            if name[1:-1] == 'delete':
                deletehistory()
                name_list = loadhistory()
                return render(request, "crawl.html", {"namels": name_list})
            else:
                name = name[1:-1]
                print name
                name_list = loadhistory()
                webmanipulate(name, 200)
                finallist, unuselist, numlist = webalgorithm(name)
                return render(request, "index.html", {"data": finallist, "unuse": unuselist, "namels": name_list, "List": numlist, "dict": json.dumps(finallist)})
            name_list = loadhistory()
            return render(request, "crawl.html", {"namels": name_list})


def autocrawl(request):
    if request.method == "POST":
        name_list = loadhistory()
        autocrawllist = request.POST.getlist("autocrawl", None)
        autoday = request.POST.get("autoday", None)
        autotimes = request.POST.get("autotimes", None)
        waittime(autoday, autotimes, autocrawllist)
        return render(request, "autocrawl.html", {"namels": name_list})
    if request.method == "GET":

        if not request.GET:
            name_list = loadhistory()
            return render(request, "autocrawl.html", {"namels": name_list})

        else:
            name = request.GET['name']
            print name
            if name[1:-1] == 'delete':
                deletehistory()
                name_list = loadhistory()
                return render(request, "autocrawl.html", {"namels": name_list})
            else:
                name = name[1:-1]
                print name
                name_list = loadhistory()
                webmanipulate(name, 200)
                finallist, unuselist, numlist = webalgorithm(name)
                return render(request, "index.html",
                              {"data": finallist, "unuse": unuselist, "namels": name_list, "List": numlist,
                               "dict": json.dumps(finallist)})
            name_list = loadhistory()
            return render(request, "autocrawl.html", {"namels": name_list})

def mainpage(request):
    #return HttpResponse("hello world!")
    if request.method == "GET":

        if not request.GET:
            name_list = loadhistory()
            return render(request, "mainpage.html", {"namels": name_list})

        else:
            name = request.GET['name']
            print name
            if name[1:-1] == 'delete':
                deletehistory()
                name_list = loadhistory()
                return render(request, "mainpage.html", {"namels": name_list})
            else:
                name = name[1:-1]
                print name
                name_list = loadhistory()
                webmanipulate(name, 200)
                finallist, unuselist, numlist = webalgorithm(name)
                return render(request, "index.html",
                              {"data": finallist, "unuse": unuselist, "namels": name_list, "List": numlist,
                               "dict": json.dumps(finallist)})
            name_list = loadhistory()
            return render(request, "mainpage.html", {"namels": name_list})


def testhtml(request):
    #return HttpResponse("hello world!")
    if request.method == "POST":
        name = request.POST.get("name", None)
        pagenum = request.POST.get("pagenum", None)
        numofword = request.POST.get("numofword", None)
        type = request.POST.get("type", None)
        if name:
            savehistory(name)
        name_list = loadhistory()

        webcrawler(name,pagenum)
        webparse(name)
        webmanipulate(name, numofword)
        finallist, unuselist, numlist = webalgorithm(name)
        return render(request, "htmltest.html",
                      {"data": finallist, "unuse": unuselist, "namels": name_list, "List": numlist,
                       "dict": json.dumps(finallist)})
    elif request.method == "GET":

        if not request.GET:
            name_list = loadhistory()
            return render(request, "htmltest.html", {"namels": name_list})

        else:
            name = request.GET['name']
            print name
            if name[1:-1] == 'delete':
                deletehistory()
                name_list = loadhistory()
                return render(request, "htmltest.html", {"namels": name_list})
            else:
                name = name[1:-1]
                print name
                name_list = loadhistory()
                webmanipulate(name, 200)
                finallist, unuselist, numlist = webalgorithm(name)
                return render(request, "index.html",
                              {"data": finallist, "unuse": unuselist, "namels": name_list, "List": numlist,
                               "dict": json.dumps(finallist)})
            name_list = loadhistory()
            return render(request, "htmltest.html", {"namels": name_list})

def webcrawler(name,pagenum):
# Or MagicGoogle()
    mg = MagicGoogle()
    excludeurl = ['youtube', 'facebook', 'twitter', 'amazon', 'linkedin', 'wikipedia','imdb','vimeo','pdf']

# The first page of results
# result = mg.search_page(query='python')
# print(result)
#
# time.sleep(random.randint(1, 5))
    tablename = name.replace(" ","").lower()
    conn = pymysql.connect(host = '127.0.0.1', port = 3306, user = 'root',password="123",
                        db = 'mysql', charset="utf8")
    cur = conn.cursor()
    cur.execute("USE data")
    cur.execute("drop table if exists " + tablename +"url")
    sql_create = "create table " + tablename +"url" +" (id int not null AUTO_INCREMENT, url varchar(300), PRIMARY KEY (id)) engine = innodb charset = utf8 "
    cur.execute(sql_create)




    time.sleep(random.randint(1, 5))

    i = 0
    try:
        while i < int(pagenum):
            for url in mg.search_url(query = name, start = i):
            # pprint.pprint(url)
                exclude = False
                for ex in excludeurl:
                    if ex in url:
                        exclude = True
                if exclude == False:
                    cur.execute("insert into " + name.replace(" ","") +"url" + " (url) values (\"%s\")", (url))
                    cur.connection.commit()
            i = i + 10
            time.sleep(random.randint(1, 5))

    finally:
        cur.close()
        conn.close()


def webmanipulate(name, numofword):

    tablename = name.replace(" ","").lower()
    conn = pymysql.connect(host = '127.0.0.1', port = 3306, user = 'root',password="123",
                        db = 'mysql', charset="utf8")
    cur = conn.cursor()
    cur.execute("USE data")
    sql = "select * from " + tablename + "raw"

    try:
        cur.execute(sql)
        results = cur.fetchall()
    except:
        print "Error: unable to fetch data"


    namelist = name.split('_')
    namelist.extend(name.upper().split('_'))


    stopwords = nltk.corpus.stopwords.words('english')



    stemmer = SnowballStemmer("english")

    def tokenize_and_stem(text):
        # first tokenize by sentence, then by word to ensure that punctuation is caught as it's own token
        tokens = [word for sent in nltk.sent_tokenize(text) for word in nltk.word_tokenize(sent)]
        filtered_tokens = []
        # filter out any tokens not containing letters (e.g., numeric tokens, raw punctuation)
        for token in tokens:
            if re.search('[a-zA-Z]', token) and token not in stopwords:
                filtered_tokens.append(token)
        stems = [stemmer.stem(t) for t in filtered_tokens]
        return stems



    cur.execute("drop table if exists " + tablename + "word")

    sql_create = "create table " + tablename + "word" +"(id int not null, url varchar(300), wordset varchar(20000), PRIMARY KEY (id)) engine = innodb charset = utf8 "
    cur.execute(sql_create)

    try:
        for row in results:
            result = "".join(row[2])

            for ele in namelist:
                result = result.replace(ele, "")
            allwords_stemmed = tokenize_and_stem(result)
            if len(allwords_stemmed) > int(numofword):
                cur.execute("insert into " + tablename + "word" + "(id, url, wordset) values (\"%s\",\"%s\", \"%s\")", (row[0], row[1], allwords_stemmed))
                cur.connection.commit()

    finally:
        cur.close()
        conn.close()


def webparse(name):
    tablename = name.replace(" ","").lower()
    conn = pymysql.connect(host = '127.0.0.1', port = 3306, user = 'root',password="123",
                      db = 'mysql', charset="utf8")
    cur = conn.cursor()
    cur.execute("USE data")

    sql = "select * from " + tablename + "url"
    try:
        cur.execute(sql)
        results = cur.fetchall()
    except:
        print "Error: unable to fetch data"

    cur.execute("drop table if exists " + tablename +"raw")
    sql_create = "create table " + tablename +"raw" +"(id int not null, url varchar(300), para varchar(20000), PRIMARY KEY (id)) engine = innodb charset = utf8 "
    cur.execute(sql_create)



    try:
        for row in results:
            urltemp = row[1]
            print urltemp[1:-1]
            paratemp = []

            try:
                req = urllib2.Request(urltemp[1:-1], headers={'User-Agent': 'Mozilla/5.0'})
                html = urllib2.urlopen(req)
                #html = urlopen(urltemp[1:-1])
                bsObj = BeautifulSoup(html, "lxml")
                paras = bsObj.find_all({'h1', 'h2', 'h3', 'article', 'p', 'blockquote'})
                for para in paras:
                    paratemp.append(para.get_text())
                paras = bsObj.find_all('div', class_='body')
                for para in paras:
                    paratemp.append(para.get_text())
                paras = bsObj.find_all('div', class_='summary')
                for para in paras:
                    paratemp.append(para.get_text())
                cur.execute("insert into " + tablename +"raw" + "(id, url, para) values (\"%s\", \"%s\", \"%s\")", (row[0], urltemp, paratemp))
                cur.connection.commit()
            except:
                continue


    finally:
        cur.close()
        conn.close()


def webalgorithm(name):
    tablename = name.replace(' ', '').lower()

    conn = pymysql.connect(host = '127.0.0.1', port = 3306, user = 'root',password="123",
                        db = 'mysql', charset="utf8")

    cur = conn.cursor()
    cur.execute("USE data")
    sql = "select * from " + tablename + "raw"

    try:
        cur.execute(sql)
        results = cur.fetchall()
    except:
        print "Error: unable to fetch data"


    namelist = name.split(' ')
    namelist.extend(name.upper().split('_'))

    stopwords = nltk.corpus.stopwords.words('english')

    stemmer = SnowballStemmer("english")

    def tokenize_and_stem(text):
        # first tokenize by sentence, then by word to ensure that punctuation is caught as it's own token
        tokens = [word for sent in nltk.sent_tokenize(text) for word in nltk.word_tokenize(sent)]
        filtered_tokens = []
        # filter out any tokens not containing letters (e.g., numeric tokens, raw punctuation)
        for token in tokens:
            if re.search('[a-zA-Z]', token) and token not in stopwords:
                filtered_tokens.append(token)
        stems = [stemmer.stem(t) for t in filtered_tokens]
        return stems


    sql = "select id from " + tablename + "word"         #find pages that have enough words

    try:
        cur.execute(sql)
        ranks = cur.fetchall()
    except:
        print "Error: unable to fetch data"

    nums = []
    for num in ranks:
        nums.append(num[0])

    synopses = []
    temp = []
    try:
        for row in results:
            if row[0] in nums:
                result = "".join(row[2])
                temp.append(row[0])
                for ele in namelist:
                    result = result.replace(ele, "")
                synopses.append(result)

    finally:
        cur.close()

    from sklearn.feature_extraction.text import TfidfVectorizer


    tfidf_vectorizer = TfidfVectorizer(max_df=0.85, max_features=200000,
                                    min_df=0.2, stop_words='english',
                                    use_idf=True, tokenizer=tokenize_and_stem, ngram_range=(1,3))

    tfidf_matrix = tfidf_vectorizer.fit_transform(synopses)

    terms = tfidf_vectorizer.get_feature_names()

    from sklearn.metrics.pairwise import cosine_similarity
    dist = 1 - cosine_similarity(tfidf_matrix)


    def getCount(arr):
        dic={}
        for item in arr:
            if item in dic.keys():
                dic[item]+=1
            else:
                dic[item]=1
        return dic
    #ap

    from sklearn.cluster import AffinityPropagation


    def affinty_propagation(feature_matrix):
        sim = feature_matrix * feature_matrix.T
        sim = sim.todense()
        ap = AffinityPropagation()
        ap.fit(sim)
        clusters = ap.labels_
        return ap, clusters, sim

    ap_obj, clusters, sim = affinty_propagation(feature_matrix=tfidf_matrix)
    a= ap_obj.affinity_matrix_

    #for c in clusters:
    #    print c

    from collections import Counter
    # get the total number of movies per cluster
    c = Counter(clusters)

    reac = clusters
    print reac
    from sklearn import metrics
    from sklearn.metrics import pairwise_distances

    silarr=[]
    silre = metrics.silhouette_samples(sim, reac, metric='euclidean')

    for i in silre:
        silarr.append(i)

    s = set()
    for i in reac:
        s.add(i)

    def silhouetterate(reac, s, d):
        sirate = []
        for j in s:
            e = 0.0
            f = 0
            for i in range(0, len(reac)):
                if reac[i] == j:
                    e += d[i]
                    f=f+1
            sirate.append(e/f)
        return sirate

    sirate = silhouetterate(reac, s, silarr)

    total_clusters = len(c)

    def showCluster(src, goal, treedict):
        d = dict()
        for i in range(0, len(src)):
            for j in treedict:
                if str(src[i]) in treedict[j]:
                    d.setdefault(goal[i], []).append(int(j))
        return d

    nums = sorted(nums)

    pure = []
    unpure = []
    for i in range(0, len(sirate)):
        if sirate[i] > 0.45:
            pure.append(i)
        else:
            unpure.append(i)


    print ('pure', pure)

    purepage = []
    unpurepage = []
    for i in range(0 ,len(nums)):
        if reac[i] in pure:
            purepage.append(nums[i])
        else:
            unpurepage.append(nums[i])

    print ('purepage', purepage)
    print ('unpurepage', unpurepage)

    synopsespure = []
    synopsesunpure = []

    try:
        for row in results:
            if row[0] in unpurepage:
                result = "".join(row[1])
                for ele in namelist:
                    result = result.replace(ele, "")
                synopsesunpure.append(result)

    finally:
        cur.close()
        conn.close()


    from sklearn.metrics.pairwise import cosine_similarity
    dist = 1 - cosine_similarity(tfidf_matrix)

    #tfidf_matrix = tfidf_vectorizer.fit_transform(synopsespure)

    #ward
    from scipy.cluster.hierarchy import ward, average, cut_tree, dendrogram, set_link_color_palette

    tfidf_vectorizerup = TfidfVectorizer(max_df=0.9, max_features=200000,
                                    min_df=0.1, stop_words='english',
                                    use_idf=True, tokenizer=tokenize_and_stem, ngram_range=(1,3))

    tfidf_matrixup = tfidf_vectorizerup.fit_transform(synopsesunpure)
    distup = 1 - cosine_similarity(tfidf_matrixup)
    linkage_matrixup = ward(distup) #define the linkage_matrix using ward clustering pre-computed distances

    cutreeup = cut_tree(linkage_matrixup, n_clusters=len(unpure))
    unpurere = []
    for i in range(0, len(cutreeup)):
        unpurere.append(cutreeup[i][0])
    print unpurere


    def clusterResult(cluster, pages, type, offset):
        d = dict()
        for i in range(0, len(cluster)):
            tarr = []
            for j in range(0, len(type)):
                if type[j] == cluster[i]:
                    tarr.append(pages[j])
            d[i + offset] = tarr
        return d

    a = clusterResult(pure, nums, reac, 0)

    b = clusterResult(list(set(unpurere)), unpurepage, unpurere, len(pure))
    clusterresult = dict()
    tempresult = dict(a.items() + b.items())
    print tempresult
    for i in tempresult:
        if len(tempresult[i]) != 1:
            clusterresult[i] = tempresult[i]


    print ('clusterresult', clusterresult)
    saveclusterresult(tablename, clusterresult)

    conn = pymysql.connect(host = '127.0.0.1', port = 3306, user = 'root',password="123",
                        db = 'mysql', charset="utf8")

    cur = conn.cursor()
    cur.execute("USE data")
    sql = "select * from " + tablename + "raw"

    try:
        cur.execute(sql)
        wordresults = cur.fetchall()
    except:
        print "Error: unable to fetch data"

    def tokenize(text):
        # first tokenize by sentence, then by word to ensure that punctuation is caught as it's own token
        tokens = [word for sent in nltk.sent_tokenize(text) for word in nltk.word_tokenize(sent)]
        filtered_tokens = []
        # filter out any tokens not containing letters (e.g., numeric tokens, raw punctuation)
        for token in tokens:
            if re.search('[a-zA-Z]', token) and token not in stopwords:
                filtered_tokens.append(token)
        return filtered_tokens

    t = []
    addressdict = []
    clusterstring = []
    unuselist = []
    try:
        for i in clusterresult:
            words = ""
            temp = []

            for row in wordresults:
                if row[0] in clusterresult[i]:
                    temp2 = dict()
                    word = row[2]
                    for ele in namelist:
                        word = word.replace(ele, "")
                    words += word
                    temp2['netaddressid'] = row[0]
                    temp2['netaddressip'] = row[1]
                    temp.append(temp2)
            clusterstring.append(words)
            addressdict.append(temp)
            t.append(tokenize(words))

        for row in wordresults:
            if row[0] not in nums:
                unuselist.append(row[1])

    finally:
        cur.close()
        conn.close()


    tags = set(['NN', 'NNS', 'NNP', 'NNPS', 'VB', 'VBD', 'VBG', 'VBP', 'VNZ', 'VBN'])
    tag = set(['PRP','PRP$','RB','VBZ','RBR','RBS','WDT','WP','WP$','WRB', 'VB', 'VBD', 'VBG', 'VBP'])

    finalwordresult = []


    for i in range(0, len(t)):
        pos_tags = nltk.pos_tag(t[i][:8500])
        ret = []
        gabwords = ['\'s', 'http', 'Inc.', 'url', 'i', 'year', 'URL', 'Mr','class=','/div']
        for word, pos in pos_tags:
            if (pos in tags and word not in gabwords):
                ret.append(word)
        temp = collections.Counter(ret).most_common(10)
        finalwordresult.append(temp)



    gabner = ['ORGANIZATION', 'LOCATION', 'PERSON']
    import ner
    tagger=ner.SocketNER(host='localhost',port=8080)
    nertype = []
    for i in clusterstring:
        ners=tagger.get_entities(i)
        d = dict()
        for j in ners:
            if j in gabner:
                if j == 'PERSON':
                    temp = []
                    for i in ners[j]:
                        if i.replace('Mr','') != '':
                            temp.append(i.replace('Mr',''))
                    d[j] = collections.Counter(temp).most_common(7)
                else:
                    d[j] = collections.Counter(ners[j]).most_common(7)
        nertype.append(d)
        print nertype

    finallist = []
    numlist=[]
    for i in range(0, len(clusterresult)):
        temp = dict()
        temp['no'] = i + 1
        temp['netaddress'] = addressdict[i]
        temp['words'] = finalwordresult[i]
        temp['ner'] = nertype[i]
        finallist.append(temp)
        numlist.append(len(clusterresult[i]))

    print finallist
    return finallist, unuselist, numlist


def showresult(name):
    tablename = name.replace(' ', '').lower()
    namelist = name.split(' ')
    namelist.extend(name.upper().split('_'))
    conn = pymysql.connect(host = '127.0.0.1', port = 3306, user = 'root',password="123",
                        db = 'mysql', charset="utf8")

    cur = conn.cursor()
    cur.execute("USE data")

    sql = "select id from " + tablename + "word"         #find pages that have enough words

    try:
        cur.execute(sql)
        ranks = cur.fetchall()
    except:
        print "Error: unable to fetch data"

    nums = []
    for num in ranks:
        nums.append(num[0])

    clusterresult = loadclusterresult(tablename)

    print ('clusterresult', clusterresult)

    conn = pymysql.connect(host = '127.0.0.1', port = 3306, user = 'root',password="123",
                        db = 'mysql', charset="utf8")

    cur = conn.cursor()
    cur.execute("USE data")
    sql = "select * from " + tablename + "raw"

    try:
        cur.execute(sql)
        wordresults = cur.fetchall()
    except:
        print "Error: unable to fetch data"

    stopwords = nltk.corpus.stopwords.words('english')
    def tokenize(text):
        # first tokenize by sentence, then by word to ensure that punctuation is caught as it's own token
        tokens = [word for sent in nltk.sent_tokenize(text) for word in nltk.word_tokenize(sent)]
        filtered_tokens = []
        # filter out any tokens not containing letters (e.g., numeric tokens, raw punctuation)
        for token in tokens:
            if re.search('[a-zA-Z]', token) and token not in stopwords:
                filtered_tokens.append(token)
        return filtered_tokens

    t = []
    addressdict = []
    clusterstring = []
    unuselist = []
    try:
        for i in clusterresult:
            words = ""
            temp = []

            for row in wordresults:
                if row[0] in clusterresult[i]:
                    temp2 = dict()
                    word = row[2]
                    for ele in namelist:
                        word = word.replace(ele, "")
                    words += word
                    temp2['netaddressid'] = row[0]
                    temp2['netaddressip'] = row[1]
                    temp.append(temp2)
            clusterstring.append(words)
            addressdict.append(temp)
            t.append(tokenize(words))

        for row in wordresults:
            if row[0] not in nums:
                unuselist.append(row[1])

    finally:
        cur.close()
        conn.close()

    print addressdict
    tags = set(['NN', 'NNS', 'NNP', 'NNPS', 'VB', 'VBD', 'VBG', 'VBP', 'VNZ', 'VBN'])
    tag = set(['PRP','PRP$','RB','VBZ','RBR','RBS','WDT','WP','WP$','WRB', 'VB', 'VBD', 'VBG', 'VBP'])

    finalwordresult = []


    for i in range(0, len(t)):
        pos_tags = nltk.pos_tag(t[i][:8500])
        ret = []
        gabwords = ['\'s', 'http', 'Inc.', 'url', 'i', 'year', 'URL', 'Mr','class=','/div']
        for word, pos in pos_tags:
            if (pos in tags and word not in gabwords):
                ret.append(word)
        temp = collections.Counter(ret).most_common(10)
        finalwordresult.append(temp)



    gabner = ['ORGANIZATION', 'LOCATION', 'PERSON']
    import ner
    tagger=ner.SocketNER(host='localhost',port=8080)
    nertype = []
    for i in clusterstring:
        ners=tagger.get_entities(i)
        d = dict()
        for j in ners:
            if j in gabner:
                if j == 'PERSON':
                    temp = []
                    for i in ners[j]:
                        if i.replace('Mr','') != '':
                            temp.append(i.replace('Mr',''))
                    d[j] = collections.Counter(temp).most_common(7)
                else:
                    d[j] = collections.Counter(ners[j]).most_common(7)
        nertype.append(d)

    finallist = []
    numlist=[]
    for i in range(0, len(clusterresult)):
        temp = dict()
        temp['no'] = i + 1
        temp['netaddress'] = addressdict[i]
        temp['words'] = finalwordresult[i]
        temp['ner'] = nertype[i]
        finallist.append(temp)
        numlist.append(len(clusterresult[i]))


    return finallist, unuselist, numlist

def loadclusterresult(tablename):
    conn = pymysql.connect(host='127.0.0.1', port=3306, user='root', password="123",
                           db='mysql', charset="utf8")

    cur = conn.cursor()
    cur.execute("USE data")
    clusterresult = dict()
    sql = "select * from " + tablename + "result"
    try:
        cur.execute(sql)
        pageresult = cur.fetchall()
    except:
        print "Error: unable to fetch data"
    try:
        for row in pageresult:
            if row[0] in clusterresult:
                clusterresult[row[0]].append(row[1])
            else:
                clusterresult[row[0]] = [row[1]]
        return clusterresult
    finally:
        cur.close()
        conn.close()

def showwebpage(name):
    tablename = name.replace(' ', '').lower()

    conn = pymysql.connect(host='127.0.0.1', port=3306, user='root', password="123",
                           db='mysql', charset="utf8")

    cur = conn.cursor()
    cur.execute("USE data")
    sql = "select * from " + tablename + "raw"

    try:
        cur.execute(sql)
        results = cur.fetchall()
    except:
        print "Error: unable to fetch data"

    def tokenize(text):
        # first tokenize by sentence, then by word to ensure that punctuation is caught as it's own token
        tokens = [word for sent in nltk.sent_tokenize(text) for word in nltk.word_tokenize(sent)]
        filtered_tokens = []
        # filter out any tokens not containing letters (e.g., numeric tokens, raw punctuation)
        for token in tokens:
            if re.search('[a-zA-Z]', token) and token not in stopwords:
                filtered_tokens.append(token)
        return filtered_tokens
    namelist = name.split(' ')
    namelist.extend(name.upper().split('_'))
    stopwords = nltk.corpus.stopwords.words('english')
    stringdata = []
    addresslist = []
    t = []
    unuselist = []
    try:
        for row in results:
            words = row[2]
            for ele in namelist:
                words = words.replace(ele, "")
            stringdata.append(words)
            addresslist.append(row[1])
            t.append(tokenize(words))

    finally:
        cur.close()
        conn.close()

    tags = set(['NN', 'NNS', 'NNP', 'NNPS', 'VB', 'VBD', 'VBG', 'VBP', 'VNZ', 'VBN'])
    tag = set(['PRP', 'PRP$', 'RB', 'VBZ', 'RBR', 'RBS', 'WDT', 'WP', 'WP$', 'WRB', 'VB', 'VBD', 'VBG', 'VBP'])

    finalwordresult = []

    for i in range(0, len(t)):
        pos_tags = nltk.pos_tag(t[i][:8500])
        ret = []
        gabwords = ['\'s', 'http', 'Inc.', 'url', 'i', 'year', 'URL', 'Mr','class=','/div']
        for word, pos in pos_tags:
            if (pos in tags and word not in gabwords):
                ret.append(word)
        temp = collections.Counter(ret).most_common(10)
        finalwordresult.append(temp)

    gabner = ['ORGANIZATION', 'LOCATION', 'PERSON']
    import ner
    tagger = ner.SocketNER(host='localhost', port=8080)
    nertype = []
    for i in stringdata:
        ners = tagger.get_entities(i)
        d = dict()
        for j in ners:
            if j in gabner:
                if j == 'PERSON':
                    temp = []
                    for i in ners[j]:
                        if i.replace('Mr', '') != '':
                            temp.append(i.replace('Mr', ''))
                    d[j] = collections.Counter(temp).most_common(6)
                else:
                    d[j] = collections.Counter(ners[j]).most_common(6)
                    print d[j]
        nertype.append(d)

    finallist = []
    for i in range(0, len(stringdata)):
        temp = dict()
        temp['no'] = i + 1
        temp['netaddress'] = addresslist[i]
        temp['words'] = finalwordresult[i]
        temp['ner'] = nertype[i]
        finallist.append(temp)

    return finallist, unuselist


def savehistory(name):
    conn = pymysql.connect(host='127.0.0.1', port=3306, user='root', password="123",
                           db='mysql', charset="utf8")
    cur = conn.cursor()
    cur.execute("USE data")


    try:
        cur.execute("insert into historylist" + " (name) values (\"%s\")", (name))
        cur.connection.commit()

    finally:
        cur.close()
        conn.close()

def loadhistory():
    conn = pymysql.connect(host='127.0.0.1', port=3306, user='root', password="123",
                           db='mysql', charset="utf8")
    cur = conn.cursor()
    cur.execute("USE data")
    cur.execute("create table if not exists historylist(id int not null AUTO_INCREMENT, name varchar(30), numofword int, PRIMARY KEY (id))")

    sql = "select * from historylist"
    try:
        cur.execute(sql)
        results = cur.fetchall()
    except:
        print "Error: unable to fetch data"

    namelist = []
    try:
        for row in results:
            namelist.append(row[1])

    finally:
        cur.close()
        conn.close()

    return namelist

def deletehistory():
    conn = pymysql.connect(host='127.0.0.1', port=3306, user='root', password="123",
                           db='mysql', charset="utf8")
    cur = conn.cursor()
    cur.execute("USE data")
    cur.execute("drop table if exists historylist")
    cur.execute("create table if not exists historylist(id int not null AUTO_INCREMENT, name varchar(30), numofword int, PRIMARY KEY (id))")

    cur.close()
    conn.close()


def handle_uploaded_file(f):
    file_name = ""
    tempstring=""
    try:
        path = "static/temp" + time.strftime('/%Y/%m/%d/%H/%M/%S/')
        if not os.path.exists(path):
            os.makedirs(path)
            file_name = path + f.name
            destination = open(file_name, 'wb+')
            for chunk in f.chunks():
                tempstring += chunk
                destination.write(chunk)
            destination.close()
            return tempstring.split(',')
    except Exception, e:
        print e

def crawlnameinfile(list, pagenum):
    for i in list:
        webcrawler(i, pagenum)
        webparse(i)




def waittime(day, times, list):
    temp = 0
    while True:
        while temp < times:
            now = datetime.datetime.now()
            if now.hour == 1 and now.minute == 0:
                for i in list:
                    webcrawler(i[1:-1], 150)
                    webparse(i)
                time.sleep(86400 * day)
                temp = temp + 1
            time.sleep(20)


def saveclusterresult(tablename, clusterresult):
    conn = pymysql.connect(host='127.0.0.1', port=3306, user='root', password="123",
                           db='mysql', charset="utf8")
    cur = conn.cursor()
    cur.execute("USE data")
    cur.execute("drop table if exists " + tablename + "result")
    sql_create = "create table " + tablename + "result" + " (id int not null, pageno int, PRIMARY KEY (pageno)) engine = innodb charset = utf8 "
    cur.execute(sql_create)

    try:
        for i in clusterresult:
            for j in clusterresult[i]:
                cur.execute("insert into " + tablename + "result" + "(id, pageno) values (\"%s\",\"%s\")",
                            (i, j))
                cur.connection.commit()


    finally:
        cur.close()
        conn.close()


def table_exists(name):        #这个函数用来判断表是否存在
    tablename = name.replace(' ', '').lower()
    tablename += "result"
    conn = pymysql.connect(host='127.0.0.1', port=3306, user='root', password="123",
                           db='mysql', charset="utf8")
    sql = "show tables;"
    cur = conn.cursor()
    cur.execute("USE data")
    cur.execute(sql)
    tables = [cur.fetchall()]
    table_list = re.findall('(\'.*?\')',str(tables))
    table_list = [re.sub("'",'',each) for each in table_list]
    print table_list
    if tablename in table_list:
        return 1        #存在返回1
    else:
        return 0        #不存在返回0

def deletepage(name,deletesql):
    tablename = name.replace(' ', '').lower()
    tablename += "result"
    conn = pymysql.connect(host='127.0.0.1', port=3306, user='root', password="123",
                           db='mysql', charset="utf8")
    cur = conn.cursor()
    cur.execute("USE data")
    num = filter(str.isdigit, deletesql.encode("utf-8"))
    print int(num) == 2
    try:
        sql= "delete from " + tablename + " where pageno = " + num
        cur.execute(sql)
        cur.connection.commit()
    finally:
        cur.close()
        conn.close()