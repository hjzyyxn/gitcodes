from django.conf.urls import url
from django.contrib import admin
from sample import views
from django.conf.urls import url,include
urlpatterns = [
    url(r'^index/', views.index, name='index'),
    url(r'^crawl/', views.index, name='crawl'),
]