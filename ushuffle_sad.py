from distutils.log import warn as printf
from os.path import dirname
from random import randrange as rand 
from sqlalchemy import Column, Integer, String, create_engine, exc, orm
from sqlalchemy.ext.declarative import declarative_base
from ushuffle_dbU import DBNAME, NAMELEN, randName, FIELDS,
tformat, cformat, setup

DSNs = {
	'mysql': 'mysql://root@localhost/%s' % DBNAME,
	'sqlite': 'sqlite:///:memory:',
}

Base = declarative_base()
class Users(Base):
	__tablename__ = 'users'
	login = Column(String(NAMELEN))
	userid = Column(Integer, primary_key=True)
	projid = Column(Integer)
	def __str__(self):
		return ''.join(map(tformat,
			(self.login, self.userid, self.projid)))

class SQLAlchemyTest(object):