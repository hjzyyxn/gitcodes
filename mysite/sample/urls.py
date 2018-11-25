from django.conf.urls import url
from django.contrib import admin
from sample import views
from django.conf.urls import url,include
urlpatterns = [
    url(r'^index/', views.index, name='index'),
    url(r'^crawl/', views.crawl, name='crawl'),
    url(r'^file/', views.file, name='file'),
    url(r'^input/', views.input, name='input'),
    url(r'^autocrawl/', views.autocrawl, name='autocrawl'),
    url(r'^mainpage/', views.mainpage, name='mainpage'),
]