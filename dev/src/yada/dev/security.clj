;; Copyright © 2015, JUXT LTD.

(ns yada.dev.security
  (:require
   [bidi.bidi :refer [RouteProvider tag]]
   [buddy.sign.jws :as jws]
   [clj-time.core :as time]
   [clojure.tools.logging :refer :all]
   [com.stuartsierra.component :refer [Lifecycle using]]
   [modular.component.co-dependency.schema :refer [co-dep]]
   [hiccup.core :refer (html)]
   [modular.bidi :refer (path-for)]
   [modular.bidi :refer (path-for)]
   [modular.component.co-dependency :refer (co-using)]
   [schema.core :as s]
   [yada.security :refer [verify-with-scheme]]
   [yada.yada :as yada :refer [yada resource as-resource]]
   [ring.middleware.cookies :refer [cookies-request cookies-response]])
  (:import [modular.bidi Router]))

(defn hello []
  (yada "Hello World!\n"))

(defn- login-form-html [fields]
  (html
   [:div
    [:style "* {margin:2px;padding:2px}"]
    [:h1 "Login form"]
    [:form {:method :post}
     (for [{:keys [label type name]} fields]
       [:div
        [:label {:for name} label]
        [:input {:type type :name name}]])
     [:div [:input {:type :submit :name :submit :value "Login"}]]]]))

(defn- login-form-parameters [fields]
  {:form
   (into {:submit String}
         (for [f fields] [(keyword (:name f)) String]))})

;; TODO: cookie expiry not seen in Chrome Network/Cookies Expires
;; column, investigate!

(defn login [fields *router]
  ;; Here we provide the fields as an argument, once. They serve 2
  ;; purposes: Generating the login form AND declaring the POST
  ;; parameters. This is a good example of cohesion. Instead of
  ;; duplicating field names (thus creating an implicit coupling
  ;; between the login form and form processor), we share them.
  (yada
   (resource
    {:id ::login
     :methods
     {:get
      {:produces "text/html"
       :response (login-form-html fields)}
      
      :post
      {:parameters (login-form-parameters fields)
       :response (fn [ctx]
                   (let [params (get-in ctx [:parameters :form])]
                     (if (= ((juxt :user :password) params)
                            ["scott" "tiger"])
                       (let [expires (time/plus (time/now) (time/minutes 15))
                             jwt (jws/sign {:user "scott"
                                            :roles ["accounts/view"]
                                            :exp expires}
                                           "secret")
                             cookie {:value jwt
                                     :expires expires
                                     :http-only true}]
                         (assoc (:response ctx)
                                :cookies {"session" cookie}
                                :body (html
                                       [:h1 (format "Thanks %s!" (get-in ctx [:parameters :form :user]))]
                                       [:p [:a {:href (path-for @*router ::secret)} "secret"]]
                                       [:p [:a {:href (path-for @*router ::logout)} "logout"]]
                                       )))
                       (assoc (:response ctx)
                              ;; It's possible the user was already logged in, in which case we log them out
                              :cookies {"session" {:value "" :expires 0}}
                              :body (html [:h1 "Login failed"]
                                          [:p [:a {:href (path-for @*router ::login)} "try again"]]
                                          [:p [:a {:href (path-for @*router ::secret)} "secret"]])))))

       :consumes "application/x-www-form-urlencoded"
       :produces "text/html"}}})))

(defmethod verify-with-scheme "Custom"
  [ctx {:keys [verify]}]
  ;; TODO: Badly need cookie support
  (let [auth (some->
              (get-in ctx [:cookies "session"])
              (jws/unsign "secret"))]
    (infof "auth is %s" auth)
    auth
    ))

(defn build-routes [*router]
  (try
    ["/security"
     [["/basic"
       (yada
        (resource
         (merge (into {} (as-resource "SECRET!"))
                {:access-control
                 {:realm "accounts"
                  :scheme "Basic"
                  :verify (fn [[user password]]
                            (when (= [user password] ["scott" "tiger"])
                              {:user "scott"
                               :roles #{"accounts/view"}}))
                  :authorization {:methods {:get true}}}})))]

      ["/cookie"

       {"/login.html"
        (login
         [{:label "User" :type :text :name "user"}
          {:label "Password" :type :password :name "password"}]
         *router)

        "/logout"
        (yada
         (resource
          {:id ::logout
           :methods
           {:get
            {:produces "text/html"
             :response (fn [ctx]
                         (->
                          (assoc (:response ctx)
                                 :cookies {"session" {:value "" :expires 0}}
                                 :body (html
                                        [:h1 "Logged out"]
                                        [:p [:a {:href (path-for @*router ::login)} "login"]]))))}}}))

        "/secret.html"
        (yada
         (resource
          {:id ::secret
           :access-control
           {:realm "accounts"
            :scheme "Custom"
            :verify identity
            :authorization {:methods {:get [:and
                                            "accounts/view"
                                            "accounts/view"]}}}
           :methods {:get "SECRET!"}}))}]]]

    (catch Throwable e
      (errorf e "Getting exception on security example routes")
      ["/security/cookie/secret.html" (yada (str e))]
      )))

(s/defrecord SecurityExamples [*router :- (co-dep Router)]
  RouteProvider
  (routes [_] (build-routes *router)))

(defn new-security-examples [config]
  (-> 
   (map->SecurityExamples {})
   (using [])
   (co-using [:router])))

