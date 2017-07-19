import httplib, urllib, base64

headers = {
    # Request headers
    'Ocp-Apim-Subscription-Key': 'edff9a26c6394e9a97cbedf79ca2cc2b',
}

params = urllib.urlencode({
    # Request parameters
    'expr': "And(Composite(AA.AuN=='whitfield diffie'),Y>2010)",
    'model': 'latest',
    'count': '100',
    'offset': '0',
    'orderby': 'Y:asc',
    'attributes': "Ti,F.FN,AA.AuId",
})

try:
    conn = httplib.HTTPSConnection('westus.api.cognitive.microsoft.com')
    conn.request("GET", "/academic/v1.0/evaluate?%s" % params, "{body}", headers)
    response = conn.getresponse()
    data = response.read()
    print(data)
    conn.close()
except Exception as e:
    print("[Errno {0}] {1}".format(e.errno, e.strerror))