# yada

yada is a web library for Clojure. It is a sibling library to [bidi](http://github.com/juxt/bidi) - whereas bidi is based on _routes as data_, yada is based on _resources as data_.

yada takes data declarations and produces a sophisticated Ring
handler.

It has the following features

* Comprehensive HTTP coverage
* Parameter coercion, automatic Swagger support
* Async foundation based on [manifold](https://github.com/ztellman/manifold)
* Protocol extensibility

The user-manual for the latest beta (1.1.x) release is available at
[https://yada.juxt.pro](https://yada.juxt.pro) and offline (see below).

The user-manual is also available as an e-book or PDF, at
[Leanpub](https://leanpub.com/yada).

## Installation

Add the following dependency to your
`project.clj` file

[![Clojars Project](http://clojars.org/yada/latest-version.svg)](http://clojars.org/yada)
[![Build Status](https://travis-ci.org/juxt/yada.png)](https://travis-ci.org/juxt/yada)

## Dependencies

yada requires the following :-

- a Java JDK/JRE installation, version 8 or above
- Clojure 1.8.0
- Aleph 0.4.1-beta3 or above (provided via a dependency)
- bidi 2.0.0 or above

A project using yada will need to bring in [aleph](https://github.com/ztellman/aleph), and optionally bidi.

``` clojure
[aleph "0.4.1-beta5"]
[bidi "2.0.4"]
[yada "1.1.2"]
```

Support for other web-severs, such as undertow, are on the road-map.

## Running documentation and examples offline

Although yada is a library, if you clone this repo you can run the documentation and examples from the REPL.

```
cd yada
lein repl
```

Once the REPL starts, type in and run the following :-

```
user> (dev)
dev> (go)
```

Now browse to http://localhost:8090.

## Contributing

If you want to help, please join the discussion group [yada-discuss](https://groups.google.com/forum/#!forum/yada-discuss).

Pull requests are welcome. Please run the test suite and check that all
tests pass prior to submission.

```
$ lein test
```

## Acknowledgments

Thanks to the following people for inspiration, contributions,
feedback and suggestions.

* Malcolm Sparks
* Martin Trojer
* Philipp Meier
* David Thomas Hume
* Zach Tellman
* Stijn Opheide
* Frankie Sardo
* Jon Pither
* Håkan Råberg
* Ernestas Lisauskas
* Thomas van der Veen
* Leandro Demartini
* Craig McCraig of the clan McCraig
* Imre Koszo
* Luo Tian
* Joshua Griffith
* Joseph Fahey
* David Smith
* Mike Fikes
* Brian Olsen
* Stanislas Nanchen
* Nicolas Ha
* Eric Fode

Also, see the dependency list. In particular, yada would certainly not
exist without the considerable efforts of those behind the following
libraries.

* Manifold & Aleph - Zach Tellman
* Prismatic Schema - Jason Wolfe (and others)
* Ring-swagger - Tommi Riemann (and others)

## Copyright & License

The MIT License (MIT)

Copyright © 2015-2016 JUXT LTD.

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
