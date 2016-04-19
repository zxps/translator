Simple language translator!
===================
**Installation**

    $ mvn clean install
    $ chmod u+x ./translate.sh
    $ cp config.properties.dist config.properties

 - Go to https://tech.yandex.ru/keys/get/?service=trnsl 
 - Get free yandex api key. 
 - Then put your yandex api key instead YOUR_YANDEX_API_KEY in config.properties file

If you whant to run from anywhere:

    $ ln -s /your/current/path/translate.sh /usr/local/bin/translate
 

**Run**

    $ ./translate.sh robust
will produce

    надежные
    
