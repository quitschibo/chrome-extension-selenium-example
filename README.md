chrome-extension-selenium-example
=================================
[![Build Status](https://travis-ci.org/quitschibo/chrome-extension-selenium-example.png?branch=master)](https://travis-ci.org/quitschibo/chrome-extension-selenium-example)
[![Coverage Status](https://coveralls.io/repos/quitschibo/chrome-extension-selenium-example/badge.png?branch=master)](https://coveralls.io/r/quitschibo/chrome-extension-selenium-example?branch=master)

### Hey, I wanna check out. What I have to do?

This example is written as Selenium test with remote server. It works best with an https://saucelabs.com/ account. So,
get an account there and add the remote url in the [RemoteDriverConfig](https://github.com/quitschibo/chrome-extension-selenium-example/blob/master/src/main/java/com/manmoe/example/config/RemoteDriverConfig.java#L21).

That's all. You can now run the example with `maven run` and see the test working in your saucelabs account. It will test a small chrome extension for firespotting; you can find the test package [here](https://github.com/quitschibo/chrome-extension-selenium-example/tree/master/src/main/resources).

### Great - but I want to run my own extension.

All right, no problem. You must download/make a *.crx file and add it to the [resources folder](https://github.com/quitschibo/chrome-extension-selenium-example/tree/master/src/main/resources). You can build it with your [chrome browser](http://developer.chrome.com/extensions/packaging.html). After that, change the file path [here](https://github.com/quitschibo/chrome-extension-selenium-example/blob/master/src/main/java/com/manmoe/example/config/RemoteDriverConfig.java#L22).
After that take the [Firespotting Test](https://github.com/quitschibo/chrome-extension-selenium-example/blob/master/src/main/java/com/manmoe/example/test/FirespottingTest.java) and change the [extension name](https://github.com/quitschibo/chrome-extension-selenium-example/blob/master/src/main/java/com/manmoe/example/test/FirespottingTest.java#L16). Take the name from your extension manifest. After that you're good to run.

### Sounds good, now I want to fork this project and build my own tests with it. What about the license?

Just three letters: [MIT](https://github.com/quitschibo/chrome-extension-selenium-example/blob/master/LICENSE). Feel free to do with it, whatever you want.
