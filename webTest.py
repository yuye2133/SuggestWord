#!/usr/bin/python
# -*- coding:utf-8 -*-
import sys
reload(sys)
sys.setdefaultencoding("utf-8")
import jpype
import tornado.web
import tornado.ioloop

class IndexHandler(tornado.web.RequestHandler):

    default_jvm_path = jpype.get_default_jvm_path()

    if not jpype.isJVMStarted():
        jpype.startJVM(default_jvm_path, "-Djava.class.path=SugestWord-1.0-SNAPSHOT.jar")
    SuggestWord = jpype.JClass("xxx.alphax.suggest.SuggestWord")
    suggestWord = SuggestWord()
    print 'suggest word load is ok...'

    def get(self):
        self.render('index.html')

    def post(self):
        searchKey = self.get_argument("searchKey")
        keyWords = self.suggestWord.getNextSuggestWord(searchKey)
        if keyWords:
            keyWords = ';'.join(list(keyWords))
        else:
            keyWords = ''
        self.write(keyWords)


if __name__ == "__main__":
    app = tornado.web.Application([(r'/', IndexHandler)])
    app.listen(9000)
    tornado.ioloop.IOLoop.current().start()
