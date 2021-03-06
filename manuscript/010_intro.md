# Introduction

yada is a web library written in Clojure that lets you create websites and web APIs.

It is sufficiently quick-and-easy for quick prototype work but scales up when you need it to, to feature-rich secure services that can handle the most demanding workloads, while remaining faithful to the HTTP standards.

## Say 'Hello!' to yada!

Perhaps the best thing about yada is that the basics are easy to learn.

The obligatory Hello World! example is `(handler "Hello World!")`, which responds with a message.

Perhaps you might want to serve a file? That's `(handler (new java.io.File "index.html"))`.

Now you know how to serve a file, what about that directory full of static resources called `public`? That's `(handler (new java.io.File "public"))`.

Maybe you've got some resources on the classpath? `(handler (clojure.java.io/resource "resources/"))`.

What about `(handler nil)`? Without knowing, can you guess what that might do? (That's right, it produces a `404 Not Found` response).

What about a quick dice generator? `(handler #(inc (rand-int 6)))`. Notice we use a function here, rather than a constant value.

How about streaming those dice rolls as 'Server Sent Events'? Put those dice rolls on a channel, and return it with yada.

All these examples demonstrate the use of Clojure types that are converted on-the-fly into yada resources, and you can create your own types too.

## Resources

Most web libraries use functions to describe the functionality of a web resource. However, in yada, resources are described by a data structure. We call this data structure a __resource-model__.

This has some benefits. While functions are opaque, data is open to inspection. Data structures are easy to generate, transform and query - tasks that Clojure is particularly good at.

Here's an example of a __resource-model__ in Clojure:

```clojure
{:properties {…}
 :methods {:get {:response (fn [ctx] "Hello World!")}
           :put {…}
           :brew {…}}
 …
}
```

You create a __resource__ from a __resource-model__ with yada's `resource` function. This resource is a Clojure record (a Java object) that wraps the raw __resource-model__, having validated against a comprehensive schema first. Since records behave like maps, resources and resource-models are approximately the same thing.

## Handlers

There's a lot of things you can do with a resource data model but perhaps the most obvious is to create a request handler from it to create responses from HTTP requests. That's the role of a __handler__.

With yada, we transform a resource into a handler using the `handler` function.

```clojure
(require '[yada.yada :refer [handler resource]])

(handler (resource {…}))
```

A handler can be called as a function, with a single argument representing an HTTP __request__. It returns a value representing the corresponding HTTP response. (If you are familiar with Ring, this is the Ring handler, but not one you have to write yourself!)

## Serving requests

To use yada to create real responses to real HTTP requests, you need to add yada to a web-server, such as Aleph or Immutant. The web server takes care of the networking and messages of HTTP (RFC 7230), while yada focuses on the semantics and content (starting with RFC 7231).

To write real applications, you also need a router that understands URIs, and yada has some features that are enabled when used with bidi, although there is nothing to stop you using yada with other routing libraries.

## Conclusion

That's yada in a nutshell, but to learn more you might want to set up a test environment to try things out and have a play.

The next chapter explains how to do that.
