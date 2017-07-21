import httplib, urllib, base64,json

headers = {
    # Request headers
    'Ocp-Apim-Subscription-Key': 'edff9a26c6394e9a97cbedf79ca2cc2b',
}

params = urllib.urlencode({
    # Request parameters
    'expr': "Ti='virtual reality'",
    'model': 'latest',
    'count': '1000',
    'offset': '0',
    'orderby': 'Y:asc',
    'attributes': "Id,Ti,Y,F.FN",
})

try:
    conn = httplib.HTTPSConnection('westus.api.cognitive.microsoft.com')
    conn.request("GET", "/academic/v1.0/evaluate?%s" % params, "{body}", headers)
    response = conn.getresponse()
    data = response.read()
    f=open("quantum.json","w")
    json.dump(data,f,encoding="utf8")
    print(data)
    conn.close()
except Exception as e:
    print("[Errno {0}] {1}".format(e.errno, e.strerror))