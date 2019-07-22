(ns todo-list.core
  (:require [ring.adapter.jetty     :as jetty]
            [ring.middleware.reload :refer [wrap-reload]]
            [compojure.core         :refer [defroutes GET]]
            [compojure.route        :refer [not-found resources]]
            [ring.handler.dump      :refer [handle-dump]]
            [hiccup.core            :refer :all]
            [hiccup.page            :refer :all]
  )
)

; (defn welcome
; 	"A ring handler to process all the request sent to webapp"
; 	[request] 
; 	(if (= "/" (:uri request))
; 		{:status 200
; 		:body "<p>Hello Clojure 1234567</p>"
; 		:header {}}
; 		{:status 400
; 		 :body "<p>Page not found 1234567"
; 		 :header {}}))

(defn welcome
	"A ring handler to process all the request sent to webapp"

	[request]

	; without Hiccup
	; {:status 200
	;  :body "<h1>Hello Clojure</h1>"
	;  :header {}
	; }

	; With Hiccup
	(html5 [:h1 "Hello Clojure World"]
		   [:p "welcome to the Clojure Woeld "]
	)
)

(defn goodbye
	"Goodbye Handler"
	[request]

	; Without Hiccup
	; {:status 200
	;  :body "<h1>This is the goodbye page</h1>"
	;  :header {}
	; }
	; With Hiccup
	(html5 {:lang "en"}
		   [:head (include-js "myscript.js") (include-css "mystyle.css")]
		   [:body
            [:div [:h1 {:class "info"} "Walking back to happiness"]]
            [:div [:p "Walking back to happiness with you"]]
            [:div [:p "Said, Farewell to loneliness I knew"]]
            [:div [:p "Laid aside foolish pride"]]
            [:div [:p "Learnt the truth from tears I cried"]]
            ]
	)
)

(defn about
	"About Handler"
	[request]
	; without Hiccup
	; {:status 200
	;  :body "<h1>This is the about page</h1>"
	;  :header {}
	; }

	; with Hiccup
	(html5 {:lang "en"}
		   [:body
		   [:div [:h1 "This is the about page"]]
		   ]
	)
)

(defn request-info
	"Rquest-info Handler"
	[request]
	; without hiccup
	; {:status 200
	;  :body (pr-str request)
	;  :header {}
	; }
	; with Hiccup
	(html5 {:lang "en"}
		   [:body (pr-str request)
		   ]
	)
)

(defn hello
	"A simple personalised greeting showing the use of variable path elements"
	[request]
	(let [name (get-in request [:route-params :name])]
		; {:status 200
		;  :body (str "<h1>Hello " name ". I got your name from the web URL</h1>")
		;  :header {}
		; }
		(html5 {:lang "en"}
			   [:body
			   		[:div [:h1 (str "Hello " name ". I got your name from the web URL")]]
			   ]
		)
	)
)

(def operands 
	{"+" + "-" - "*" * ":" /}
)

(defn calculator
 	"A very simple calculator handler"
 	[request]

 	(let [a (Integer. (get-in request [:route-params :a]))
 		  b (Integer. (get-in request [:route-params :b]))
 		  op (get-in request [:route-params :op])
 		  f (get operands op)]

 		(if f
 			; {:status 200
 			;  :body (str "<h1>Calculated result:</h1>"(f a b))
 			;  :header {}
 			; }
 			; {:status 404
 			;  :body "Sorry unknown operators"
 			;  :header {}
 			; }
 			(html5 {:lang "en"}
 				[:body [:h1 (str "Calculated result:" (f a b))]]
 			)
 			(html5 {:lang "en"}
 				[:body [:h1 "Sorry unknown operands"]]
 			)
 		)  
 	)
)

(defroutes app
	(GET "/" [] welcome)
	(GET "/goodbye" [] goodbye)
	(GET "/about" [] about)
	(GET "/request-info" [] handle-dump)
	(GET "/hello/:name" [] hello)
	(GET "/calculator/:a/:op/:b" [] calculator)
	(resources "/")
	(not-found "<h1>Page not found</h1>")
)

(defn -main
	"A very simple web server using Ring and Jetty"
	[port-number]
	(jetty/run-jetty app
		{:port (Integer. port-number)}))


(defn -dev-main
	"A very simple web server using Ring and Jetty"
	[port-number]
	(jetty/run-jetty (wrap-reload #'app)
		{:port (Integer. port-number)}))
