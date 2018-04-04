# 使用Trietree做的推荐词工具

## Directory
- src/ 为源码目录，已经编译成了jar包，可以直接下载
- index.html 为测试网页
- webTest.py 为http测试服务，接收前端消息，并调用jar包中的方法
- wordcount.txt 为词典与词频信息，词频用于排序，用户可以自定义词典，替换该文件即可

## Requirements

- Python 2.7
- tornado
- jpype
- jdk1.6



## Quick Start 

```bash
git clone https://github.com/yuye2133/SuggestWord.git
cd SuggestWord
python webTest.py 
```
