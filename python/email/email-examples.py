from email.mime.image import MIMEImage
from email.mime.multipart import MIMEMultipart
from email.mime.text import MIMEText
from smtplib import SMTP 

def make_mpa_msg():
	email = MIMEMultipart('alternative')
	text = MIMEText('Hello World!\r\n', 'plain')
	email.attach(text)
	html = MIMEText(
		'<html><body><h4>Hello World!</h4>'
		'</body></html>', 'html')
	email.attach(html)
	return email

def make_img_msg(fn):
	f = open(fn, 'r')
	data = f.read()
	f.close()
	email = MIMEImage(data, name=fn)
	email.add_header('Content-Disposition',
		'attachment; filename="%s"' % fn)
	return email

def sendMsg(fr, to, msg):
	s = SMTP('localhost')
	errs = s.sendmail(fr, to, msg)
	s.quit()

if __name__ == '__main__':
	print 'Sending multipart alternative msg...'
	msg = make_mpa_msg()
	msg['From'] = SENDER
	msg['To'] = ', '.join(RECIPS)
	msg['Subject'] = 'multipart alternative test'
	sendMsg(SENDER, RECIPS, msg.as_string())

	print 'Sending image msg...'
	msg = make_mpa_msg(SOME_IMG_FILE)
	msg['From'] = SENDER
	msg['To'] = ', '.join(RECIPS)
	msg['Subject'] = 'image file test'
	sendMsg(SENDER, RECIPS, msg.as_string())
